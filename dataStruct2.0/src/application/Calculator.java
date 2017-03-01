package application;

import stack.SeqStack;
import stack.Stack;

/**
 * inToPost,calPostfix适用于10以内的四则运算（带括号）<p>
 * calculate适用于int型整数的四则运算（带括号）
 * @author hjg
 *
 */
public class Calculator {
	/**
	 * 操作符栈，中缀转后缀时使用
	 */
	private Stack<Character> operator;
	/**
	 * 操作数栈,后缀求值时使用
	 */
	private Stack<Double> operand;
	
	private char[] infix;
	private StringBuilder postfix;
	
	public Calculator() {
		operator=new SeqStack<>();
		operand=new SeqStack<>();
		postfix=new StringBuilder();
	}
	
	public double calculate(String string){
		infix=string.replace(" ", "").toCharArray();
		for (int i=0;i<infix.length;i++) {
			char ch=infix[i];
			if (Character.isDigit(ch)) {
				double num=ch-'0';
				while (i+1<infix.length&&Character.isDigit(infix[i+1])) {
					num=10*num+infix[++i]-'0';
				}
				operand.push(num);
			}else if (operator.isEmpty()||ch=='(') {
				operator.push(ch);
			}else if (ch==')') {
				while (!operator.peek().equals('(')) {
					executeOperator();
				}
				operator.pop();//弹出'('
			}else {
				while (!operator.isEmpty()&&priority(operator.peek(), ch)>=0) {
					executeOperator();
				}
				operator.push(ch);
			}
		}
		while (!operator.isEmpty()) {
			executeOperator();
		}
		return operand.pop();
	}

	/**
	 * 操作数栈顶的两个数+操作符栈顶的操作符
	 */
	private void executeOperator() {
		double num2=operand.pop();
		double num1=operand.pop();
		operand.push(cal(num1, num2, operator.pop()));
	}
	/**
	 * 中序表达式转后序表达式<p>
	 * 操作数直接输出<p>
	 * '('入栈前无需比较优先级，直接入栈<p>
	 * ')'入栈前无需比较优先级，弹出所有操作符并输出，直到遇到'('，之后'('弹出，')'不入栈，')('不输出<p>
	 * 其余符号入栈时弹出所有操作符并输出，直到操作符栈顶的优先级小于该符号的优先级
	 * @return
	 */
	public String inToPost(String string){
		string=string.replaceAll(" ", "");
		this.infix=string.toCharArray();
		for (char c:infix) {
			if (Character.isDigit(c)) {
				postfix.append(c);
			}else if (operator.isEmpty()) {
				operator.push(c);
			}else if (c=='(') {
				operator.push(c);
			}else if (c==')') {
				while (!operator.peek().equals('(')) {
					postfix.append(operator.pop());
				}
				operator.pop();
			}else {
				while (!operator.isEmpty()&&priority(operator.peek(), c)>=0) {
					postfix.append(operator.pop());
				}
				operator.push(c);
			}
		}
		while (!operator.isEmpty()) {
			postfix.append(operator.pop());
		}
		return postfix.toString();
	}
	/**
	 * 计算后缀表达式的值
	 * @param postfix
	 * @return
	 */
	public double calPostfix(String postfix){
		char[] chararray=postfix.toCharArray();
		for (int i=0;i<chararray.length;i++) {
			char ch=chararray[i];
			if (Character.isDigit(ch)) {
				double num=ch-'0';
				operand.push(num);
			}else {
				double num2=operand.pop();
				double num1=operand.pop();
				operand.push(cal(num1, num2, ch));
			}
		}
		return operand.pop();
	}
	private double cal(double i, double j, char operator){
		if (operator=='+') {
			return i+j;
		}else if (operator=='-') {
			return i-j;
		}else if (operator=='*') {
			return i*j;
		}else {
			return i/j;
		}
	}
	/**
	 * 比较栈中操作符的优先级的大小,-1表示i的优先级小于j的优先级
	 * @param i 栈中元素
	 * @param j 即将入栈的元素
	 * @return
	 */
	private int priority(char i, char j){
		if (i=='(') {//j为()的情况已经处理
			return -1;
		}else if (i=='+'||i=='-') {
			return j==')'?1:(j=='+'||j=='-'?0:-1);
		}else {
			return j=='*'||j=='/'?0:1;
		}
	}
}
