package org.iushu.jdk.algrithom;

import java.util.Arrays;

/**
 * @author iuShu
 * @since 7/14/21
 */
public class LinkedList<T> {

    private Node<T> head = new Node<>(null);

    public void add(T value) {
        Node<T> n = head;
        while (n.next != null)
            n = n.next;
        n.next = new Node<>(value);
    }

    public void add(T... values) {
        Arrays.stream(values).forEach(this::add);
    }

    public boolean contains(T value) {
        Node<T> n = head;
        while (n != null && n.value != value)
            n = n.next;
        return n != null;
    }

    public void traversal() {
        Node<T> n = head;
        while (n.next != null) {
            n = n.next;
            System.out.print(n.value.toString() + ' ');
        }
        System.out.println();
    }

    public void reverse() {
        if (head.next == null)
            return;

        Node<T> n = head.next, p, q = null;
        while (n != null) {
            p = n.next;
            n.next = q;
            q = n;
            n = p;
        }
        head.next = q;
    }

    class Node<T> {
        T value;
        Node<T> next;
        public Node(T value) {
            this.value = value;
        }
        @Override
        public String toString() {
            return value.toString();
        }
    }

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(2,5,15,1,6,8,10,2,7,9);
        list.traversal();
        list.reverse();
        list.traversal();
    }

}
