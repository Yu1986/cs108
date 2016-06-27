package assign4;

public class Transaction {
	
	public enum TRAN_TYPE {
	    IN,
	    OUT
	}
	
	public static final Transaction nullTrans = new Transaction(-1,0,0);
	public static final Transaction invalidTrans = new Transaction(-2,0,0);
	
	public Transaction(int srcAct, int dstAct, int value) {
		init(srcAct, dstAct, value);
	}
	
	public Transaction(String tr) {
		String[] split = tr.split(" ");
		if (split.length != 3) {
			srcAct = -2;
		}
		init(Integer.parseInt(split[0]), 
				Integer.parseInt(split[1]), 
				Integer.parseInt(split[2]));
	}
	
	public Transaction(Transaction tr) {
		this(tr.srcAct, tr.dstAct, tr.value);
	}

	public int getSrcAct() { return srcAct;}
	public int getDstAct() { return dstAct;}
	public int getValue() { return value;}
	
	public boolean isNullTrans() {
		if (srcAct == -1) return true;
		else return false;
	}
	
	public boolean isInvalidTrans() {
		if (srcAct == -2) return true;
		else return false;
	}
	
	protected int srcAct;
	protected int dstAct;
	protected int value;
	
	private void init(int srcAct, int dstAct, int value) {
		this.srcAct = srcAct;
		this.dstAct = dstAct;
		this.value = value;
	}
	
	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder();
		buff.append(String.format("Tran: src=%d, des=%d, value=%d", srcAct, dstAct, value));
		return buff.toString();
	}
}
