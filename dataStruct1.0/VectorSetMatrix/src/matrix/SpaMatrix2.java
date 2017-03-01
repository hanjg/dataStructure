package matrix;


public class SpaMatrix2 {//用十字链表实现稀疏矩阵
	private OLNode[] rowHead;
	private OLNode[] colHead;//行链表头指针数组，第i个元素代表第i行，所以总长度为rows+1
	private int rows,cols,dNum;
	
	public SpaMatrix2(){
		
	}
	
	public void createMatrix(int r,int c,int d,Three[] item) throws Exception{
		rows=r;
		cols=c;
		dNum=d;
		rowHead=new OLNode[rows+1];
		colHead=new OLNode[cols+1];
		
		for(int k=0;k<dNum;k++){
			OLNode node=new OLNode(item[k].row, item[k].col, item[k].value);
			if (rowHead[node.i]==null) {
				rowHead[node.i]=node;
			}
			else {
				OLNode current=rowHead[node.i];//在current之后一个位置插入node
				while(current.right!=null&&node.j>current.right.j){
					current=current.right;
				}
				node.right=current.right;
				current.right=node;
			}
			if (colHead[node.j]==null) {
				colHead[node.j]=node;
			}
			else {
				OLNode current=colHead[node.j];
				while(current.down!=null&&node.i>current.down.i){
					current=current.down;
				}
				node.down=current.down;
				current.down=node;
			}
		}
	}
	
	public void add(SpaMatrix2 matrixB){//将matrixB加到矩阵A上
		OLNode rowA;
		OLNode rowB;//指向A和B某行的非零节点
		OLNode rowPre;//指向rowA的前驱节点
		OLNode[] colPre=new OLNode[cols+1];//之上的列节点都已处理好，初值为列链表的头指针,辅助列链表的插入
		for(int j=1;j<=cols;j++){
				colPre[j]=colHead[j];//colPre[j]当A的第j列没有元素是为null
		}
		OLNode temp=null;
		for(int row=1;row<=this.rows;row++){//依次处理[1,rows]行
			rowA=rowHead[row];
			rowB=matrixB.rowHead[row];
			rowPre=null;
			while(rowB!=null){//依次处理本行结点直到B中无非零节点
				if (rowA==null||rowA.j>rowB.j) {//新矩阵的元素由B中的元素得到：
					//								A这一行元素已处理完或者A中指向的元素j值大于B指向的元素
					//System.out.println(rowB.i+" "+rowB.j+" "+rowB.data);
					temp=new OLNode(rowB.i, rowB.j, rowB.data);//将B中节点的镜像temp插入A
					if (rowPre==null) {//rowA==null或者rowA==rowHead[row]
						rowHead[row]=temp;
						temp.right=rowA;
						rowPre=rowA;
					}
					else {
						rowPre.right=temp;
						temp.right=rowA;
						rowPre=rowA;
					}//行链表插入完毕
					//System.out.print(temp+":");
					//System.out.println(temp.i+" "+temp.j+" "+temp.data);
					if (colHead[temp.j]==null||colHead[temp.j].i>temp.i) {
						temp.down=colHead[temp.j];
						colHead[temp.j]=temp;
					}else {
						temp.down=colPre[temp.j].down;
						colPre[temp.j].down=temp;
						colPre[temp.j]=temp;
					}//列链表插入完毕
					rowB=rowB.right;
				}
				else if (rowA!=null&&rowA.j<rowB.j) {//新矩阵的元素由A中元素得到
					rowPre=rowA;
					rowA=rowA.right;
				}
				else if (rowA.j==rowB.j) {//新矩阵的元素由A中元素+B中的元素得到
					rowA.data=(double)rowA.data+(double)rowB.data;
					if ((double)rowA.data==0){
						if (rowPre==null) {
							rowHead[row]=rowA.right;
							temp=rowA;//为了列链表的删除，temp指向将要删除的节点
							rowA=rowA.right;
						}
						else {
							rowPre.right=rowA.right;
							temp=rowA;
							rowA=rowA.right;
						}
						//从行链表中删除完毕
						if (colHead[temp.j]==temp) {
							colHead[temp.j]=temp.down;
							colPre[temp.j]=temp.down;
						}
						else {
							colPre[temp.j].down=temp.down;
						}
						//从列链表中删除完毕
						rowB=rowB.right;
					}
				}
			}
			//System.out.println(row+" success");
		}
		
	}
	public void add2(SpaMatrix2 matrixB){//将matrixB加到矩阵A上,与add类似，只是colPre的初值不同
		OLNode rowA;
		OLNode rowB;//指向A和B某行的非零节点
		OLNode rowPre;//指向rowA的前驱节点
		OLNode[] colPre=new OLNode[cols+1];//之上的列节点都已处理好，初值为列链表的头指针,辅助列链表的插入
		for(int j=1;j<=cols;j++){
				colPre[j]=colHead[j];//colPre[j]当A的第j列没有元素是为null
		}
		OLNode temp=null;
		for(int row=1;row<=this.rows;row++){//依次处理[1,rows]行
			rowA=rowHead[row];
			rowB=matrixB.rowHead[row];
			rowPre=rowHead[row];
			while(rowB!=null){//依次处理本行结点直到B中无非零节点
				if (rowA==null||rowA.j>rowB.j) {//新矩阵的元素由B中的元素得到：
					//								A这一行元素已处理完或者A中指向的元素j值大于B指向的元素
					//System.out.println(rowB.i+" "+rowB.j+" "+rowB.data);
					temp=new OLNode(rowB.i, rowB.j, rowB.data);//，temp指向将要插入的节点，即B中节点的镜像
					if (rowHead[row]==null||rowHead[row].j>temp.j) {//需要在行的头结点处插入节点
						temp.right=rowHead[row];
						rowHead[row]=temp;
						rowPre=rowHead[row];
					}
					else {
						temp.right=rowA;
						rowPre.right=temp;
						rowPre=temp;
					}//行链表插入完毕
					//System.out.print(temp+":");
					//System.out.println(temp.i+" "+temp.j+" "+temp.data);
					if (colHead[temp.j]==null||colHead[temp.j].i>temp.i) {
						temp.down=colHead[temp.j];
						colHead[temp.j]=temp;
						colPre[temp.j]=temp;
					}else {
						temp.down=colPre[temp.j].down;
						colPre[temp.j].down=temp;
						colPre[temp.j]=temp;
					}//列链表插入完毕
					
					rowB=rowB.right;
				}
				else if (rowA!=null&&rowA.j<rowB.j) {//新矩阵的元素由A中元素得到
					rowPre=rowA;
					
					rowA=rowA.right;
				}
				else if (rowA.j==rowB.j) {//新矩阵的元素由A中元素+B中的元素得到
					rowA.data=(double)rowA.data+(double)rowB.data;
					if ((double)rowA.data==0){
						temp=rowA;//为了列链表的删除，temp指向将要删除的节点
						
						if (rowHead[row]==temp) {//在头结点处删除节点
							rowHead[row]=temp.right;
							rowPre=rowHead[row];
						}
						else {
							rowPre.right=rowA.right;
							rowPre=rowPre.right;
						}
						//从行链表中删除完毕
						if (colHead[temp.j]==temp) {
							colHead[temp.j]=temp.down;
							colPre[temp.j]=colHead[temp.j];
						}
						else {
							colPre[temp.j].down=temp.down;
							colPre[temp.j]=colPre[temp.j].down;
						}
						//从列链表中删除完毕
					}
					rowA=rowA.right;
					rowB=rowB.right;
				}
			}
			//System.out.println(row+" success");
		}
		
	}

