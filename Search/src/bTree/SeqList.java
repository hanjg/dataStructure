package bTree;


public class SeqList{
	private Object[] elemData;
	private int size;//当前长度
	int maxsize;//当前分配的存储容量
	private static final int initSize=100;
	//private static final int increseSize=10;

	public SeqList() {//构造函数
		elemData=new Object[initSize];
		size=0;
		maxsize=initSize;
	}
	
	public SeqList(int maxSize){
		elemData=new Object[maxSize];
		this.size=0;
		maxsize=maxSize;
	}
	
	public SeqList mergeList(SeqList listB) throws Exception{//AB非增排列，将A和B合并为C，使得合并的表中元素依然非增排列
		SeqList listC=new SeqList();
		int i=0;int j=0;int k=0;//分别指向ABC
		while(i<this.size&&j<listB.size){
			if((Integer)this.elemData[i]<(Integer)listB.elemData[j]){
				listC.insert(k++, this.elemData[i++]);
			}
			else {
				listC.insert(k++, listB.elemData[j++]);
			}
		}
		while(i<this.size){
			listC.insert(k++, this.elemData[i++]);
		}
		while(j<listB.size){
			listC.insert(k++, listB.elemData[j++]);
		}
		return listC;
	}
	
	public void uniteList(SeqList listB) throws Exception{//AUB,结果存储在A中
		for(int i=0;i<listB.size;i++){
			Object elem=listB.elemData[i];
			if(locateElem(elem)==-1){
				this.insert(size, elem);
			}
			//this.print();
		}
	}

	public int locateElem(Object elem){//查找元素elem,返回下标，若不存在返回-1
		for(int i=0;i<size;i++){
			if (elem.equals(elemData[i])) {//equals,==??????
				return i;
			}
		}
		return -1;
		/*int i=0;
		while(i<size&&!(e.equals(elemData[i]))){
			i++;
		}
		if (i<size) return i;
		else {
			return -1;
		}*/
	}
	
	

	public void insert(int i, Object elem) throws Exception {
		if(i<0||i>size) 
			throw new IndexOutOfBoundsException("线性表越界");
		
		
		/*for(int j=size-1;j>=i;j--){
			elemData[j+1]=elemData[j];
		}
		elemData[i]=e;
		size++;*/
		//System.out.println("size="+size+"i="+i);
		System.arraycopy(elemData, i, elemData, i+1, size-i);
		size++;
		elemData[i]=elem;
	}
	
	public void insert(Object elem) throws Exception{
		insert(getSize(), elem);
	}
	public void orderInsert(Object elem) throws Exception{
		if (isEmpty()) {
			insert(0, elem);
			return;
		}
		int i=0;
		while(i<size&&((Integer)elem).intValue()>((Integer)elemData[i]).intValue()){
			i++;
		}
		insert(i, elem);
	}

	public Object delete(int i) throws Exception {
		if(i<0||i>=size)
			throw new IndexOutOfBoundsException("线性表越界");
		Object elem=elemData[i];
		System.arraycopy(elemData, i+1, elemData, i, size-i-1);
		size--;
		elemData[size]=null;//清除多余的数据
		return elem;
	}

	public Object getData(int i)  {
		return elemData[i];
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		return size==0;
	}

	public void print(){
		for(int i=0;i<size;i++){
			System.out.print(elemData[i]+" ");
		}
		System.out.println();
	}

	public static void main(String[] arg) {
		int[] a={2,5,6,7,9,34,56,77};
		int[] b={4,5,6,7,9,76,87,99,101};
		SeqList listA=new SeqList();
		try {
			for(int i=0;i<a.length;i++){
				listA.insert(i, a[i]);
			}
			SeqList listB=new SeqList();
			for(int i=0;i<b.length;i++){
				listB.insert(i, b[i]);
			}
			listA.print();listB.print();
			SeqList listC=listA.mergeList(listB);listC.print();
			listA.uniteList(listB);listA.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*int[] a={43,4,56,76,87,7,324,33,454,43};
		SeqList list=new SeqList();
		for(int i=0;i<a.length;i++){
			try {
				//System.out.println("a"+i+":"+a[i]);
				list.orderInsert(a[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		list.print();*/
	}
}
