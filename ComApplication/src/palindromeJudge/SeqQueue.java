package palindromeJudge;

public class SeqQueue {
	final int defaultSize=10;
	int maxSize;
	Object[] data;
	int rear;
	int front;
	int count;
	
	public SeqQueue() {
		initiate(defaultSize);
	}
	
	public SeqQueue(int size){
		initiate(size);
	}
	private void initiate (int size){
		maxSize=size;
		data=new Object[maxSize];
		rear=front=0;
		count=0;
	}
	public void append(Object obj) throws Exception {
		if (count>0&&front==rear) {
		//if(count>=maxSize){
			throw new IndexOutOfBoundsException("full");
		}
		data[rear]=obj;
		rear=(rear+1)%maxSize;
		count++;
	}

	public Object delete() throws Exception {
		if (count==0) {
			throw new Exception("empty");
		}
		Object temp=data[front];
		front=(front+1)%maxSize;
		count--;
		return temp;
	}

	public Object getFront() throws Exception {
		if (count==0) {
			throw new Exception("empty");
		}
		return data[front];
	}

	public boolean notEmpty() {
		return count!=0;
	}

	public static void main(String[] args) {
		int[] a={3,43,5,3,34,3,43};
		SeqQueue queue=new SeqQueue(a.length);
		try {
			for(int i=0;i<a.length;i++){
				queue.append(a[i]);
			}
			while(queue.notEmpty()){
				System.out.print(queue.delete()+" ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
