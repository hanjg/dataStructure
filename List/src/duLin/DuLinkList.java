package duLin;
//循环双向链表

class Node{
	Object element;
	Node prior;
	Node next;
	
	public Node() {
	}
	
	public Node(Object element) {
		this.element=element;
	}
	
	public Node(Object element,Node prior,Node next){
		this.element=element;
		this.prior=prior;
		this.next=next;
	}
}

public class DuLinkList{
	public Node head;
	private Node current;
	private int size;
	
	public DuLinkList() {
		head=new Node(null);
		head.prior=head;
		head.next=head;
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
		current=head.next;
		int j=0;
		while(j<i){//j=size时，current==null
			current=current.next;
			j++;
		}
	}
	
	public void insert(int i,Object elem){
		if (i<0||i>size) {
			throw new IndexOutOfBoundsException("none");
		}
		index(i-1);
		Node node=new Node(elem, current, current.next);
		current.next=node;
		node.next.prior=node;
		size++;
	}
	
	public void orderInsert( int obj){
		if (isEmpty()) {
			insert(0, obj);
		}
		else {
			current=head.next;
			int j=0;
			while(j<size&&obj>(Integer)current.element){
				current=current.next;j++;
			}
			insert(j, obj);
		}
	}
	
	public Object delete(int i) throws Exception{
		if (size==0) {
			throw new Exception("empty");
		}
		if (i<0||i>=size) {
			throw new IndexOutOfBoundsException("none");
		}
		index(i);
		Object temp=current.element;
		current.prior.next=current.next;
		current.next.prior=current.prior;
		size--;
		return temp;
	}
	
	public Object getData(int i) throws Exception {
		if (i<0||i>size-1) {
			throw new IndexOutOfBoundsException();
		}
		index(i);
		return current.element;
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		return size==0;
	}
	
	public void print() throws Exception{
		if (isEmpty()) {
			throw new Exception("empty");
		}
		current=head.next;
		for(int i=0;i<this.size;i++){
			System.out.print(current.element+",");
			current=current.next;
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		int[] a={43,4,56,76,87,7,324,33,454,43};
		DuLinkList list=new DuLinkList();
		try {
			for(int i=0;i<a.length;i++){
					list.orderInsert(a[i]);
			}
			list.print();
			list.insert(1, 100);list.print();
			list.delete(5);list.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
