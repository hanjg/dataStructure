package list;

/**
 * 单链表
 * @author hjg
 *
 * @param <E>
 */
public class LinList<E> implements List<E>{
	/**
	 * 虚拟头结点
	 */
	private Node<E> dum;
	private Node<E> tail;
	private Node<E> cur;
	private int size;
	
	public LinList(){
		init();
	}
	@Override
	public void init() {
		dum=cur=tail=new Node<>();
		size=0;
	}

	/**
	 * 定位cur指针至下标index
	 * @param index
	 */
	private void locateCur(int index) {
		if (index==size-1) {
			cur=tail;
			return;
		}
		cur=dum;
		int i=-1;
		while(i<index){
			cur=cur.next;
			i++;
		}
	}
	@Override
	public void add(int index, E elem) {
		if (index<0||index>size) {
			throw new IndexOutOfBoundsException();
		}
		locateCur(index-1);
		Node<E> temp=new Node<E>(elem, cur.next);
		cur.next=temp;
		if (index==size) {
			tail=temp;
		}
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
		if (cur.next==tail) {
			tail=cur;
		}
		E temp=cur.next.elem;
		cur.next=cur.next.next;
		size--;
		return temp;
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
		for(cur=dum;cur.next!=null;cur=cur.next){
			if (cur.next.elem.equals(elem)) {
				removeNext();
				done=true;
				break;
			}
		}
		return done;
	}
	@Override
	public boolean contain(E elem) {
		for(cur=dum.next;cur!=null;cur=cur.next){
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
		while (cur!=null) {
			if (i!=0) {
				System.out.print(",");
			}
			System.out.print(cur.elem);
			cur=cur.next;
			i++;
		}
		System.out.println("]");
	}
	
	public static class Node<E>{
		private E elem;
		private Node<E> next;
		
		public Node(E elem,Node<E> next) {
			this.elem=elem;
			this.next=next;
		}
		public Node() {
		}
		public E getElem() {
			return elem;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setElem(E elem) {
			this.elem = elem;
		}
		public void setNext(Node<E> next) {
			this.next = next;
		}
	}
}
