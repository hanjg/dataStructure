package HaffmanTree;

class HaffNode{//haffman树的节点为双亲孩子存储结构
	int weight;
	int flag;//0表示未加入树，1表示已加入树
	int parent;
	int leftChild;
	int rightChild;
}

class HaffCode{//haffman编码类
	int [] bit;
	int start;//编码的起始下标
	int weight;
	
	public HaffCode(int n){
		bit=new int[n];
		start=n;
	}
}

public class HaffmanTree {//haffman树：具有最小带权路径长度的二叉树
						//带权路径长度:所有（叶节点的权值*相应叶节点到根节点的路径长度）之和
						//双亲孩子表示法，仿真链表结构
	private static final int maxWeight=10000;
	private int nodeNum;//叶节点个数
	private HaffNode[] nodes;//存储叶节点和非叶节点
	
	public HaffmanTree(int n){
		nodeNum=n;
		nodes=new HaffNode[2*n-1];
	}
	
	public void createHaffmanTree(int[] weight){//构造haffmanTree
		//初始化2n-1个结点
		for(int i=0;i<2*nodeNum-1;i++){
			HaffNode temp=new HaffNode();
			if (i<nodeNum) {
				temp.weight=weight[i];
			}
			else {
				temp.weight=0;
			}
			temp.flag=0;
			temp.parent=-1;
			temp.leftChild=temp.rightChild=-1;
			nodes[i]=temp;
		}
		//构造n-1个非叶节点
		int minWeight1,minWeight2;
		int index1,index2;//两个权重最小的树的权重和下标
		for(int i=nodeNum;i<2*nodeNum-1;i++){//i表示正在构造的叶节点的下标,[n,2n-1)共构造n-1个结点
			minWeight1=minWeight2=maxWeight;
			index1=index2=0;
			for(int j=0;j<i;j++){
				if (nodes[j].flag==0&&nodes[j].weight<minWeight1) {
					minWeight2=minWeight1;
					index2=index1;
					minWeight1=nodes[j].weight;
					index1=j;
				}
				else if (nodes[j].flag==0&&nodes[j].weight<minWeight2) {
					minWeight2=nodes[j].weight;
					index2=j;
				}
			}
			nodes[index1].parent=i;
			nodes[index2].parent=i;
			nodes[i].leftChild=index1;
			nodes[i].rightChild=index2;
			nodes[index1].flag=nodes[index2].flag=1;
			nodes[i].weight=nodes[index1].weight+nodes[index2].weight;
		}
	}
	
	public HaffCode[] createHaffmanCode(){//由haffmanTree构造haffmanCode
		HaffCode[] codes=new HaffCode[nodeNum];
		HaffCode temp;
		int child,parent;
		
		for(int i=0;i<nodeNum;i++){
			temp=new HaffCode(nodeNum);
			temp.weight=nodes[i].weight;
			child=i;
			parent=nodes[i].parent;
			while(parent!=-1){//parent=-1表示child节点为头结点
				temp.start--;
				if (nodes[parent].leftChild==child) {
					temp.bit[temp.start]=0;
				}
				else {
					temp.bit[temp.start]=1;
				}
				child=parent;
				parent=nodes[child].parent;
			}
			codes[i]=temp;
		}
		return codes;
	}
	
	public static void main(String[] args) {
		int[] weight={1,3,5,7,5,3,4,6,2,3,4,3,4,5,4,33};
		int n=weight.length;
		HaffmanTree tree=new HaffmanTree(n);
		
		tree.createHaffmanTree(weight);
		HaffCode[] codes=tree.createHaffmanCode();
		
		for(int i=0;i<n;i++){
			System.out.print((i+1)+":weight="+weight[i]+" code=");
			for(int j=codes[i].start;j<n;j++){
				System.out.print(codes[i].bit[j]);
			}
			System.out.println();
		}
	}

}
