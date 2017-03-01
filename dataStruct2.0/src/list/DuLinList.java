package list;

/**
 * 双向链表
 * @author hjg
 *
 * @param <E>
 */
public class DuLinList<E> implements List<E> {
	private Node<E> dum;
	private Node<E> cur;
	private int size;
	
	public DuLinList() {
		init();
	}
	@Override
	public void init() {
		dum=cur=new Node<>();
		cur.next=cur;
		cur.pre=cur;
		size=0;
	}

	/**
	 * 定位cur指针至下标index
	 * @param index
	 */
	private void locateCur(int index) {
		if (index<size/2) {
			cur=dum;
			int i=-1;
			while(i<index){
				cur=cur.next;
				i++;
			}
		}
		else {
			cur=dum;
			int i=size;
			while(i>index){
				cur=cur.pre;
				i--;
			}
		}
	}
	@Override
	public void add(int index, E elem) {
		if (index<0||index>size) {
			throw new IndexOutOfBoundsException();
		}
		locateCur(index-1);
		Node<E> temp=new Node<E>(elem, cur, cur.next);
		cur.next=temp;
		temp.next.pre=temp;
		size++;
	}
	@Override
	public void add(E elem){
		try {
			add(size, elem);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除cur.next节点
	 * @return cur.next节点的值
	 */
	private E removeNext() {
		Node<E> temp=cur.next;
		cur.next=temp.next;
		cur.next.pre=cur;
		size--;
		return temp.elem;
	}
	@Override
	public E remove(int index) {
		if (index<0||index>=size) {
			throw new IndexOutOfBoundsException();
		}
		locateCur(index-1);
		E temp = removeNext();
		return temp;
	}
	@Override
	public E remove() {
		return remove(size-1);
	}
	@Override
	public boolean remove(E elem) {
		boolean done=false;
		//删除cur.next
		for(cur=dum;cur.next!=dum;cur=cur.next){
			if (cur.next.elem.equals(elem)) {
				removeNext();
				done=true;
			}
		}
		return done;
	}
	@Override
	public boolean contain(E elem) {
		for(cur=dum.next;cur!=dum;cur=cur.next){
			if (cur.elem.equals(elem)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public E get(int index) {
		if (index<0||index>=size) {
			throw new IndexOutOfBoundsException();
		}
		locateCur(index);
		return cur.elem;
	}
	@Override
	public int size() {
		return size;
	}
	@Override
	public boolean isEmpty() {
		return size==0;
	}
	@Override
	public void print() {
		System.out.print("[");
		cur=dum.next;
		int i=0;
		while(cur!=dum){
			if (i!=0) {
				System.out.print(",");
			}
			System.out.print(cur.elem);
			cur=cur.next;
			i++;
		}
		System.out.println("]");
	}
	public void printReverse(){
		System.out.print("[");
		cur=dum.pre;
		int i=0;
		while(cur!=dum){
			if (i!=0) {
				System.out.print(",");
			}
			System.out.print(cur.elem);
			cur=cur.pre;
			i++;
		}
		System.out.println("]");
	}
	private static class Node<E>{
		E elem;
		Node<E> pre;
		Node<E> next;
		
		Node(E elem, Node<E> pre,Node<E> next) {
			this.elem=elem;
			this.pre=pre;
			this.next=next;
		}
		Node(){
			
		}
	}
	
}
