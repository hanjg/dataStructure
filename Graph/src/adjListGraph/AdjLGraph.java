package adjListGraph;

import adjMatrixGraph.Arc;
import adjMatrixGraph.SeqList;

public class AdjLGraph {//邻接表存储结构表示图
	public static final int maxWeight=10000;
	private SeqList vertices;
	private int numOfEdge;

	public AdjLGraph(int maxV){//maxV为节点个数
		vertices=new SeqList(maxV);
		numOfEdge=0;
	}

	public static void createGraph(AdjLGraph graph,Object[] vertices,int NumOfVertices,
			Arc[] edges,int NumOfEdge){
		for(int i=0;i<NumOfVertices;i++){
			graph.insertVertex((Vertex)vertices[i]);
		}
		for(int j=0;j<NumOfEdge;j++){
			graph.insertEdge(edges[j].row, edges[j].col, edges[j].weight);
		}
	}

	public int getNumOfVertices(){
		return vertices.getSize();
	}

	public int getNumOfEdge(){
		return numOfEdge;
	}

	public Object getValue(int v){
		return ((Vertex)vertices.getData(v)).getVertexData();
	}

	public int getWeight(int v1,int v2){
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		Vertex vertex=(Vertex) vertices.getData(v1);
		EdgeData edgeData=vertex.adj.getEdge(v2);
		return edgeData.weight;
	}

	public void insertVertex(Vertex vertex){
		vertices.insert(vertex);
	}

	public void insertEdge(int v1,int v2,int weight){
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		Vertex vertex=(Vertex) vertices.getData(v1);
		vertex.adj.orderInsert(new EdgeData(v2, weight));
		numOfEdge++;
	}

	public void deleteEdge(int v1,int v2) throws Exception{
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		Vertex vertex=(Vertex) vertices.getData(v1);
		vertex.adj.delete(v2);
		numOfEdge--;
	}

	public int getFirstNeighbor(int v){
		if (v<0||v>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v error");
		}
		Vertex vertex=(Vertex) vertices.getData(v);
			return vertex.adj.getFirstEdge();
	}

	public int getNextNeighbor(int v1,int v2){
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		Vertex vertex=(Vertex) vertices.getData(v1);
		return vertex.adj.getNextEdge(v2);
	}
	
	public void print(){
		for(int i=0;i<vertices.getSize();i++){
			System.out.print(i+":");
			if (((Vertex)vertices.getData(i)).adj.isEmpty()) {
				System.out.println();
			}
			else {
				try {
					((Vertex)vertices.getData(i)).adj.print();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		Character[] characters={'A','B','C','D','E'};
		Vertex[] vertices=new Vertex[characters.length];
		for(int i=0;i<vertices.length;i++){
			Vertex temp=new Vertex(characters[i]);
			vertices[i]=temp;
		}
		Arc[] edges={new Arc(0, 4, 20),
				new Arc(0, 1, 10),
				new Arc(1, 3, 30),
				new Arc(2, 1, 40),
				new Arc(3, 2, 50)};
		AdjLGraph graph=new AdjLGraph(characters.length);
		createGraph(graph, vertices, vertices.length, edges, edges.length);
		graph.print();
		graph.insertEdge(1, 2, 11);
		System.out.println();
		graph.print();
		try {
			graph.deleteEdge(3, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
		graph.print();
		//System.out.println(graph.getNextNeighbor(0, 2));
	}

}
