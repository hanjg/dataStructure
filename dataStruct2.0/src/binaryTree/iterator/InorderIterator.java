package binaryTree.iterator;

import binaryTree.BinaryTree;
import binaryTree.BinaryTree.Node;
import stack.SeqStack;
import stack.Stack;

public class InorderIterator<E> implements BinaryTreeIterator<E> {
	private Stack<Node<E>> stack;
	
	public InorderIterator(BinaryTree<E> tree) {
		stack=new SeqStack<>();
		pushAll(tree.getRoot());
	}
	/**
	 * 模仿中序遍历过程中，将遍历的路径节点压入递归栈
	 * @param root
	 */
	private void pushAll(Node<E> node) {
		while(node!=null){
			stack.push(node);
			node=node.getLeft();
		}
	}
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public E next() {
		try {
			Node<E> temp=stack.pop();
			pushAll(temp.getRight());
			return temp.getVal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
