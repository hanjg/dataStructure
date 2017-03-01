package seq;

import java.util.Arrays;

import Interface.Stack;

public class SeqStack implements Stack{
	private Object[] elemData;
	private int top;
	private int maxStackSize;
	static final int defaultIncreaseSize=10;
	static final int defaultInitSize=10;
	
	public SeqStack(){
		initiate(defaultInitSize);
	}
	
	public SeqStack(int size){
		initiate(size);
	}
	
	private void initiate(int size){
		maxStackSize=size;
		elemData=new Object[maxStackSize];
		top=0;
	}
	
	public void push(Object obj){
		if(top==maxStackSize){
			maxStackSize+=defaultIncreaseSize;
			elemData=Arrays.copyOf(elemData, maxStackSize);
		}
		elemData[top]=obj;
		top++;
	}
	
	public Object pop() throws Exception{
		if (top==0) {
			throw new Exception("empty");
		}
		top--;
		return elemData[top];
	}
	
	public Object getTop() throws Exception{
		if (top==0) {
			throw new Exception("empty");
		}
		return elemData[top-1];
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
