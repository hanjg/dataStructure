package queue;

import list.SeqList;

/**
 * 循环队列（顺序表实现）
 * @author hjg
 *
 * @param <E>
 */
public class CirQueue<E> extends SeqList<E> implements Queue<E> {
	
	/**
	 * 队首元素的位置
	 */
	private int front;
	/**
	 * 队尾元素的位置(不包含)
	 */
	private int rear;
	
	public CirQueue(int capacity) {
		super(capacity);
		front=rear=0;
	}
	public void add(E elem){
		if (front==rear&&!isEmpty()) {
			expandCapacity();
		}
		data[rear]=elem;
		rear=(rear+1)%capacity;
		size++;
	}
	/**
	 * 
	 */
	private void expandCapacity() {
		Object[] temp=data;
		capacity=size*2;
		data=new Object[capacity];
		int j=0;
		for(int i=front;i<size;i++){
			data[j++]=temp[i];
		}
		if (rear!=0) {
			for(int i=0;i<rear;i++){
				data[j++]=temp[i];
			}
		}
		front=0;
		rear=size;
	}

	@Override
	public E poll() {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException("empty");
		}
		E temp=elemData(front);
		front=(front+1)%capacity;
		size--;
		return temp;
	}

	@Override
	public E peek() {
		if (isEmpty()) {
			return null;
		}
		return get(rear-1);
	}
	
	@Override
	public void print() {
		System.out.print("[");
		for(int i=front;i!=rear;i=(i+1)%capacity){
			if (i!=front) {
				System.out.print(",");
			}
			System.out.print(elemData(i));
		}
		System.out.println("]");
	}
}
