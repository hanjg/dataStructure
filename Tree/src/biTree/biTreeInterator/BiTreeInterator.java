package biTree.biTreeInterator;

import biTree.BiTreeNode;

public class BiTreeInterator {//二叉树游标类,分步遍历二叉树
	BiTreeNode root;
	BiTreeNode current;
	int iteComplete;//到达尾部的标记,遍历结束返回1，遍历未结束返回0
	
	public BiTreeInterator(){
		
	}
	
	public BiTreeInterator(BiTreeNode root){
		current=this.root=root;
		iteComplete=1;
	}
	
	public void reset(){
		
	}
	
	public void next(){
		
	}
	
	public boolean endOfBiTree(){
		return iteComplete==1;
	}
	
	public Object getData(){
		if (current==null) {
			return null;
		}
		else {
			return current.getData();
		}
	}

}
