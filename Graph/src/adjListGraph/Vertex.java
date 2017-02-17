package adjListGraph;

class Vertex {//临界表中每一个节点的信息
	Object data;
	DuLinkList adj;
	
	public Vertex(Object data){
		this.data=data;
		adj=new DuLinkList();
	}
	
	public Object getVertexData(){
		return data;
	}
}
