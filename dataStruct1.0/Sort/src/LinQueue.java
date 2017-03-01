

class Node{
	 Object element;
	 Node next;
	
	public Node(Node nextval) {
		next=nextval;
	}
	
	public Node(Object obj,Node nextval) {
		element=obj;
		next=nextval;
	}
	
	public Node getNext(){
		return next;
	}
	
	public void  setNext(Node nextval) {
		next=nextval;
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

public class LinQueue {
	Node front;
	Node rear;
	int count;
	
	LinQueue(){
		initiate();
	}
	
	private void initiate(){
		front=rear=null;
		count=0;
	}
	
	public void append(Object obj) throws Exception {
		Node newNode=new Node(obj, null);
		if (count==0) {
			front=rear=newNode;
			count++;
		}
		else {
			rear.next=newNode;
			rear=newNode;
			count++;
		}
	}

	public Object delete() throws Exception {
		if (count==0) {
			throw new Exception("empty");
		}
		Node temp=front;
		front=front.next;
		count--;
		return temp.getElement();
	}
	
	public Object getFront() throws Exception {
		if (count==0) {
			throw new Exception("empty");
		}
		return front.getElement();
	}
	
	public boolean notEmpty() {
		return count!=0;
	}

	public static void main(String[] args) {
		int[] a={4,5,6,43,5,234,34,3};
		LinQueue linQueue=new LinQueue();
		
		try {
			for(int i=0;i<a.length;i++){
				linQueue.append(a[i]);
			}
			Node t=linQueue.front;
			for(int i=0;i<linQueue.count;i++){
				System.out.print(t.element+" ");
				t=t.next;
			}
			System.out.println();
			linQueue.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Node t=linQueue.front;
		for(int i=0;i<linQueue.count;i++){
			System.out.print(t.element+" ");
			t=t.next;
		}
	}

}
