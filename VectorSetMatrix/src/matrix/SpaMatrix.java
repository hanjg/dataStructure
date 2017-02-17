package matrix;

import vector.MyVector;

public class SpaMatrix {//用三元组顺序表（向量）实现稀疏矩阵,一般当dNum/(rows*cols)<0.05时称为稀疏矩阵
						//矩阵元素三元组的r,c[1,rows||cols]
	private int rows;
	private int cols;//矩阵的行数和列数
	private int dNum;//非零元个数
	private MyVector value;//用来存储三元组即矩阵元素的向量
	private int[]	rolPos;//rolPos[rol]第rol行的一个非零元素在value中的位置
	
	public SpaMatrix(int max) {//矩阵 非零元素个数的最大值max
		rows=cols=dNum=0;
		value=new MyVector(max);
	}
	
	public void createMatrix(int r,int c,int d,Three[] item) throws Exception{
		//输入按照先行有序，再列有序的顺序
		rows=r;
		cols=c;
		dNum=d;
		for(int i=0;i<d;i++){
			value.add(item[i]);
		}
	}
	
	public void add(Three item){
		value.add(item);
		dNum++;
	}
	
	public SpaMatrix transpose(){//按照行序转置矩阵,使得原来先行后列有序的矩阵转置为先列后行有序的矩阵，时间复杂度为dNum
		SpaMatrix matrix=new SpaMatrix(dNum);
		matrix.cols=this.rows;
		matrix.rows=this.cols;
		matrix.dNum=this.dNum;
		for(int i=0;i<dNum;i++){
			Three temp=(Three) this.value.get(i);//指向原矩阵的每一个三元组
			matrix.value.add(new Three(temp.col, temp.row, temp.value));//将原矩阵的三元组行列互换插入新矩阵
		}
		return matrix;
	}
	
	public SpaMatrix transpose2(){//按照列序转置矩阵，使得原来先行后列有序的转置为先行后列有序的矩阵,时间复杂度为cols*dNum
		SpaMatrix matrix=new SpaMatrix(dNum);
		matrix.cols=this.rows;
		matrix.rows=this.cols;
		matrix.dNum=this.dNum;
		for(int col=1;col<=cols;col++){
			for(int i=0;i<dNum;i++){
				Three current=(Three)value.get(i);
				if (current.col==col) {
					matrix.value.add(new Three(current.col, current.row, current.value));
				}
			}
		}
		return matrix;
	}
	
	public SpaMatrix quickTranspose(){//按照三元组的次序转置，将转置后的三元组放入新矩阵的恰当位置。时间复杂度为cols+dNum
		//使得原来先行后列有序的矩阵转置为先行后列有序
		SpaMatrix matrix=new SpaMatrix(dNum);
		matrix.cols=this.rows;
		matrix.rows=this.cols;
		matrix.dNum=this.dNum;
		int[] num=new int[this.cols+1];//num[col]表示原矩阵第col列中非零元素的个数
		int[] colPos=new int[this.cols+1];//colPos[col]表示原矩阵M第col列中第一个非零元素在新矩阵T中的位置
		for(int i=1;i<=cols;i++){
			num[i]=0;
		}
		for(int i=0;i<dNum;i++){
			num[((Three)value.get(i)).col]++;
		}
		colPos[1]=0;
		for(int col=2;col<=cols;col++){
			colPos[col]=colPos[col-1]+num[col-1];
		}
		for(int i=0;i<dNum;i++){
			Three current=(Three)value.get(i);
			matrix.value.insert(colPos[current.col]++,
					new Three(current.col, current.row, current.value));
		}
		return matrix;
	}
	
	private void initRolPos(){//rolPos[rol]第rol行的一个非零元素在value中的位置
		rolPos=new int[rows+1];
		int[] num=new int[rows+1];//第row行中非零元素的个数
		for(int i=1;i<=rows;i++){
			num[i]=0;
		}
		for(int i=0;i<dNum;i++){
			num[((Three)value.get(i)).row]++;
		}
		rolPos[1]=0;
		for(int row=2;row<=rows;row++){
			rolPos[row]=rolPos[row-1]+num[row-1];
		}
	}
	
	public SpaMatrix mul(SpaMatrix matrixB) throws Exception{//用行逻辑链接实现矩阵乘法
		if (this.cols!=matrixB.rows) {
			throw new IndexOutOfBoundsException();
		}
		initRolPos();
		matrixB.initRolPos();
		SpaMatrix result=new SpaMatrix(dNum);
		result.rows=this.rows;
		result.cols=matrixB.cols;
		double [] mul;//mul[i]存储result中第rowA行第i列的乘积和
		for(int rowA=1;rowA<=rows;rowA++){
			mul=new double[matrixB.cols+1];
			for(int i=1;i<=matrixB.cols;i++){
				mul[i]=0;
			}
			int beginA=rolPos[rowA];//第rowA行元素在value中的起止位置
			int endA=rowA==rows?dNum:rolPos[rowA+1];//每一行的元素位置[begin,end)
			for(int indexA=beginA;indexA<endA;indexA++){
				Three currentA=(Three)value.get(indexA);
				int rowB=currentA.col;//对该行的每一个元素，分别找到对应元素在B中的行号
				int beginB=matrixB.rolPos[rowB];
				int endB=rowB==matrixB.rows?matrixB.dNum:matrixB.rolPos[rowB+1];
				for(int indexB=beginB;indexB<endB;indexB++){//将该元素与B中行号相同的元素相乘
															//B中元素列号为i，则将相乘的结果加入mul[i]
					Three currentB=(Three)matrixB.value.get(indexB);
					mul[currentB.col]+=currentA.value*currentB.value;
				}
			}
			for(int i=1;i<=matrixB.cols;i++){
				if (mul[i]!=0) {
					result.add(new Three(rowA, i, mul[i]));
				}
			}
		}
		return result;
	}
	
	public void print(){
		System.out.println("rows="+rows+" cols="+cols+" dNum="+dNum);
		for(int i=0;i<dNum;i++){
			System.out.print("<"+((Three)value.get(i)).row+","+((Three)value.get(i)).col+","
					+((Three)value.get(i)).value+"> ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		Three[] a=new Three[4];
		a[0]=new Three(1, 1, 3);
		a[1]=new Three(1, 4, 5);
		a[2]=new Three(2, 2, -1);
		a[3]=new Three(3, 1, 2);
		Three[] b=new Three[4];
		b[0]=new Three(1, 2, 2);
		b[1]=new Three(2, 1, 1);
		b[2]=new Three(3, 1, -2);
		b[3]=new Three(3, 2, 4);
		
		SpaMatrix matrix=new SpaMatrix(a.length);
		SpaMatrix matrix2=new SpaMatrix(b.length);
		try {
			matrix.createMatrix(3, 4, a.length, a);matrix.print();
			matrix2.createMatrix(4, 2, b.length, b);matrix2.print();
			SpaMatrix matrix3=matrix.mul(matrix2);matrix3.print();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//SpaMatrix matrix2=matrix.transpose();matrix2.print();
		//matrix2=matrix.transpose2();matrix2.print();
		//matrix2=matrix.quickTranspose();matrix2.print();

	}

}

class Three{
	int row;
	int col;
	double value;
	
	public Three(int r,int c,double v){
		row=r;
		col=c;
		value=v;
	}
	
}
