package adjMatrixGraph;

import java.util.Arrays;

public class SeqList {
	public Object[] ElemData;
	private int size;//当前长度
	private int maxsize;//当前分配的存储容量
	private final int LIST_INIT_SIZE=100;
	private final int LISTINCREMENT=10;
	
	public static void main(String[] arg) {
		SeqList list=new SeqList();
		list.insert(0, 4);
		list.insert(1, 5);
		list.print();
		int i=list.LocateElem(5);
		list.delete(1);
		list.print();
		System.out.println(i);
	}
	
	//构造函数
	public SeqList() {
		ElemData=new Object[LIST_INIT_SIZE];
		size=0;
		maxsize=LIST_INIT_SIZE;
	}
	
	public SeqList(int maxSize){
		this.maxsize=maxSize;
		size=0;
		ElemData=new Object[maxSize];
	}
	
	public int getSize(){
		return size;
	}
	//索引i处插入元素
	public void insert(int i,Object e){
		if(i<0||i>size) 
			throw new IndexOutOfBoundsException("线性表越界");
		if(size>=maxsize){
			maxsize+=LISTINCREMENT;
			ElemData=Arrays.copyOf(ElemData, maxsize);
		}
		/*for(int j=size-1;j>=i;j--){
			ElemData[j+1]=ElemData[j];
		}
		ElemData[i]=e;
		size++;*/
		System.arraycopy(ElemData, i, ElemData, i+1, size-i);
		size++;
		ElemData[i]=e;
	}
	
	public void insert(Object elem){
		insert(size, elem);
	}
	//索引i处删除元素	
	public Object delete(int i){
		if(i<0||i>size-1)
			throw new IndexOutOfBoundsException("线性表越界");
		Object e=ElemData[i];
		System.arraycopy(ElemData, i+1, ElemData, i, size-i-1);
		size--;
		return e;
	}
	
	//查找元素e
	public int LocateElem(Object e){
		for(int i=0;i<size;i++){
			if (e.equals(ElemData[i])) {//equals,==??????
				return i;
			}
		}
		return -1;
		/*int i=0;
		while(i<size&&!(e.equals(ElemData[i]))){
			i++;
		}
		if (i<size) return i;
		else {
			return -1;
		}*/
	}
	
	public Object getData(int i){
		if (i<0||i>=size) {
			throw new IndexOutOfBoundsException();
		}
		return ElemData[i];
	}
	
	//合并两个表
	public void ListUnion(SeqList ListB){
		for(int i=0;i<ListB.size;i++){
			Object e=ListB.ElemData[i];
			if(LocateElem(e)==-1){
				this.insert(size++, e);
			}
		}
	}
	
	public SeqList MergeList(SeqList ListB){
		SeqList ListC=new SeqList();
		int i=0;int j=0;int k=0;
		while(i<this.size&&j<ListB.size){
			if((Integer)this.ElemData[i]<(Integer)ListB.ElemData[j]){
				ListC.insert(k, this.ElemData[i]);
				k++;
				i++;
			}
			else {
				ListC.insert(k++, ListB.ElemData[j++]);
			}
		}
		while(i<this.size){
			ListC.insert(k++, this.ElemData[i++]);
		}
		while(j<ListB.size){
			ListC.insert(k++, ListB.ElemData[j++]);
		}
		return ListC;
	}
	
	public void print(){
		for(int i=0;i<size;i++){
			System.out.print(ElemData[i]+" ");
		}
		System.out.println();
	}
}
