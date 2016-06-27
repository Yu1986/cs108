package assign4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Bank {
	
	public static final int WORKQUEUE_CAPACITY = 100;
	public static final int MAX_ACCOUNT_NUM = 20;
	public static final int TASKWORKER_NUM = 4;
	
	public Bank() {
		accounts = new Account[MAX_ACCOUNT_NUM];
		for (int i=0; i<MAX_ACCOUNT_NUM; i++) {
			accounts[i] = new Account(i, 1000);
		}
	}
	
	public void processTrans(String path) {
		
		for (int i=0; i<MAX_ACCOUNT_NUM; i++) {
			accounts[i].reset();
		}
		
		ArrayBlockingQueue<Transaction> transQ = new ArrayBlockingQueue<Transaction>(WORKQUEUE_CAPACITY);
		CountDownLatch cdLatch = new CountDownLatch(TASKWORKER_NUM);
		
		TransferWorker[] worker = new TransferWorker[TASKWORKER_NUM];
		for (int i=0; i<TASKWORKER_NUM; i++) {
			worker[i] = new TransferWorker(i, transQ, cdLatch, accounts);
			worker[i].start();
		}
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			while (line != null) {
				while (transQ.size() >= WORKQUEUE_CAPACITY) {
					
				}
				transQ.put(new Transaction(line));
				line = br.readLine();
			}
			br.close();
			for (int i=0; i<TASKWORKER_NUM; i++) {
				transQ.put(Transaction.nullTrans);
			}
			cdLatch.await();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i=0; i<MAX_ACCOUNT_NUM; i++) {
			System.out.println(accounts[i].toString());
		}
		
		System.out.printf("==%s: End==\n", path);
	}

	protected Account[] accounts;
	
	public static void main(String[] args) {
		Bank bank = new Bank();
		//bank.processTrans("asset/small.txt");
		bank.processTrans("asset/100k.txt");
	}
}
