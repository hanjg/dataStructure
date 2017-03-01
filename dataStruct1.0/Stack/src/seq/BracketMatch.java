package seq;


public class BracketMatch {//(){}[]的匹配
	public static void bracketMatch(String string) throws Exception{
		char[] expChars=string.toCharArray();
		SeqStack stack=new SeqStack();
		for(int i=0;i<expChars.length;i++){
			if (expChars[i]=='('||expChars[i]=='['||expChars[i]=='{') {
				stack.push(expChars[i]);;
			}
			else if ((expChars[i]==')'||expChars[i]==']'||expChars[i]=='}')&&!stack.notEmpty()) {
				System.out.println("右括号多");return;
			}
			else if (expChars[i]==')'&&(char)stack.getTop()=='(') {
				stack.pop();
			}
			else if (expChars[i]==')'&&(char)stack.getTop()!='(') {
				System.out.println("括号匹配次序错误");return;
			}
			else if (expChars[i]==']'&&(char)stack.getTop()=='[') {
				stack.pop();
			}
			else if (expChars[i]==']'&&(char)stack.getTop()!='[') {
				System.out.println("括号匹配次序错误");return;
			}
			else if (expChars[i]=='}'&&(char)stack.getTop()=='{') {
				stack.pop();
			}
			else if (expChars[i]=='}'&&(char)stack.getTop()!='{') {
				System.out.println("括号匹配次序错误");return;
			}
		}
		if (stack.notEmpty()) {
			System.out.println("左括号多"); 
		}
		else {
			System.out.println("匹配正确");
		}
	}
	public static void main(String[] args) {
		String string="({}sdfsdfd[]s";
		String string2="{}}(dfsd)";
		String string3="{)sdf";
		String string4="{}{}(d)df";
		try {
			bracketMatch(string);
			bracketMatch(string2);
			bracketMatch(string3);
			bracketMatch(string4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