	public void print(){
		System.out.println("rows="+rows+" cols="+cols+" dNum="+dNum);
		for(int i=1;i<rows;i++){
			OLNode row=rowHead[i];
			//System.out.println(row);
			if (row!=null) {
				for(OLNode pNode=row;pNode!=null;pNode=pNode.right){
					System.out.print("<"+pNode.i+","+pNode.j+","+pNode.data+"> ");
				}
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Three[] a=new Three[4];
		a[0]=new Three(1, 1, 3);
		a[1]=new Three(1, 4, 5);
		a[2]=new Three(2, 2, -1);
		a[3]=new Three(3, 1, 2);
		Three[] b=new Three[4];
		b[0]=new Three(1, 2, 2);
		b[1]=new Three(2, 1, 1);
		b[2]=new Three(3, 1,-2);
		b[3]=new Three(3, 2, 4);
		
		SpaMatrix2 matrix=new SpaMatrix2();
		SpaMatrix2 matrix2=new SpaMatrix2();
		SpaMatrix2 matrix3=new SpaMatrix2();
		try {
			matrix.createMatrix(4, 4, a.length, a);matrix.print();
			matrix2.createMatrix(4, 4, b.length, b);matrix2.print();
			matrix3.createMatrix(4, 4, a.length, a);matrix3.print();
			matrix.add2(matrix2);matrix.print();
			matrix3.add2(matrix2);matrix3.print();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class OLNode{
	int i,j;//行下标和列下标
	Object data;
	OLNode right,down;
	
	public OLNode(int i,int j,Object data){
		this.i=i;
		this.j=j;
		this.data=data;
	}
}
