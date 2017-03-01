package list;

/**
 * 可自动扩容的顺序表
 * @author hjg
 *
 * @param <E>
 */
public class SeqList<E> implements List<E>{
	/**
	 * java中没有泛型数组，使用Object数组和强制类型转换
	 */
	protected Object[] data;
	/**
	 * 当前元素数量
	 */
	protected int size;
	/**
	 * 当前数组容量
	 */
	protected int capacity;
	
	private static final int DEFAULT_CAPACITY=10;
	
	public SeqList(){
		init();
	}
	public SeqList(int capacity){
		init(capacity);
	}
	@Override
	public void init() {
		init(DEFAULT_CAPACITY);
	}
	public void init(int capacity) {
		this.capacity=capacity;
		data=new Object[capacity];
		size=0;
	}

	@Override
	public void add(int index, E elem){
		if (index<0||index>size) {
			throw new IndexOutOfBoundsException();
		}
		ensureCapacity(size+1);
		System.arraycopy(data, index, data, index+1, size-index);
		data[index]=elem;
		size++;
	}
	
	/**
	 * 不连续地插入元素
	 * @param index
	 * @param elem
	 * @throws Exception
	 */
	public void insert(int index,E elem){
		if (index<0||index>=capacity) {
			throw new IndexOutOfBoundsException();
		}
		if (data[index]==null) {
			size++;
		}
		data[index]=elem;
	}
	public void set(int index, E elem){
		if (index<0||index>=size) {
			throw new IndexOutOfBoundsException();
		}
		data[index]=elem;
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
	 * 确保数组的容积不小于所需的最小容积
	 * @param minCapacity
	 */
	private void ensureCapacity(int minCapacity){
		int oldCapacity=capacity;
		if (minCapacity>oldCapacity) {
			int newCapacity=Math.max(oldCapacity*2, minCapacity);
			Object[] oldData=data;
			data=new Object[newCapacity];
			capacity=newCapacity;
			System.arraycopy(oldData, 0, data, 0, size);
		}
	}
	@Override
	public E remove() {
		return remove(size-1);
	}
	@Override
	public E remove(int index) {
		if (index<0||index>=size) {
			throw new IndexOutOfBoundsException();
		}
		E elem=elemData(index);
		System.arraycopy(data, index+1, data, index, size-index-1);
		size--;
		return elem;
	}

	@Override
	public boolean remove(E elem){
		boolean done=false;
		for(int i=0;i<size;i++){
			if (elemData(i).equals(elem)) {
				remove(i);
				i--;
				done=true;
			}
		}
		return done;
	}
	@Override
	public E get(int index) {
		return elemData(index);
	}

	@Override
	public boolean contain(E elem) {
		return indexOf(elem)!=-1;
	}
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	/**
	 * 返回elem在list中的下标，不存在则返回-1
	 * @param elem
	 * @return
	 */
	public int indexOf(E elem){
		for(int i=0;i<size;i++){
			if (elemData(i).equals(elem)) {
				return i;
			}
		}
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	protected E elemData(int index){
		return (E)data[index];
	}
	@Override
	public void print() {
		System.out.print("[");
		for(int i=0;i<size;i++){
			if (i!=0) {
				System.out.print(",");
			}
			System.out.print(elemData(i));
		}
		System.out.println("]");
	}
}
