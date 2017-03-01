package heap;

import list.List;

/**
 * 使用数组表示完全二叉树，实现最小堆(父节点<=子节点)
 * @author hjg
 *
 * @param <E>
 */
@SuppressWarnings("unchecked")
public class MinHeap<E extends Comparable<? super E>>{
	/**
	 * 下标为0处不存储数据<p>
	 * 对于下标i的节点，左孩子为2i，右孩子为2i+1，父节点为(int)i/2<p>
	 * size表示最后一个有效元素的下标
	 */
	private Object[] data;
	/**
	 * 有效数据的容量
	 */
	private int capacity;
	private int size;
	
	public MinHeap(int capacity){
		data=new Object[capacity+1];
		this.capacity=capacity;
		size=0;
	}
	
	public MinHeap(int capacity, List<E> list){
		this(capacity);
		buildHeap(list);
	}
	private void buildHeap(List<E> list){
		for(int i=1;i<=list.size();i++){
			try {
				data[i]=list.get(i-1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		size=list.size();
		//从第一个非叶节点开始下滤
		for(int i=size/2;i>0;i--){
			percolateDown(i);
		}
	}
	public void add(E elem){
		if (size==capacity) {
			Object[] temp=data;
			data=new Object[data.length*2];
			System.arraycopy(data, 0, temp, 0, data.length);
			capacity=data.length-1;
		}
		data[++size]=elem;
		percolateUp(size);
	}

	public E poll(){
		E min=peek();
		data[1]=data[size--];
		//从hole开始下滤
		percolateDown(1);
		return min;
	}

	public E peek(){
		if (isEmpty()) {
			throw new NullPointerException();
		}
		return (E)data[1];
	}

	/**
	 * 上滤
	 * @param hole 开始上滤的下标
	 */
	private void percolateUp(int hole) {
		E temp=(E)data[hole];
		while(hole>1&&((E)temp).compareTo((E)data[hole/2])<0){
			data[hole]=data[hole/2];
			hole/=2;
		}
		data[hole]=temp;
	}

	/**
	 * 下滤
	 * @param hole 开始下滤的下标
	 */
	private void percolateDown(int hole) {
		Object temp=data[hole];
		while(hole*2<=size){ 
			int child=hole*2;
			//右孩子存在并且比左孩子小
			if (child<size&&((E)data[child+1]).compareTo((E)data[child])<0) {
				child++;
			}
			if (((E)data[child]).compareTo((E)temp)<0) {
				data[hole]=data[child];
			}else {
				break;
			}
			hole=child;
		}
		data[hole]=temp;
	}
	public boolean isEmpty(){
		return size==0;
	}
	public int getSize(){
		return size;
	}
	public void print(){
		System.out.println("[");
		print(1, 1);
		System.out.println("]");
	}
	private void print(int root, int level){
		if (root>size) {
			return;
		}
		print(2*root+1, level+1);
		for(int i=1;i<level;i++){
			System.out.print("\t");
		}
		System.out.println("---"+(E)data[root]);
		print(2*root, level+1);
	}
}
