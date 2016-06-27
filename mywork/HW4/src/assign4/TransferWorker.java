package assign4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

class TransferWorker extends Thread {
	
	public TransferWorker(int id, ArrayBlockingQueue<Transaction> transQ, CountDownLatch cdLatch, Account[] accounts) {
		this.id = id;
		this.transQ = transQ;
		this.cdLatch = cdLatch;
		this.accounts = accounts;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Transaction tr = transQ.take();
				if (tr.isInvalidTrans()) continue;
				if (tr.isNullTrans()) {
					System.out.printf("Task-%d: finished\n", id);
					cdLatch.countDown();
					break;
				}
				accounts[tr.getDstAct()].transfer(tr, Transaction.TRAN_TYPE.IN);
				accounts[tr.getSrcAct()].transfer(tr, Transaction.TRAN_TYPE.OUT);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		super.run();
	}
	
	private ArrayBlockingQueue<Transaction> transQ;
	private CountDownLatch cdLatch;
	private Account[] accounts;
	private int id;
}