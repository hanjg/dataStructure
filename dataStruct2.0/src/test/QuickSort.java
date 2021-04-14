package test;

public class QuickSort {
	
	public static void quickSort(int[] data, int begin, int end) {
		if (begin >= end) {
			return;
		}
		int pivot = data[end];
		int i = begin - 1;
		int j = end;
		while (i < j) {
			while (i < j && data[++i] < pivot) {
				;
			}
			while (i < j && data[--j] > pivot) {
				;
			}
			if (i < j) {
				swap(data, i, j);
			}
		}
		swap(data, i, end);
		quickSort(data, begin, i - 1);
		quickSort(data, i + 1, end);
	}
	
	private static void swap(int[] data, int i, int j) {
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}
	
	public static void main(String[] args) {
		int[] data = {60,55,48,37,10,90,84,36,43,435,4,3,42,43,4,5,76,878,3};
		quickSort(data, 0, data.length - 1);
		for (int i : data) {
			System.out.print(i + " ");
		}
	}
}
