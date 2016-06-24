package block;

public class Playground {
	public Playground(int width, int height) {
		this.width = width;
		this.height = height;
		bmp = new int[width * height];
		clear();
	}
	
	public boolean isHitGround(int[] blockBmp, int x, int y) {
		for (int i=0; i<4; i++) {
			int bottom = blockBmp[19+i];
			if (bottom >= 0) {
				if ((y+bottom) <= 0) {
					return true;
				}
				if ((y+bottom-1)<height &&
						bmp[(y+bottom-1)*width + x+i] != 0) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isPositionOk(int[] blockBmp, int x, int y) {
		int left = blockBmp[16];
		int right = blockBmp[17];
		int bottom = blockBmp[18];
		
		if ((left + x) < 0) return false;
		if ((right + x) >= width) return false;
		if ((y-bottom) > highest) return true;
		
		return !isOverlay(blockBmp, x, y);
	}
	
	public void addBlock(int[] blockBmp, int x, int y) {
		for (int i=0; i<4; i++) {
			int h = 3-i;
			for (int j=0; j<4; j++) {
				if (blockBmp[h*4+j] != 0) {
					if ((y+i) < height && y+i>=0) {
						bmp[(y+i)*width + x+j] = 1;
						if ((y+i) > highest) highest = y+i;
					} else {
						System.out.printf("invisible block: %d x %d, %d, %d, %d, %d\n",y+i, x+j,x, y, i, j);
					}
				}
			}
		}
	}
	
	public boolean isReachTop() {
		if (highest >= (height-1))
			return true;
		else 
			return false;
	}
	
	private void eraseLine(int line) {
		for (int i=line+1; i<=highest;i++) {
			for (int j=0; j<width; j++) {
				bmp[(i-1)*width +j] = bmp[i*width +j];
			}
		}
		highest--;
	}
	
	public void eraseCompleteLine(int num) {
		for (int i=0; i<=highest;) {
			int j=0;
			for (; j<width; j++) {
				if (bmp[i*width +j] == 0) break;
			}
			if (j >= width) {
				eraseLine(i);
			} else {
				i++;
			}
		}
		for (int i=highest+1; i<=highest+num; i++) {
			for (int j=0; j<width; j++) {
				bmp[(i)*width +j] = 0;
			}
		}
	}
	
	public int getCompleteLineNum(int[] line){
		int num = 0;
		for (int i=0; i<=highest; i++) {
			int j=0;
			for (; j<width; j++) {
				if (bmp[i*width +j] == 0) break;
				if (line != null) {
					line[num] = i;
				}
			}
			if (j >= width) {
				num++;
			}
		}
		return num;
	}
	
	public void clear() {
		highest = -1;
		for (int i=0; i<height; i++) 
			for (int j=0; j<width; j++) 
				bmp[i*width + j] = 0; 
	}
	
	public int[] getBmp() {return bmp;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public int getHighest() {return highest;}
	
	
	private int[] bmp;
	private int width;
	private int height;
	private int highest;
	
	private boolean isOverlay(int[] blockBmp, int x, int y) {
		for (int i=0; i<4; i++) {
			int h = 3-i;
			for (int j=0; j<4; j++) {
				if (blockBmp[h*4+j] != 0 &&
						(y+i>=0) && (y+i<24) && bmp[(y+i)*width + x+j] != 0)
					return true;
			}
		}
		
		return false;
	}
}
