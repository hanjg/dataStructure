package binaryTree.huffmanTree;

/**
 * huffman树（最优二叉树）：树的带权路径长度最小的树。<p>
 * 树的带权路径长度：所有叶节点的带权路径长度之和。<p>
 * 节点的带权路径长度：到根节点的路径长度*节点的权重。
 * @author hjg
 *
 */
public class HuffmanTree{
	/**
	 * 叶节点数量
	 */
	private int number;
	/**
	 * huffman树的所有节点
	 */
	private Node[] nodes;
	
	private static final int MAX_WEIGHT=Integer.MAX_VALUE;
	
	public HuffmanTree(int number){
		this.number=number;
		nodes=new Node[number*2-1];
	}
	
	/**
	 * @param weight 节点的权重
	 */
	public void init(int[] weight){
		//构造叶节点
		for(int i=0;i<number;i++){
			nodes[i]=new Node(weight[i]);
		}
		//构造非叶节点
		for(int i=number;i<nodes.length;i++){
			//未加入树的节点中权重最小的两个节点的下标
			int min1Weight=MAX_WEIGHT;
			int min2Weight=MAX_WEIGHT;
			int min1Index=-1;
			int min2Index=-1;
			for(int j=0;j<i;j++){
				if(nodes[j].parent!=-1) continue;
				if (nodes[j].weight<min1Weight) {
					min2Weight=min1Weight;min2Index=min1Index;
					min1Weight=nodes[j].weight;min1Index=j;
				}
				else if (nodes[j].weight<min2Weight) {
					min2Weight=nodes[j].weight;min2Index=j;
				}
			}
			nodes[i]=new Node(nodes[min1Index].weight+nodes[min2Index].weight,min1Index, min2Index);
			nodes[min1Index].parent=i;
			nodes[min2Index].parent=i;
		}
	}
	
	public Code[] getCodes(){
		Code[] codes=new Code[number];
		for(int i=0;i<number;i++){
			StringBuilder builder=new StringBuilder();
			//从叶节点开始逆向求编码
			int cur=i;
			while(nodes[cur].parent!=-1){//cur不为根节点
				int parent=nodes[cur].parent;
				builder.append((cur==nodes[parent].leftChild?0:1));
				cur=parent;
			}
			codes[i]=new Code(builder.reverse().toString(), nodes[i].weight);
		}
//		for(int i=0;i<nodes.length;i++){
//			System.out.println(i+":\tweight:"+nodes[i].weight+"\tparent:"+nodes[i].parent+"\tleft:"+
//					nodes[i].leftChild+"\tright"+nodes[i].rightChild);
//		}
		return codes;
	}
	 
	/**
	 * huffman树的节点，使用双亲孩子表示法，仿真链表结构，-1表示null
	 * @author hjg
	 *
	 */
	public static class Node{
		private int weight;
		private int parent;
		private int leftChild;
		@SuppressWarnings("unused")
		private int rightChild;
		
		public Node(int weight){
			this.weight=weight;
			parent=-1;
			leftChild=-1;
			rightChild=-1;
		}
		public Node(int weight,int leftChild,int rightChild){
			this.weight=weight;
			parent=-1;
			this.leftChild=leftChild;
			this.rightChild=rightChild;
		}
	}
	public static class Code{
		private String code;
		private int weight;
		public Code(String code,int weight){
			this.code=code;
			this.weight=weight;
		}
		public String getCode() {
			return code;
		}
		public int getWeight() {
			return weight;
		}
	}
}
