package binaryTree;

import binaryTree.BinaryTree.Node;
import binaryTree.iterator.BinaryTreeIterator;
import binaryTree.iterator.InorderIterator;
import binaryTree.iterator.LevelorderIterator;
import binaryTree.iterator.PostorderIterator;
import binaryTree.iterator.PreorderIterator;

public class TestTree {
	public static void main(String[] args) {
		Node<Character> hNode=new Node<Character>('H', null, null);
		Node<Character> gNode=new Node<Character>('G', null, hNode);
		Node<Character> dNode=new Node<Character>('D', null, gNode);
		Node<Character> bNode=new Node<Character>('B', dNode, null);
		Node<Character> eNode=new Node<Character>('E', null, null);
		Node<Character> fNode=new Node<Character>('F', null, null);
		Node<Character> cNode=new Node<Character>('C', eNode, fNode);
		BinaryTree<Character> tree=new BinaryTree<>(new Node<Character>('A', bNode, cNode));
		
		tree.print();
		
		Visit visit=new Visit();
		System.out.println("pre:");
		tree.preorderTraverse(visit);
		System.out.println("in:");
		tree.inorderTraverse(visit);
		System.out.println("post:");
		tree.postorderTraverse(visit);
		System.out.println("level:");
		tree.levelorderTraverse(visit);
		
		
		System.out.println("pre");
		BinaryTreeIterator<Character> iterator=new PreorderIterator<>(tree);
		while(iterator.hasNext()){
			System.out.print(iterator.next()+" ");
		}
		System.out.println();
		
		System.out.println("in:");
		iterator=new InorderIterator<>(tree);
		while(iterator.hasNext()){
			System.out.print(iterator.next()+" ");
		}
		System.out.println();
		
		System.out.println("post:");
		iterator=new PostorderIterator<>(tree);
		while(iterator.hasNext()){
			System.out.print(iterator.next()+" ");
		}
		System.out.println();
		
		System.out.println("level:");
		iterator=new LevelorderIterator<>(tree);
		while(iterator.hasNext()){
			System.out.print(iterator.next()+" ");
		}
		System.out.println();
	}
}
