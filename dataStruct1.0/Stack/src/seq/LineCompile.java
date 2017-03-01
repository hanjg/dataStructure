package seq;

public class LineCompile {//#表示退格符，@表示退行符
	public static SeqStack lineStack=new SeqStack();
		
	public static void lineCompile() throws Exception{
		char c=(char) System.in.read();
		while(c!='\n'){
			switch (c) {
			case '#':
				lineStack.pop();
				break;
			case '@':
				lineStack=new SeqStack();
				break;
			default:
				lineStack.push(c);
				break;
			}
			c=(char) System.in.read();
		}
		SeqStack seqStack=new SeqStack();
		while(lineStack.notEmpty()){
			seqStack.push(lineStack.pop());
		}
		while(seqStack.notEmpty()){
			System.out.print(seqStack.pop());
		}
	}
	public static void main(String[] args) {
		try {
			LineCompile.lineCompile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
