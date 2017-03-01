package seq;


class Element{
	Object elem;
	int priority;
	
	public Element(Object object,int priority){
		this.elem=object;
		this.priority=priority;
	}
	
	public Object getElem(){
		return elem;
	}
	
	public void setElem(Object object){
		this.elem=object;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public void setPriority(int i){
		priority=i;
	}
}
public class SeqPriQueue  {
	private Element[] data;
	private int maxSize;
	private int count;
	private int rear;
	//private int front;顺序优先队列物理上的第一个元素下标为0，逻辑上的第一个下标为priority最小的，所以front不需要使用
	private static final int defaultSize=10;
	
	public SeqPriQueue() {
		initiate(defaultSize);
	}
	
	public SeqPriQueue (int size){
		initiate(size);
	}
	
	private void initiate (int size){
		maxSize=size;
		data=new Element[maxSize];
		rear=0;
		count=0;
	}
	
	public void append(Element obj) throws Exception {
		if (count>=maxSize) {
			throw new IndexOutOfBoundsException("full");
		}
		data[rear]=obj;
		rear=rear+1;
		count++;
	}

	public Element delete() throws Exception {
		if (count==0) {
			throw new Exception("empty");
		}
		Element temp=data[0];
		int minIndex=0;
		for(int i=0;i<count;i++){
			if (data[i].getPriority()<temp.getPriority()) {
				temp=data[i];
				minIndex=i;
			}
		}
		for(int i=minIndex+1;i<count;i++){
			data[i-1]=data[i];
		}
		rear-=1;
		count--;
		return temp;
	}

	public Element getFront() throws Exception {
		if (count==0) {
			throw new Exception("empty");
		}
		Element temp=data[0];
		for(int i=0;i<count;i++){
			if (data[i].getPriority()<temp.getPriority()) {
				temp=data[i];
			}
		}
		return temp;
	}

	public boolean notEmpty() {
		return count!=0;
	}

	public static void main(String[] args)  {
		Element[] a={new Element(1, 30),new Element(2, 20),new Element(3, 40),
				new Element(4, 20),new Element(5, 0)};
		SeqPriQueue queue=new SeqPriQueue(a.length);
		try {
			for(int i=0;i<a.length;i++){
				queue.append(a[i]);
			}
			while(queue.notEmpty()){
				Element temp=queue.delete();
				System.out.println(temp.getElem()+" "+temp.getPriority());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
