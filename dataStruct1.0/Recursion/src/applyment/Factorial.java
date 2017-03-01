package applyment;
//计算n的阶乘
public class Factorial {
	public static int fact(int n) throws Exception{
		if (n<0) {
			throw new Exception("n<0");
		}
		if (n==0) {
			return 1;
		}
		else {
			return n*fact(n-1);
		}
	}
	
	public static void main(String[] args){
		try {
			System.out.println(fact(3));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
