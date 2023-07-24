package com.evildoer.train.algorithm;

import com.evildoer.train.utils.Log;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class HeapSortTest {
    private HeapSort sort = new HeapSort();

    @Test
    public void heapSort_should_successful() {
        int[] nums = new int[]{3, 2, 1, 5, 6, 4};
        assertThat(catchThrowable(() -> sort.heapSort(nums))).isNull();
        Log.print(Arrays.toString(nums));
    }
}