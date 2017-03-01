package search.staticTree;

import binaryTree.BinaryTree;
import list.List;

/**
 * 次优查找树（静态查找）
 * @author hjg
 *
 * @param <E>
 */
public class NearlyOptimalSearchTree<E extends Comparable<? super E>> extends BinaryTree<E>{
	/**
	 * 记录节点[0,i)的权值和,即节点i之前所有节点的权值和<p>
	 * 节点i的权值为sum[i+1]-sum[i]
	 */
	private double[] sum;
	private List<E> vertice;
	
	public NearlyOptimalSearchTree(List<E> vertice,double[] weight) {
		super();
		this.vertice=vertice;
		sum=new double[weight.length+1];
		for(int i=1;i<sum.length;i++){
			sum[i]=sum[i-1]+weight[i-1];
		}
		try {
			root=init(0, vertice.size()-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sum=null;
		vertice=null;
	}
	/**
	 * 在节点[left,right]之间构造次优查找树
	 * @param left
	 * @param right
	 * @return
	 * @throws Exception 
	 */
	private Node<E> init(int left,int right) throws Exception{
		//将节点分为三部分[left,mid),mid,(mid,right]使得1、3两部分权值和差距最小
		if (left>=right) {
			return new Node<E>(vertice.get(left), null, null);
		}
		int mid=0;
		double min=Double.MAX_VALUE;
		for(int i=left;i<=right;i++){
			double temp=Math.abs((sum[i]-sum[left])-(sum[right+1]-sum[i+1]));
			if (temp<min) {
				mid=i;
				min=temp;
			}
		}
		Node<E> leftChild=init(left, mid-1);
		Node<E> rightChild=init(mid+1, right);
		return new Node<E>(vertice.get(mid), leftChild, rightChild);
	}
	/**
	 * 查找成功返回该节点，查找失败返回查找路径上的最后一个节点
	 * @param elem
	 * @return
	 */
	public boolean contain(E elem){
		return search(root,elem);
	}
	/**
	 * 以node为根节点查找elem
	 * @param node
	 * @param elem
	 * @return 是否查找成功
	 */
	private boolean search(Node<E> node,E elem){
		if (node==null) {
			return false;
		}
		int temp=elem.compareTo(node.getVal());
		if (temp==0) {
			return true;
		}else if (temp<0) {
			return search(node.getLeft(), elem);
		}else {
			return search(node.getRight(), elem);
		}
	}
	
}
