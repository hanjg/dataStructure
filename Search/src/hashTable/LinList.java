package hashTable;
//带头结点的单链表

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

public class LinList {
	Node head;
	Node current;
	int size;
	
	public LinList() {
		head=current=new Node(null);
		size=0;
	}
	
	//定位函数index
	public void index(int i){
		if (i<-1||i>=size) {
			throw new IndexOutOfBoundsException();
		}
		if(i==-1) {//i=-1,定位在头结点，头结点不存储数据;
					//i=0为第一个节点
			current=head;
			return;
		}//
		current=head.next;
		int j=0;
		while(current!=null&&j<i){//j=i时，current==null
			current=current.next;
			j++;
		}
	}
	
	public void insert(int i,Object obj){
		if (i<0||i>size) {
			throw new IndexOutOfBoundsException();
		}
		index(i-1);
		Node pNode=new Node(obj, current.next);
		current.next=pNode;
		size++;
	}
	
	public void insert(Object object){
		insert(size, object);
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
		if(i<0||i>=size){
			throw new IndexOutOfBoundsException();
		}
		index(i-1);
		Object object=current.next.getElement();
		current.next=current.next.next;
		size--;
		return object;
	}
	
	public Object getData(int i) {
		if (i<0||i>size-1) {
			throw new IndexOutOfBoundsException();
		}
		index(i);
		return current.getElement();
	}
	
	public int locateData(Object object){
		int i;//i为链表中节点的下标，从[0,size),i=-1表示不存在值为object的节点
		current=head.next;
		for(i=0;i<size;i++){
			if (current.getElement().equals(object)) {
				return i;
			}
			else {
				current=current.getNext();
			}
		}
		return -1;
	}
	public int size(){
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
		LinList list=new LinList();
		int a[]={4,5,2,4,5};
		for(int i=0;i<a.length;i++){
			//list.orderInsert(a[i]);
			list.insert(a[i]);
		}
		try {
			list.print();
			list.insert(0, 33);
			list.print();
			list.insert(1, 99);
			list.print();
			list.delete(4);
			list.print();
			list.delete(0);
			list.print();
			System.out.println(list.locateData(6));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
