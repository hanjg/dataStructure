package search.hashTable;


/**
 * 利用线性探查法解决冲突
 * @author hjg
 *
 */
public class HashTable1 implements HashTable{
	private String[] data;
	private int size;
	private int capacity;
	/**
	 * 填充因子
	 */
	private double factor;
	
	public HashTable1(int capacity, double factor) {
		data=new String[capacity];
		this.capacity=capacity;
		this.factor=factor;
		size=0;
	}
	public boolean contain(String elem){
		int pos=findPosition(elem);
		return data[pos]!=null;
	}
	public void insert(String elem){
		if (size>=capacity*factor) {
			reHash();
		}
		int pos=findPosition(elem);
		if (data[pos]==null) {
			data[pos]=elem;
			size++;
		}
	}
	public void delete(String elem){
		int pos=findPosition(elem);
		if (data[pos]!=null) {
			data[pos]=null;
			size--;
		}
	}
	public int size() {
		return size;
	}
	/**
	 * 线性探查法解决冲突
	 * @param elem
	 * @return elem应该出现的位置,即1、没有数据；2、等于elem
	 */
	private int findPosition(String elem){
		int pos=hash(elem);
		int offset=1;
		while(data[pos]!=null&&!data[pos].equals(elem)){
			//pos处有数据且关键字不相等
			pos=(pos+offset)%capacity;
		}
		return pos;
		
	}
	/**
	 * 散列函数
	 * @param elem
	 * @return
	 */
	private int hash(String elem){
		int hashVal=0;
		for(int i=0;i<elem.length();i++){
			hashVal+=37*hashVal+elem.charAt(i);
		}
		hashVal%=capacity;
		if (hashVal<0) {//由于hashVal溢出导致负数
			hashVal+=capacity;
		}
		return hashVal;
	}
	private void reHash() {
		String[] temp=data;
		capacity*=2;
		data=new String[capacity];
		size=0;
		for (int i=0;i<temp.length;i++) {
			if (temp[i]!=null) {
				insert(temp[i]);
			}
		}
	}
}
