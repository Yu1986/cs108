// Board.java
package tetris;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean[][] gridBackup;
	private boolean DEBUG = true;
	boolean committed;
	
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		//Jie's code here
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		gridBackup = new boolean[width][height];
		committed = true;
		
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {	 
		//Jie's code here
		int maxHeight = 0;
		int colHeight = 0;
		for(int i=0; i<width; i++){
			for(int j=height-1; j>=0; j--){
				if(grid[i][j]){
					colHeight = j+1;
					break;
				}
			}
			maxHeight = Math.max(colHeight, maxHeight);
		}
		return maxHeight; 
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			//skip this part
		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		// Jie's code 
		int dropH = 0;
		for(int i=0; i <piece.getWidth(); i++){
			int h = getColumnHeight(x+i) - piece.getSkirt()[i];
			if(dropH < h) dropH = h;
		}
		return dropH;
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		//Jie's code
		int i;
		for(i= getMaxHeight()-1; i>=0; i--){
			if(grid[x][i]) break;
			
		}
		return i+1; 
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		//Jie's code
	   int count = 0;
	   for(int i=0; i<width; i++){
		   if(grid[i][y]) count++;
	   }
		 return count; 
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		//Jie's code
		if(x<0 || x>=width || y<0 || y>=height) return true;
		
		return grid[x][y]; 
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
			
		//Jie's code
		for(int i=0; i<width; i++){
			System.arraycopy(grid[i], 0, gridBackup[i], 0, height);
		}
		committed = false;
		
		if(x<0 || x+piece.getWidth() > width || y<0 || y+piece.getHeight()> height){
			return PLACE_OUT_BOUNDS;
		}
		
		for(int i=0; i<4; i++){
			TPoint[] pts = piece.getBody();
			if(getGrid(x+pts[i].x, y+pts[i].y)){
				return PLACE_BAD;
			}
		}
		int result = PLACE_OK;
		for(int i=0; i<4; i++){
			TPoint[] pts = piece.getBody();
			grid[x+pts[i].x][y+pts[i].y] = true;
			if(getRowWidth(y+pts[i].y) == width) result = PLACE_ROW_FILLED;
		}
	
		
		return result;
	}
	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		int rowsCleared = 0;
		// Jie's code
		int cur_height = getMaxHeight()-1;
		for(int des_y=0, src_y=0; src_y<=cur_height;){
			if(getRowWidth(src_y) == width){
				src_y++;
				rowsCleared++;
			}else{
				if(src_y != des_y){
					for(int i=0; i<width; i++){
						grid[i][des_y] = grid[i][src_y];
					}
				}
				src_y++;
				des_y++;
			}
		}
		for(int x=0; x<rowsCleared; x++){
			for(int i=0; i<width; i++){
				grid[i][cur_height-x] = false;
			}
		}
		sanityCheck();
		return rowsCleared;
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		// Jie's code
		if(!committed){
			for(int i=0; i<width; i++){
				System.arraycopy(gridBackup[i], 0, grid[i], 0, height);
			}
			committed = true;
		}
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


