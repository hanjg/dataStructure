
public class InternalSort {//各种int型量的排序
	
	public static void insertSort(int[] a){//直接插入排序
		int n=a.length;
		
		for(int i=0;i<n-1;i++){//指向已经插入排序好的最后一个元素下标,本次插入下标为i+1的数据
			int temp=a[i+1];
			int j=i;//j为试图插入元素的位置
			while(j>=0&&temp<a[j]){
				a[j+1]=a[j];
				j--;
			}
			a[j+1]=temp;
		}
	}
	
	public static void biInsertSort(int[] a){//折半插入排序，减少插入排序的关键词比较次数
		int n=a.length;
		for(int i=0;i<n-1;i++){//指向已经插入排序好的最后一个元素下标,本次插入下标为i+1的数据
			int temp=a[i+1];
			int low=0;
			int high=i;
			while(low<=high){
				int mid=(low+high)/2;
				if (temp<a[mid]) {
					high=mid-1;
				}
				else {
					low=mid+1;
				}
			}//数据将插入到low的位置,a[low]>=temp
			for(int j=i;j>=low;j--){
				a[j+1]=a[j];
			}
			a[low]=temp;
		}
	}
	
	public static void twoWayInsertSort(int a[]){//在折半插入排序的基础上减少记录的移动次数
		int n=a.length;
		int[] d=new int[n];
		d[0]=a[0];//将a[0]为标准，之后的元素>=a[0],之前的元素存在数组d的后半部分<a[0]
		int first=0;//最小元素的下标
		int last=0;//最大元素的下标
		for(int i=1;i<n;i++){//将要插入a[i]
			if (a[i]<d[0]) {
				if (first==0) {
					d[n-1]=a[i];
					first=n-1;
				}
				int low=first;
				int high=n-1;
				while(low<=high){
					int mid=(low+high)/2;
					if (a[i]<d[mid]) {
						high=mid-1;
					}
					else {
						low=mid+1;
					}
				}//数据将插入到low-1的位置，并将[first,low-1]前移
				for(int j=first;j<=low-1;j++){
					d[j-1]=d[j];
				}
				d[low-1]=a[i];
				first--;
			}
			else {
				int low=0;
				int high=last;
				while(low<=high){
					int mid=(low+high)/2;
					if (a[i]<d[mid]) {
						high=mid-1;
					}
					else {
						low=mid+1;
					}
				}//数据将插入到low的位置，并将[low,last]后移
				for(int j=last;j>=low;j--){
					d[j+1]=d[j];
				}
				d[low]=a[i];
				last++;
			}
		}
		for(int i=0;i<n;i++){
			a[i]=d[first];
			first=(first+1)%n;
		}
	}

	public static void shellSort(int[] a,int[] d){//
		for(int m=0;m<d.length;m++){
			int span=d[m];//增量
			for(int k=0;k<span;k++){//总共span个组
				for(int i=k;i<a.length-span;i+=span){
					int temp=a[i+span];
					int j=i;
					while(j>=0&&temp<a[j]){
						a[j+span]=a[j];
						j-=span;
					}
					a[j+span]=temp;
				}
			}
		}
	}
	
	public static void selectedSort(int[] a){
		for(int i=0;i<a.length-1;i++){//i表示将要插入元素的下标
			int smallest=i;
			for(int j=i+1;j<a.length;j++){
				if (a[j]<a[smallest]) {
					smallest=j;
				}
			}
			if (smallest!=i) {
				int temp=a[smallest];
				a[smallest]=a[i];
				a[i]=temp;
			}
		}
		
	}
	
	public static void selectedSortStable(int[] a){
		for(int i=0;i<a.length-1;i++){//i表示将要插入元素的下标
			int smallest=i;
			for(int j=i+1;j<a.length;j++){
				if (a[j]<a[smallest]) {
					smallest=j;
				}
			}
			if (smallest!=i) {//选出最小数据后，将他前面的无序记录[i,smallest-1]移至[i+1,smallest]依次后移
				int temp=a[smallest];
				for(int j=smallest;j>i;j--){
					a[j]=a[j-1];
				}
				a[i]=temp;
			}
		}
		
	}
	
	private static void createHeap(int[] a,int num,int node){
		//创建以node为根节点的最大堆(双亲节点大于两个孩子节点的完全二叉树),前提是h的左右孩子节点都是最大堆
		//num为最大堆的元素数量,不同于a.length
		//借助数组a[]表示的完全二叉树进行排序,i的孩子节点为2i+1和2i+2，i的双亲节点为(i-1)/2
		int i=node;
		int j=2*node+1;//j为i较大的孩子节点，初值为i的左孩子
		while(j<num){//节点j存在
			if (j+1<num&&a[j]<a[j+1]) {//若存在i的右孩子节点且右孩子节点比左孩子节点大,则将j指向右孩子节点
				j++;
			}
			if (a[i]>a[j]) {
				break;
			}
			else {
				int temp=a[i];
				a[i]=a[j];
				a[j]=temp;
				i=j;
				j=2*i+1;//有可能i节点的值很小，需要连续调整
			}
		}
		//print(a);
	}
	
	private static void initCreateHeap(int[] a){
		for(int i=(a.length-2)/2;i>=0;i--){//从最后一个元素的双亲节点(最后一个非叶节点)开始，创建最大堆
			createHeap(a,a.length, i);
		}
	}
	
