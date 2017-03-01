package lin;


import Interface.Queue;

class Node{
	 Object element;
	 Node next;
	
	public Node(Node next) {
		this.next=next;
	}
	
	public Node(Object obj,Node next) {
		element=obj;
		this.next=next;
	}
	
	public Node getNext(){
		return next;
	}
	
	public void  setNext(Node next) {
		this.next=next;
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

public class LinQueue implements Queue{
	private Node front;
	private Node rear;
	private int count;
	
	public LinQueue(){
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
	
	public int getSize(){
		return count;
	}
	
	public boolean notEmpty() {
		return count!=0;
	}

	public static void main(String[] args) {
		int[] a={3,43,5,3,34,3,43};
		LinQueue queue=new LinQueue();
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
