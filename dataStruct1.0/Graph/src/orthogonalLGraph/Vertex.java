package orthogonalLGraph;

class Vertex {//图中每一个节点的信息
	Object data;
	ArcBox firstIn;//该节点第一条入弧
	ArcBox firstOut;//该节点第一条出弧
	
	public Vertex(Object data,ArcBox firstIn,ArcBox firstOut){
		this.data=data;
		this.firstIn=firstIn;
		this.firstOut=firstOut;
	}
}