	public static void heapSort(int[] a){
		initCreateHeap(a);
		for(int i=a.length-1;i>0;i--){//i为将要确定元素的下标，初值为最后一个元素
			int temp=a[i];
			a[i]=a[0];
			a[0]=temp;//将最大堆中最大的元素(下标0)和i交换，之后前i个元素组成的完全二叉树就不是最大堆了，需要重新构造最大堆
			//System.out.println(i+":"+a[i]+" ");
			createHeap(a, i, 0);//这i个元素的元素数量为i,不是a.length
		}
		
	}
	
	public static void bubbleSort(int[] a){
		int flag=1;//flag==1表示排序过程有交换步骤,flag==0没有交换步骤，排序已经完成
		for(int i=a.length-1;i>=1&&flag==1;i--){//i表示本次排序最大元素的下标(确定元素位置的下标)
			flag=0;
			for(int j=0;j<i;j++){
				if (a[j]>a[j+1]) {
					flag=1;
					int temp=a[j];
					a[j]=a[j+1];
					a[j+1]=temp;
				}
			}
		}
	}
	
	public static void quickSort(int[] a,int low,int high){
		if (low>=high) {
			return;
		}
		
		int i=low;
		int j=high;
		int temp=a[low];//取第一个元素为标准元素,排序后确定标准元素在排序好的数组中的位置
		//并且数组左端的元素小于标准元素，数组右端的元素大于等于标准元素
		
		while(i<j){
			while(i<j&&a[j]>=temp){//从数组右端开始扫描
				j--;
			}
			if (i<j) {
				a[i]=a[j];
				i++;
			}
			while(i<j&&a[i]<temp){
				i++;
			}
			if (i<j) {
				a[j]=a[i];
				j--;
			}
		}
		a[i]=temp;
		
		if (low<i) {
			quickSort(a, low, i-1);
		}
		if (j<high) {
			quickSort(a, j+1, high);
		}
	}
	
	private static void merge(int[] a,int[] temp,int k){//k为子数组长度，将a中的元素归并到temp中
		int l1,h1,l2,h2;//子数组1和2的范围[l2,h2),[l2,h2)
		l1=0;
		int i,j,m=0;
		while(l1+k<a.length){//剩余元素个数大于k,即可以分为两组归并；l1+k为第二组的第一个元素下标
			h1=l2=l1+k;
			h2=(l2+k<=a.length)?l2+k:a.length;//第二组的上限为l2+k，元素数量不够k为a.length
			//System.out.println("l1="+l1+" h1="+h1+" l2="+l2+" h2="+h2);
			for(i=l1,j=l2;i<h1&&j<h2;m++){
				if (a[i]<a[j]) {
					temp[m]=a[i];i++;
				}
				else {
					temp[m]=a[j];j++;
				}
			}
			while(i<h1){
				temp[m]=a[i];i++;m++;
			}
			while(j<h2){
				temp[m]=a[j];j++;m++;
			}
			l1=h2;
		}
		for(i=l1;i<a.length;i++,m++){//剩余元素个数小于等于k时，即只够一组数据元素
			temp[m]=a[i];
		}
	}
	
	public static void mergeSort(int[] a){
		int k=1;
		int[] temp=new int[a.length];
		
		while(k<a.length){
			merge(a, temp, k);
			for(int i=0;i<a.length;i++){
				a[i]=temp[i];
			}
			k=k*2;
		}
	}
	
	public static void radixSort(int[] a,int m,int d) throws Exception{//m为数据元素的最大位数，d为进制的基数
		LinQueue[] queues=new LinQueue[d];
		for(int i=0;i<d;i++){
			LinQueue temp=new LinQueue();
			queues[i]=temp;
		}
		
		int power=1;
		for(int i=1;i<=m;i++){//按照数据的倒数第i位分别放入相应的队列
			if (i==1) {
				power=1;
			}
			else {
				power*=d;
			}//power=d^(i-1)
			
			for(int j=0;j<a.length;j++){//数据a[j]的第i位为k
				int k=a[j]/power-a[j]/(power*d)*d;
				queues[k].append(new Integer(a[j]));
			}
			
			int j=0;
			for(int k=0;k<d;k++){//将队列中的元素按照队列的顺序取出放回a
				while(queues[k].notEmpty()){
					a[j]=((Integer)(queues[k].delete())).intValue();
					j++;
				}
			}
		}
	}
	public static void print(int[] a){
		for(int i=0;i<a.length;i++){
			System.out.print(a[i]+" ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		int[] test={60,55,48,37,10,90,84,36,43,435,4,3,42,43,4,5,76,878,3};
		/*int[] test=new int[100000];
		for(int i=0;i<test.length;i++){
			test[i]=(int) (Math.random()*1000000);
		}*/
		
		//long start,end;
		//start=System.currentTimeMillis();
		//insertSort(test);
		//biInsertSort(test);
		twoWayInsertSort(test);
		
		//int[] d={100,10,1};
		//shellSort(test, d);
		//selectedSortStable(test);;
		//heapSort(test);
		//bubbleSort(test);
		//quickSort(test, 0, test.length-1);
		//mergeSort(test);
		try {
			//radixSort(test, 3, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		print(test);
		//end=System.currentTimeMillis();
		//System.out.println(end-start);
		
	}

}
