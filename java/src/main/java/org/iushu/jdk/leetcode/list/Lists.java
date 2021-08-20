package org.iushu.jdk.leetcode.list;

/**
 * @author iuShu
 * @since 8/19/21
 */
public class Lists {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {
            this(val, null);
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
        ListNode next(int val) {
            this.next = new ListNode(val);
            return next;
        }
        ListNode next(ListNode next) {
            this.next = next;
            return next;
        }
        void print() {
            ListNode node = this;
            while (node != null) {
                System.out.print(node.val + "\t");
                node = node.next;
            }
            System.out.println();
        }
    }

    static void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    static ListNode removeFromTail(ListNode head, int n) {
        if (head == null || (head.next == null && n == 1))
            return null;

        ListNode hh = new ListNode(0, head);    // head of head
        ListNode p = hh;
        ListNode node = head;
        while (node.next != null) {
            n = n == 0 ? 0 : n-1;
            p = n == 0 ? p.next : hh;
            node = node.next;
        }

        if (p.next.next == null) {
            p.next = null;
        }
        else {
            p.next.val = p.next.next.val;
            p.next.next = p.next.next.next;
        }

        return hh.next;
    }

    static ListNode reverse(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode node = head.next.next, p = head, q = p.next;
        if (head.next.next == null) {
            q.next = p;
            p.next = null;
            return q;
        }

        while (node != null) {
            if (p == head)
                p.next = null;
            q.next = p;
            p = q;
            q = node;
            node = node.next;
        }
        q.next = p;
        return q;
    }

    static ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null)
            return null;
        else if (l1 == null)
            return l2;
        else if (l2 == null)
            return l1;

        ListNode sh = l1.val >= l2.val ? l2 : l1;   // small head
        ListNode bh = l1.val >= l2.val ? l1 : l2;   // big head
        ListNode head = sh;                         // new head

        ListNode ssh = null, tmp;
        while (sh != null && bh != null) {
            if (sh != head && ssh == null) {
                ssh = head;
            }
            else if (ssh != null){
                ssh = ssh.next;
            }

            if (sh.val < bh.val)
                sh = sh.next;
            else {
                tmp = bh.next;
                bh.next = sh;
                ssh.next = bh;
                bh = tmp;
            }
        }
        if (bh != null)
            ssh.next.next = bh;

        return head;
    }

    static boolean isPalindrome(ListNode head) {
        return false;
    }

    static boolean hasCycle(ListNode head) {
        return false;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next(2).next(3).next(9);
        ListNode head2 = new ListNode(5);
        head2.next(6).next(7).next(9);

//        head.print();
//        head2.print();

//        deleteNode(head.next);
//        head.print();

//        removeFromTail(head, 3).print();

//        reverse(head).print();

//        merge(head, head2).print();

    }

}
