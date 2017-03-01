package matrix;

import matrix.SparseMatrix.Triple;

/**
 * 一维数组实现n阶对称矩阵(Aij=Aji)
 * @author hjg
 *
 */
public class SymmetricalMatrix implements Matrix{
	/**
	 * 储存左下的数据
	 */
	private double[] data;
	/**
	 * 矩阵阶数
	 */
	private int n;
	/**
	 * 元素个数
	 */
	private int number;
	
	public SymmetricalMatrix(int n){
		this.n=n;
		number=(n+1)*n/2;
		data=new double[number];
	}

	@Override
	public void init(double[][] matrix){
		int k=0;
		for(int i=0;i<n;i++){
			for(int j=0;j<=i;j++){
				data[k++]=matrix[i][j];
			}
		}
	}
	@Override
	public void init(double[] matrix){
		for(int k=0;k<number;k++){
			data[k]=matrix[k];
		}
	}

	@Override
	public void init(Triple[] data) {}

	@Override
	public void print() {
		int k=0;
		System.out.println("[");
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if (j<=i) {
					k=(i+1)*i/2+j;
				}
				else {
					k=(j+1)*j/2+i;
				}
				if (j!=0){
					System.out.print("\t");
				}
				System.out.print(data[k++]);
			}
			System.out.println();
		}
		System.out.println("]");
	}

	@Override
	public Matrix add(Matrix matrix) throws Exception {
		if (!(matrix instanceof SymmetricalMatrix)) {
			throw new Exception("class unequal");
		}
		SymmetricalMatrix matrix2=(SymmetricalMatrix)matrix;
		if (n!=matrix2.n) {
			throw new Exception("order unequal");
		}
		SymmetricalMatrix res=new SymmetricalMatrix(matrix2.n);
		for(int k=0;k<number;k++){
			res.data[k]=this.data[k]+matrix2.data[k];
		}
		return res;
	}

	@Override
	public Matrix transpose() {
		SymmetricalMatrix res=new SymmetricalMatrix(n);
		res.init(this.data);
		return res;
	}

	@Override
	public Matrix mul(Matrix matrix) throws Exception {
		if (!(matrix instanceof SymmetricalMatrix)) {
			throw new Exception("class unequal");
		}
		SymmetricalMatrix matrix2=(SymmetricalMatrix)matrix;
		if (n!=matrix2.n) {
			throw new Exception("order unequal");
		}
		SymmetricalMatrix res=new SymmetricalMatrix(n);
		double[][] temp=new double[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<=i;j++){
				temp[i][j]=0;
				for(int k=0;k<n;k++){
					temp[i][j]+=(k<=i?data[(i+1)*i/2+k]:data[(k+1)*k/2+i])*
							(j<=k?data[(k+1)*k/2+j]:data[(j+1)*j/2+k]);
				}
			}
		}
		res.init(temp);
		return res;
	}
}
