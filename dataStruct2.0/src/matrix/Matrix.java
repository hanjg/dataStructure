package matrix;

import matrix.SparseMatrix.Triple;

/**
 * 矩阵（行和列的下标均从0开始）
 * @author hjg
 *
 */
public interface Matrix {
	public void init(double[][] matrix);
	public void init(double[] matrix);
	public void init(Triple[] data);
	public Matrix add(Matrix matrix) throws Exception;
	public Matrix transpose();
	public Matrix mul(Matrix matrix) throws Exception;
	public void print();
}
