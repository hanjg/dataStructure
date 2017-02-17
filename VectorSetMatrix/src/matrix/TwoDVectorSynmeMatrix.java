package matrix;

import vector.MyVector;

public class TwoDVectorSynmeMatrix {//不等长列向量方法存储n阶对称矩阵
	private MyVector values;
	private int n;
	
	public TwoDVectorSynmeMatrix(int n) {
		if (n<=0) {
			throw new ArrayIndexOutOfBoundsException(n);
		}
		values=new MyVector(n);
		for(int i=0;i<n;i++){
			MyVector row=new MyVector(i+1);
			for(int j=0;j<i+1;j++){
				row.add(null);
			}
			values.add(row);
		}
		this.n=n;
	}
	
	public void createMatrix(double[][] b){
		for(int i=0;i<n;i++){
			MyVector temp=(MyVector) values.get(i);
			for(int j=0;j<i+1;j++){
				temp.set(j, b[i][j]);
			}
		}
	}
	
	public void set(int row,int col,Object object){
		if (row<0||row>=n||col<0||col>=n) {
			throw new ArrayIndexOutOfBoundsException(row+" "+col);
		}
		try {
			MyVector selrow=(MyVector)values.get(row);
			selrow.set(col, object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Object get(int row,int col){
		if (row<0||row>=n||col<0||col>=n) {
			throw new ArrayIndexOutOfBoundsException(row+" "+col);
		}
		MyVector selrow=(MyVector)values.get(row);
		return selrow.get(col);
	}
	
	public TwoDVectorSynmeMatrix add(TwoDVectorSynmeMatrix matrixB){
		TwoDVectorSynmeMatrix matrix=new TwoDVectorSynmeMatrix(n);
		for(int i=0;i<n;i++){
			for(int j=0;j<i+1;j++){
				Double double1=(Double) matrixB.get(i, j);
				Double double2=(Double) this.get(i, j);
				matrix.set(i, j, double1+double2);
			}
		}
		return matrix;
	}
	
	public void print(){//i,j从1开始计数
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if (j>i) {
					System.out.print(this.get(j, i)+" ");
				}
				else {
					System.out.print(this.get(i, j)+" ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}	
	
	public static void main(String[] args) {
		double[][] a={{1},{2,3},{4,5,6}};
		TwoDVectorSynmeMatrix matrix=new TwoDVectorSynmeMatrix(3);
		matrix.createMatrix(a);
		
		TwoDVectorSynmeMatrix matrix2=new TwoDVectorSynmeMatrix(3);
		for(int i=0;i<3;i++){
			for(int j=0;j<=i;j++){
				matrix2.set(i, j, new Double(Math.random()*10));
			}
		}
		matrix.print();
		matrix2.print();
		TwoDVectorSynmeMatrix matrix3=new TwoDVectorSynmeMatrix(3);
		matrix3=matrix.add(matrix2);
		
		matrix3.print();
	}

}
