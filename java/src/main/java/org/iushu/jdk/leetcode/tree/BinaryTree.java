package org.iushu.jdk.leetcode.tree;

import java.util.*;

/**
 * @author iuShu
 * @since 8/10/21
 */
public class BinaryTree {

    static <T> List<T> preorderTraversal(TreeNode<T> root) {
        if (root == null)
            return Collections.emptyList();

        List<T> list = new ArrayList<T>();
        list.add(root.val);
        list.addAll(preorderTraversal(root.left));
        list.addAll(preorderTraversal(root.right));
        return list;
    }

    static <T> List<T> inorderTraversal(TreeNode<T> root) {
        if (root == null)
            return Collections.emptyList();

        List<T> list = new ArrayList<T>();
        list.addAll(inorderTraversal(root.left));
        list.add(root.val);
        list.addAll(inorderTraversal(root.right));
        return list;
    }

    static <T> List<T> postorderTraversal(TreeNode<T> root) {
        if (root == null)
            return Collections.emptyList();

        List<T> list = new ArrayList<T>();
        list.addAll(postorderTraversal(root.left));
        list.addAll(postorderTraversal(root.right));
        list.add(root.val);
        return list;
    }

    static <T> List<List<T>> levelTraversal(TreeNode<T> root) {
        if (root == null)
            return Collections.emptyList();
        if (root.left == null && root.right == null)
            return Collections.singletonList(Collections.singletonList(root.val));

        List<List<T>> lists = new ArrayList<List<T>>();
        List<TreeNode<T>> temp = new ArrayList<TreeNode<T>>();
        temp.add(root);
        while (!temp.isEmpty()) {
            List<T> level = new ArrayList<T>();
            for (TreeNode<T> node : temp)
                level.add(node.val);
            lists.add(level);

            List<TreeNode<T>> list = new ArrayList<TreeNode<T>>(temp);
            temp.clear();
            for (TreeNode<T> node : list) {
                if (node.left != null)
                    temp.add(node.left);
                if (node.right != null)
                    temp.add(node.right);
            }
        }
        return lists;
    }

    static int maxDepth(TreeNode root) {
        if (root == null)
            return 0;

        int depth = 1;
        if (root.left == null && root.right == null)
            return depth;

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        depth += left > right ? left : right;
        return depth;
    }

    static <T> boolean isSymmetric(TreeNode<T> root) {
        if (root == null)
            return false;
        else if (root.left == null && root.right == null)
            return true;
        else if (root.left == null || root.right == null)
            return false;

        TreeNode<T> left = root.left;
        TreeNode<T> right = root.right;
        return compared(left, right);
    }
    private static <T> boolean compared(TreeNode<T> node1, TreeNode<T> node2) {
        if (node1 != null && node2 != null) {
            if (!node1.val.equals(node2.val))
                return false;
            return compared(node1.left, node2.right) & compared(node1.right, node2.left);
        }
        return node1 == null && node2 == null;
    }

    static boolean hasPathSum(TreeNode<Integer> root, int targetSum) {
        return false;
    }
//    private static Integer traversalPathSum(TreeNode<Integer> root, Integer sum, int target) {
//
//    }

    static class TreeNode<T> {

        T val;
        TreeNode<T> left;
        TreeNode<T> right;
        TreeNode(T val) {
            this.val = val;
        }

        TreeNode<T> addLeft(T left) {
            return addLeft(new TreeNode<>(left));
        }

        TreeNode<T> addRight(T right) {
            return addRight(new TreeNode<>(right));
        }

        TreeNode<T> addLeft(TreeNode<T> left) {
            this.left = left;
            return left;
        }

        TreeNode<T> addRight(TreeNode<T> right) {
            this.right = right;
            return right;
        }

    }

    public static void main(String[] args) {
        TreeNode<Integer> root = new TreeNode<>(1);
//        root.addLeft(7).addLeft(5);
//        root.left.addRight(9);
//        root.addRight(19).addLeft(14).addLeft(11);
//        root.right.addRight(23);
        root.addLeft(2);

//        root.addLeft(7).addLeft(3);
//        root.left.addRight(9);
//        root.addRight(7).addRight(3);
//        root.right.addLeft(9);

        System.out.println(preorderTraversal(root));
        System.out.println(inorderTraversal(root));
        System.out.println(postorderTraversal(root));
        System.out.println(levelTraversal(root));
        System.out.println(maxDepth(root));
        System.out.println(isSymmetric(root));
        System.out.println(hasPathSum(root, 1));

    }

}