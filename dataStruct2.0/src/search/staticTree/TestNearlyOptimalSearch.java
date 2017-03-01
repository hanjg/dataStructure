package search.staticTree;

import binaryTree.Visit;
import list.List;
import list.SeqList;

public class TestNearlyOptimalSearch {
	public static void main(String[] args) {
		Character[] characters={'A','B','C','D','E','F','G','H','I'};
		
		List<Character> vertice=new SeqList<>(characters.length);
		for(int i=0;i<characters.length;i++){
			vertice.add(characters[i]);
		}
		double[] weight={1,1,2,5,3,4,4,3,5};
		NearlyOptimalSearchTree<Character> tree=new NearlyOptimalSearchTree<>(vertice, weight);
		tree.print();
		
		System.out.println("E exist?:"+tree.contain('E'));
		System.out.println("J exist?:"+tree.contain('J'));
		tree.inorderTraverse(new Visit());
		
		
	}
}
