package algorithm.leetcode.buildtree;

import java.util.*;
public class Main{
    private int[] inorder;
    private int[] preorder;
    Map<Integer, Integer> map = new HashMap<>();
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String[] s1 = scan.nextLine().split(" ");
        String[] s2 = scan.nextLine().split(" ");
        int N = s1.length;
        int[] preorder = new int[N];
        int[] inorder = new int[N];
        for(int i = 0; i < N; i++){
            preorder[i] = Integer.valueOf(s1[i]);
            inorder[i] = Integer.valueOf(s2[i]);
        }
        Main m = new Main();
        TreeNode root = m.buildTree(preorder, inorder);
        TreeNode newRoot = m.buildSumTree(root);
        ArrayList<Integer> list = m.inOrderTraverse(newRoot);
        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i));
            if(i == list.size() - 1)
                break;
            System.out.print(" ");
        }
    }


    private TreeNode buildTree(int[] preorder, int[] inorder){
        this.preorder = preorder;
        this.inorder = inorder;
        for(int i = 0; i < inorder.length; i++){
            map.put(inorder[i], i);
        }
        return build(0, preorder.length - 1, 0, inorder.length - 1);
    }

    private TreeNode build(int preLeft, int preRight, int inLeft, int inRight){
        if(preLeft > preRight || inLeft > inRight)
            return null;
        TreeNode node = new TreeNode(preorder[preLeft]);
        int i = map.get(node.val);
        node.left = build(preLeft + 1,preLeft - inLeft + i, inLeft, i - 1);
        node.right = build(preLeft - inLeft + i + 1, preRight, i + 1, inRight);
        return node;
    }

    private TreeNode buildSumTree(TreeNode root){
        if(root == null)
            return null;
        TreeNode node = new TreeNode(sum(root) - root.val);
        node.left = buildSumTree(root.left);
        node.right = buildSumTree(root.right);
        return node;
    }

    private int sum(TreeNode root){
        if(root == null)
            return 0;
        return root.val + sum(root.left) + sum(root.right);
    }

    private ArrayList<Integer> inOrderTraverse(TreeNode root){
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        ArrayList<Integer> res = new ArrayList<>();
        while(!stack.isEmpty() || node != null){
            while(node != null){
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            res.add(node.val);
            node = node.right;
        }
        return res;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }
}