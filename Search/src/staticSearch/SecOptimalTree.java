package staticSearch;

public class SecOptimalTree {//次优二叉查找树
	public BiTreeNode root;
	private double[] sumWeight;//[0,i)项的权值和:wo+...w(i-1)
	
	public SecOptimalTree(double[] weight){
		root=new BiTreeNode();
		sumWeight=new double[weight.length+1];
		sumWeight[0]=0;
		for(int i=0;i<weight.length;i++){
			sumWeight[i+1]=sumWeight[i]+weight[i];
		}
	}
	
	public void SecOptimal(BiTreeNode node,Object[] elems,int low,int high){
		//构建以node为根节点的查找树
		int i=low;//存储使得min最小的i,即i将[low,i-1]和[i+1,high]的权重和尽量均分
		double min=Math.abs(sumWeight[high+1]+sumWeight[low]-sumWeight[i+1]-sumWeight[i]);
		for(int j=low+1;j<=high;j++){
			if (Math.abs(sumWeight[high+1]+sumWeight[low]-sumWeight[j+1]-sumWeight[j])<min) {
				i=j;
				min=Math.abs(sumWeight[high+1]+sumWeight[low]-sumWeight[j+1]-sumWeight[j]);
			}
		}
		node.setData(elems[i]);
		if (i==low) {
			node.setLeft(null);
		}
		else {
			BiTreeNode left=new BiTreeNode();
			node.setLeft(left);
			SecOptimal(left, elems, low, i-1);
		}
		if (i==high) {
			node.setRight(null);
		}
		else {
			BiTreeNode right=new BiTreeNode();
			node.setRight(right);
			SecOptimal(right, elems, i+1, high);
		}
	}
	
	public static void main(String[] args) {
		Character[] characters={'A','B','C','D','E','F','G','H','I'};
		double[] weight={1,1,2,5,3,4,4,3,5};
		SecOptimalTree tree=new SecOptimalTree(weight);
		tree.SecOptimal(tree.root, characters, 0, characters.length-1);
		BiTreeNode.printBiTree(tree.root, 0);
	}

}
