package block;

public class Block {
	
	public Block() {
		resetWithRandomType();
	}
	public Block(int type) {
		resetWithType(type);
	}
	public int[] getBmp() {
		return bmp[type][posture];
	}
	public int getType(){return type;}
	public int getPosture(){return posture;}
	public int[] getNextPostureBmp() {
		int next = (posture+1) % 4;
		return bmp[type][next];
	}
	public void rotate() {
		posture = (posture+1) % 4;
	}
	
	public void reset() {
		posture = 0;
	}
	public void resetWithRandomType(){
		posture =0;
		type = ((int)(Math.random() * 1000)) % bmp.length;
	}
	public void resetWithType(int type){
		posture = 0;
		this.type = type;
	}
	
	public static int getRandomType() {return 0;}
	
	private int type;
	private int posture;
	
	private static final int[] stick_02 = {// 0 3 1
			0, 0, 0, 0,
			0, 0, 0, 0,
			1, 1, 1, 1,
			0, 0, 0, 0,
			0, 3, 1,
			1, 1, 1, 1
	};
	private static final int[] stick_13 = {// 2 2 0
			0, 0, 1, 0,
			0, 0, 1, 0,
			0, 0, 1, 0,
			0, 0, 1, 0,
			2, 2, 0,
			-1,-1,0, -1
	};
	private static final int[] l_0 = { // 1 2 1
			0, 1, 0, 0,
			0, 1, 0, 0,
			0, 1, 1, 0,
			0, 0, 0, 0,
			1, 2, 1,
			-1,1, 1, -1
	};
	private static final int[] l_1 = { // 1 3 1
			0, 0, 0, 0,
			0, 1, 1, 1,
			0, 1, 0, 0,
			0, 0, 0, 0,
			1, 3, 1,
			-1,1, 2, 2
	};
	private static final int[] l_2 = { // 1 2  1
			0, 1, 1, 0,
			0, 0, 1, 0,
			0, 0, 1, 0,
			0, 0, 0, 0,
			1, 2, 1,
			-1,3, 1, -1
	};
	private static final int[] l_3 = {
			0, 0, 0, 0,
			0, 0, 0, 1,
			0, 1, 1, 1,
			0, 0, 0, 0,
			1, 3, 1,
			-1,1, 1, 1
	};
	private static final int[] l2_0 = {
			0, 0, 1, 0,
			0, 0, 1, 0,
			0, 1, 1, 0,
			0, 0, 0, 0,
			1, 2, 1,
			-1,1, 1, -1
	};
	private static final int[] l2_1 = {
			0, 0, 0, 0,
			0, 1, 0, 0,
			0, 1, 1, 1,
			0, 0, 0, 0,
			1, 3, 1,
			-1,1, 1, 1
	};
	private static final int[] l2_2 = {
			0, 1, 1, 0,
			0, 1, 0, 0,
			0, 1, 0, 0,
			0, 0, 0, 0,
			1, 2, 1,
			-1,1, 3, -1
	};
	private static final int[] l2_3 = {
			0, 0, 0, 0,
			0, 1, 1, 1,
			0, 0, 0, 1,
			0, 0, 0, 0,
			1, 3, 1,
			-1,2, 2, 1
	};
	private static final int[] s_02 = {
			0, 0, 1, 1,
			0, 1, 1, 0,
			0, 0, 0, 0,
			0, 0, 0, 0,
			1, 3, 2,
			-1,2, 2, 3
	};
	private static final int[] s_13 = {
			0, 1, 0, 0,
			0, 1, 1, 0,
			0, 0, 1, 0,
			0, 0, 0, 0,
			1, 2, 1,
			-1,2, 1, -1
	};
	private static final int[] s2_02 = {
			0, 1, 1, 0,
			0, 0, 1, 1,
			0, 0, 0, 0,
			0, 0, 0, 0,
			1, 3, 2,
			-1,3, 2, 2
	};
	private static final int[] s2_13 = {
			0, 0, 1, 0,
			0, 1, 1, 0,
			0, 1, 0, 0,
			0, 0, 0, 0,
			1, 2, 1,
			-1,1, 2, -1
	};
	private static final int[] square_0123 = {
			0, 0, 0, 0,
			0, 1, 1, 0,
			0, 1, 1, 0,
			0, 0, 0, 0,
			1, 2, 1,
			-1,1, 1, -1
	};
	private static final int[] pyramid_0 = {
			0, 0, 1, 0,
			0, 1, 1, 1,
			0, 0, 0, 0,
			0, 0, 0, 0,
			1, 3, 2,
			-1,2, 2, 2
	};
	private static final int[] pyramid_1 = {
			0, 0, 1, 0,
			0, 0, 1, 1,
			0, 0, 1, 0,
			0, 0, 0, 0,
			2, 3, 1,
			-1,-1,1, 2
	};
	private static final int[] pyramid_2 = {
			0, 0, 0, 0,
			0, 1, 1, 1,
			0, 0, 1, 0,
			0, 0, 0, 0,
			1, 3, 1,
			-1,2, 1, 2
	};
	private static final int[] pyramid_3 = {
			0, 0, 1, 0,
			0, 1, 1, 0,
			0, 0, 1, 0,
			0, 0, 0, 0,
			1, 2, 1,
			-1,2, 1, -1
	};
	private static final int bmp[][][] = {
			{stick_02, stick_13, stick_02, stick_13},
			{l_0, l_1, l_2, l_3},
			{l2_0, l2_1, l2_2, l2_3},
			{s_02, s_13, s_02, s_13},
			{s2_02, s2_13, s2_02, s2_13},
			{square_0123, square_0123, square_0123, square_0123},
			{pyramid_0, pyramid_1, pyramid_2, pyramid_3}
	};
}
