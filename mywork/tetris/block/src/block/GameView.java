package block;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class GameView extends Canvas{
	public GameView(int width, int height, int blockSize){
		this.width = width;
		this.height = height;
		this.blockSize = blockSize;
		this.canWidth = width * blockSize;
		this.canHeight = height * blockSize;
		bmp = new int[width*height];
	}
	
	public void drawPlayGround(Playground pg) {
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				bmp[i*width+j] = pg.getBmp()[i*width+j];
			}
		}
	}
	
	private boolean isPosValid(int x, int y) {
		if (x<0 || x>=width ||
			y<0 || y>=height) return false;
		return true;
	}
	private void drawOrClearBlock(int[] blockbmp, int x, int y, int value) {
		for (int i=0; i<4; i++) {
			int h = 3-i;
			for (int j=0; j<4; j++) {
				if (blockbmp[h*4+j] == 1) {
					if (isPosValid(x+j, y+i)) {
						bmp[(i+y)*width + x + j] = value;
					}
				}
			}
		}
	}
	public void clearBlock(int[] blockbmp, int x, int y) {
		drawOrClearBlock(blockbmp, x, y, 0);
	}
	public void drawBlock(int[] blockbmp, int x, int y) {
		drawOrClearBlock(blockbmp, x, y, 1);
	}
	
	public void drawResult() {
		System.out.println("GAME OVER");
	}
	public void drawScore() {}
	
	public void drawErazeAnimation(int[] lineNum) {}
	public void drawGameOverAnimation() {}
	
	@Override
	public void paint(Graphics g){
		g.setColor(new Color(160,220,220));
		g.drawRect(0, 0, canWidth, canHeight);
		
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				if (bmp[i*width+j] != 0) {
					g.drawImage(img, blockSize*j, (height-i-1)*blockSize, null);
				}
			}
		}
	}
	
	private int[] bmp;
	private int width;
	private int height;
	private int canWidth;
	private int canHeight;
	private int blockSize;
	private java.awt.Image img =
			new javax.swing.ImageIcon("img/block_small.png").getImage();
}
