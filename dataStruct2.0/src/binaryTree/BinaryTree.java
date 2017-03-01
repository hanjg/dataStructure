package binaryTree;

import queue.LinQueue;
import queue.Queue;

public class BinaryTree<E> implements Tree<E>{
	protected Node<E> root;
	
	public BinaryTree(Node<E> root){
		this.root=root;
	}
	
	public BinaryTree(){
		this.root=null;
	}
	
	public void preorderTraverse(Visit visit){
		preorderTraverse(root, visit);
		System.out.println();
	}
	public void inorderTraverse(Visit visit){
		inorderTraverse(root, visit);
		System.out.println();
	}
	public void postorderTraverse(Visit visit){
		postorderTraverse(root, visit);
		System.out.println();
	}
	public void levelorderTraverse(Visit visit){
		if(root==null) return;
		Queue<Node<E>> queue=new LinQueue<>();
		queue.add(root);
		while(!queue.isEmpty()){
			try {
				Node<E> cur=queue.poll();
				visit.print(cur.val);
				if(cur.left!=null) queue.add(cur.left);
				if(cur.right!=null) queue.add(cur.right);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println();
	}
	private void preorderTraverse(Node<E> node,Visit visit){
		if(node==null) return;
		visit.print(node.val);
		preorderTraverse(node.left, visit);
		preorderTraverse(node.right, visit);
	}
	private void inorderTraverse(Node<E> node,Visit visit){
		if(node==null) return;
		inorderTraverse(node.left, visit);
		visit.print(node.val);
		inorderTraverse(node.right, visit);
	}	
	
	private void postorderTraverse(Node<E> node,Visit visit){
		if(node==null) return;
		postorderTraverse(node.left, visit);
		postorderTraverse(node.right, visit);
		visit.print(node.val);
	}
	public Node<E> getRoot() {
		return root;
	}
	/**
	 * 把二叉树逆时针旋转90°，按照凹入表示法打印
	 */
	public void print(){
		System.out.println("[");
		print(root, 1);
		System.out.println("]");
	}
	private void print(Node<E> node,int level){
		if (node==null) return;
		print(node.right, level+1);
		for(int i=1;i<level;i++){
			System.out.print("\t");
		}
		System.out.println("-----"+node.val);
		print(node.left, level+1);
	}
	/**
	 * 二叉链表节点
	 * @author hjg
	 *
	 * @param <E>
	 */
	public static class Node<E>{
		private E val;
		private Node<E> left;
		private Node<E> right;
		
		public Node(E val,Node<E> left,Node<E> right){
			this.val=val;
			this.left=left;
			this.right=right;
		}
		public E getVal() {
			return val;
		}
		public Node<E> getLeft() {
			return left;
		}
		public Node<E> getRight() {
			return right;
		}
		public void setVal(E val) {
			this.val = val;
		}
		public void setLeft(Node<E> left) {
			this.left = left;
		}
		public void setRight(Node<E> right) {
			this.right = right;
		}
	}
}
