package binaryTree.iterator;

import binaryTree.BinaryTree;
import binaryTree.BinaryTree.Node;
import stack.SeqStack;
import stack.Stack;

public class PreorderIterator<E> implements BinaryTreeIterator<E>{
	private Stack<Node<E>> stack;
	public PreorderIterator(BinaryTree<E> tree) {
		stack=new SeqStack<>();
		stack.push(tree.getRoot());
	}
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public E next() {
		try {
			Node<E> temp=stack.pop();
			if(temp.getRight()!=null) stack.push(temp.getRight());
			if(temp.getLeft()!=null) stack.push(temp.getLeft());
			return temp.getVal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
