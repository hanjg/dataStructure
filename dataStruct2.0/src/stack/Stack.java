package stack;

public interface Stack<E> {
	public void push(E elem);
	public E pop();
	public E peek();
	public boolean isEmpty();
	public void print();
}
