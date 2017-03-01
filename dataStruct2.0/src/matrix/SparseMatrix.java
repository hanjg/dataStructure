package matrix;

import java.util.Comparator;

import list.SeqList;

/**
 * 三元组顺序表实现稀疏矩阵(number<rows*cols*0.05时称为稀疏矩阵)
 * @author hjg
 *
 */
public class SparseMatrix implements Matrix{
	private int rows;
	private int cols;
	/**
	 * 行序为主序的三元组列表,存储非零元素。
	 * data.size为非零元素的个数；data.capacity为列表容量
	 */
	private SeqList<Triple> data;
	
	public SparseMatrix(int rows,int cols){
		this.rows=rows;
		this.cols=cols;
		data=new SeqList<>();
	}
	
	public void init(Triple[] data){
		this.data=new SeqList<>(data.length);
		for(Triple triple:data){
			this.data.add(triple);
		}
	}
	
	@Override
	public void init(double[][] matrix) {
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				if (matrix[i][j]!=0) {
					data.add(new Triple(i, j, matrix[i][j]));
				}
			}
		}
	}

	@Override
	public void init(double[] matrix) {
		for(int i=0;i<rows;i++){
			for(int j=0;j<=i;j++){
				data.add(new Triple(i, j, matrix[(i+1)*i/2+j]));
			}
			for(int j=i+1;j<cols;j++){
				data.add(new Triple(i, j, matrix[(j+1)*j/2+i]));
			}
		}
	}

	@Override
	public Matrix add(Matrix matrix) throws Exception {
		if (!(matrix instanceof SparseMatrix)) {
			throw new Exception("class unequal");
		}
		SparseMatrix matrix2=(SparseMatrix)matrix;
		if (matrix2.cols!=cols||matrix2.rows!=rows) {
			throw new Exception("order unequal");
		}
		SparseMatrix res=new SparseMatrix(rows, cols);
		SeqList<Triple> resData=new SeqList<>();
		int i=0,j=0;
		while(i<this.data.size()||j<matrix2.data.size()){
			if (i==this.data.size()) {
				Triple triple=matrix2.data.get(j);
				resData.add(new Triple(triple.row, triple.col, triple.val));
				j++;
			}
			else if (j==matrix2.data.size()) {
				Triple triple=this.data.get(i);
				resData.add(new Triple(triple.row, triple.col, triple.val));
				i++;
			}
			else {
				Triple o1=this.data.get(i);
				Triple o2=matrix2.data.get(j);
				if (o1.compare(o1, o2)<0) {
					resData.add(new Triple(o1.row, o1.col, o1.val));
					i++;
				}
				else if (o1.compare(o1, o2)>0) {
					resData.add(new Triple(o2.row, o2.col, o2.val));
					j++;
				}
				else if (o1.val+o2.val==0) {
					i++;j++;
				}
				else {
					resData.add(new Triple(o1.row, o2.col, o1.val+o2.val));
					i++;j++;
				}
			}
		}
		res.data=resData;
		return res;
	}

	public SparseMatrix transpose(){
		SparseMatrix res=new SparseMatrix(cols,rows);
		SeqList<Triple> resData=new SeqList<>(data.size());
		for(int col=0;col<cols;col++){//按当前矩阵的列寻找，即按照转置后矩阵的行寻找
			for(int num=0;num<data.size();num++){
				try {
					Triple cur=this.data.get(num);
					if (cur.col==col) {
						resData.add(new Triple(col, cur.row, cur.val));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		res.data=resData;
		return res;
	}
	
	/**
	 * 快速转置，直接将当前矩阵元素放在转置矩阵的目标位置
	 * @return
	 */
	public SparseMatrix quickTranspose(){
		SparseMatrix res=new SparseMatrix(cols, rows);
		SeqList<Triple> resData=new SeqList<>(data.size());
		//当前矩阵列中（转置矩阵行中）非零元素的个数
		int[] num=new int[cols];
		//当前矩阵列的（转置矩阵行）第一个非零元素在resData中的位置
		int[] pos=new int[cols];
		try {
			for(int i=0;i<data.size();i++){
				num[data.get(i).col]++;
			}
			for(int i=1;i<cols;i++){
				pos[i]=pos[i-1]+num[i-1];
			}
			for(int i=0;i<data.size();i++){
				Triple triple=data.get(i);
				resData.insert(pos[triple.col]++, new Triple(triple.col, triple.row, triple.val));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.data=resData;
		return res;
	}
	/** 
	 * 利用行逻辑链接求矩阵乘积。
	 */
	@Override
	public Matrix mul(Matrix matrix) throws Exception {
		if (!(matrix instanceof SparseMatrix)) {
			throw new Exception("class unequal");
		}
		SparseMatrix matrix2=(SparseMatrix)matrix;
		if (cols!=matrix2.rows) {
			throw new Exception("order unequal");
		}
		//两个矩阵各行第一个非零元素的位置
		int[] pos1=calPos(this);
		int[] pos2=calPos(matrix2);
		
		SparseMatrix res=new SparseMatrix(rows, matrix2.cols);
		for(int i=0;i<rows;i++){
			//temp记录乘积矩阵第i行各个列的值
			double[] temp=new double[matrix2.cols];
			for(int k1=pos1[i];k1<(i+1==rows?this.data.size():pos1[i+1]);k1++){
				//取matrix1中第i行的各个元素triple
				Triple triple=this.data.get(k1);
				//在matrix2中寻找可与triple相乘的元素，即matrix中第triple.col行的元素
				int row2=triple.col;
				for(int k2=pos2[row2];k2<(row2+1==matrix2.rows?matrix2.data.size():pos2[row2+1]);k2++){
					Triple triple2=matrix2.data.get(k2);
					temp[triple2.col]+=triple.val*triple2.val;
				}
			}
			for(int j=0;j<matrix2.cols;j++){
				if (temp[j]!=0) {
					res.data.add(new Triple(i, j, temp[j]));
				}
			}
		}
		return res;
	}

	/**
	 * 计算矩阵各行第一个非零元素的位置
	 * @param matrix
	 * @throws Exception
	 */
	private int[] calPos(SparseMatrix matrix) throws Exception {
		//matrix各行非零元素的个数
		int[] num=new int[matrix.rows];
		//matrix各行第一个非零元素的位置
		int[] pos=new int[matrix.rows];
		for(int i=0;i<matrix.data.size();i++){
			num[matrix.data.get(i).row]++;
		}
		for(int i=1;i<matrix.rows;i++){
			pos[i]=pos[i-1]+num[i-1];
		}
		return pos;
	}

	@Override
	public void print() {
		try {
			int k=0;
			Triple triple=data.get(k);
			System.out.println("[");
			for(int i=0;i<rows;i++){
				for(int j=0;j<cols;j++){
					if (j!=0) {
						System.out.print("\t");
					}
					if (k<data.size()&&(triple.row==i&&triple.col==j)) {
						System.out.print(triple.val);
						if (++k<data.size()) {
							triple=data.get(k);
						}
					}
					else {
						System.out.print("0");
					}
				}
				System.out.println();
			}
			System.out.println("]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class Triple implements Comparator<Triple>{
		private int row;
		private int col;
		private double val;
		
		public Triple(int row,int col,double val){
			this.row=row;
			this.col=col;
			this.val=val;
		}

		@Override
		public int compare(Triple o1, Triple o2) {
			if (o1.row==o2.row) {
				return o1.col-o2.col;
			}
			else {
				return o1.row-o2.col;
			}
		}
		public int getRow() {
			return row;
		}
		public int getCol() {
			return col;
		}
		public double getVal() {
			return val;
		}
	}
}
