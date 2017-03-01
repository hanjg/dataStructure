//使用泛型的顺序栈
package seq;

import java.lang.reflect.Array;
import java.util.Arrays;



public class TSeqStack<T> {
	private T[] elemData;
	//public Class<T> type;
	private int top;
	private int maxStackSize;
	static final int defaultSize=10;
	static final int defaultIncreaseSize=10;
	
	public TSeqStack(Class<T> type){
		initiate(type,defaultSize);
	}
	
	public TSeqStack(Class<T>type ,int size){
		initiate(type,size);
	}
	
	@SuppressWarnings("unchecked")
	private void initiate(Class<T> type,int size){
		maxStackSize=size;
		//this.type=type;
		elemData=(T[])(Array.newInstance(type, maxStackSize));
		top=0;
	}
	
	public void push(T obj){
		if(top==maxStackSize){
			maxStackSize+=defaultIncreaseSize;
			elemData=Arrays.copyOf(elemData, maxStackSize);
		}
		elemData[top]=obj;
		top++;
	}
	
	public T pop() throws Exception{
		if (top==0) {
			throw new Exception("empty");
		}
		top--;
		return elemData[top];
	}
	
	public T getTop() throws Exception{
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
		TSeqStack<Integer> seqStack=new TSeqStack<>(Integer.class);
		try {
			for(int i=0;i<s.length;i++){
				seqStack.push(s[i]);;
			}
			while(seqStack.notEmpty()){
				System.out.print(seqStack.pop()+" ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
