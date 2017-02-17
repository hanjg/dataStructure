package adjMultiListGraph;

class EdgeBox {
	int mark;//访问标记
	int iVex,jVex;//该边依附的两个顶点的位置i,j
	EdgeBox iLink,jLink;//依附i,j两个顶点的下一条边
	int weight;
	
	public EdgeBox(int iVex,int jVex,EdgeBox iLink,EdgeBox jLink,int weight){
		this.iVex=iVex;
		this.jVex=jVex;
		this.iLink=iLink;
		this.jLink=jLink;
		this.weight=weight;
	}
}
