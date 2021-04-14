package heap;

/**
 * int最小堆，大小不可变
 *
 * @Author: hjg
 * @Date: Create in 2017/10/10 10:14
 * @Description:
 */
public class Heap {

    private int[] data;
    private int size;

    public Heap(int capacity) {
        data = new int[capacity];
        size = 0;
    }

    public void add(int num) {
        data[++size] = num;
        up(size);
    }

    public int poll() {
        int temp = data[1];
        data[1] = data[size--];
        down(1);
        return temp;
    }

    private void up(int hole) {
        int temp = data[hole];
        int parent = hole / 2;
        while (hole > 1 && temp < data[parent]) {
            data[hole] = data[parent];
            hole = parent;
            parent = hole / 2;
        }
        data[hole] = temp;
    }

    private void down(int hole) {
        int temp = data[hole];
        int child = hole * 2;
        if (child + 1 <= size && data[child + 1] < data[child]) {
            child++;
        }
        while (child <= size && temp > data[child]) {
            data[hole] = data[child];
            hole = child;
            child = hole * 2;
            if (child + 1 <= size && data[child + 1] < data[child]) {
                child++;
            }
        }
        data[hole] = temp;
    }
    public boolean isEmpty() {
        return size == 0;
    }
}
