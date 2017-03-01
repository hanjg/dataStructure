package biThreadTree;


public class ThreadNode {
	Object data;
	ThreadNode leftchild;
	ThreadNode rightchild;
	int leftThread;//0表示指向左孩子节点，1表示指向前驱节点
	int rightThread;//0表示指向右孩子节点，1表示指向后继节点
	
	public ThreadNode(){
		
	}
	
	public ThreadNode(Object data,ThreadNode left,ThreadNode right){
		this.data=data;
		leftchild=left;
		rightchild=right;
	}
	
	
}
