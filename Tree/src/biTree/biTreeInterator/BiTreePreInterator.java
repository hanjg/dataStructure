package biTree.biTreeInterator;

import biTree.BiTreeNode;
import biTree.LinStack;

public class BiTreePreInterator extends BiTreeInterator {//二叉树前序游标类

	private LinStack stack;

	public BiTreePreInterator(BiTreeNode root) {
		super(root);
		stack=new LinStack();
	}


	public void reset(){
		if (root==null) {
			iteComplete=1;
		}
		else {
			iteComplete=0;
			current=root;
			//System.out.println("current="+(Character)current.getData());
		}
	}

	public void next(){
		if (iteComplete==1) {
			System.out.println("end");
			return;
		}
		
		if (current.getright()!=null) {
			try {
				stack.push(current.getright());
				//System.out.print((Character)current.getData()+".right!=null ");
				//System.out.println((Character)current.getright().getData()+" push stack");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (current.getLeft()!=null) {
			//System.out.print((Character)current.getData()+".left!=null ");
			//System.out.println("current="+(Character)current.getLeft().getData());
			current=current.getLeft();
		}
		else if (stack.notEmpty()) {
			try {
				current=(BiTreeNode)(stack.pop());
				//System.out.println("current=pop="+(Character)current.getData());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			//System.out.println("all done");
			iteComplete=1;
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
		BiTreePreInterator interator=new BiTreePreInterator(root);
		for(interator.reset();!interator.endOfBiTree();interator.next()){
			System.out.print(interator.getData());
		}
	}

}
