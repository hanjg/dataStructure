package biThreadTree;

import biTree.Visit;

public class BiThreadTree {//线索二叉树
	ThreadNode root;//树的根节点
	ThreadNode head;//head为头结点，不存储数据，左孩子节点存储实际上的根节点；还存在后继节点，指向最后遍历的节点
	ThreadNode pre;
	
	public void makeTree(){
		ThreadNode gThreadNode=new ThreadNode('G', null, null);
		ThreadNode dThreadNode=new ThreadNode('D', null, gThreadNode);
		ThreadNode bThreadNode=new ThreadNode('B', dThreadNode, null);
		ThreadNode eThreadNode=new ThreadNode('E', null,null);
		ThreadNode fThreadNode=new ThreadNode('F', null, null);
		ThreadNode cThreadNode=new ThreadNode('C', eThreadNode, fThreadNode);
		root=new ThreadNode('A', bThreadNode, cThreadNode);//root A为根节点
		head=new ThreadNode(null, root, null);//head为头结点
	}
	
	public void inOrderThreading(){//建立中序线索
		head.leftThread=0;//指向左孩子节点
		if (root==null) {
			head.leftchild=head;
			head.rightThread=1;//指向后继节点
			head.rightchild=head;
		}
		else {
			head.leftchild=root;
			pre=head;
			inThreading(root);
			pre.rightThread=1;
			pre.rightchild=head;
			head.rightThread=1;
			head.rightchild=pre;//head的后继节点指向最后一个节点
		}
	}
	
	private void inThreading(ThreadNode node){//中序线索化
			if (node==null) {
				return;
			}
			inThreading(node.leftchild);
			if (node.leftchild==null) {//建立node的前驱节点
				node.leftThread=1;
				node.leftchild=pre;
			}
			if (pre.rightchild==null) {//建立pre的后继节点
				pre.rightThread=1;
				pre.rightchild=node;
			}
			pre=node;
			inThreading(node.rightchild);
		
	}
	
	public void inOrderTravelForward(Visit visit){//正向前序遍历二叉树
		ThreadNode node=root;
		while(node!=head){
			while(node.leftThread==0){
				node=node.leftchild;
			}
			visit.print(node.data);
			while(node.rightThread==1&&node.rightchild!=head){//访问后继节点
				node=node.rightchild;
				visit.print(node.data);
			}
			node=node.rightchild;//若没有后继节点则指向右孩子节点
		}
		System.out.println();
	}
	
	public void inOrderTravelBackward(Visit visit){//反向前序遍历二叉树
		ThreadNode node=head.rightchild;
		while(node!=head){
			visit.print(node.data);
			while(node.leftThread==1&&node.leftchild!=head){
				node=node.leftchild;
				visit.print(node.data);
			}
			node=node.leftchild;
			while(node.rightThread==0){
				node=node.rightchild;
			}
		}
		System.out.println();
	}
	
	public static void printThreadTree(ThreadNode root,int level){//把二叉树逆时针旋转90°，按照凹入表示法打印
		if (root==null) {
			return;
		}
		printThreadTree(root.rightchild, level+1);
		if (level!=0) {
			for(int i=0;i<4*level;i++){
				System.out.print(" ");
			}
			System.out.print("---");
		}
		System.out.println(root.data);
		printThreadTree(root.leftchild,level+1);
	}

	public static void main(String[] args) {
		BiThreadTree threadTree=new BiThreadTree();
		threadTree.makeTree();
		printThreadTree(threadTree.root, 0);
		threadTree.inOrderThreading();
		threadTree.inOrderTravelForward(new Visit());
		threadTree.inOrderTravelBackward(new Visit());
	}
}
