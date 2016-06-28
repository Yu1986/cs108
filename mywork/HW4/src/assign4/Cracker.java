package assign4;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ArrayBlockingQueue;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();	
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	public static byte[] generateHash(byte[] strByte) {
		byte[] result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			result = md.digest(strByte);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static byte[] generateHash(char[] strChar) {
		return generateHash(new String(strChar).getBytes());
	}
	
	public static byte[] generateHash(String str) {
		return generateHash(str.getBytes());
	}
	
	private static void indexInc(int[] index) {
		int carry = 1;
		for (int i=index.length-1; i>0; i--) {
			index[i] += carry;
			if (index[i] >= CHARS.length) {
				index[i] = 0;
			} else {
				carry = 0;
				break;
			}
		}
		index[0] += carry;
	}
	
	public static String crack(String hashStr, int maxStringLen, int dictStart, int dictEnd) {
		
		for (int width=1; width<=maxStringLen; width++) {
			char[] tryStr = new char[width];
			int[] index = new int[width];
			index[0] = dictStart;
			while (index[0] < dictEnd) {
				for (int i=0; i<width; i++) {
					tryStr[i] = CHARS[index[i]];
				}
				String result = hexToString(generateHash(tryStr));
				if (result.compareTo(hashStr) == 0) {
					return new String(tryStr);
				}
				indexInc(index);
			}
		}
		
		return null;
	}
	
	private static class Result {
		String str;
		int threadId;
		
		Result(String str, int id) {
			this.str = str;
			threadId = id;
		}
	}
	
	private static class  CrackWorker extends Thread {
		private int id;
		private int dictStart;
		private int dictEnd;
		private String hashStr;
		private int maxStrLen; 
		private ArrayBlockingQueue<Result> resultQ;
		private boolean isExit;
		public CrackWorker(int id, String hashStr, int maxStrLen, int dictStart, int dictEnd,
				ArrayBlockingQueue<Result> resultQ) {
			this.id = id;
			this.dictStart = dictStart;
			this.dictEnd = dictEnd;
			this.hashStr = hashStr;
			this.maxStrLen = maxStrLen;
			this.resultQ = resultQ;
			isExit = false;
		}
		
		@Override
		public void run() {
			System.out.printf("Task-%d dictStart=%d, dictEnd=%d\n", id, dictStart, dictEnd);
			String result = null;
			for (int width=1; width<=maxStrLen; width++) {
				char[] tryStr = new char[width];
				int[] index = new int[width];
				index[0] = dictStart;
				while (index[0] < dictEnd && !isExit) {
					for (int i=0; i<width; i++) {
						tryStr[i] = CHARS[index[i]];
					}
					String hash = hexToString(generateHash(tryStr));
					if (hash.compareTo(hashStr) == 0) {
						result = new String(tryStr);
						break;
					}
					indexInc(index);
				}
				if (isExit || result != null) break;
			}
			System.out.printf("Task-%d End\n", id);
			if (result != null) {
				try {
					resultQ.put(new Result(result, id));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public void interrupt() {
			isExit = true;
			super.interrupt();
		}
		
		
	}
	
	public static String crackScheduler(String hashStr, int maxStrLen, int threadNum) {
		CrackWorker[] worker = new CrackWorker[threadNum];
		int eachThread;
		int left = 0;
		if (CHARS.length % threadNum == 0) {
			eachThread = CHARS.length / threadNum;
		} else {
			eachThread = CHARS.length / (threadNum-1);
			left = CHARS.length - eachThread * (threadNum-1);
		}
		
		ArrayBlockingQueue<Result> resultQ = new ArrayBlockingQueue<Result>(threadNum);
		int id = 0;
		for (int i=0; i<CHARS.length; i+=eachThread) {
			worker[id] = new CrackWorker(id, hashStr, maxStrLen, i, i+eachThread, resultQ);
			worker[id].start();
			id++;
		}
		if (left != 0) {
			worker[id] = new CrackWorker(id, hashStr, maxStrLen, CHARS.length - left, CHARS.length, resultQ);
			worker[id].start();
		}
		
		// Wait for thread to finish
		try {
			Result result = resultQ.take();
			for (int i=0; i<threadNum; i++) {
				if (i != result.threadId) {
					worker[i].interrupt();
				}
			}
			return result.str;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	public static void main(String[] args) {
		if (args.length == 1) {
			System.out.println(hexToString(generateHash(args[0])));
		} else if (args.length == 3) {
			String result = crackScheduler(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			if (result == null) {
				System.out.printf("Can not crack: %s\n", args[0]);
			} else {
				System.out.printf("%s cracked: %s\n", args[0], result);
			}
		} else {
			System.out.println("Wrong parameters");
			System.out.println("Example usage:");
			System.out.println("        java assign4/Cracker molly");
			System.out.println("        java assign4/Cracker 4181eecbd7a755d19fdf73887c54837cbecf63fd 5 8");
		}
	}
	
	// possible test values:
	// a 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8
	// fm adeb6f2a18fe33af368d91b09587b68e3abcb9a7
	// a! 34800e15707fae815d7c90d49de44aca97e2d759
	// xyz 66b27417d37e024c46526c2f6d358a754fc552f3

}
