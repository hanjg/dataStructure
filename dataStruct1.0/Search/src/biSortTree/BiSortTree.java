package biSortTree;

public class BiSortTree {//二叉排序树
	private BSTreeNode root;
	
	private void inOrder(BSTreeNode node,Visit visit){
		if (node!=null) {
			inOrder(node.getLeftChild(), visit);
			visit.print(node.getData());
			inOrder(node.getRightChild(), visit);
		}
	}
	
	private void preOrder(BSTreeNode node,Visit visit){
		if (node!=null) {
			visit.print(node.getData());
			preOrder(node.getLeftChild(), visit);
			preOrder(node.getRightChild(), visit);
		}
	}
	
	public	BiSortTree(){
		root=null;
	}
	
	public void setRoot(BSTreeNode node){
		root=node;
	}
	
	public BSTreeNode getRoot(){
		return root;
	}
	
	public void inOrder(Visit visit){
		inOrder(root, visit);
	}
	
	public void preOrder(Visit visit){
		preOrder(root, visit);
	}
	
	public BSTreeNode getLeft(BSTreeNode current){
		return current!=null?current.getLeftChild():null;
	}
	
	public BSTreeNode getRight(BSTreeNode current){
		return current!=null?current.getRightChild():null;
	}
	
	public BSTreeNode find(int item){
		if (root!=null) {
			BSTreeNode temp=root;
			while(temp!=null){
				if (temp.getData()==item) {
					return temp;
				}
				if (temp.getData()<item) {
					temp=temp.getRightChild();
				}
				else {
					temp=temp.getLeftChild();
				}
			}
		}
		return null;
	}
	
	public void insert(BSTreeNode ptr,int item){//以ptr为根节点插入item
		if (item==ptr.getData()) {
			return;
		}
		if (item<ptr.getData()) {
			if (ptr.getLeftChild()==null) {
				BSTreeNode temp=new BSTreeNode(item);
				temp.setParent(ptr);
				ptr.setLeftChild(temp);
			}
			else {
				insert(ptr.getLeftChild(), item);
			}
		}
		else {
			if (ptr.getRightChild()==null) {
				BSTreeNode temp=new BSTreeNode(item);
				temp.setParent(ptr);
				ptr.setRightChild(temp);
			}
			else {
				insert(ptr.getRightChild(), item);
			}
		}
	}
	
	public void deltete(BSTreeNode ptr,int item){
		if (ptr==null) {
			return;
		}
		if (item<ptr.getData()) {
			deltete(ptr.getLeftChild(), item);
		}
		else if (item>ptr.getData()) {
			deltete(ptr.getRightChild(), item);
		}
		else if (ptr.getLeftChild()!=null&&ptr.getRightChild()!=null) {//待删除的节点有左右孩子
			BSTreeNode min=ptr.getRightChild();//找到比item大的最小节点
			while(min.getLeftChild()!=null){
				min=min.getLeftChild();
			}
			ptr.setData(min.getData());
			deltete(ptr.getRightChild(), min.getData());
		}
		else if (ptr.getLeftChild()!=null&&ptr.getRightChild()==null) {//待删除的节点只有左孩子
			if (ptr.getParent().getLeftChild()==ptr) {//ptr是其双亲节点的左孩子
				ptr.getLeftChild().setParent(ptr.getParent());
				ptr.getParent().setLeftChild(ptr.getLeftChild());
			}
			else {
				ptr.getLeftChild().setParent(ptr.getParent());
				ptr.getParent().setRightChild(ptr.getLeftChild());
			}
		}
		else if (ptr.getLeftChild()==null&&ptr.getRightChild()!=null) {//待删除的节点只有右孩子
			if (ptr.getParent().getLeftChild()==ptr) {
				ptr.getRightChild().setParent(ptr.getParent());
				ptr.getParent().setLeftChild(ptr.getRightChild());
			}
			else {
				ptr.getRightChild().setParent(ptr.getParent());
				ptr.getParent().setRightChild(ptr.getRightChild());
			}
		}
		else {//待删除的节点无左右孩子，为叶节点
			if (ptr.getParent().getLeftChild()==ptr) {
				ptr.getParent().setLeftChild(null);
			}
			else {
				ptr.getParent().setRightChild(null);
			}
		}
	}
	public static void printBiTree(BSTreeNode root,int level){//把二叉树逆时针旋转90°，按照凹入表示法打印
		if (root==null) {
			return;
		}
		printBiTree(root.getRightChild(), level+1);
		if (level!=0) {
			for(int i=0;i<4*level;i++){
				System.out.print(" ");
			}
			System.out.print("---");
		}
		System.out.println(root.getData());
		printBiTree(root.getLeftChild(),level+1);
	}

	public static void main(String[] args) {
		int[] a={4,5,7,2,1,9,8,11,3};
		BSTreeNode root=new BSTreeNode(a[0]);
		BiSortTree tree=new BiSortTree();
		tree.setRoot(root);
		for(int i=1;i<a.length;i++){
			tree.insert(tree.getRoot(), a[i]);//从根节点插入数据
		}
		System.out.println("9: "+tree.find(9)!=null);
		printBiTree(tree.getRoot(), 0);
		tree.deltete(tree.getRoot(),4);
		printBiTree(tree.getRoot(), 0);
	}

}
