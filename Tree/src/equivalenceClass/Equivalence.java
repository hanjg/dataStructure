package equivalenceClass;

class Node{
	Object elem;
	int parent;
	
	public Node(Object object,int p){
		this.elem=object;
		this.parent=p;
	}
}

public class Equivalence {//双亲表示法,仿真链表
	Node[] nodes;
	
	public Equivalence(Object[] elems){
		nodes=new Node[elems.length];
		for(int i=0;i<elems.length;i++){
			Node temp=new Node(elems[i], -1);
			nodes[i]=temp;
		}
	}
	
	public int findRoot(int i){//找到下标为i的节点的根节点的下标
		if (i<0||i>=nodes.length) {
			throw new IndexOutOfBoundsException();
		}
		while(nodes[i].parent>0){//nodes[i].parent=-1表示nodes[i]是根节点
			i=nodes[i].parent;
		}
		return i;
	}
	
	public int findRootAndFixSet(int i){//找到i的根节点下标，并将从i至根节点路径上的所有节点都变成根的孩子
		if (i<0||i>=nodes.length) {
			throw new IndexOutOfBoundsException();
		}
		int j=i;
		while(nodes[j].parent>0){//nodes[i].parent=-1表示nodes[i]是根节点
			j=nodes[j].parent;
		}
		//此时，j为根节点
		int temp;
		for(int k=i;k!=j;k=temp){
			temp=nodes[k].parent;
			nodes[k].parent=j;
		}
		return j;
	}
	
	public void mergeSet(int i,int j){//合并两个集合,ij为根节点的下标
		if (i<0||i>=nodes.length||j<0||j>=nodes.length) {
			throw new IndexOutOfBoundsException();
		}
		nodes[i].parent=j;
	}
	
	public void fixSet(int i,int j){//合并两个集合,ij为根节点的下标:令成员少的集合的根节点指向成员多的集合的根节点
									//根节点的parent中存储所含成员数的负值
		if (i<0||i>=nodes.length||j<0||j>=nodes.length) {
			throw new IndexOutOfBoundsException();
		}
		if (nodes[i].parent<nodes[j].parent) {//j为根节点的集合成员数少于i的集合成员数,将j的双亲设为i
			nodes[i].parent+=nodes[j].parent;
			nodes[j].parent=i;//注意先后顺序
		}
		else {
			nodes[j].parent+=nodes[i].parent;
			nodes[i].parent=j;
		}
	}
	
	public void printRoot(){
		for(int i=0;i<nodes.length;i++){
			System.out.print(i+":"+findRoot(i)+'\t');
			//System.out.print(i+":"+nodes[i].parent+'\t');
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		Integer[] elems={1,2,3,4,5,6,7,8,9,10,11};
		Equivalence equivalence=new Equivalence(elems);
		int[][] relations={{0,1},{2,3},{4,5},{6,7},{0,2},{4,6},{0,4}};
		for(int i=0;i<relations.length;i++){
			int m=relations[i][0];
			int n=relations[i][1];
			int i1=equivalence.findRootAndFixSet(m);
			int j1=equivalence.findRootAndFixSet(n);
			equivalence.fixSet(i1, j1);
			equivalence.printRoot();
		}
	}

}
