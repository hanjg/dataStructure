package test;

public class TestHeap {
	public static void main(String[] args) {
		int[] data = {60,55,48,37,10,90,84,36,43,435,4,3,42,43,4,5,76,878,3};
		QuickSort.quickSort(data, 0, data.length - 1);
		for (int i : data) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		
		data = new int[]{60,55,48,37,10,90,84,36,43,435,4,3,42,43,4,5,76,878,3};
		SimpleHeap heap = new SimpleHeap(10);
		for (int i : data) {
			heap.add(i);
		}
		while (!heap.isEmpty()) {
			System.out.print(heap.poll() + " ");
		}
		
	}
}
