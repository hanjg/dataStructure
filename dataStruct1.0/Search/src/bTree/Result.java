package bTree;

public class Result {//存储B-树的查找返回结果
	BDecTreeNode node;//指向找到的节点
	int i;
	int tag;//tag==1查找成功，tag==0查找失败
	
	public Result(BDecTreeNode node,int i,int tag){
		this.node=node;
		this.i=i;
		this.tag=tag;
	}
}
