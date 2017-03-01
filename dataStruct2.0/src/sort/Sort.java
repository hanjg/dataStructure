package sort;

/**
 * 为了突出算法本身，避免使用泛型列表，算法对int[]排序
 * @author hjg
 *
 */
public class Sort {
	public static void insertSort(int[] a){
		insertSort(a, 0, a.length-1);
	}
	private static void insertSort(int[] a, int begin, int end){
		for(int i=begin+1;i<=end;i++){
			//将a[i]插入[0,i],a[0,i)已经有序
			int temp=a[i];
			int j;
			for(j=i;j>begin&&temp<a[j-1];j--){
				a[j]=a[j-1];
			}
			a[j]=temp;
		}
	}
	public static void selectSort(int[] a){
		for(int i=0;i<a.length;i++){
			//[i,length)选择最小元素,与i处的元素交换位置
			int min=i;
			for(int j=i;j+1<a.length;j++){
				if (a[j+1]<a[min]) {
					min=j+1;
				}
			}
			if (min!=i) {
				int temp=a[min];a[min]=a[i];a[i]=temp;
			}
		}
	}
	public static void bubbleSort(int[] a){
		boolean done=false;
		for(int i=0;!done&&i<a.length;i++){
			//将[i,length)的最小值冒泡至i
			done=true;
			for(int j=a.length-1;j>i;j--){
				if (a[j]<a[j-1]) {
					done=false;
					swap(a, j, j);
				}
			}
		}
	}
	public static void quickSort(int[] a, boolean oneOrTwo){
		if (oneOrTwo) {
			quickSort(a, 0, a.length-1);
		}else {
			quickSort2(a, 0, a.length-1);
		}
	}
	/**
	 * 使用最后一个元素作为枢轴
	 * @param a
	 * @param begin
	 * @param end
	 */
	private static void quickSort(int[] a, int begin, int end){
		if (begin>=end) {
			return;
		}
		int pivot=a[end];//pivot储存在最后一个位置
		int i=begin-1,j=end;
		while(i<j){
			while(i<j&&a[++i]<pivot) {}//i右移，移过<pivot的元素，停在>=pivot元素
			while(i<j&&a[--j]>pivot) {};//j左移，移过>pivot的元素，停在<=pivot元素
			if (i<j) {
				swap(a, i, j);
			}
		}
		swap(a, i, end);//交换a[i](>=pivot)和pivot
		quickSort(a, begin, i-1);
		quickSort(a, i+1, end);
	}
	/**
	 * 使用三数中值分割法得到枢轴，当元素个数在10以内时插入排序
	 * @param a
	 * @param begin
	 * @param end
	 */
	private static void quickSort2(int[] a, int begin, int end){
		if (end-begin>=10) {
			int pivot=median3(a, begin, end);
			int i=begin,j=end-1;
			while(i<j){
				while(a[++i]<pivot){}//i右移，移过小于pivot的元素，停在>=pivot的位置
				while(a[--j]>pivot){}//i左移，移过大于pivot的元素，停在<=pivot的位置
				if (i<j) {
					swap(a, i, j);
				}
			}
			swap(a, j, end-1);//交换a[i](>=pivot)和pivot
			quickSort2(a, begin, j-1);
			quickSort2(a, j+1, end);
		}else {
			insertSort(a, begin, end);
		}
	}
	/**
	 * 三数中值分割。结束时a[begin]<=pivot<=a[end]，pivot值存储在end-1
	 * @param a
	 * @param begin
	 * @param end
	 * @return 枢轴的值
	 */
	private static int median3(int[] a, int begin, int end){
		int mid=(begin+end)/2;
		if (a[begin]>a[mid]) {
			swap(a, begin, mid);
		}
		if (a[begin]>a[end]) {
			swap(a, begin, end);
		}
		if (a[mid]>a[end]) {
			swap(a, mid, end);
		}
		swap(a, mid, end-1);
		return a[end-1];
	}
	/**
	 * 交换a[]中下标i,j的两个值
	 * @param a
	 * @param i
	 * @param j
	 */
	private static void swap(int[] a, int i, int j) {
		int temp=a[i];a[i]=a[j];a[j]=temp;
	}
	
	/**
	 * 使用shell增量的shell排序
	 * @param a
	 */
	public static void shellSort(int[] a){
		for(int gap=a.length/2;gap>=1;gap/=2){
			for(int offset=0;offset<gap;offset++){
				//分成gap个相互交叉的组直接插入排序
				//每一个组第一个元素下标为offset,每个元素间隔gap
				for(int i=offset+gap;i<a.length;i+=gap){
					int temp=a[i];
					int j;
					for(j=i;j-gap>=offset&&a[j]<a[j-gap];j-=gap){
						a[j]=a[j-gap];
					}
					a[j]=temp;
				}
			}
		}
	}
	/**
	 * 使用最大堆输出递增序列,和之前的minHeap略有不同，<p>
	 * 有效数据范围为[0,heapSize)，上滤和下滤的条件也相反<p>
	 * 节点i，左孩子2*i+1,右孩子2*i+2，父节点(i-1)/2
	 * @param a
	 */
	public static void heapSort(int[] a){
		buildMaxHeap(a);
		for(int i=0;i<a.length;i++){
			poll(a);
		}
	}
	private static void poll(int[] a){
		swap(a, 0, --heapSize);
		percolateDown(a, 0);
	}
	private static void buildMaxHeap(int[] a) {
		heapSize=a.length;
		for(int i=(heapSize-2)/2;i>=0;i--){
			percolateDown(a, i);
		}
	}
	private static int heapSize;
	private static void percolateDown(int[] a, int hole){
		int temp=a[hole];
		int child=2*hole+1;
		while(child<heapSize){
			if (child+1<heapSize&&a[child+1]>a[child]) {
				child++;
			}
			if (a[child]>temp) {
				a[hole]=a[child];
			}else {
				break;
			}
			hole=child;
			child=2*hole+1;
		}
		a[hole]=temp;
	}
	
	public static void mergeSort(int[] a){
		mergeSort(a, new int[a.length], 0, a.length-1);
	}
	private static void mergeSort(int[] a, int[] temp, int begin, int end){
		if (begin>=end) {
			return;
		}
		int mid=(begin+end)/2;
		mergeSort(a, temp, begin, mid);
		mergeSort(a, temp, mid+1, end);
		merge(a, temp, begin, mid, mid+1, end);
	}
	private static void merge(int[] a ,int[] temp, int begin1, int end1, int begin2, int end2){
		int p1=begin1, p2=begin2, pt=begin1;
		while(p1<=end1&&p2<=end2){
			if (a[p1]<a[p2]) {
				temp[pt++]=a[p1++];
			}else {
				temp[pt++]=a[p2++];
			}
		}
		while(p1<=end1){
			temp[pt++]=a[p1++];
		}
		while(p2<=end2){
			temp[pt++]=a[p2++];
		}
		System.arraycopy(temp, begin1, a, begin1, end2-begin1+1);
	}
	public static void print(int[] a){
		for(int i:a){
			System.out.print(i+" ");
		}
		System.out.println();
	}
}
