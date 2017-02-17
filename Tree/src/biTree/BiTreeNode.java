package biTree;

public class BiTreeNode {//以左右孩子表示法二叉链存储结构实现二叉树
	private Object data;
	private BiTreeNode leftChild;
	private BiTreeNode rightChild;
	
	public BiTreeNode() {
		leftChild=null;
		rightChild=null;
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
	
	public static BiTreeNode makeTree(){
		BiTreeNode gBiTreeNode=new BiTreeNode('G', null, null);
		BiTreeNode dBiTreeNode=new BiTreeNode('D', null, gBiTreeNode);
		BiTreeNode bBiTreeNode=new BiTreeNode('B', dBiTreeNode, null);
		BiTreeNode eBiTreeNode=new BiTreeNode('E', null,null);
		BiTreeNode fBiTreeNode=new BiTreeNode('F', null, null);
		BiTreeNode cBiTreeNode=new BiTreeNode('C', eBiTreeNode, fBiTreeNode);
		return new BiTreeNode('A', bBiTreeNode, cBiTreeNode);
	}

	public static void preOrder(BiTreeNode treeNode,Visit visit){//以treeNode为根节点前序遍历
		if (treeNode!=null) {
			visit.print(treeNode.data);
			preOrder(treeNode.leftChild,visit);
			preOrder(treeNode.rightChild,visit);
		}
	}
	
	public static void inOrder(BiTreeNode treeNode,Visit visit){//以treeNode为根节点中序遍历
		if (treeNode!=null) {
			inOrder(treeNode.leftChild,visit);
			visit.print(treeNode.data);
			inOrder(treeNode.rightChild,visit);
		}
	}
	
	public static void postOrder(BiTreeNode treeNode,Visit visit){//以treeNode为根节点后序遍历
		if (treeNode!=null) {
			postOrder(treeNode.leftChild,visit);
			postOrder(treeNode.rightChild,visit);
			visit.print(treeNode.data);
		}
	}
	
	public static void levelOrder(BiTreeNode treeNode,Visit visit) throws Exception{
		//以treeNode为根节点层序遍历
		if (treeNode==null) {
			return;
		}
		LinQueue queue=new LinQueue();//按照层序遍历的次序存放将要访问的节点
		BiTreeNode current;//指向将要访问的节点
		queue.append(treeNode);
		while(queue.notEmpty()){
			current=(BiTreeNode)queue.delete();
			visit.print(current.data);
			if (current.leftChild!=null) {
				queue.append(current.leftChild);
			}
			if (current.rightChild!=null) {
				queue.append(current.rightChild);
			}
		}
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
	
	public static BiTreeNode preSearch(BiTreeNode root,Object item){//前序遍历查找元素
		//查找成功返回该节点，失败返回null
		if (root==null) {
			//System.err.println("null;");
			return null;
		}
		//System.out.println("search "+root.data+";");
		if (root.data.equals(item)) {
			//System.out.println(root.data+":"+item+" find");
			return root;
		}
		if (root.getLeft()!=null) {
			//System.out.print("search "+root.data+"'s left--");
			BiTreeNode temp=preSearch(root.getLeft(), item);
			if (temp!=null) {
				//System.out.println(root.data+":"+item+" find");
				return temp;
			}
		}
		if (root.getright()!=null) {
			//System.out.print("search "+root.data+"'s right--");
			BiTreeNode temp=preSearch(root.getright(), item);
			if (temp!=null) {
				//System.out.println(root.data+":"+item+" find");
				return temp;
			}
		}
		//System.out.println("search "+root.data+" end;");
		return null;
	}
	public static void main(String[] args) {
		BiTreeNode root=makeTree();
		/*Visit visit=new Visit();
		preOrder(root, visit);System.out.println(" preOrder");
		inOrder(root, visit);System.out.println(" inOrder");
		postOrder(root, visit);System.out.println(" postOrder");
		try {
			levelOrder(root, visit);System.out.println(" levelOrder");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		printBiTree(root, 0);
		BiTreeNode node=preSearch(root, 'B');
		if (node!=null) {
			System.out.println("exsit:"+node.data);
		}
		else {
			System.out.println("not exsit:"+'B');
		}
		BiTreeNode node2=preSearch(root, 'H');
		if (node2!=null) {
			System.out.println("exsit:"+node2.data);
		}
		else {
			System.out.println("not exsit:"+'H');
		}
	}

}
