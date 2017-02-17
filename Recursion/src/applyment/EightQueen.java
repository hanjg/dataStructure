package applyment;

public class EightQueen {
	int[] queens=new int[8];//索引表示行的索引，值表示列的索引
	int count=0;//解的编号
	
	private boolean isSafe(int tempRow,int tempCol){
		for(int row=0;row<tempRow;row++){
			int col=queens[row];
			if (tempRow==row) {
				return false;
			}
			if (tempCol==col) {
				return false;
			}
			if (tempCol+tempRow==row+col) {
				return false;
			}
			if (tempCol-tempRow==col-row) {
				return false;
			}
		}
		return true;
	}
	
	public void placeQueen(int row){
		if (row==8) {
			count++;
			System.out.print(count+":");
			printQueens();
			return;
		}
		for(int col=0;col<8;col++){
			if (isSafe(row, col)) {
				queens[row]=col;
				placeQueen(row+1);
			}
		}
	}
	
	public void printQueens(){
		for(int i=0;i<8;i++){
			System.out.print(queens[i]+" ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		EightQueen eightQueen=new EightQueen();
		//long start=System.currentTimeMillis();
		eightQueen.placeQueen(0);
		System.out.println("count="+eightQueen.count);
		//long end=System.currentTimeMillis();
		//System.out.println(end-start);
	}

}
