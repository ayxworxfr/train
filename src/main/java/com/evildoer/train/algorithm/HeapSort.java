package com.evildoer.train.algorithm;

public class HeapSort {
    public void heapSort(int[] nums) {
        int heapSize = nums.length;
        buildMaxHeap(nums, heapSize);
        for (int i = nums.length - 1; i >= 0; i--) {
            swap(nums, 0, i);
            heapSize--;
            maxHeapify(nums, 0, heapSize);
        }
    }

    public void buildMaxHeap(int[] arr, int heapSize) {
        for (int i = heapSize / 2; i >= 0; i--) {
            maxHeapify(arr, i, heapSize);
        }
    }

    public void maxHeapify(int[] a, int i, int heapSize) {
        int l = i * 2 + 1, r = i * 2 + 2, largest = i;
        if (l < heapSize && a[l] > a[largest]) {
            largest = l;
        }
        if (r < heapSize && a[r] > a[largest]) {
            largest = r;
        }
        if (largest != i) {
            swap(a, i, largest);
            maxHeapify(a, largest, heapSize);
        }
    }

    /**
     * swap
     *
     * @param arr 数组
     * @param i   交换元素位置
     * @param j   交换元素位置
     * @author evildoer
     */
    public void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
