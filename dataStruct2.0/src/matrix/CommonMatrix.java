package matrix;

import matrix.SparseMatrix.Triple;

/**
 * 二维数组实现通常矩阵
 * @author hjg
 *
 */
public class CommonMatrix implements Matrix{
	private double[][] data;
	private int rows;
	private int cols;
	
	public CommonMatrix(int rows,int cols) {
		this.rows=rows;
		this.cols=cols;
		data=new double[rows][cols];
	}
	@Override
	public void init(double[][] matrix) {
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				data[i][j]=matrix[i][j];
			}
		}
	}

	@Override
	public void init(double[] matrix) {
		int k=0;
		for(int i=0;i<rows;i++){
			for(int j=0;j<=i;j++){
				data[i][j]=matrix[k++];
			}
		}
		for(int i=0;i<rows;i++){
			for(int j=i+1;j<cols;j++){
				data[i][j]=data[j][i];
			}
		}
	}

	@Override
	public void init(Triple[] data) {
		for(Triple triple:data){
			this.data[triple.getRow()][triple.getCol()]=triple.getVal();
		}
	}
	
	@Override
	public Matrix add(Matrix matrix) throws Exception {
		if (!(matrix instanceof CommonMatrix)) {
			throw new Exception("class unequal");
		}
		CommonMatrix matrix2=(CommonMatrix)matrix;
		if (matrix2.cols!=cols||matrix2.rows!=rows) {
			throw new Exception("order unequal");
		}
		CommonMatrix res=new CommonMatrix(rows, cols);
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				res.data[i][j]=this.data[i][j]+matrix2.data[i][j];
			}
		}
		return res;
	}

	@Override
	public Matrix transpose() {
		CommonMatrix res=new CommonMatrix(cols, rows);
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				res.data[j][i]=this.data[i][j];
			}
		}
		return res;
	}

	@Override
	public Matrix mul(Matrix matrix) throws Exception {
		if (!(matrix instanceof CommonMatrix)) {
			throw new Exception("class unequal");
		}
		CommonMatrix matrix2=(CommonMatrix)matrix;
		if (cols!=matrix2.rows) {
			throw new Exception("order unequal");
		}
		CommonMatrix res=new CommonMatrix(rows, matrix2.cols);
		for(int i=0;i<rows;i++){
			for(int j=0;j<matrix2.cols;j++){
				for(int k=0;k<cols;k++){
					res.data[i][j]+=this.data[i][k]*matrix2.data[k][j];
				}
			}
		}
		return res;
	}

	@Override
	public void print() {
		System.out.println("[");
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				if (j!=0) {
					System.out.print("\t");
				}
				System.out.print(data[i][j]);
			}
			System.out.println();
		}
		System.out.println("]");
	}

}
