package bTree;

public class BDecTreeNode {//B-树的节点
	int numOfKey;
	SeqList keyWords;//节点中的关键词,共m个存储位置，空余一个
	BDecTreeNode parent;
	SeqList	children;//节点孩子节点指针
	
	public BDecTreeNode(int m){
		numOfKey=0;
		parent=null;
		keyWords=new SeqList(m);//空余一个插入时使用,有效长度m-1
		children=new SeqList(m+1);//空余一个插入时使用，有效长度m
	}
}
