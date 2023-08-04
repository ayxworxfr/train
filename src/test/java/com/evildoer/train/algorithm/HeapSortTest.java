package com.evildoer.train.algorithm;

import com.evildoer.train.utils.Log;
import mockit.Tested;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class HeapSortTest {
    @Tested
    private HeapSort sort;

    @Test
    public void heapSort_should_successful() {
        int[] nums = new int[]{3, 2, 1, 5, 6, 4};
        assertThat(catchThrowable(() -> sort.heapSort(nums))).isNull();
        Log.print(Arrays.toString(nums));
    }


    @Test
    public void test() {
        Log.print(removeDuplicateLetters("cbacdcbc"));
        // 下一位小于等于我或我是首位且下一位为0(连续删0)

        // 预处理：获取后续比我大的数字个数(另外一个数组全都可以再我后面？)
        // 比我大的数小于count
    }

    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        ListNode slow = head, fast = head;
        while (fast!=null && fast.next != null) {
            slow = slow.next;
            fast = fast.next;
            if (fast != null) {
                fast = fast.next;
            }
        }

        ListNode tmp = null;
        ListNode pre = null;
        while (slow != null) {
            tmp = slow.next;
            slow.next = pre;
            pre = slow;
            slow = tmp;
        }

        ListNode list = head;
        ListNode list2 = pre;

        ListNode dummyHead = new ListNode(0);
        boolean flag = true;
        while(list != null && list2 != null){
            System.out.println("list="+list.val+"===" + list2.val);
            if (flag) {
                tmp = list.next;
                dummyHead.next = list;
                list.next = null;
                list = tmp;
            } else {
                tmp = list2.next;
                dummyHead.next = list2;
                list2.next = null;
                list2 = tmp;
            }
            dummyHead = dummyHead.next;
            flag = !flag;
        }
        // 末尾节点置空
        if (list2 != null) {
            System.out.println(list2.val);
            dummyHead.next = list2;
            list2.next = null;
        }
    }


    public ListNode detectCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        ListNode cur = head;
        while (cur != null && !set.contains(cur)) {
            set.add(cur);
            cur = cur.next;
        }
        if (set.contains(cur)) {
            return cur;
        }
        return null;
    }

    public String removeDuplicateLetters(String s) {
        boolean[] vis = new boolean[26];
        int[] nums = new int[26];
        for (int i = 0; i < s.length(); i++) {
            nums[s.charAt(i) - 'a']++;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 结果集非空, 新字符够小(想贪), 待淘汰字符有候选人(可贪)
            if (!vis[c - 'a']) {
                while (sb.length() > 0 && c < sb.charAt(sb.length() - 1)) {
                    // 待淘汰字符有候选人
                    if (nums[sb.charAt(sb.length() - 1) - 'a'] > 0) {
                        vis[sb.charAt(sb.length() - 1) - 'a'] = false;
                        sb.deleteCharAt(sb.length() - 1);
                    } else {
                        break;
                    }
                }
                vis[c - 'a'] = true;
                sb.append(c);
            }
            nums[c - 'a']--;
        }
        return sb.toString();
    }

}