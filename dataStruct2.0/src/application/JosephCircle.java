package application;
import list.LinList.Node;
/**
 * 循环单列表解决Joseph环问题
 * @author hjg
 *
 */
public class JosephCircle {
	private Node<Person> dum;
	private Node<Person> tail;
	private int size=0;
	
	private Node<Person> cur;
	
	public JosephCircle() {
		dum=new Node<JosephCircle.Person>(new Person(-1, -1), dum);
		cur=tail=dum;
	}
	public JosephCircle(int[] keys){
		this();
		add(keys);
	}
	private void add(int[] keys){
		for (int i=0;i<keys.length;i++) {
			Person person=new Person(i, keys[i]);
			tail.setNext(new Node<Person>(person, dum));
			tail=tail.getNext();
			size++;
		}
	}
	public Person remove(int key){
		key%=size;
		//cur停留在待删除的节点之前
		for (int i=1;i<key;i++) {
			cur=cur.getNext();
			if (cur==dum) {//略过dum
				cur=cur.getNext();
			}
		}
		//不能删除dum
		if (cur.getNext()==dum) {
			cur=cur.getNext();
		}
		Node<Person> temp=cur.getNext();
		cur.setNext(temp.getNext());
		size--;
		return temp.getElem();
	}
	public int size(){
		return size;
	}
	public static class Person{
		private int index;
		private int key;
		public Person(int index, int key) {
			this.index=index;
			this.key=key;
		}
		public int getIndex() {
			return index;
		}
		public int getKey() {
			return key;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public void setKey(int key) {
			this.key = key;
		}
	} 
}
