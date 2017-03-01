package search.hashTable;

import list.LinList;
import list.List;

/**
 * 链表法解决冲突
 * @author hjg
 *
 */
public class HashTable2 implements HashTable{
	private Elem[] data;
	private int size;
	private int capacity;
	
	public HashTable2(int capacity) {
		data=new Elem[capacity];
		size=0;
		this.capacity=capacity;
	}
	@Override
	public boolean contain(String elem) {
		int pos=hash(elem);
		if (data[pos]==null) {
			return false;
		}else {
			return data[pos].list.contain(elem);
		}
	}
	
	@Override
	public void insert(String elem) {
		int pos=hash(elem);
		if (data[pos]==null) {
			List<String> list=new LinList<>();
			list.add(elem);
			data[pos]=new Elem(list);
			size++;
		}else {
			List<String> list=data[pos].list;
			if (!list.contain(elem)) {
				try {
					list.add(0, elem);
					size++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(String elem) {
		int pos=hash(elem);
		if (data[pos]==null) {
			return;
		}else {
			List<String> list=data[pos].list;
			if (list.contain(elem)) {
				try {
					list.remove(elem);
					size--;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public int size() {
		return size;
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
	public class Elem{
		private List<String> list;
		public Elem(List<String> list){
			this.list=list;
		}
	}
}
