package hashTable;
public class HashTable {
	//哈希函数采用除留余数法，哈希冲突采用开放定址法中的线性探查法
	
	class HashItem{
		int data;
		int info;//标志数组的该位置是否被占用；0表示空闲，1表示占用
		
		public HashItem(int i){
			info=i;
		}
		
		public HashItem(int d,int i) {
			data=d;
			info=i;
		}
	}


	private HashItem[] hashItems;
	private int maxSize;//哈希表的长度
	private int size;//当前的数据个数
	
	public HashTable(int m) {
		maxSize=m;
		hashItems=new HashItem[m];
		size=0;
	}
	
	public boolean isIn(int x){
		if (find(x)>=0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int getValue(int i){//取下标为i的值
		return hashItems[i].data;
	}
	
	public int find(int x){//查找元素x,若存在返回该元素的下标，若不存在返回该元素哈希地址的负值，若整个哈希表已经填满并且未找到该元素，返回-maxSize
		int i=x%maxSize;//哈希函数对应的值
		int j=i;//哈希冲突函数对应的值
		
		if (hashItems[j]==null) {
			return -j;
		}
		while(hashItems[j].info==1&&hashItems[j].data!=x){//存在哈希冲突
			j=(j+1)%maxSize;//线性探查法解决哈希冲突
			if (hashItems[j]==null) {//如果j为实例化，则实例化将其置为空闲
				hashItems[j]=new HashItem(0);
			}
			if (j==i) {
				return -maxSize;//已查找完整个哈希表,x通过哈希函数和冲突函数能够取得所有值都被占据,但是并没有找到x
			}
		}
		if (hashItems[j].info==1) {
			return j;//x存在
		}
		else {
			return -j;//x不存在
		}
	}
	
	public void insert(int x) throws Exception{
		int i=find(x);
		if (i>0) {//x存在x>0，原因：当第一次i=0时，即插入数据正好是maxsize的整数倍，则应该在i=0处插入，以后i再插入maxsize的整数倍时i不会为0
		//即当i=0是表示是第一次插入的maxSize的整数
			throw new Exception("already exsit");
		}
		if (i!=-maxSize) {
			hashItems[-i]=new HashItem(x, 1);
			size++;
		}
		else {
			throw new Exception("full");
		}
	}
	
	public void delete(int x) throws Exception{
		int i=find(x);
		if (i>=0) {//当i=0是表示是第一次插入的maxSize的整数
			hashItems[i].info=0;
			size--;
		}
		else {
			throw new Exception("none");
		}
	}
	
	public int getSize(){
		return size;
	}
	public static void main(String[] args) {
		HashTable hashTable=new HashTable(13);
		int[] a={180,520,456,430,750,900,260};
		for(int i=0;i<a.length;i++){
			try {
				hashTable.insert(a[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(int i=0;i<a.length;i++){
			int j=hashTable.find(a[i]);
			if (j>=0) {
				System.out.println(a[i]+" index is:"+j);
			}
		}
		System.out.println(hashTable.find(521));
		try {
			hashTable.delete(520);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(hashTable.find(521));
	}

}
