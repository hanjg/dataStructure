package search.dynamicTree;


import list.SeqList;

public class BMinusTree<E extends Comparable<? super E>>{
	/**
	 * 阶数
	 */
	private int m;
	/**
	 * 节点关键字个数最大值
	 */
	private final int maxSize;
	/**
	 * 节点关键字个数最小值
	 */
	private final int minSize;
	private Node<E> root;
	
	public BMinusTree(int m){
		this.m=m;
		maxSize=m-1;
		minSize=(int)Math.ceil((double)m/2)-1;
	}
	public boolean contain(E elem){
		return search(root, elem).contain;
	}
	private SearchResult search(Node<E> node, E elem){
		if (node==null) {
			return new SearchResult(false, null, 0);
		}
		try {
			int index = findIndex(node, elem);
			//结束时keys[index-1]<=elem<keys[index]或者index==keys.size
			if (index>0&&elem.compareTo(node.keys.get(index-1))==0) {//元素在node中存在
				return new SearchResult(true, node, index-1);
			}else {//元素在Node中不存在
				SearchResult result=search(node.children.get(index), elem);
				if (result.node==null) {//该孩子节点为Null
					return new SearchResult(false, node, index);
				}else {
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 找到elem在node中keys的下标或者即将插入的下标
	 * @param node
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	private int findIndex(Node<E> node, E elem){
		int index=0;
		try {
			while(index<node.keys.size()&&elem.compareTo(node.keys.get(index))>=0){
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return index;
	}
	public void insert(E elem){
		SearchResult result=search(root, elem);
		if (result.contain) {
			return;
		}else if (result.node==null) {
			root=new Node<E>(m, elem);
			return;
		}
		try {
			Node<E> node=result.node;
			node.addKey(result.index, elem);
			node.addChild(result.index, null);
			while (node.getSize()==maxSize+1) {
				//节点大小为m，超过上限，需要分裂节点
				//分裂节点成三部分：1、keys[mid]；
				//2、keys[0,mid),children[0,mid];
				//3、keys[mid+1,m),children[mid+1,m]
				int mid=node.getSize()/2;
				E midKey=node.getKey(mid);
				
				Node<E> split1=new Node<>(m);
				for(int i=0;i<mid;i++){
					split1.addKey(node.getKey(i));
					split1.addChild(node.getChild(i));
				}
				split1.addChild(node.getChild(mid));
				
				Node<E> split2=new Node<>(m);
				for(int i=mid+1;i<m;i++){
					split2.addKey(node.getKey(i));
					split2.addChild(node.getChild(i));
				}
				split2.addChild(node.getChild(m));
				
				Node<E> parent=node.parent;
				if (parent==null) {//根节点分裂
					parent=new Node<>(m);
					parent.addChild(null);//parent.setchild需要
					root=parent;
				}
				int index=findIndex(parent, midKey);
				parent.addKey(index, midKey);
				parent.setChild(index, split2);
				parent.addChild(index, split1);
				node=parent;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void print(){
		System.out.println("[");
		print(root, 1);
		System.out.println("]");
	}
	private void print(Node<E> node, int level){
		if (node==null) {
			return;
		}
		try {
			print(node.getChild(node.getSize()), level+1);
			for(int i=node.getSize()-1;i>=0;i--){
				for(int j=1;j<level;j++){
					System.out.print("\t");
				}	
				System.out.println("----"+node.getKey(i));
				print(node.getChild(i), level+1);
			}
			for(int j=1;j<level;j++){
				System.out.print("\t");
			}
			System.out.println("====");//节点开始的标志
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * b-树节点<p>
	 * child[0]<key[0]<=child[1]<...<=child[n]<key[n]<=child[n+1]
	 * @author hjg
	 *
	 * @param <E>
	 */
	public static class Node<E>{
		/**
		 * 最多储存m-1个关键字,最后一个节点插入分裂时使用
		 */
		private SeqList<E> keys;
		/**
		 * 最多m个子树，最后一个节点插入分裂时使用
		 */
		private SeqList<Node<E>> children;
		private Node<E> parent;
		
		/**
		 * @param m 阶数
		 */
		public Node(int m) {
			keys=new SeqList<>(m);
			children=new SeqList<>(m+1);
		}
		public Node(int m, E elem){
			this(m);
			try {
				addKey(elem);
				addChild(null);
				addChild(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public void addKey(int index, E elem) throws Exception{
			keys.add(index,elem);
		}
		public void addKey(E elem) throws Exception{
			addKey(keys.size(), elem);
		}
		public E getKey(int index) throws Exception{
			return keys.get(index);
		}
		public Node<E> getChild(int index) throws Exception{
			return children.get(index);
		}
		public void addChild(int index, Node<E> child) throws Exception{
			children.add(index, child);
			if (child!=null) {
				child.parent=this;
			}
		}
		public void addChild(Node<E> child) throws Exception{
			addChild(children.size(), child);
		}
		public void setChild(int index, Node<E> child) throws Exception{
			children.set(index, child);
			if (child!=null) {
				child.parent=this;
			}
		}
		public int getSize(){
			return keys.size();
		}
	}
	/**
	 * 记录查找结果<p>
	 * 包含：在node节点keys[index]<p>
	 * 不包含:在node节点keys[index]处插入
	 * @author hjg
	 *
	 */
	public class SearchResult{
		private boolean contain;
		private Node<E> node;
		private int index;
		
		public SearchResult(boolean contain, Node<E> node, int index){
			this.contain=contain;
			this.node=node;
			this.index=index;
		}
	}
}
