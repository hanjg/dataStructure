package applyment;
//计算最大公约数
public class GreatestCommonDivisor {
	public static int gcd(int n,int m){
		if (n<0||m<0) {
			System.out.println(m+" or "+n+"<0");
		}
		if (m==0) {
			return n;
		}
		else if (n<m) {
			return gcd(m, n);
		}
		else {
			return gcd(m, n%m);
		}
	}
	
	public static int gcd2(int n,int m){
		if (n<0||m<0) {
			System.out.println(m+" or "+n+"<0");
		}
		if (n<m) {
			int temp=n;
			n=m;
			m=temp;
		}
		while(m!=0){
			int temp=n;
			n=m;
			m=temp%m;
		}
		return n;
	}
	public static void main(String[] args) {
		System.out.println(gcd(434233434, 33434330));
		System.out.println(gcd2(434233434, 33434330));
	
	}

}
