package singleLin;


public class CirLinList {
	public Node head;
	private Node current;
	private int size;
	
	public CirLinList() {
		head=current=new Node(null, head);
		size=0;
	}
	
	//定位函数index
	private void index(int i){
		if (i<-1||i>=size) {
			throw new IndexOutOfBoundsException();
		}
		if(i==-1) {//i=-1,定位在头结点，头结点不存储数据;
					//i=0为第一个节点
			current=head;
			return;
		}//
		current=head.getNext();
		int j=0;
		while(j<i){
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
	
	public void orderInsert( int obj){
		if (isEmpty()) {
			insert(0, obj);
		}
		else {
			current=head.getNext();
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
		index(i-1);
		Object object=current.getNext().getElement();
		current.setNext(current.getNext().getNext());
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
		current=head.getNext();
		for(int i=0;i<this.size;i++){
			System.out.print(current.getElement()+",");
			current=current.getNext();
		}
		System.out.println();
	}
	public static void main(String[] args) {
		int[] a={43,4,56,76,87,7,324,33,454,43};
		CirLinList list=new CirLinList();
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
