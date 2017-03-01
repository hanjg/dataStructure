package search.dynamicTree;

import binaryTree.Tree;

/**
 * 平衡二叉树<p>
 * 通过递归调用，插入和删除元素，返回新的根节点
 * @author hjg
 *
 * @param <E>
 */
public class AVLTree<E extends Comparable<? super E>> implements Tree<E>{
	private Node<E> root;
	public void insert(E elem){
		root=insert(root, elem);
	}
	/**
	 * 在node为根节点的树中插入elem
	 * @param node
	 * @param elem
	 * @return 新的根节点
	 */
	private Node<E> insert(Node<E> node,E elem){
		if (node==null) {//插入元素
			return new Node<E>(elem, null, null, 1);
		}
		int temp=elem.compareTo(node.val);
		if (temp<0) {
			node.left=insert(node.left, elem);
			if (height(node.left)-height(node.right)==2) {
				if (elem.compareTo(node.left.val)<0) {
					node=rightRotate(node);
				}else {
					node.left=leftRotate(node.left);
					node=rightRotate(node);
				}
			}
		}else if (temp>0) {
			node.right=insert(node.right, elem);
			if (height(node.right)-height(node.left)==2) {
				if (elem.compareTo(node.right.val)<0) {
					node.right=rightRotate(node.right);
					node=leftRotate(node);
				}else {
					node=leftRotate(node);
				}
			}
		}else {
			//元素已存在
		}
		node.height=Math.max(height(node.left), height(node.right))+1;
		return node;
	}
	public void delete(E elem){
		root=delete(root, elem);
	}
	/**
	 * 在node为根节点的树中删除elem
	 * @param node
	 * @param elem
	 * @return 新的根节点
	 */
	private Node<E> delete(Node<E> node, E elem){
		if (node==null) {//元素不存在
			return null;
		}
		int compare=elem.compareTo(node.val);
		if (compare<0) {
			node.left=delete(node.left, elem);
			if (height(node.right)-height(node.left)==2) {
				if (height(node.right.left)<=height(node.right.right)) {
					node=leftRotate(node);
				}else {
					node.right=rightRotate(node.right);
					node=leftRotate(node.left);
				}
			}
		}else if (compare>0) {
			node.right=delete(node.right, elem);
			if (height(node.left)-height(node.right)==2) {
				if (height(node.left.left)>=height(node.left.right)) {
					node=rightRotate(node);
				}else {
					node.left=leftRotate(node.left);
					node=rightRotate(node);
				}
			}
		}else {
			//删除元素
			if (node.left==null) {
				node=node.right;
			}else if (node.right==null) {
				node=node.left;
			}else {
				Node<E> temp=node.right;
				while(temp.left!=null){
					temp=temp.left;
				}
				node.val=temp.val;
				node.right=delete(node.right, node.val);
			}
		}
		if (node!=null) {
			node.height=Math.max(height(node.left), height(node.right))+1;
			return node;
		}
		return null;
	}
	/**
	 * 以root为根节点右旋
	 * @param root
	 * @return 新的根节点
	 */
	public Node<E> rightRotate(Node<E> root){
		Node<E> newRoot=root.left;
		root.left=newRoot.right;
		newRoot.right=root;
		//更新高度信息
		root.height=Math.max(height(root.left), height(root.right))+1;
		newRoot.height=Math.max(height(newRoot.left), height(newRoot.right))+1;
		return newRoot;
	}
	/**
	 * 以root为根节点左旋
	 * @param root
	 * @return 新的根节点
	 */
	public Node<E> leftRotate(Node<E> root){
		Node<E> newRoot=root.right;
		root.right=newRoot.left;
		newRoot.left=root;
		root.height=Math.max(height(root.left), height(root.right))+1;
		newRoot.height=Math.max(height(newRoot.left), height(newRoot.right))+1;
		return newRoot;
	}
	/**
	 * 节点的高度，null时高度为0
	 * @param node
	 * @return 
	 */
	private int height(Node<E> node){
		return node==null?0:node.height;
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

	public static class Node<E>{
		private E val;
		private Node<E> left;
		private Node<E> right;
		/**
		 * 节点的高度，即以该节点为根的树的高度
		 */
		private int height;
		public Node(E val, Node<E> left, Node<E> right, int height) {
			this.val=val;
			this.left=left;
			this.right=right;
			this.height=height;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public E getVal() {
			return val;
		}
		public void setVal(E val) {
			this.val = val;
		}
		public Node<E> getLeft() {
			return left;
		}
		public void setLeft(Node<E> left) {
			this.left = left;
		}
		public Node<E> getRight() {
			return right;
		}
		public void setRight(Node<E> right) {
			this.right = right;
		}
	}
	
}
