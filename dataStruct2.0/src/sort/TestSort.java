package sort;

import java.util.Arrays;
import java.util.Random;

public class TestSort {
	public static void main(String[] args) {
		int n=50000;
		Random random=new Random();
		int[] a=new int[n];
		for(int i=0;i<a.length;i++){
			a[i]=random.nextInt(100000);
		}
		int[] test;
		long begin=0;
		
		System.out.println("insert:");
		test=Arrays.copyOf(a, a.length);
		begin=System.currentTimeMillis();
		Sort.insertSort(test);
		System.out.println(System.currentTimeMillis()-begin);

		System.out.println("bubble:");
		test=Arrays.copyOf(a, a.length);
		begin=System.currentTimeMillis();
		Sort.bubbleSort(test);
		System.out.println(System.currentTimeMillis()-begin);

		System.out.println("select:");
		test=Arrays.copyOf(a, a.length);
		begin=System.currentTimeMillis();
		Sort.selectSort(test);
		System.out.println(System.currentTimeMillis()-begin);

		System.out.println("quick base:");
		test=Arrays.copyOf(a, a.length);
		begin=System.currentTimeMillis();
		Sort.quickSort(test, true);
		System.out.println(System.currentTimeMillis()-begin);

		System.out.println("quick+insert:");
		test=Arrays.copyOf(a, a.length);
		begin=System.currentTimeMillis();
		Sort.quickSort(test, false);
		System.out.println(System.currentTimeMillis()-begin);

		System.out.println("heap:");
		test=Arrays.copyOf(a, a.length);
		begin=System.currentTimeMillis();
		Sort.heapSort(test);
		System.out.println(System.currentTimeMillis()-begin);

		System.out.println("merge:");
		test=Arrays.copyOf(a, a.length);
		begin=System.currentTimeMillis();
		Sort.mergeSort(test);
		System.out.println(System.currentTimeMillis()-begin);

		System.out.println("shell:");
		test=Arrays.copyOf(a, a.length);
		begin=System.currentTimeMillis();
		Sort.shellSort(test);
		System.out.println(System.currentTimeMillis()-begin);

	}
}
