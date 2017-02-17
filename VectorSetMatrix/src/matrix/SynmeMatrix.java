package matrix;

public class SynmeMatrix {//一维数组存储n阶对称矩阵,i,j[1,n],k从0开始计数
	private double a[];
	private int n;//阶数
	private int m;//实际存储元素的个数
	
	public SynmeMatrix(int n) {
		m=n*(n+1)/2;
		a=new double[m];
		this.n=n;
	}
	
	public void createMatrix(double[][] b){
		for(int k=0,i=0;i<n;i++){
			for(int j=0;j<=i;j++){
				a[k]=b[i][j];
				k++;
			}
		}
	}
	
	public void createMatrix(double[] b){
		for(int k=0;k<m;k++){
			a[k]=b[k];
		}
	}
	
	public SynmeMatrix add(SynmeMatrix matrixB){
		SynmeMatrix matrix=new SynmeMatrix(n);
		for(int k=0;k<m;k++){
			matrix.a[k]=a[k]+matrixB.a[k];
		}
		return matrix;
	}
	
	public void print(){//i,j[1,n],k从0开始计数
		for(int k=0,i=1;i<=n;i++){
			for(int j=1;j<=n;j++){
				if (j<=i) {
					k=i*(i-1)/2+j-1;//矩阵元素a[i][j]和a[k]的关系为:k=(1+i-1)*(i-1)/2+j-1
				}
				else {
					k=j*(j-1)/2+i-1;
				}
				System.out.print(a[k]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		SynmeMatrix matrix=new SynmeMatrix(3);
		SynmeMatrix matrix2=new SynmeMatrix(3);
		SynmeMatrix matrix3;
		
		double[][] a={{1,0,2},{2,3,4},{4,5,6}};
		double[] b={1,2,3,4,5,6};
		
		matrix.createMatrix(a);
		matrix2.createMatrix(b);
		matrix3=matrix.add(matrix2);
		
		matrix.print();
		matrix2.print();
		matrix3.print();
	}

}
