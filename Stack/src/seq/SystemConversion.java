package seq;

public class SystemConversion {//将非负的十进制数转化为d进制数
	public static void conversion(int n,int d){
		if (n>=0) {
			SeqStack stack=new SeqStack();
			while(n!=0){
				stack.push(new Integer(n%d));
				n/=d;
			}
			while(stack.notEmpty()){
				try {
					System.out.print(stack.pop());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println();
		}
		else {
			n=-n;
			SeqStack stack=new SeqStack();
			System.out.print("-");
			while(n!=0){
				stack.push(new Integer(n%d));
				n/=d;
			}
			while(stack.notEmpty()){
				try {
					System.out.print(stack.pop());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		SystemConversion.conversion(1348, 8);
		SystemConversion.conversion(-1348, 8);
	}

}
