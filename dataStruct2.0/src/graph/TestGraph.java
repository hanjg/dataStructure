package graph;

import binaryTree.Visit;
import graph.DiGraph.Arc;
import graph.UnDiGraph.Edge;
import list.List;
import list.SeqList;

public class TestGraph {
	public static void main(String[] args) {
		Character[] characters={'A','B','C','D','E','F','G'};
		SeqList<Character> vertice=new SeqList<>(characters.length);
		for(Character character:characters){
			vertice.add(character);
		}
		
		System.out.println("DiGraph:");
		Arc[] arcs={new Arc(0, 1, 50),
				new Arc(0, 2, 60),
				new Arc(1, 3, 65),
				new Arc(1, 4, 40),
				new Arc(2, 3, 52),
				new Arc(2, 6, 45),
				new Arc(3, 4, 50),
				new Arc(3, 5, 30),
				new Arc(3, 6, 42),
				new Arc(4, 5, 70),
				new Arc(5, 6, 40)};
		DiGraph<Character> diGraph=new DiGraph<>(vertice, arcs);
		Visit visit=new Visit();
		System.out.println("dfs:");
		diGraph.depthFirstSearch(visit);
		System.out.println();
		System.out.println("bfs:");
		diGraph.breadthFirstSearch(visit);
		System.out.println();
		System.out.println("topologicalSort:");
		List<Character> list=diGraph.topologicalSort();
		list.print();
		System.out.println("ciriticalPath:");
		list=diGraph.criticalPath();
		list.print();
		
		try {
			System.out.println("dijkstra:");
			int v0=2;
			List<List<Character>> res=diGraph.dijkstra(v0);
			for(int i=0;i<res.size();i++){
				System.out.print(characters[v0]+"->"+characters[i]+":");
				res.get(i).print();
			
			}
			System.out.println("bellmanFord:");
			res=diGraph.bellmanFord(v0);
			for(int i=0;i<res.size();i++){
				System.out.print(characters[v0]+"->"+characters[i]+":");
				res.get(i).print();
			
			}
			System.out.println("floyd:");
			List<List<List<Character>>> res2=diGraph.floyd();
			for(int i=0;i<res2.size();i++){
				for(int j=0;j<res2.get(i).size();j++){
					System.out.print(characters[i]+"->"+characters[j]+":");
					res2.get(i).get(j).print();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("\n\n\nUnDiGraph:");
		Edge[] edges={new Edge(0, 1, 50),
				new Edge(0, 2, 60),
				new Edge(1, 3, 65),
				new Edge(1, 4, 40),
				new Edge(2, 3, 52),
				new Edge(2, 6, 45),
				new Edge(3, 4, 50),
				new Edge(3, 5, 30),
				new Edge(3, 6, 42),
				new Edge(4, 5, 70)};
		UnDiGraph<Character> unDiGraph=new UnDiGraph<>(vertice, edges);
		System.out.println("dfs:");
		unDiGraph.depthFirstSearch(visit);
		System.out.println();
		System.out.println("bfs:");
		unDiGraph.breadthFirstSearch(visit);
		System.out.println();
		
		System.out.println("heap dijstra:");
		int v0=2;
		List<List<Character>> res=diGraph.dijkstra(v0);
		for(int i=0;i<res.size();i++){
			System.out.print(characters[v0]+"->"+characters[i]+":");
			res.get(i).print();
		
		}
		System.out.println();
		
		System.out.println("prim:");
		Edge[] minTree=unDiGraph.prim();
		for(Edge edge:minTree){
			edge.print();
		}
		System.out.println("cruskal:");
		minTree=unDiGraph.kruskal();
		for(Edge edge:minTree){
			edge.print();
		}
		
		System.out.println("arti:");
		list=unDiGraph.findArticulation();
		list.print();
	}
}
