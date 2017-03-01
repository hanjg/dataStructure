package binaryTree.iterator;

import binaryTree.BinaryTree;
import binaryTree.BinaryTree.Node;
import stack.SeqStack;
import stack.Stack;

/**
 * 后续迭代的两种实行：1、前序迭代的反向输出；2、模仿后序递归的栈
 * @author hjg
 *
 * @param <E>
 */
public class PostorderIterator<E> implements BinaryTreeIterator<E> {
	Stack<Node<E>> stack;
	public PostorderIterator(BinaryTree<E> tree) {
		stack=new SeqStack<>();
		pushAll(tree.getRoot());
	}
	private void pushAll(Node<E> node){
		while(node!=null){
			stack.push(node);
			pushAll(node.getRight());
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
			return temp.getVal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
