package biTree.biTreeInterator;

import biTree.BiTreeNode;
import biTree.LinStack;

public class BiTreeInInterator extends BiTreeInterator {//二叉树中序游标类
	private LinStack stack;
	
	public BiTreeInInterator(BiTreeNode root) {
		super(root);
		stack=new LinStack();
	}
	
	private BiTreeNode goFarLeft(BiTreeNode treeNode){//寻找最左的孩子节点
		if (treeNode==null) {
			return null;
		}
		while(treeNode.getLeft()!=null){
			try {
				stack.push(treeNode);
				//System.out.println("stack push="+(Character)treeNode.getData());
			} catch (Exception e) {
				e.printStackTrace();
			}
			treeNode=treeNode.getLeft();
		}
		//System.out.println("current's left==null "+"current="+(Character)treeNode.getData());
		return treeNode;
	}
	
	public void reset(){
		if (root==null) {
			iteComplete=1;
		}
		else {
			iteComplete=0;
			current=goFarLeft(root);
		}
	}
	
	public void next(){
		if (iteComplete==1) {
			//System.out.println("end");
			return;
		}
		
		if (current.getright()!=null) {
			//System.out.println("current.right!=null travel "+(Character)current.getData()+"'s right--"+ (Character)current.getright().getData());
			current=goFarLeft(current.getright());
		}
		else if (stack.notEmpty()) {
			try {
				current=(BiTreeNode)stack.pop();
				//System.out.println("current=stack pop="+(Character)current.getData());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			iteComplete=1;
			//System.out.println("all done");
		}
		
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

	public static void main(String[] args) {
		BiTreeNode root=makeTree();
		BiTreeNode.printBiTree(root, 0);
		BiTreeInInterator inInterator=new BiTreeInInterator(root);
		for(inInterator.reset();!inInterator.endOfBiTree();inInterator.next()){
			System.out.print(inInterator.getData());
		}
	}

}
