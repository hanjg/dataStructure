package graph;

import binaryTree.Visit;

public interface Graph<E> {
	public int numberOfVertice();
	public int numberOfEdges();
	public E getVertex(int index);
	public double getWeight(int tail,int head);
	public void depthFirstSearch(Visit visit);
	public void breadthFirstSearch(Visit visit);
}
