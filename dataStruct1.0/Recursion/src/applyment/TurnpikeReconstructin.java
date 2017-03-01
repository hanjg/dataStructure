package applyment;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * 收费公路重建问题
 * @author hjg
 *
 */
public class TurnpikeReconstructin {
	/**
	 * @param x 收费站的坐标
	 * @param distance 距离的集合
	 * @param n 收费站的个数
	 * @return
	 */
	public static boolean turnpike(int[] x, PriorityQueue<Integer> distance, int n){
		x[0]=0;
		x[n-1]=distance.poll();
		return place(x, distance, n, 1, n-2);
	}
	/**
	 * @param x
	 * @param distance
	 * @param n
	 * @param left
	 * @param right 将要放置的范围[left,right]
	 * @return
	 */
	private static boolean place(int[] x, PriorityQueue<Integer> distance, int n,
			int left, int right){
		if(distance.isEmpty()) return true;
		
		int dmax=distance.peek();
		boolean found=false;
		//判断x[right]==dmax是否合理
		if (isValid(dmax, x, distance, left, right)) {
			x[right]=dmax;
			
			found=place(x, distance, n, left, right-1);
			
			if (!found) {//还原distance
				for(int i=0;i<left;i++) distance.add(x[right]-x[i]);
				for(int i=right+1;i<n;i++) distance.add(x[i]-x[right]);
			}
		}
		//判断x[left]==x[n-1]-dmax是否合理
		if (!found&&isValid(x[n-1]-dmax, x,distance, left, right)) {
			x[left]=x[n-1]-dmax;
			
			found=place(x, distance, n, left+1, right);
			
			if (!found) {//i,j易弄混
				for(int i=0;i<left;i++) distance.add(x[left]-x[i]);
				for(int i=right+1;i<n;i++) distance.add(x[i]-x[left]);
			}
		}
		return found;
	}
	/**
	 * 在location处建立是否合理
	 * @param location 即将建立收费站的位置
	 * @param distance
	 * @param left
	 * @param right
	 * @return
	 */
	private static boolean isValid(int location, int[] x, PriorityQueue<Integer> distance, 
			int left, int right){
		//在distance中查询完距离后将其删除，若不合理则还原初始的distance
		for(int i=0;i<left;i++){
			if(distance.contains(location-x[i])){
				distance.remove(location-x[i]);
			}
			else {
				for(int j=i-1;j>=0;j--) distance.add(location-x[j]);//i,j易混淆
				return false;
			}
		}
		for(int i=right+1;i<x.length;i++){
			if (distance.contains(x[i]-location)) {
				distance.remove(x[i]-location);
			}
			else {
				for(int j=i-1;j>=right+1;j--) distance.add(x[j]-location);
				for(int j=0;j<left;j++) distance.add(location-x[j]);
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args) {
		int n=6;
		int[] x=new int[n];
		int[] dis=new int[]{1,2,2,2,3,3,3,4,5,5,5,6,7,8,10};
		PriorityQueue<Integer> distance=new PriorityQueue<>(n*n, Collections.reverseOrder());
		for(int i:dis) distance.add(i);
		System.out.println(turnpike(x, distance, n));
		for(int i:x) System.out.print(i+",");
	}
}
