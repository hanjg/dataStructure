package orthogonalLGraph;

import adjMatrixGraph.Arc;
import adjMatrixGraph.SeqList;

public class OLGraph {//使用十字链表存储图,通常存储有向图
	private SeqList vertices;
	private int numOfArc;
	
	public OLGraph(int maxV){
		vertices=new SeqList(maxV);
		numOfArc=0;
	}
	
	
	public static void createGraph(OLGraph graph,Object[] vertices,int NumOfVertices,
			Arc[] edges,int NumOfArc){
		for(int i=0;i<NumOfVertices;i++){
			graph.insertVertex(new Vertex(vertices[i], null, null));
		}
		for(int j=0;j<NumOfArc;j++){
			graph.insertArc(edges[j].row, edges[j].col, edges[j].weight);
		}
	}


	public int getNumOfVertices(){
		return vertices.getSize();
	}


	public int getNumOfEdge(){
		return numOfArc;
	}


	public Object getValue(int v){//返回下标为v的节点的值
		return ((Vertex)vertices.getData(v)).data;
	}


	public int getWeight(int v1,int v2){//返回<v1,v2>的权重
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		ArcBox arcBox=getArcBox(v1, v2);
		return arcBox.weight;
	}
	
	public ArcBox getArcBox(int v1,int v2){
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		Vertex vertex=(Vertex) vertices.getData(v1);
		if (vertex.firstOut!=null) {
			ArcBox current=vertex.firstOut;
			while(current.tailLink!=null&&current.col<v2){
				current=current.tailLink;
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


	public void insertArc(int v1,int v2,int weight){
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		ArcBox current;
		Vertex vertex;
		ArcBox temp=new ArcBox(v1, v2, weight);
		
		vertex=(Vertex) vertices.getData(v1);//弧尾节点
		if (vertex.firstOut==null||v2<vertex.firstOut.col) {
			temp.tailLink=vertex.firstOut;
			vertex.firstOut=temp;
		}
		else {
			current=vertex.firstOut;
			while(current.tailLink!=null&&current.tailLink.col<v2){
				current=current.tailLink;
			}
			if (current.tailLink==null) {
				current.tailLink=temp;
			}
			else {
				temp.tailLink=current.tailLink;
				current.tailLink=temp;
			}
		}
		
		vertex=(Vertex) vertices.getData(v2);//弧头节点
		if (vertex.firstIn==null||v1<vertex.firstIn.row) {
			temp.headLink=vertex.firstIn;
			vertex.firstIn=temp;
		}
		else {
			current=vertex.firstIn;
			while(current.headLink!=null&&current.headLink.row<v1){
				current=current.headLink;
			}
			if (current.headLink==null) {
				current.headLink=temp;
			}
			else {
				temp.headLink=current.headLink;
				current.headLink=temp;
			}
		}
		numOfArc++;
	}


	public void deleteArc(int v1,int v2) throws Exception{
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		Vertex vertex;
		ArcBox current;
		vertex=(Vertex) vertices.getData(v1);
		if (vertex.firstOut==null) {
			throw new Exception("v2 error");
		}
		else if (vertex.firstOut.col==v2) {
			vertex.firstOut=vertex.firstOut.tailLink;
		}
		else {
			current=vertex.firstOut;
			while(current.tailLink!=null&&current.tailLink.col!=v2){
				current=current.tailLink;
			}
			if (current.tailLink==null) {
				throw new Exception("v2 error");
			}
			else {
				current.tailLink=current.tailLink.tailLink;
			}
		}
		vertex=(Vertex) vertices.getData(v2);
		if (vertex.firstIn==null) {
			throw new Exception("v1 error");
		}
		else if (vertex.firstIn.row==v1) {
			vertex.firstIn=vertex.firstIn.headLink;
		}
		else {
			current=vertex.firstIn;
			while(current.headLink!=null&&current.headLink.row!=v1){
				current=current.headLink;
			}
			if (current.headLink==null) {
				throw new Exception("v1 error");
			}
			else {
				current.headLink=current.headLink.headLink;
			}
		}
	}


	public int getFirstNeighbor(int v){
		if (v<0||v>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v error");
		}
		Vertex vertex=(Vertex) vertices.getData(v);
		if (vertex.firstOut==null) {
			return -1;
		}
		return vertex.firstOut.col;
	}


	public int getNextNeighbor(int v1,int v2){
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		Vertex vertex=(Vertex) vertices.getData(v1);
		ArcBox arcBox=vertex.firstOut;
		while(arcBox!=null&&arcBox.col!=v2){
			arcBox=arcBox.tailLink;
		}
		if (arcBox!=null&&arcBox.tailLink!=null) {
			return arcBox.tailLink.col;
		}
		return -1;
	}


	public void print(){
		for(int i=0;i<vertices.getSize();i++){
			System.out.print(i+":");
			Vertex vertex=(Vertex) vertices.getData(i);
			ArcBox current=vertex.firstOut;
			while(current!=null){
				System.out.print(current.col+" ");
				current=current.tailLink;
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
		OLGraph graph=new OLGraph(characters.length);
		createGraph(graph, vertices, vertices.length, edges, edges.length);graph.print();
	graph.insertArc(1, 2, 11);graph.print();
		try {
	graph.deleteArc(3, 2);graph.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
