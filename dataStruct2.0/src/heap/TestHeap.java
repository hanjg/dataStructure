package heap;

import list.List;
import list.SeqList;

public class TestHeap {
	public static void main(String[] args) {
		int[] data={60,55,48,37,10,90,84,36,43,435,4,3,42,43,4,5,76,878,3};
		List<Integer> list=new SeqList<>(data.length);
		for(int i:data){
			list.add(i);
		}
		MinHeap<Integer> heap=new MinHeap<>(2*data.length, list);
		while (!heap.isEmpty()) {
			System.out.print(heap.poll()+",");
		}
		System.out.println();

		Heap intHeap = new Heap(100);
		for(int num : data) {
			intHeap.add(num);
		}
		while (!intHeap.isEmpty()) {
            System.out.print(intHeap.poll()+",");
        }
		
	}
}
