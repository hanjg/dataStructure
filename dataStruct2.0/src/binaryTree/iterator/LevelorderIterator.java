package binaryTree.iterator;

import binaryTree.BinaryTree;
import binaryTree.BinaryTree.Node;
import queue.LinQueue;
import queue.Queue;

public class LevelorderIterator<E> implements BinaryTreeIterator<E> {
	Queue<Node<E>> queue;
	
	public LevelorderIterator(BinaryTree<E> tree) {
		queue=new LinQueue<>();
		if (tree.getRoot()!=null) {
			queue.add(tree.getRoot());
		}
	}
	@Override
	public boolean hasNext() {
		return !queue.isEmpty();
	}

	@Override
	public E next() {
		try {
			Node<E> temp=queue.poll();
			if(temp.getLeft()!=null) queue.add(temp.getLeft());
			if(temp.getRight()!=null) queue.add(temp.getRight());
			return temp.getVal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
