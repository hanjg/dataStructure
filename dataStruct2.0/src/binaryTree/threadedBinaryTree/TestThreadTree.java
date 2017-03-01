package binaryTree.threadedBinaryTree;
import binaryTree.Visit;
import binaryTree.threadedBinaryTree.ThreadedBinaryTree.Node;

public class TestThreadTree {
	public static void main(String[] args) {

		Node<Character> hNode=new Node<Character>('H', null, null);
		Node<Character> gNode=new Node<Character>('G', null, hNode);
		Node<Character> dNode=new Node<Character>('D', null, gNode);
		Node<Character> bNode=new Node<Character>('B', dNode, null);
		Node<Character> eNode=new Node<Character>('E', null, null);
		Node<Character> fNode=new Node<Character>('F', null, null);
		Node<Character> cNode=new Node<Character>('C', eNode, fNode);
		ThreadedBinaryTree<Character> tree=
				new ThreadedBinaryTree<>(new Node<Character>('A', bNode, cNode));
		
		tree.inorderThread();
		tree.inorderTraverse(new Visit());System.out.println();
		tree.reverseInorderTraverse(new Visit());System.err.println();
	}
}
