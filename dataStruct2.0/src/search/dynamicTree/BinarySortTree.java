package search.dynamicTree;

import binaryTree.BinaryTree;
import binaryTree.Tree;

/**
 * 二叉排序树
 * @author hjg
 *
 * @param <E>
 */
public class BinarySortTree<E extends Comparable<? super E>> extends BinaryTree<E> implements Tree<E>{
	/**
	 * 从根节点开始查找元素。<p>
	 * 元素存在，cur指向该元素。元素不存在，pre指向查找路径上最后一个节点
	 * @param elem
	 * @return 元素是否存在
	 */
	public boolean contain(E elem){
		return contain(root, elem);
	}
	private boolean contain(Node<E> node, E elem){
		if (node==null) {
			return false;
		}
		int compare=elem.compareTo(node.getVal());
		if (compare==0) {
			return true;
		}else if (compare<0) {
			return contain(node.getLeft(), elem);
		}else {
			return contain(node.getRight(), elem);
		}
	}
	public void insert(E elem){
		root=insert(root, elem);
	}
	/**
	 * 在node为根节点的树中插入elem
	 * @param node
	 * @param elem
	 * @return 新的根节点
	 */
	private Node<E> insert(Node<E> node, E elem){
		if (node==null) {
			return new Node<E>(elem, null, null);
		}
		int compare=elem.compareTo(node.getVal());
		if (compare<0) {
			node.setLeft(insert(node.getLeft(), elem));
		}else if (compare>0) {
			node.setRight(insert(node.getRight(), elem));
		}
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
		if (node==null) {
			return null;
		}
		int compare=elem.compareTo(node.getVal());
		if (compare<0) {
			node.setLeft(delete(node.getLeft(), elem));
		}else if (compare>0) {
			node.setRight(delete(node.getRight(), elem));
		}else {
			if (node.getLeft()==null) {
				node=node.getRight();
			}else if (node.getRight()==null) {
				node=node.getLeft();
			}else {
				Node<E> temp=node.getRight();
				while(temp.getLeft()!=null){
					temp.setLeft(temp.getLeft());
				}
				node.setVal(temp.getVal());
				node.setRight(delete(node.getRight(), node.getVal()));
			}
		}
		return node;
	}
}
