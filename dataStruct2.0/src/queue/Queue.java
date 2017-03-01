package queue;

public interface Queue<E> {
	public void add(E elem);
	public E poll();
	public E peek();
	public void print();
	public boolean isEmpty();
	public int size();
}
