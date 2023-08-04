package com.evildoer.train.algorithm;

import lombok.Data;

@Data
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public ListNode sortList(ListNode head) {
        return sortList(head, null);
    }

    private ListNode sortList(ListNode head, ListNode tail) {
        if (head == null) {
            return null;
        }
        if (head.next == tail) {
            head.next = null;
            return head;
        }
        ListNode slow = head, fast = head;
        while (fast != tail) {
            slow = slow.next;
            fast = fast.next;
            if (fast != tail) {
                fast = fast.next;
            }
        }
        // 此时slow在列表中心
        ListNode list = sortList(head, slow);
        ListNode list2 = sortList(slow, tail);
        return merge(list, list2);
    }

    private ListNode merge(ListNode list, ListNode list2) {
        ListNode dummyHead = new ListNode(0);
        ListNode tmp = dummyHead, tmp2 = list, tmp3 = list2;
        while (tmp2 != null && tmp3 != null) {
            if (tmp2.val <= tmp3.val) {
                tmp.next = tmp2;
                tmp2 = tmp2.next;
            } else {
                tmp.next = tmp3;
                tmp3 = tmp3.next;
            }
            tmp = tmp.next;
        }
        if (tmp2 != null) {
            tmp.next = tmp2;
        } else if (tmp3 != null) {
            tmp.next = tmp3;
        }
        return dummyHead.next;
    }
}