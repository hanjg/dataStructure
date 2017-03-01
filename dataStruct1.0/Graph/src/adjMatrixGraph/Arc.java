package adjMatrixGraph;

/**
 * 存储弧的信息
 * @author hjg
 *
 */
public class Arc {
	public int row;
	public int col;
	public int weight;
	
	public Arc(int r,int c,int w){
		row=r;
		col=c;
		weight=w;
	}
	
	public static void main(String[] args) {
		Character[] characters={'A','B','C','D','E'};
		Arc[] edges={new Arc(0, 1, 10),
				new Arc(0, 4, 20),
				new Arc(1, 3, 30),
				new Arc(2, 1, 40),
				new Arc(3, 2, 50)};
		AdjMGraph graph=new AdjMGraph(characters.length);
		AdjMGraph.createGraph(graph, characters, characters.length, edges, edges.length);
	
	}

}
