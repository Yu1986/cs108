package assign4;

import java.util.ArrayList;

public class Account {
	
	private static class TransactionLog extends Transaction {
		
		private TransactionLog(Transaction tr, TRAN_TYPE type) {
			super(tr);
			this.type = type;
		}
		
		@SuppressWarnings("unused")
		protected TRAN_TYPE type;
	}
	
	public Account(int id, int initialFund) {
		this.id = id;
		this.balance = initialFund;
		this.initialFund = initialFund;
		log = new ArrayList<TransactionLog>();
	}
	
	public void reset() {
		balance = initialFund;
		log.clear();
	}
	
	public int getId() { return id; }
	public int getBalance() { return balance; }
	public int getTransNum() { return log.size(); }
	
	public int transfer(Transaction tr, Transaction.TRAN_TYPE type) {
		int ret = -1;
		synchronized(this){
			if (type == Transaction.TRAN_TYPE.OUT && tr.srcAct == id) {
				balance -= tr.value;
				log.add(new TransactionLog(tr, type));
				ret = 0;
			} else if (type == Transaction.TRAN_TYPE.IN && tr.dstAct == id) {
				balance += tr.value;
				log.add(new TransactionLog(tr, type));
				ret = 0;
			}
		}
		//System.out.printf("%s, %s\n", tr.toString(), this.toString());
		
		return ret;
	}
	
	protected int id;
	protected int balance;
	protected int initialFund;
	protected ArrayList<TransactionLog> log; //number of transaction
	
	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder();
		buff.append(String.format("Account: id=%d, balance=%d, transNum=%d", id, balance, log.size()));
		return buff.toString();
	}
}
