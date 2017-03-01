package biTree.biTreeInterator;

import biTree.BiTreeNode;
import biTree.LinQueue;

public class BiTreeLevelInterator extends BiTreeInterator{//二叉树层序游标类
	private LinQueue queue;
	
	public BiTreeLevelInterator(BiTreeNode root) {
		super(root);
		queue=new LinQueue();//按照即将访问的顺序放入元素
	}
	
	public void reset(){
		if (root==null) {
			iteComplete=1;
		}
		else {
			iteComplete=0;
			current=root;
			try {
				if (current.getLeft()!=null) {
					queue.append(current.getLeft());
				}
				if (current.getright()!=null) {
					queue.append(current.getright());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void next(){
		if (iteComplete==1) {
			System.out.println("end");
			return;
		}
		if (queue.notEmpty()) {
			try {
				current=(BiTreeNode) queue.delete();
				if (current.getLeft()!=null) {
					queue.append(current.getLeft());
				}
				if (current.getright()!=null) {
					queue.append(current.getright());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
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
		BiTreeLevelInterator interator=new BiTreeLevelInterator(root);
		for(interator.reset();!interator.endOfBiTree();interator.next()){
			System.out.print(interator.current.getData());
		}
	}

}
