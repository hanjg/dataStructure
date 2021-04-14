package test;

import java.util.Arrays;

public class SimpleHeap {
	private int[] data;
	
	private int size;
	
	private int capacity;
	
	public SimpleHeap(int capacity) {
		this.capacity = capacity;
		this.size = 0;
		this.data = new int[capacity + 1];
	}
	
	public void add(int elem) {
		if (size == capacity) {
			data = Arrays.copyOf(data, 2 * capacity + 1);
			capacity *= 2;
		}
		data[++size] = elem;
		shiftup(size);
	}
	
	public int peek() {
		if (size == 0) {
			throw new NullPointerException();
		}
		return data[1];
	}
	
	public int poll() {
		int temp = peek();
		data[1] = data[size--];
		shiftdown(1);
		return temp;
	}
	
	private void shiftup(int hole) {
		int temp = data[hole];
		int parent = hole / 2;
		//与初始hole处的值temp比较
		while (parent >= 1 && temp < data[parent]) {
			 data[hole] = data[parent];
			 hole = parent;
			 parent = hole / 2;
		}
		data[hole] = temp;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	private void shiftdown(int hole) {
		int temp = data[hole];
		int child = hole * 2;
		while (child <= size) {
			if (child + 1 <= size && data[child + 1] < data[child]) {
				child++;
			}
			if (data[child] < temp) {
				data[hole] = data[child];
				hole = child;
				child = hole * 2;
			} else {
				break;
			}
		}
		data[hole] = temp;
	}
}
