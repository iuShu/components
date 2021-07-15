package org.iushu.jdk.algrithom;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * @author iuShu
 * @since 7/14/21
 */
public class BinaryTreeNode<T> {

    private T value;
    private BinaryTreeNode<T> left;
    private BinaryTreeNode<T> right;

    public BinaryTreeNode<T> set(T value, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
        return this;
    }

    public BinaryTreeNode<T> append(BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        this.left = left;
        this.right = right;
        return this;
    }

    public static <T> BinaryTreeNode<T> create(T value) {
        BinaryTreeNode<T> node = new BinaryTreeNode<>();
        node.value = value;
        return node;
    }

    public static <T> BinaryTreeNode<T> create(T value, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        BinaryTreeNode<T> node = new BinaryTreeNode<>();
        node.value = value;
        node.left = left;
        node.right = right;
        return node;
    }

    public static void preOrderRecursion(BinaryTreeNode root) {
        if (root == null)
            return;

        System.out.println(root.value);
        preOrderRecursion(root.left);
        preOrderRecursion(root.right);
    }

    public static void inOrderRecursion(BinaryTreeNode root) {
        if (root == null)
            return;

        inOrderRecursion(root.left);
        System.out.println(root.value);
        inOrderRecursion(root.right);
    }

    public static void postOrderRecursion(BinaryTreeNode root) {
        if (root == null)
            return;

        postOrderRecursion(root.left);
        postOrderRecursion(root.right);
        System.out.println(root.value);
    }

    public static void preOrderTraversal(BinaryTreeNode root) {
        if (root == null)
            return;

        BinaryTreeNode n = root;
        Stack<BinaryTreeNode> stack = new Stack<>();
        while (n != null || !stack.isEmpty()) {
            while (n != null) {
                System.out.println(n.value);    // visit
                stack.push(n);
                n = n.left;
            }
            n = stack.pop();
            n = n.right;
        }
    }

    public static void inOrderTraversal(BinaryTreeNode root) {
        if (root == null)
            return;

        BinaryTreeNode n = root;
        Stack<BinaryTreeNode> stack = new Stack<>();
        while (n != null || !stack.empty()) {
            while (n != null) {
                stack.push(n);
                n = n.left;
            }
            n = stack.pop();
            System.out.println(n.value);    // visit
            n = n.right;
        }
    }

    public static void postOrderTraversal(BinaryTreeNode root) {
        if (root == null)
            return;

        BinaryTreeNode n = root;
        Stack<BinaryTreeNode> stack = new Stack<>();
        BinaryTreeNode ar = new BinaryTreeNode<>();    // repeat visit delegate
        while (n != null || !stack.empty()) {
            while (n != null) {
                stack.push(n);
                n = n.left;
            }
            n = stack.pop();
            if (n.right != null) {
                stack.push(ar.set(n.value, n.left, null));    // avoid repeat visit
                n = n.right;
            }
            else {
                System.out.println(n.value);    // visit
                n = null;
            }
        }
    }

    public static void levelTraversal(BinaryTreeNode root) {
        if (root == null)
            return;

        BinaryTreeNode n = root;
        Deque<BinaryTreeNode> deque = new ArrayDeque<>();
        deque.offer(n);
        while (!deque.isEmpty()) {
            n = deque.poll();
            System.out.println(n.value);    // visit
            if (n.left != null)
                deque.offer(n.left);
            if (n.right != null)
                deque.offer(n.right);
        }
    }

    public static int calculateDepth(BinaryTreeNode root) {
        if (root == null)
            return 0;

        int ld = calculateDepth(root.left);
        int rd = calculateDepth(root.right);
        return (ld > rd ? ld : rd) + 1;
    }

    public static int calculateDepth(BinaryTreeNode root, BinaryTreeNode node) {
        if (root == null)
            return 0;
        if (root == node)
            return 1;

        Stack<BinaryTreeNode> stack = new Stack<>();
        postOrderSearch(root, node, stack);
        return stack.size();
    }

    private static void postOrderSearch(BinaryTreeNode root, BinaryTreeNode node, Stack<BinaryTreeNode> stack) {
        if (root == null)
            return;
        if (stack.size() != 0 && stack.peek() == node)
            return;

        stack.push(root);
        postOrderSearch(root.left, node, stack);
        postOrderSearch(root.right, node, stack);

        if (stack.peek() == node)
            return;
        stack.pop();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static void main(String[] args) {
        BinaryTreeNode<Integer> root = create(50, create(20), create(67));
        root.left.append(create(12), create(24));
        root.right.append(null, create(71));
        root.left.left.append(create(5), null);

        System.out.println(calculateDepth(root) + "\n");
        System.out.println(calculateDepth(root, root.right.right) + "\n");

        levelTraversal(root);
//        preOrderTraversal(root);
//        inOrderTraversal(root);
//        postOrderTraversal(root);
    }

}
