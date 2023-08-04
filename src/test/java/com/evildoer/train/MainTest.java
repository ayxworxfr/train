package com.evildoer.train;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void test() {
    }

    public String minimumString(String a, String b, String c) {
        String[] tmp = new String[]{a, b, c};
        LinkedList<String> ans = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            int j = (i + 1) % 3;
            int k = (i + 2) % 3;
            String aa = merge(tmp[i], tmp[j]);
            String bb = merge(tmp[j], tmp[i]);
            ans.add(merge(aa, tmp[k]));
            ans.add(merge(tmp[k], aa));
            ans.add(merge(bb, tmp[k]));
            ans.add(merge(tmp[k], bb));
        }
        ans.sort((str, str2) -> {
            if (str.length() != str2.length()) {
                return str.length() - str2.length();
            }
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) != str2.charAt(i)) {
                    return str.charAt(i) - str2.charAt(i);
                }
            }
            return 0;
        });
        return ans.get(0);
    }

    private String merge(String a, String b) {
        if (a.indexOf(b) != -1) {
            return a;
        }
        int m = a.length();
        int n = b.length();
        int start = Math.min(m, n);
        while (start > 0) {
            if (a.substring(m - start, m).equals(b.substring(0, start))) {
                return a.substring(0, m - start) + b;
            }
            start--;
        }
        return a + b;
    }


    public int countCompleteSubarrays(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Set<Integer> set =
                Arrays.stream(nums).boxed().collect(Collectors.toSet());
        int ans = 0;
        int n = nums.length;
        int count = set.size();
        Set<Integer> putSet = new HashSet<>(set.size());
        for (int i = 0; i <= n - count; i++) {
            putSet.add(nums[i]);
            for (int j = i; j < n; j++) {
                putSet.add(nums[j]);
                if (set.size() - putSet.size() == 0) {
                    ans += n - j;
                    break;
                }
            }
            putSet.clear();
        }
        return ans;
    }
}