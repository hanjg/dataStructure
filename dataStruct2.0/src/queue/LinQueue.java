package queue;

import list.LinList;

/**
 * 队列（链表实现）
 * @author hjg
 *
 * @param <E>
 */
public class LinQueue<E> extends LinList<E> implements Queue<E>{

	@Override
	public E poll() {
		return remove(0);
	}

	@Override
	public E peek(){
		if(isEmpty()) return null;
		return get(size()-1);
	}

}
