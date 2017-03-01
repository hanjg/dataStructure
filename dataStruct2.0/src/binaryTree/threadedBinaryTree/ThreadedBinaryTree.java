package binaryTree.threadedBinaryTree;

import binaryTree.Visit;

public class ThreadedBinaryTree<E> {
	private Node<E> root;
	/**
	 * 头结点，left指向根节点，right指向遍历时的最后一个节点
	 */
	private Node<E> head;
	private Node<E> pre;
	
	public ThreadedBinaryTree(Node<E> root) {
		this.root=root;
		this.head=new Node<E>(null,null,null);
	}
	
	/**
	 * 中序线索化
	 */
	public void inorderThread(){
		if (root==null) {
			head.leftChild=head;
			head.rightChild=head;
			head.rightTag=1;
		}
		else {
			head.leftChild=root;
			pre=head;
			inThread(root);
			pre.rightTag=1;
			pre.rightChild=head;
			head.rightTag=1;
			head.rightChild=pre;
		}
	}
	
	private void inThread(Node<E> node){
		if(node==null) return;
		inThread(node.leftChild);
		//建立pre的后继线索
		if (pre.rightChild==null) {
			pre.rightTag=1;
			pre.rightChild=node;
		}
		//建立node的前驱线索
		if (node.leftChild==null) {
			node.leftTag=1;
			node.leftChild=pre;
		}
		pre=node;
		inThread(node.rightChild);
	}
	
	public void inorderTraverse(Visit visit){
		//node指向根节点
		Node<E> node=head.leftChild;
		//node指向中序遍历的第一个节点
		while(node.leftTag==0){
			node=node.leftChild;
		}
		while(node!=head){
			visit.print(node.val);
			while(node.rightTag==1&&node.rightChild!=head){
				//直到node存在右孩子节点或者node的后续节点为head
				node=node.rightChild;
				visit.print(node.val);
			}
			node=node.rightChild;
			while(node.leftTag==0){
				node=node.leftChild;
			}
		}
	}
	/**
	 * 反向中序遍历
	 * @param visit
	 */
	public void reverseInorderTraverse(Visit visit){
		Node<E> node=head.rightChild;
		while(node!=head){
			visit.print(node.val);
			while(node.leftTag==1&&node.leftChild!=head){
				node=node.leftChild;
				visit.print(node.val);
			}
			node=node.leftChild;
			while(node.rightTag==0){
				node=node.rightChild;
			}
		}
	}
	public static class Node<E>{
		private E val;
		private Node<E> leftChild;
		private Node<E> rightChild;
		/**
		 * leftTag=0:指向节点的左孩子。leftTag=1:指向节点的前驱
		 */
		private int leftTag;
		/**
		 * rightTag=0:指向节点的右孩子。rightTag=1:指向节点的后继
		 */
		private int rightTag;
		
		public Node(E val,Node<E> left,Node<E> right) {
			this.val=val;
			this.leftChild=left;
			this.rightChild=right;
		}
	}
}
