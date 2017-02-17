package hashTable;

public class HashTable2 {
//哈希函数采用除留余数法，解决哈希冲突采用链表法，即为不同的同义词建立不同的单链表
	class HashItem{
		int data;
		LinList linList;
		
		HashItem(int d){
			data=d;
		}
		
		HashItem(int d,LinList linList){
			data=d;
			this.linList=linList;
		}
	}
	
	
	private HashItem[] hashItems;
	private int maxSize;//哈希表的长度
	private int size;//当前的数据个数


	public HashTable2(int m) {
		maxSize=m;
		hashItems=new HashItem[m+1];//第一格不使用,不用去考虑下标为0的情况
		size=0;
	}

	public int[] getValue(int i){//取下标为i的值
		if (hashItems[i].linList==null) {
			int[] a=new int[1];
			a[0]=hashItems[i].data;
			return a;
		}
		else {
			int[] a=new int[hashItems[i].linList.size()+1];
			a[0]=hashItems[i].data;
			for(int j=0;i<hashItems[i].linList.size();j++){
				a[j+1]=(int) hashItems[i].linList.getData(j);
			}
			return a;
		}
	}


	public int find(int x){//查找元素x
		int i=x%maxSize+1;//哈希函数对应的值
		
		if (hashItems[i]==null) {
			return -i;
		}
		else if (hashItems[i].data==x) {
			return i; 
		}
		else if (hashItems[i].linList!=null&&hashItems[i].linList.locateData(x)!=-1){
			return i;
		}
		else {
			return -i;
		}
	}

	public void insert(int x) throws Exception{
		int i=find(x);
		if (i>0) {
			throw new Exception("already exsit");
		}
		if (hashItems[-i]==null) {
			hashItems[-i]=new HashItem(x);
		}
		else if (hashItems[-i].linList==null) {
			hashItems[-i].linList=new LinList();
			hashItems[-i].linList.insert(x);
		}
		else {
			hashItems[-i].linList.insert(x);
		}
	}


	public void delete(int x) throws Exception{
		int i=find(x);
		if (i<0) {
			throw new Exception("none");
		}
		else if (hashItems[i].linList==null) {//下标i处的链表不存在，说明i在data里
			hashItems[i]=null;
		}
		else if (hashItems[i].data!=x) {//下标i处的链表存在，并且x在链表里
			int j=hashItems[i].linList.locateData(x);
			hashItems[i].linList.delete(j);
			if (hashItems[i].linList.size()==0) {
				hashItems[i].linList=null;
			}
		}
		else {//下标i处的链表纯在，并且x在data里
			hashItems[i].data=(int) hashItems[i].linList.delete(0);
			if (hashItems[i].linList.size()==0) {
				hashItems[i].linList=null;
			}
		}
	}
	
	public int getSize(){
		return size;
	}

	public static void main(String[] args) {
		HashTable2 hashTable=new HashTable2(13);
		int[] a={180,520,456,430,750,900,260,454,677,222};
		for(int i=0;i<a.length;i++){
			try {
				hashTable.insert(a[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(int i=0;i<a.length;i++){
			int j=hashTable.find(a[i]);
			if (j>0) {
				System.out.println(a[i]+" index is:"+j);//[1,13]
			}
		}
		System.out.println(hashTable.find(520));
		try {
			hashTable.delete(520);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(hashTable.find(520));
	}

}
