package matrix;

import vector.MyVector;

public class MyMatrix {//用行向量和列向量(二维数组)实现矩阵，矩阵元素i,j[0,height||width)
	private MyVector values;
	private int height;//行数
	private int width;//列数
	
	public MyMatrix(int height,int width){
		if (height<=0||width<=0) {
			throw new ArrayIndexOutOfBoundsException(height+"or"+width);
		}
		values=new MyVector(height);
		for(int i=0;i<height;i++){
			MyVector row=new MyVector(width);
			values.add(row);
			for(int j=0;j<width;j++){
				row.add(null);
			}
		}
		this.height=height;
		this.width=width;
	}
	
	public void set(int row,int col,Object object){
		if (row<0||row>=height||col<0||col>=width) {
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
		if (row<0||row>=height||col<0||col>=width) {
			throw new ArrayIndexOutOfBoundsException(row+" "+col);
		}
		MyVector selrow=(MyVector)values.get(row);
		return selrow.get(col);
	}
	
	public int width(){
		return width;
	}
	
	public int height(){
		return height;
	}
	
	public MyMatrix add(MyMatrix matrixB){//整型矩阵相加
		if (height!=matrixB.height||width!=matrixB.width) {
			throw new ArrayIndexOutOfBoundsException(height+"or"+width);
		}
		MyMatrix result=new MyMatrix(height, width);
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				Integer integer=(Integer)get(i, j);
				Integer integer2=(Integer)matrixB.get(i, j);
				result.set(i, j, integer+integer2);
				//result.set(i, j, new Integer(integer.intValue()+integer2.intValue()));
			}
		}
		return result;
	}
	
	public MyMatrix mul(MyMatrix matrixB){
		if (width!=matrixB.height) {
			throw new ArrayIndexOutOfBoundsException("width!=matrixB.height");
		}
		MyMatrix result=new MyMatrix(height, matrixB.width);
		for(int i=0;i<height;i++){
			for(int j=0;j<matrixB.width;j++){
				Integer temp=0;
				for(int k=0;k<width;k++){
					temp+=(Integer)this.get(i, k)*(Integer)matrixB.get(k, j);
					result.set(i, j, temp);
				}
			}
		}
		return result;
	}
	
	public MyMatrix addRow(int index,MyVector row){
		values.add(index, row);
		height++;
		return this;
	}
	
	public MyMatrix deleteRow(int index){
		values.remove(index);
		height--;
		return this;
	}
	
	public MyMatrix addCol(int index,MyVector col){
		for(int i=0;i<height;i++){
			MyVector temp=(MyVector) values.get(i);
			temp.add(index, col.get(i));
		}
		width++;
		return this;
	}
	
	public MyMatrix deleteCol(int index){
		for(int i=0;i<height;i++){
			MyVector temp=(MyVector)values.get(i);
			temp.remove(index);
		}
		width--;
		return this;
	}
	
	public void print(){
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				System.out.print(get(i, j)+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
	public static void main(String[] args) {
		MyMatrix matrix=new MyMatrix(3, 4);
		for(int i=0;i<matrix.height;i++){
			for(int j=0;j<matrix.width;j++){
				matrix.set(i, j, new Integer(i+j));
			}
		}
		
		MyMatrix matrix2=new MyMatrix(4, 3);
		for(int i=0;i<matrix2.height;i++){
			for(int j=0;j<matrix2.width;j++){
				matrix2.set(i, j, new Integer((int)(Math.random()*10)));
			}
		}
		matrix.print();
		matrix2.print();
		MyMatrix matrix3=matrix.mul(matrix2);matrix3.print();
	}

}
