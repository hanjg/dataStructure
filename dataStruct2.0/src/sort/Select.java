package sort;

/**
 * @Author: hjg
 * @Date: Create in 2017/10/10 11:02
 * @Description:
 */
public class Select {

    public int thirdMax(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        } else if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        return select(nums, 0, nums.length - 1, 3);
    }

    /**
     * @param k 第k个数
     */
    private int select(int[] nums, int begin, int end, int k) {
        if (k == 1) {
            return nums[begin];
        }
        int pivotIndex = partition(nums, begin, end);
        if (begin + k - 1 == pivotIndex) {
            return nums[pivotIndex];
        } else if (begin + k - 1 < pivotIndex) {
            return select(nums, begin, pivotIndex - 1, k);
        } else {
            return select(nums, pivotIndex + 1, end, k - (pivotIndex - begin + 1));
        }
    }

    private int partition(int[] nums, int begin, int end) {
        int pivot = nums[end];
        int i = begin - 1, j = end;
        while (i < j) {
            while (nums[++i] > pivot) {
            }
            while (i < j && nums[--j] < pivot) {
            }
            if (i < j) {
                swap(nums, i, j);
            }
        }
        swap(nums, i, end);
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
