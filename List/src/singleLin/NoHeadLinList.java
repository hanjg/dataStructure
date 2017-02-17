package singleLin;


//无头结点的单链表
public class NoHeadLinList {
	public Node head;
	private Node current;
	private int size;
	
	public NoHeadLinList() {
		head=current=new Node(null, null);
		size=0;
	}
	
	private void index(int i){
		if (i<0||i>=size) {
			throw new IndexOutOfBoundsException();
		}
		else {
			int j=0;
			current=head;
			while(j<i){
				current=current.getNext();
				j++;
			}
		}
	}
	public void insert(int i,Object obj){
		if (i<0||i>size) {
			throw new IndexOutOfBoundsException();
		}
		if (i==0) {
			Node pNode=new Node(obj, head);
			head=pNode;
			size++;
		}
		else {
			index(i-1);
			Node pNode=new Node(obj, current.getNext());
			current.setNext(pNode);
			size++;
		}
	}
	
	public void orderInsert( int obj){
		if (isEmpty()) {
			insert(0, obj);
		}
		else {
			current=head;
			int j=0;
			while(j<size&&obj>(Integer)current.getElement()){
				current=current.getNext();j++;
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
		if (i==0) {
			Object object=head.getElement();
			head=head.getNext();
			size--;
			return object;
		}
		else {
			index(i-1);
			Object object=current.getNext().getElement();
			current.setNext(current.getNext().getNext());
			size--;
			return object;
		}
	}
	
	public Object getData(int i) {
		if (i<0||i>size-1) {
			throw new IndexOutOfBoundsException();
		}
		index(i);
		return current.getElement();
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public int getSize() {
		return size;
	}

	public void print() throws Exception{
		if (isEmpty()) {
			throw new Exception("empty");
		}
		current=head;
		for(int i=0;i<this.size;i++){
			System.out.print(current.getElement()+",");
			current=current.getNext();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		int[] a={43,4,56,76,87,7,324,33,454,43};
		NoHeadLinList list=new NoHeadLinList();
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
