package vector;

public class MyVector {
	private Object[] data;//存储向量的元素
	private int count;//向量元素的个数
	private int maxSize;
	
	private void ensureCapacity(int minCapacity){
		int oldCapacity=maxSize;
		if (minCapacity>oldCapacity) {
			int newCapacity=oldCapacity*2;
			if (newCapacity<minCapacity) {
				newCapacity=minCapacity;
			}
			//int newCapacity=oldCapacity*2>minCapacity?oldCapacity*2:minCapacity;
			Object[] oldData=data;
			data=new Object[newCapacity];
			maxSize=newCapacity;
			System.arraycopy(oldData, 0, data, 0, count);
		}
	}
	
	public MyVector(int initialCapacity){
		data=new Object[initialCapacity];
		count=0;
		maxSize=initialCapacity;
	}
	
	public MyVector(){
		this(10);
	}
	
	public void add(int index,Object element){//插入后必须使得元素连续排列，所以对插入的下标有限制
		if (index>count) {
			throw new ArrayIndexOutOfBoundsException(index+">"+count);
		}
		ensureCapacity(count+1);
		System.arraycopy(data, index, data, index+1, count-index);
		data[index]=element;
		count++;
	}
	
	public void add(Object element){
		this.add(count, element);
	}
	
	public void insert(int index,Object elem){//在data数组中插入元素，不需要使得存储的元素连续
		data[index]=elem;
		count++;
	}

	public void set(int index,Object element){
		if (index>=count) {
			throw new ArrayIndexOutOfBoundsException(index+">="+count);
		}
		data[index]=element;
	}
	
	public Object get(int index){
		if (index>=count) {
			throw new ArrayIndexOutOfBoundsException(index+">="+count);
		}
		return data[index];
	}
	
	public int getSize(){
		return count;
	}
	
	public int indexOf(Object element){//返回element的下标，若不存在则返回-1
		if (element==null) {
			for(int i=0;i<count;i++){
				if (data[i]==null) {
					return i;
				}
			}
		}//null
		else {
			for(int i=0;i<count;i++){
				if (element.equals(data[i])) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public boolean contain(Object element){
		return indexOf(element)>=0;
		//return indexOf(element)!=-1;
	}
	
	public void remove(int index){
		if (index>=count) {
			throw new ArrayIndexOutOfBoundsException(index+">="+count);
		}
		else if (index<0) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		System.arraycopy(data, index+1, data, index, count-index-1);
		count--;
		data[count]=null;
	}
	
	public void remove(Object element){
		int i=indexOf(element);
		if (i>=0) {
			remove(i);
		}
	}
	
	public static void main(String[] args) {
		MyVector myVector=new MyVector(10);
		for(int i=0;i<20;i++){
			myVector.add(new Integer(i));
		}
		myVector.add(new Integer(11));
		myVector.set(1, new Integer(10));
		System.out.println(myVector.getSize());
		for(int i=0;i<myVector.getSize();i++){
			System.out.print(myVector.get(i)+" ");
		}
		Integer integer=(Integer)myVector.get(1);
		System.out.println();
		System.out.println(integer);
	}

}
