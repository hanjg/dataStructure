package matrix;

public class TestMatrix {
	public static void main(String[] args) {
		try {
			double[][] a={{1,0,2},{2,3,4},{4,5,6}};
			double[] b={1,2,3,4,5,6};
			Matrix matrix=new SymmetricalMatrix(3);
			matrix.init(a);
			Matrix matrix2=new SymmetricalMatrix(3);
			matrix2.init(b);
			
			System.out.println("matrix1:");
			matrix.print();
			System.out.println("matrix2:");
			matrix2.print();
			
			System.out.println("add:");
			matrix.add(matrix2).print();
			
			System.out.println("transpose:");
			matrix.transpose().print();
			
			System.out.println("mul:");
			matrix.mul(matrix2).print();
			
//			System.out.println("quick transpose:");
//			((SparseMatrix)matrix).quickTranspose().print();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
