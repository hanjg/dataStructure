package biTree;

public class BiTree extends BiTreeNode{//通过BiTreeNode来实现BiTree,将根节点地址存储在root中
	private BiTreeNode root;
	
	
	public BiTree(BiTreeNode root){
		this.root=root;
	}
	
	public static BiTree makeTree(){
		BiTreeNode gBiTreeNode=new BiTreeNode('G', null, null);
		BiTreeNode dBiTreeNode=new BiTreeNode('D', null, gBiTreeNode);
		BiTreeNode bBiTreeNode=new BiTreeNode('B', dBiTreeNode, null);
		BiTreeNode eBiTreeNode=new BiTreeNode('E', null,null);
		BiTreeNode fBiTreeNode=new BiTreeNode('F', null, null);
		BiTreeNode cBiTreeNode=new BiTreeNode('C', eBiTreeNode, fBiTreeNode);
		return new BiTree(new BiTreeNode('A', bBiTreeNode, cBiTreeNode));
	}

	public void preOrder(Visit visit){
		preOrder(root, visit);
	}
	
	public void inOrder(Visit visit){
		inOrder(root, visit);
	}

	public void postOrder(Visit visit){
		postOrder(root, visit);
	}

	public void levelOrder(Visit visit) throws Exception{
		levelOrder(root, visit);
	}

	public void printBiTree(){
		printBiTree(root, 0);
	}
	
	public BiTreeNode preSearch(Object item){
		return preSearch(root, item);
	}
	
	public static void main(String[] args) {
		BiTree tree=makeTree();
		/*Visit visit=new Visit();
		preOrder(root, visit);System.out.println(" preOrder");
		inOrder(root, visit);System.out.println(" inOrder");
		postOrder(root, visit);System.out.println(" postOrder");
		try {
			levelOrder(root, visit);System.out.println(" levelOrder");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		tree.printBiTree();
		BiTreeNode node=tree.preSearch('B');
		if (node!=null) {
			System.out.println("exsit:"+node.getData());
		}
		else {
			System.out.println("not exsit:"+'B');
		}
		BiTreeNode node2=tree.preSearch('H');
		if (node2!=null) {
			System.out.println("exsit:"+node2.getData());
		}
		else {
			System.out.println("not exsit:"+'H');
		}
	}

}
