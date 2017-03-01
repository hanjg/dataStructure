package applyment;
//二分搜索法
public class BSearch {
	public static int bSearch(int[] a,int x,int low,int high){
		if (low>high) {
			return -1;
		}
		int mid=(low+high)/2;
		if (x==a[mid]) {
			return mid;	
		}
		else if (x<a[mid]) {
			return bSearch(a, x, low, mid-1);
		}
		else {
			return bSearch(a, x, mid+1, high);
		}
	}
	
	public static int bSearch2(int[] a,int x,int low,int high){
		int mid=(low+high)/2;
		while(x!=a[mid]&&low<=high){
			if (x<a[mid]) {
				high=mid-1;
				mid=(low+high)/2;
			}
			else {
				low=mid+1;
				mid=(low+high)/2;
			}
		}
		if (x==a[mid]) {
			return mid;
		}
		else {
			return -1;
		}
		
	}
	public static void main(String[] args) {
		int[] a=new int[10000000];
		for(int i=0;i<a.length;i++){
			a[i]=i+1;
		}
		long start;
		long end;
		start=System.currentTimeMillis();
		System.out.println(bSearch(a, 3, 0, a.length-1));
		end=System.currentTimeMillis();
		System.out.println("time:"+(end-start));
		start=System.currentTimeMillis();
		System.out.println(bSearch2(a, 3, 0, a.length-1));
		end=System.currentTimeMillis();
		System.out.println("time:"+(end-start));
	}

}
