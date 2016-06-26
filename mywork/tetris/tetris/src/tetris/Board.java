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
	private boolean[][] gridBackUp;
	private boolean backRestored;
	private boolean DEBUG = true;
	boolean committed;
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[height][width];
		gridBackUp = new boolean[height][width];
		backRestored = true;
		committed = true;
		
		// YOUR CODE HERE
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
		int i = 0;
		for (i=height-1; i>=0; i--) {
			int j = 0;
			for (j=0; j<width; j++) {
				if (grid[i][j]) break;
			}
			if(j < width) {
				break;
			}
		}
		return i+1; // YOUR CODE HERE
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			// YOUR CODE HERE
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
		int dropH = 0;
		for (int i=0; i<piece.getWidth(); i++) {
			int h = getColumnHeight(x+i) - piece.getSkirt()[i];
			if (dropH < h) dropH = h;
		}
		return dropH; // YOUR CODE HERE
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		int i;
		for (i=getMaxHeight() - 1; i>=0; i--) {
			if (grid[i][x]) break;
		}
		return i+1; // YOUR CODE HERE
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		int cnt = 0;
		for (int i=0; i<width; i++) {
			if (grid[y][i]) cnt++;
		}
		return cnt; // YOUR CODE HERE
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if (x<0 || x>=width || y<0 || y>=height) return true;
		
		return grid[y][x]; // YOUR CODE HERE
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
		
		for (int i=0; i<height; i++) {
			System.arraycopy(grid[i], 0, gridBackUp[i], 0, width);
		}
		backRestored = false;
			
		if (x<0 || x+piece.getWidth() > width ||
				y<0 || y+piece.getHeight() > height)
			return PLACE_OUT_BOUNDS;
		
		for (int i=0; i<4; i++) {
			TPoint[] pts = piece.getBody();
			if (getGrid(x+pts[i].x, y+pts[i].y)) {
				return PLACE_BAD;
			}
		}
		
		int result = PLACE_OK;
		for (int i=0; i<4; i++) {
			TPoint[] pts = piece.getBody();
			grid[y+pts[i].y][x+pts[i].x] = true;
			if (getRowWidth(y+pts[i].y) == width) result = PLACE_ROW_FILLED;
		}
		
		return result;
		
		// YOUR CODE HERE
		
		
	}
	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		int rowsCleared = 0;
		// YOUR CODE HERE
		int max_row = getMaxHeight() - 1;
		for (int des_y=0, src_y=0; src_y<=max_row;) {
			if (getRowWidth(src_y) == width) {
				src_y++;
				rowsCleared++;
			} else {
				if (des_y != src_y) {
					for (int i=0; i<width; i++) {
						grid[des_y][i] = grid[src_y][i];
					}
				}
				src_y++;
				des_y++;
			}
		}
		for (int y=0; y<rowsCleared; y++) {
			for (int i=0; i<width; i++) {
				grid[max_row-y][i] = false;
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
		// YOUR CODE HERE
		if (!backRestored) {
			for (int i=0; i<height; i++) {
				System.arraycopy(gridBackUp[i], 0, grid[i], 0, width);
			}
			backRestored = true;
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


