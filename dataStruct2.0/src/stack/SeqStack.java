package stack;

import list.SeqList;

/**
 * 栈（顺序表实现）
 * @author hjg
 *
 * @param <E>
 */
public class SeqStack<E> extends SeqList<E> implements Stack<E> {

	@Override
	public void push(E elem){
		add(elem);
	}

	@Override
	public E pop() {
		return remove();
	}

	@Override
	public E peek() {
		if (isEmpty()){
			return null;
		}
		return get(size()-1);
	}

}
