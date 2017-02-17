package biSortTree;

public class BalenceBiSortTree {
	public static final int LHigh=1;//左子树高
	public static final int EHigh=0;//左右子树等高
	public static final int RHigh=-1;//右子树高
	public BBSTreeNode root;
	
	private boolean taller;
	
	private BBSTreeNode rightRotate(BBSTreeNode node){//对以node为根的二叉排序树做右旋处理
		//处理之后node指向新的树根节点
		BBSTreeNode temp=node.getParent();//存储node的双亲节点
		BBSTreeNode nr=node.getLeftChild();//lr指向node的左子树的根节点,即新的根节点
		node.setLeftChild(nr.getRightChild());
		if (nr.getRightChild()!=null) {
			nr.getRightChild().setParent(node);
		}
		nr.setRightChild(node);
		node.setParent(nr);
		if (node==root) {//调整此次旋转的根节点,使其不与之上的节点断开
			root=nr;
		}
		else {
			nr.setParent(temp);
			if (temp.getLeftChild()==node) {
				nr.getParent().setLeftChild(nr);
			}
			else {
				nr.getParent().setRightChild(nr);
			}
		}
		return nr;
	}
	
	private BBSTreeNode leftRoate(BBSTreeNode node){
		BBSTreeNode temp=node.getParent();//存储node的双亲节点
		BBSTreeNode nr=node.getRightChild();
		node.setRightChild(nr.getLeftChild());
		if (nr.getLeftChild()!=null) {
			nr.getLeftChild().setParent(node);
		}
		nr.setLeftChild(node);
		node.setParent(nr);
		if (node==root) {//调整此次旋转的根节点,使其不与之上的节点断开
			root=nr;
		}
		else {
			nr.setParent(temp);
			if (temp.getLeftChild()==node) {
				nr.getParent().setLeftChild(nr);
			}
			else {
				nr.getParent().setRightChild(nr);
			}
		}
		return nr;
	}
	
	private void leftBalence(BBSTreeNode node){//node原来左子树比右子树高,
		//对以node为根节点的二叉树作左平衡处理，
		//结束时node指向新的根节点
		BBSTreeNode lc=node.getLeftChild();//lc指向node的左子树的根节点
		switch (lc.bf) {
		case LHigh://新插入节点在左孩子的左子树上
			node.bf=lc.bf=EHigh;
			node=rightRotate(node);
			break;
		case RHigh://新插入的节点在左孩子的右子树上
			BBSTreeNode rd=lc.getRightChild();//指向node的左孩子的右子树的根
			switch (rd.bf) {//修改node及其左孩子lc的平衡因子
			case LHigh:
				node.bf=RHigh;
				lc.bf=EHigh;
				break;
			case EHigh:
				node.bf=lc.bf=EHigh;
				break;
			case RHigh:
				node.bf=EHigh;
				lc.bf=LHigh;
				break;
			}
			rd.bf=EHigh;
			node.setLeftChild(leftRoate(node.getLeftChild()));
			node=rightRotate(node);
		}
	}
	
	private void rightBalence(BBSTreeNode node){
		//node原来右子树比左子树高
		//对以node为根节点的二叉树作右平衡处理，
		//结束时node指向新的根节点
		BBSTreeNode rc=node.getRightChild();
		switch (rc.bf) {
		case RHigh://新插入节点在右孩子的右子树上
			node.bf=rc.bf=EHigh;
			node=leftRoate(node);
			break;
		case LHigh://新插入的节点在右孩子的左子树上
			BBSTreeNode ld=rc.getLeftChild();//指向node的右孩子的左子树根节点
			switch (ld.bf) {//修改node及其右孩子rc的平衡因子
			case LHigh:
				node.bf=EHigh;
				rc.bf=RHigh;
				break;
			case EHigh://插入是其自身
				node.bf=rc.bf=EHigh;
				break;
			case RHigh:
				node.bf=LHigh;
				rc.bf=EHigh;
				break;
			}
			ld.bf=EHigh;
			node.setRightChild(rightRotate(node.getRightChild()));;
			node=leftRoate(node);
		}	
	}
	
	public int insert(BBSTreeNode node,int item){
		//若在平衡二叉树node中不存在elem，则插入一个元素节点，返回1，否则返回0
		//若因插入使得二叉排序树失去平衡，则做平衡旋转处理
		//taller反应树node是否长高
		//System.out.println("insert root "+node.getData()+":");
			if (node.getData()==item) {
				taller=false;
				return 0;
			}
			if (item<node.getData()) {//在左子树中尝试插入
				if (node.getLeftChild()==null) {
					node.setLeftChild(new BBSTreeNode(item, null, null));
					node.getLeftChild().setParent(node);
					node.getLeftChild().bf=EHigh;
					taller=true;
				}
				else if (insert(node.getLeftChild(), item)==0) {//左子树中存在item，所以未插入
					return 0;
				}
				if (taller) {//左子树插入成功并且左子树长高
					switch (node.bf) {
					case LHigh://原来左子树比右子树高，须作平衡处理
						leftBalence(node);taller=false;
						break;
					case EHigh://原来等高，现因左子树增高而使得树增高
						node.bf=LHigh;taller=true;
						break;
					case RHigh:
						node.bf=EHigh;taller=false;
						break;
					}
				}
			}
			else {
			
				if (node.getRightChild()==null) {
					node.setRightChild(new BBSTreeNode(item, null, null));
					node.getRightChild().setParent(node);
					node.getRightChild().bf=EHigh;
					taller=true;
				}
				else if (insert(node.getRightChild(), item)==0) {
					return 0;
				}
				if (taller) {
					switch (node.bf) {
					case LHigh:
						node.bf=EHigh;taller=false;
						break;
					case EHigh:
						node.bf=RHigh;taller=true;
						break;
					case RHigh:
						rightBalence(node);taller=false;
						break;
					}
				}
			}
		//System.out.println("insert root "+node.getData()+" end");
		return 1;
	}
	public static void printBiTree(BBSTreeNode root,int level){
		if (root==null) {
			return;
		}
		printBiTree(root.getRightChild(), level+1);
		if (level!=0) {
			for(int i=0;i<4*level;i++){
				System.out.print(" ");
			}
			System.out.print("---");
		}
		//System.out.println(root.getData()+":"+root.bf);
		System.out.println(root.getData());
		printBiTree(root.getLeftChild(),level+1);
	}

	public static void main(String[] args) {
		int[] a={4,5,7,2,1,9,8,11,3,44,65,77,88,99,565,45,677,77,999,543,345,234};
		BBSTreeNode root=new BBSTreeNode(a[0]);
		BalenceBiSortTree tree=new BalenceBiSortTree();
		tree.root=root;
		for(int i=1;i<a.length;i++){
			tree.insert(tree.root, a[i]);//从根节点插入数据
		}
		printBiTree(tree.root, 0);
	}

}
