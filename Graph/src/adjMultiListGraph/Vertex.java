package adjMultiListGraph;

class Vertex {
	Object data;
	EdgeBox firstEdge;//第一条依附于该节点的边
	
	public Vertex(Object data,EdgeBox firstEdge){
		this.data=data;
		this.firstEdge=firstEdge;
	}
}
