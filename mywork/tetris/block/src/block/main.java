package block;

import java.awt.*; 
import java.awt.event.*; 

public class main extends WindowAdapter{ 
	
	static final int GAME_PLAYGROUND_WIDTH = 12;
	static final int GAME_PLAYGROUND_HEIGHT = 24;
	static final int GAME_BLOCK_PIXEL_SIZE = 22;
	
	@Override
	public void windowClosing(WindowEvent e){ 
		System.exit(0); 
	}
	
	private void _main() {
		Frame f = new Frame("Russis Squre"); 
		gameView = new GameView(GAME_PLAYGROUND_WIDTH, 
				GAME_PLAYGROUND_HEIGHT, GAME_BLOCK_PIXEL_SIZE);
        f.add(gameView); 
        f.addWindowListener(this);
        f.setBackground(new Color(160,220,220));
        f.setLocationByPlatform(true); 
        f.setSize(22*12,22*25); 
        f.setVisible(true);
	}
	
	static GameView gameView;
	
	public static void main(String[] args){
		(new main())._main();
		Control game = new Control(gameView);
		game.newGame();
	} 
	
	
}
