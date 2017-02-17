package seq;//计算以#结尾的表达式值(计算n位数的+-*/())

import java.io.IOException;

public class EvaluateExpression {
	TSeqStack<Character> OPTR;//存放操作符的栈
	TSeqStack<Double> OPND;//存放操作数的栈
	char c='#';
	char pre='#';//标记c之前的字符
	
	public EvaluateExpression() throws IOException {
		OPTR=new TSeqStack<>(Character.class);
		OPND=new TSeqStack<>(Double.class);
		OPTR.push('#');
		c=getChar();
	}
	
	/*public char getChar() throws IOException{
		while((c=(char)System.in.read())!='#'){
			return c;
		}
		return c;//当输入为#时返回#并且结束
	}*/
	
	public char getChar() throws IOException{
		pre=c;
		c=(char)System.in.read();
		while(c!='#'){
			return c;
		}
		return c;
	}

	public char priority(char a,char b) throws Exception{//操作符的优先级
		if (a=='+'||a=='-') {
			if (b=='*'||b=='/'||b=='(') {
				return '<';
			}
		}
		if (a=='*'||a=='/') {
			if (b=='(') {
				return '<';
			}
		}
		if (a=='(') {
			if (b=='+'||b=='-'||b=='*'||b=='/'||b=='(') {
				return '<';
			}
			if (b==')') {
				return '=';
			}
			if (b=='#') {
				throw new Exception();
			}
		}
		if (a==')'&&b=='(') {
			throw new Exception();
		}
		if (a=='#') {
			if (b=='+'||b=='-'||b=='*'||b=='/'||b=='(') {
				return '<';
			}
			if (b=='#') {
				return '=';	
			}
			if (b==')') {
				throw new Exception();
			}
		}
		return '>';
	}
	
	public double operate(double a,char theta,double b){
		if (theta=='+') return a+b;
		else if(theta=='-') return a-b;
		else if(theta=='*') return a*b;
		else return a/b;
		
	}

	public void evaluate() throws Exception{
		while(c!='#'||OPTR.getTop()!='#'){//当c和操作符栈顶都为#时结束
			if (Character.isDigit(c)) {
				if (Character.isDigit(pre)) {
					double temp=OPND.pop();
					OPND.push(10*temp+Double.valueOf(String.valueOf(c)));
				}
				else {
					OPND.push(Double.valueOf(String.valueOf(c)));
				}
				pre=c;
				c=getChar();
			}
			else {
				switch (priority(OPTR.getTop(), c)) {
					case '<':
						OPTR.push(c);
						pre=c;
						c=getChar();
						break;
					case '=':
						OPTR.pop();
						pre=c;
						c=getChar();
						break;
					case'>':
						double b=OPND.pop();
						double a=OPND.pop();
						OPND.push(operate(a, OPTR.pop(), b));
						break;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			EvaluateExpression expression=new EvaluateExpression();
			expression.evaluate();
			System.out.println("result:"+expression.OPND.getTop());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
