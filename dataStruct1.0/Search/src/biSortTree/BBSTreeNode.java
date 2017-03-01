package biSortTree;

public class BBSTreeNode {//平衡二叉树的节点
	
	
	public int bf;//节点平衡因子,用上面三个常数表示
	private int data;
	private BBSTreeNode leftChild;
	private BBSTreeNode rightChild;
	private BBSTreeNode parent;

	public BBSTreeNode(){
		leftChild=null;
		rightChild=null;
	}

	public BBSTreeNode(int item){
		leftChild=null;
		rightChild=null;
		this.data=item;
	}

	public BBSTreeNode(int item,BBSTreeNode left,BBSTreeNode right){
		this.data=item;
		leftChild=left;
		rightChild=right;
	}

	public void setParent(BBSTreeNode parent){
		this.parent=parent;
	}

	public BBSTreeNode getParent(){
		return parent;
	}

	public void setLeftChild(BBSTreeNode left){
		leftChild=left;
	}

	public BBSTreeNode getLeftChild(){
		return leftChild;
	}

	public void setRightChild(BBSTreeNode right){
		rightChild=right;
	}

	public BBSTreeNode getRightChild(){
		return rightChild;
	}

	public void setData(int item){
		data=item;
	}

	public int getData(){
		return data;
	}

}
