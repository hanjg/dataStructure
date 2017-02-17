package bankSimulation.java;

class Event {//客户事件，包括客户到达事件和客户离开事件
	//带头结点的单链表
	
	int occurTime;
	int nType;//0表示到达事件，1-4表示第几个窗口的客户离开事件
	
	public Event(int time,int type) {
		this.occurTime=time;
		this.nType=type;
	}
	
	public Event() {
	}
}

public class EventList {
	public Node head;
	private Node current;
	private int size;

	public EventList() {
		head=current=new Node(null);
		size=0;
	}

	private void index(int i){//定位函数index,使得current指向下标为i的节点， i满足[-1,size)
		if (i<-1||i>=size) {
			throw new IndexOutOfBoundsException();
		}
		if(i==-1) {//i=-1,定位在头结点，头结点不存储数据;
					//i=0为第一个存储数据的节点
			current=head;
			return;
		}//
		current=head.getNext();
		int j=0;
		while(j<i){//j=size时，current==null
			current=current.getNext();
			j++;
		}
	}

	public void insert(int i,Object obj){
		if (i<0||i>size) {
			throw new IndexOutOfBoundsException();
		}
		index(i-1);
		Node pNode=new Node(obj, current.getNext());
		current.setNext(pNode);
		size++;
	}

	public void orderInsert( Event event){
		if (isEmpty()) {
			insert(0, event);
		}
		else {
			current=head.next;
			int j=0;
			while(j<size&&event.occurTime>((Event)current.element).occurTime){
				current=current.next;j++;
			}
			insert(j, event);
		}
	}

	public Event delete(int i) throws Exception{
		if (size==0) {
			throw new Exception("empty");
		}
		if(i<0||i>=size){
			throw new IndexOutOfBoundsException();
		}
		index(i-1);
		Object object=current.getNext().getElement();
		current.setNext(current.getNext().getNext());
		size--;
		return (Event)object;
	}

	public boolean isEmpty() {
		return size==0;
	}

	public Object getData(int i) {
		if (i<0||i>size-1) {
			throw new IndexOutOfBoundsException();
		}
		index(i);
		return current.getElement();
	}

	public int getSize() {
		return size;
	}

	public void print() throws Exception{
		if (isEmpty()) {
			throw new Exception("empty");
		}
		current=head.getNext();
		for(int i=0;i<this.size;i++){
			System.out.print(current.getElement()+",");
			current=current.getNext();
		}
		System.out.println();
	}

	class Node{
		Object element;
		Node next;
		
		public Node(Node nextNode) {
			next=nextNode;
		}
		
		public Node(Object obj,Node nextNode) {
			element=obj;
			next=nextNode;
		}
		
		public Node getNext(){
			return next;
		}
		
		public void  setNext(Node nextNode) {
			next=nextNode;
		}
		
		public Object getElement() {
			return element;
		}
		
		public void setElement(Object obj) {
			element=obj;
		}
		
		public String toString() {
			return element.toString();
		}
	}

	public static void main(String[] args) {
		
	}
}
