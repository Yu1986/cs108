package block;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Control {
	public Control(GameView v) {
		playground = new Playground(12, 24);
		curBlock = new Block();
		view = v;
		v.addKeyListener(ka);
	}
	
	public void newGame() {
		playground.clear();
		newBlock();
		timer = new Timer();
        timer.schedule(timeTask,
                       500,        //initial delay
                       500);  //subsequent rate
                       
	}
	
	private void stopGame() {
		timer.cancel();
	}
	
	private void newBlock() {
		curBlock.resetWithRandomType();
		//curBlock.resetWithType(2);
		blockPosX = playground.getWidth()/2 - 2;
		blockPosY = playground.getHeight()-1;
		view.drawBlock(curBlock.getBmp(), blockPosX, blockPosY);
		view.repaint();
	}
	
	private void rotation() {
		if (playground.isPositionOk(curBlock.getNextPostureBmp(), 
				blockPosX, blockPosY)) {
			view.clearBlock(curBlock.getBmp(), blockPosX, blockPosY);
			view.drawBlock(curBlock.getNextPostureBmp(), blockPosX, blockPosY);
			curBlock.rotate();
			view.repaint();
		}
	}
	
	private void movePos(int x, int y) {
		if (x != 0 || y != 0) {
			if (playground.isPositionOk(curBlock.getBmp(), 
					blockPosX+x, blockPosY+y)) {
				blockPosX = blockPosX+x;
				blockPosY = blockPosY+y;
				view.clearBlock(curBlock.getBmp(), blockPosX-x, blockPosY-y);
				view.drawBlock(curBlock.getBmp(), blockPosX, blockPosY);
				view.repaint();
			}
		}
	}
	
	private void moveToNextStep() {
		if (playground.isHitGround(curBlock.getBmp(), blockPosX, blockPosY)) {
			playground.addBlock(curBlock.getBmp(), blockPosX, blockPosY);
			int completeLineNum = playground.getCompleteLineNum(null);
			if (completeLineNum != 0) {
				//view.drawErazeAnimation(completeLine);
				playground.eraseCompleteLine(completeLineNum);
				view.drawPlayGround(playground);
				view.repaint();
			}
			if (playground.isReachTop()) {
				view.drawResult();
				view.repaint();
				stopGame();
			} else {
				newBlock();
			}
		} else {
			//System.out.println("Move Next Step:"+blockPosX+","+blockPosY);
			movePos(0, -1);
		}
	}
	
	private Timer timer;
	private TimerTask timeTask = new TimerTask(){

		@Override
		public void run() {
			moveToNextStep();
		}
	};
	
	KeyAdapter ka = new KeyAdapter(){
		private int i = 0;
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
		    if(keyCode == KeyEvent.VK_LEFT) {
		    	movePos(-1, 0);
		    } else if (keyCode == KeyEvent.VK_RIGHT) {
		    	movePos(1, 0);
		    } else if (keyCode == KeyEvent.VK_UP) {
		    	rotation();
		    } else if (keyCode == KeyEvent.VK_DOWN) {
		    	moveToNextStep();
		    }
		}
	};
	
	public void pauseGame() {}
	public void resumeGame() {}
	
	private GameView view;
	private Playground playground;
	private Score score;
	private Block curBlock;
	private Block nextBlock;
	private boolean isGameRuning;
	private int blockPosX; 
	private int blockPosY;
}
