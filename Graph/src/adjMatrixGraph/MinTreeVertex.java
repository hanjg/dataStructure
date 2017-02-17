package adjMatrixGraph;

/**
 * 存储最小生成树的节点数据和相应边的权值
 * 最小生成树：边的权值总和最小的生成树
 * @author hjg
 *
 */
public class MinTreeVertex {
	Object vertexData;
	int weight;
	int link;//与该节点相邻的节点下标和权重
	
	public MinTreeVertex(Object object,int weight, int link){
		vertexData=object;
		this.weight=weight;
		this.link=link;
	}
	
	public MinTreeVertex(Object object){
		vertexData=object;
		weight=0;
		link=0;
	}
}
