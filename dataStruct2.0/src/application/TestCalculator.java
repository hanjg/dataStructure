package application;

public class TestCalculator {

	public static void main(String[] args) {
		String infix="1-2*3+(4*5+6)*7";
		Calculator calculator=new Calculator();
		String postfix=calculator.inToPost(infix);
		System.out.println("postfix:"+postfix);
		System.out.println(infix+"="+calculator.calPostfix(postfix));
		
		calculator=new Calculator();
		System.out.println(infix+"="+calculator.calculate(infix));
		infix="1-2*3+(4*5+6)*72";
		System.out.println(infix+"="+calculator.calculate(infix));
	}

}
