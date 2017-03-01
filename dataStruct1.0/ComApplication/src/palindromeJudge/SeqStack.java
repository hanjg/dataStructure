package palindromeJudge;


import java.util.Arrays;


public class SeqStack {
	int defaultSize=10;
	int top;
	Object[] stack;
	int maxStackSize;
	int defaultIncreaseSize=10;
	
	public SeqStack(){
		initiate(defaultSize);
	}
	public SeqStack(int size){
		initiate(size);
	}
	private void initiate(int size){
		maxStackSize=size;
		stack=new Object[maxStackSize];
		top=0;
	}
	
	public void push(Object obj){
		if(top==maxStackSize){
			maxStackSize+=defaultIncreaseSize;
			stack=Arrays.copyOf(stack, maxStackSize);
		}
		stack[top]=obj;
		top++;
	}
	
	public Object pop() throws Exception{
		if (top==0) {
			throw new Exception("empty");
		}
		top--;
		return stack[top];
	}
	
	public Object getTop() throws Exception{
		if (top==0) {
			throw new Exception("empty");
		}
		return stack[top-1];
	}
	
	public boolean notEmpty(){
		return(top>0);
	}
	public static void main(String[] args) {
		int[] s={34,4,6,6,7,6,76,3,43,5,45,35,453,5};
		SeqStack seqStack=new SeqStack();
		try {
			for(int i=0;i<s.length;i++){
				seqStack.push(s[i]);
			}
			while(seqStack.notEmpty()){
				System.out.print(seqStack.pop()+",");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
