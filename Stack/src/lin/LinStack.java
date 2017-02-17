package lin;

import Interface.Stack;

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

public class LinStack implements Stack {
	public Node head;
	private int size;
	
	public LinStack() {
		head=null;
		size=0;
	}
	
	public void push(Object obj) throws Exception {
		head=new Node(obj, head);
		size++;
	}

	
	public Object pop() throws Exception {
		if(size==0){
			throw new Exception("empty");
		}
		Object obj=head.element;
		head=head.next;
		size--;
		return obj;
	}

	
	public Object getTop() throws Exception {
		if (size==0) {
			throw new Exception("empty");
		}
		return head.element;
	}

	
	public boolean notEmpty() {
		return(size!=0);
	}

	public static void main(String[] args) {
		int[] s={34,4,6,6,7,6,76};
		LinStack LinStack=new LinStack();
		try {
			for(int i=0;i<s.length;i++){
				LinStack.push(s[i]);
			}
			while(LinStack.notEmpty()){
				System.out.print(LinStack.pop()+",");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
