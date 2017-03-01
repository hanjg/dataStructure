package bTree;

 public class BDecTree {
	 public BDecTreeNode root;
	 public int m;//阶数
	 //注意child的插入和parent的改变
	 public BDecTree(int m){
		 this.m=m;
		 root=null;
	 }
	 public static int searchElem(BDecTreeNode node,Object elem){
		//搜索元素elem，返回i，key[i-1]<elem<=key[i],m阶B树
		//i=0表示elem<=key[0];i=m-1表示key[m-2]<elem，
		//即elem应处在key[i]处或者key[i-1]和key[i]之间插入
		
		//child[0]<key[0]<child[1]<....<key[m-2]<child[m-1]
		if (node==null) {
			return 0;
		}
		int i=0;
		while((i<node.keyWords.getSize())&&((Integer)elem>(Integer)node.keyWords.getData(i))){
			i++;
		}
		return i;
	}
	public Result searchTree(Object item){
		//node为开始查找的item
		 BDecTreeNode node=root;
		 BDecTreeNode pre=null;
		 int i=0;
		 while(node!=null){
			 i=searchElem(node,item);
			 if (node.keyWords.getData(i)!=null&&
					 item.equals(node.keyWords.getData(i))) {
				return new Result(node, i, 1); //查找成功，i在node的key[i]处
			}
			 else {
				 pre=node;
				node=(BDecTreeNode) node.children.getData(i);
			}
		 }
		 return new Result(pre, i, 0);//查找不成功，i应该在pre的key[i-1]和key[i]之间插入
	 }
	 
	 public void insert(Object item,BDecTreeNode node,int i) throws Exception{
		 //在node的key[i-1]和key[i]之间插入item,node和i均来自Result
		 Object x=item;//将要插入的元素，或者分裂节点是向上插入的元素
		 BDecTreeNode xChild=null;//跟随x的孩子节点
		 BDecTreeNode temp=null;//分割后的第二段节点
		 while(node!=null){
	//System.out.println(root.numOfKey+" child:"+root.children.getSize());

			node.keyWords.insert(i, x);
			node.numOfKey++;	
			
			if (xChild==null) {
				node.children.insert(i, xChild);
			}
			else {
				if ((BDecTreeNode)node.children.getData(i)!=xChild) {//防止xChild的重复插入
					node.children.insert(i, xChild);
				}
				xChild.parent=node;
			}
			
			
			if (temp!=null) {
				node.children.insert(i+1, temp);
				temp.parent=node;
			}
			 
			//printBDecTree(root, 0);	
			//System.out.println("-------------------");
			if (node.numOfKey<m) {
				return;
			}
			else {
	//分裂节点，第一个节点keynum1个关键词keywords[0,keynum1-1],下标为children[0,keynum1]
	//第二个节点keynum2个关键词keywords[keynum1+1,m-1],下标为children[keynum1+1,m]
	//单独取出的关键词在原节点的下标为keynum1
				int keynum1=(int) Math.ceil((double)m/2)-1;
				//int keynum2=m-1-keynum1;
				temp=new BDecTreeNode(m);
				//System.out.println("keynum1="+keynum1);
				for(int index=keynum1+1;index<=m-1;index++){
					temp.keyWords.insert(node.keyWords.delete(keynum1+1));
					temp.numOfKey++;
					node.numOfKey--;
				}
				for(int index=keynum1+1;index<=m;index++){
					temp.children.insert(node.children.delete(keynum1+1));
				}
				for(int k=0;k<temp.children.getSize();k++){//设置temp的孩子节点为temp,而不是今后的xChild
					if (temp.children.getData(k)!=null) {
						((BDecTreeNode)temp.children.getData(k)).parent=temp;
					}
				}
				x=node.keyWords.delete(keynum1);
				node.numOfKey--;
				xChild=node;
				node=node.parent;
				i=searchElem(node,x);
			}
		 }
		 if (node==null) {//生成新的根节点
				BDecTreeNode newRoot=new BDecTreeNode(m);
				newRoot.keyWords.insert(x);
				newRoot.numOfKey++;
				newRoot.children.insert(xChild);
				newRoot.children.insert(temp);
				if (xChild!=null) {
					xChild.parent=newRoot;
				}
				if (temp!=null) {
					temp.parent=newRoot;
				}
				root=newRoot;
			 }
	 }
	 
	 public static void printBDecTree(BDecTreeNode root,int level){
		if (root==null) {
			return;
		}
		for(int i=root.numOfKey;i>0;i--){
			printBDecTree((BDecTreeNode) root.children.getData(i), level+1);
			if (level!=0) {
				for(int j=0;j<4*level;j++){
					System.out.print(" ");
				}
				System.out.print("---");
			}
			System.out.println(root.keyWords.getData(i-1));
			/*System.out.print(root.keyWords.getData(i-1)+":p=");
			if (root.parent!=null) {
				System.out.print(root.parent.keyWords.getSize()+":");
				for(int k=0;k<root.parent.keyWords.getSize();k++){
					System.out.print(root.parent.keyWords.getData(k)+" ");
				}
			}
			System.out.println("c="+root.children.getSize());*/
			
		}
		printBDecTree((BDecTreeNode) root.children.getData(0), level+1);
	}
	public static void main(String[] arg){
		 int[] a=new int[100];
		 for(int i=0;i<a.length;i++){
			 a[i]=(int)(Math.random()*1000);
		 }
		 BDecTree tree=new BDecTree(3);
		 for(int i=0;i<a.length;i++){
			 Result result=tree.searchTree(a[i]);
			 if (result.tag==0) {
				try {
					
					//System.out.println(i+":"+a[i]);
					tree.insert(a[i], result.node, result.i);
				
					//printBDecTree(tree.root, 0);	
					//System.out.println("-------------------");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		 }
		 printBDecTree(tree.root, 0);
	 }
}
