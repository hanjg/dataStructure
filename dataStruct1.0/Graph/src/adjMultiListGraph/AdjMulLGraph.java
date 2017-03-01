package adjMultiListGraph;

import adjMatrixGraph.Arc;
import adjMatrixGraph.SeqList;

public class AdjMulLGraph {//邻接多重表表示无向图(未完成)
	private SeqList vertices;
	private int numOfEdge;
	
	
	public AdjMulLGraph(int maxV){
		vertices=new SeqList(maxV);
		numOfEdge=0;
	}

	public static void createGraph(AdjMulLGraph graph,Object[] vertices,int NumOfVertices,
			Arc[] edges,int NumOfArc){
		for(int i=0;i<NumOfVertices;i++){
			graph.insertVertex(new Vertex(vertices[i], null));
		}
		for(int j=0;j<NumOfArc;j++){
			graph.insertEdge(edges[j].row, edges[j].col, edges[j].weight);
		}
	}

	public int getNumOfVertices(){
		return vertices.getSize();
	}


	public int getNumOfEdge(){
		return numOfEdge;
	}


	public Object getValue(int v){//返回下标为v的节点的值
		return ((Vertex)vertices.getData(v)).data;
	}


	public int getWeight(int v1,int v2){//返回<v1,v2>的权重
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		EdgeBox arcBox=getEdgeBox(v1, v2);
		return arcBox.weight;
	}


	public EdgeBox getEdgeBox(int v1,int v2){
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		Vertex vertex=(Vertex) vertices.getData(v1);
		if (vertex.firstEdge!=null) {
			EdgeBox current=vertex.firstEdge;
			while(current.iLink!=null&&current.jVex<v2){
				current=current.iLink;
			}
			if (current!=null) {
				return current;
			}
		}
		return null;
	}


	public void insertVertex(Vertex vertex){
		vertices.insert(vertex);
	}


	public void insertEdge(int v1,int v2,int weight){
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		EdgeBox current;
		Vertex vertex;
		EdgeBox temp=new EdgeBox(v1, v2, null, null, weight);
		
		vertex=(Vertex) vertices.getData(v1);
		if (vertex.firstEdge==null) {
			temp.iLink=vertex.firstEdge;
			vertex.firstEdge=temp;
		}
		else if (vertex.firstEdge.iVex==v1&&v2<vertex.firstEdge.jVex) {
			temp.iLink=vertex.firstEdge;
			vertex.firstEdge=temp;
		}
		else if(vertex.firstEdge.iVex==v1){
			current=vertex.firstEdge;
			while(current.iLink!=null&&current.iLink.jVex<v2){
				current=current.iLink;
			}
			if (current.iLink==null) {
				current.iLink=temp;
			}
			else {
				temp.iLink=current.iLink;
				current.iLink=temp;
			}
		}
		
		vertex=(Vertex) vertices.getData(v2);//弧头节点
		if (vertex.firstEdge==null||v1<vertex.firstEdge.iVex) {
			temp.jLink=vertex.firstEdge;
			vertex.firstEdge=temp;
		}
		else {
			current=vertex.firstEdge;
			while(current.jLink!=null&&current.jLink.iVex<v1){
				current=current.jLink;
			}
			if (current.jLink==null) {
				current.jLink=temp;
			}
			else {
				temp.jLink=current.jLink;
				current.jLink=temp;
			}
		}
		numOfEdge++;
	}


	public void deleteEdge(int v1,int v2) throws Exception{
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		Vertex vertex;
		EdgeBox current;
		vertex=(Vertex) vertices.getData(v1);
		if (vertex.firstEdge==null) {
			throw new Exception("v2 error");
		}
		else if (vertex.firstEdge.jVex==v2) {
			vertex.firstEdge=vertex.firstEdge.iLink;
		}
		else {
			current=vertex.firstEdge;
			while(current.iLink!=null&&current.iLink.jVex!=v2){
				current=current.iLink;
			}
			if (current.iLink==null) {
				throw new Exception("v2 error");
			}
			else {
				current.iLink=current.iLink.iLink;
			}
		}
		vertex=(Vertex) vertices.getData(v2);
		if (vertex.firstEdge==null) {
			throw new Exception("v1 error");
		}
		else if (vertex.firstEdge.iVex==v1) {
			vertex.firstEdge=vertex.firstEdge.jLink;
		}
		else {
			current=vertex.firstEdge;
			while(current.jLink!=null&&current.jLink.iVex!=v1){
				current=current.jLink;
			}
			if (current.jLink==null) {
				throw new Exception("v1 error");
			}
			else {
				current.jLink=current.jLink.jLink;
			}
		}
	}


	public int getFirstNeighbor(int v){
		if (v<0||v>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v error");
		}
		Vertex vertex=(Vertex) vertices.getData(v);
		if (vertex.firstEdge==null) {
			return -1;
		}
		return vertex.firstEdge.jVex;
	}


	public int getNextNeighbor(int v1,int v2){
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		Vertex vertex=(Vertex) vertices.getData(v1);
		EdgeBox arcBox=vertex.firstEdge;
		while(arcBox!=null&&arcBox.jVex!=v2){
			arcBox=arcBox.iLink;
		}
		if (arcBox!=null&&arcBox.iLink!=null) {
			return arcBox.iLink.jVex;
		}
		return -1;
	}


	public void print(){
		for(int i=0;i<vertices.getSize();i++){
			System.out.print(i+":");
			Vertex vertex=(Vertex) vertices.getData(i);
			EdgeBox current=vertex.firstEdge;
			while(current!=null){
				System.out.print(current.jVex+" ");
				current=current.iLink;
			}
			System.out.println();
		}
		
	}


	public static void main(String[] args) {
		Character[] characters={'A','B','C','D','E'};
		Vertex[] vertices=new Vertex[characters.length];
		Arc[] edges={new Arc(0, 4, 20),
				new Arc(0, 1, 10),
				new Arc(1, 3, 30),
				new Arc(2, 1, 40),
				new Arc(3, 2, 50)};
		AdjMulLGraph graph=new AdjMulLGraph(characters.length);
		createGraph(graph, vertices, vertices.length, edges, edges.length);graph.print();
		//graph.insertEdge(1, 2, 11);graph.print();
		try {
			//graph.deleteEdge(3, 2);graph.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
