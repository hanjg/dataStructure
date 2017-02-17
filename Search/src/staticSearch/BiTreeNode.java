package staticSearch;

public class BiTreeNode {//以左右孩子表示法二叉链存储结构实现二叉树
	private Object data;
	private BiTreeNode leftChild;
	private BiTreeNode rightChild;
	
	public BiTreeNode() {
	}
	
	public BiTreeNode(Object data,BiTreeNode left,BiTreeNode right){
		this.data=data;
		this.leftChild=left;
		this.rightChild=right;
	}
	public BiTreeNode getLeft(){
		return leftChild;
	}
	
	public BiTreeNode getright(){
		return rightChild;
	}
	
	public Object getData(){
		return data;
	}

	public void setLeft(BiTreeNode left){
		leftChild=left;
	}
	
	public void setRight(BiTreeNode right){
		rightChild=right;
	}
	
	public void setData(Object data){
		this.data=data;
	}

	public static void printBiTree(BiTreeNode root,int level){//把二叉树逆时针旋转90°，按照凹入表示法打印
		if (root==null) {
			return;
		}
		printBiTree(root.getright(), level+1);
		if (level!=0) {
			for(int i=0;i<4*level;i++){
				System.out.print(" ");
			}
			System.out.print("---");
		}
		System.out.println(root.data);
		printBiTree(root.getLeft(),level+1);
	}

}
