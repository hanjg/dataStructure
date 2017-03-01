package stack;

import list.LinList;

/**
 * 栈（链表实现）
 * 
 * @author hjg
 *
 * @param <E>
 */
public class LinStack<E> extends LinList<E> implements Stack<E> {

	@Override
	public void push(E elem) {
		add(elem);
	}

	@Override
	public E pop() {
		return remove();
	}

	@Override
	public E peek() {
		if (isEmpty())
			return null;
		return get(size() - 1);
	}

}
