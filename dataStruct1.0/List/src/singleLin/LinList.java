package singleLin;


//带头结点的单链表

class Node{
	private Object element;
	private Node next;
	
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

public class LinList {
	public Node head;
	private Node current;
	private int size;
	
	public LinList() {
		head=current=new Node(null);
		size=0;
	}
	
	public LinList mergeList(LinList listB) throws Exception{//AB非增排列，将A和B合并为C，使得合并的表中元素依然非增排列
		int i=0,j=0,k=0;
		LinList listC=new LinList();
		while(i<this.size&&j<listB.size){
			if ((Integer)this.getData(i)<(Integer)listB.getData(j)) {
				listC.insert(k, this.getData(i));
				i++;k++;
			}
			else {
				listC.insert(k, listB.getData(j));
				j++;k++;
			}
		}
		while(i<size){
			listC.insert(k, this.getData(i));
			i++;k++;
		}
		while(j<listB.size){
			listC.insert(k, listB.getData(j));
			j++;k++;
		}
		return listC;
	}
	
	
	public void mergeList2(LinList listB) throws Exception{//AB非增排列，将A和B合并为C，使得合并的表中元素依然非增排列
		//C以A的头结点为头结点,指针实现,输出由于并未调用insert函数使得size并未扩大，需使用指针实现的print2()
		Node pa=this.head.getNext();
		Node pb=listB.head.getNext();
		Node pc=this.head;
		while(pa!=null&&pb!=null){
			if ((Integer)pa.getElement()<(Integer)pb.getElement()) {
				pc.setNext(pa);
				pc=pa;
				pa=pa.getNext();
			}
			else {
				pc.setNext(pb);
				pc=pb;
				pb=pb.getNext();
			}
		}
		pc.setNext(pa!=null?pa:pb);
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
	
	public int locateElem(Object object){
		current=head.getNext();
		for(int i=0;i<size;i++){//i为链表中节点的下标，从[0,size)
			if (current.getElement().equals(object)) {
				return i;
			}
			current=current.getNext();
		}
		return -1;
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
				current=current.getNext();
				j++;
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

	public boolean isEmpty() {
		return size==0;
	}
	
	public boolean isEmpty2(){
		return head.getNext()==null;
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
	
	public void print2() throws Exception{//指针实现，配合mergelist2
		if (isEmpty()) {
			throw new Exception("empty");
		}
		current=this.head.getNext();
		while(current!=null){
			System.out.print(current.getElement()+" ");
			current=current.getNext();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		/*int[] a={43,4,56,76,87,7,324,33,454,43};
		LinList list=new LinList();
		try {
			for(int i=0;i<a.length;i++){
					list.orderInsert(a[i]);
			}
			list.print();
			list.insert(1, 100);list.print();
			list.delete(5);list.print();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		int[] a={2,5,6,7,9,34,56,77};
		int[] b={4,5,6,7,9,76,87,99,101};
		LinList listA=new LinList();
		try {
			for(int i=0;i<a.length;i++){
				listA.insert(i, a[i]);
			}
			LinList listB=new LinList();
			for(int i=0;i<b.length;i++){
				listB.insert(i, b[i]);
			}
			listA.print();listB.print();
			LinList listC=listA.mergeList(listB);listC.print();
			//listA.mergeList2(listB);listA.print2();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
