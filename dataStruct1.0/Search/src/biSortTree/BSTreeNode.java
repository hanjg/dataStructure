package biSortTree;

public class BSTreeNode {//包含双亲节点的三叉链二叉排序树的节点
	private int data;
	private BSTreeNode leftChild;
	private BSTreeNode rightChild;
	private BSTreeNode parent;
	
	public BSTreeNode(){
		leftChild=null;
		rightChild=null;
	}
	
	public BSTreeNode(int item){
		leftChild=null;
		rightChild=null;
		this.data=item;
	}
	
	public BSTreeNode(int item,BSTreeNode left,
			BSTreeNode right){
		this.data=item;
		leftChild=left;
		rightChild=right;
	}
	
	public void setParent(BSTreeNode parent){
		this.parent=parent;
	}
	
	public BSTreeNode getParent(){
		return parent;
	}
	
	public void setLeftChild(BSTreeNode left){
		leftChild=left;
	}
	
	public BSTreeNode getLeftChild(){
		return leftChild;
	}
	
	public void setRightChild(BSTreeNode right){
		rightChild=right;
	}
	
	public BSTreeNode getRightChild(){
		return rightChild;
	}
	
	public void setData(int item){
		data=item;
	}
	
	public int getData(){
		return data;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
