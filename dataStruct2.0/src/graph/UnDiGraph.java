package graph;

import java.util.Comparator;
import java.util.PriorityQueue;

import binaryTree.Visit;
import list.List;
import list.SeqList;
import queue.LinQueue;
import queue.Queue;

/**
 * 邻接表实现无向图
 * @author hjg
 *
 */
public class UnDiGraph<E> implements Graph<E>{
	private List<E> vertice;
	private List<List<EdgeData>> edges;
	private static final double MAX_WEIGHT=Double.MAX_VALUE;
	/**
	 * 弧的数量
	 */
	private int numberOfEdges;
	
	public UnDiGraph(List<E> vertice,Edge[] edges) {
		this.vertice=new SeqList<>(vertice.size());
		this.edges=new SeqList<>(vertice.size());
		for(int i=0;i<vertice.size();i++){
			this.edges.add(new SeqList<>());
		}
		init(vertice, edges);
	}
	private void init(List<E> vertice, Edge[] edges) {
		for(int i=0;i<vertice.size();i++){
			try {
				this.vertice.add(vertice.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(Edge edge:edges){
			insertEdgeData(edge.v1, new EdgeData(edge.v2, edge.weight));
			insertEdgeData(edge.v2, new EdgeData(edge.v1, edge.weight));
			numberOfEdges++;
		}
	}
	private void insertEdgeData(int v1,EdgeData edgeData){
		try {
			int i;
			List<EdgeData> adjoin=edges.get(v1);
			for(i=0;i<adjoin.size();i++){
				if (edgeData.v2<adjoin.get(i).v2) {
					adjoin.add(i, edgeData);
					return;
				}
			}
			adjoin.add(edgeData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int numberOfVertice() {
		return vertice.size();
	}

	@Override
	public int numberOfEdges() {
		
		return numberOfEdges;
	}

	@Override
	public E getVertex(int index) {
		try {
			return vertice.get(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public double getWeight(int v1, int v2) {
		try {
			if(v1==v2) return 0;
			List<EdgeData> adjoin=edges.get(v1);
			for(int i=0;i<adjoin.size();i++){
				if (v2==adjoin.get(i).v2) {
					return adjoin.get(i).weight;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MAX_WEIGHT;
	}
	
	/**
	 * @param v
	 * @return 节点v相连的边，包括节点下标和权重
	 */
	private List<EdgeData> getNeighbours(int v){
		return edges.get(v);
	}
	
	public void depthFirstSearch(Visit visit){
		boolean[] visited=new boolean[numberOfVertice()];
		for(int i=0;i<numberOfVertice();i++){
			dfs(visit, i, visited);
		}
	}
	private void dfs(Visit visit, int v,boolean[] visited){
		if(visited[v]) return;
		visit.print(getVertex(v));
		visited[v]=true;
		List<EdgeData> list=getNeighbours(v);
		for(int i=0;i<list.size();i++){
			try {
				dfs(visit, list.get(i).v2, visited);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void breadthFirstSearch(Visit visit){
		boolean[] visited=new boolean[numberOfVertice()];
		for(int i=0;i<numberOfVertice();i++){
			if(!visited[i]) bfs(visit, i, visited);
		}
	}
	private void bfs(Visit visit,int v,boolean[] visited){
		//存储已经访问过的节点下标
		Queue<Integer> queue=new LinQueue<>();
		visit.print(getVertex(v));
		visited[v]=true;
		queue.add(v);
		while(!queue.isEmpty()){
			try {
				v=queue.poll();
				List<EdgeData> adjoin=getNeighbours(v);
				for(int i=0;i<adjoin.size();i++){
					int next=adjoin.get(i).v2;
					if(!visited[next]){
						visit.print(getVertex(next));
						visited[next]=true;
						queue.add(next);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 堆优化最短路径
	 * @param v0
	 * @return
	 */
	public List<List<E>> dijstra(int v0) {
		Section[] sections = new Section[numberOfVertice()];
		for (int i = 0; i < numberOfVertice(); i++) {
			if (i == v0) {
				sections[i] = new Section(i, true, -1, 0);
			} else {
				sections[i] = new Section(i, false, v0, getWeight(v0, i));
			}
		}
		
		//存储v0到该节点路径的信息
		PriorityQueue<Section> heap = new PriorityQueue<>(numberOfVertice(), new Comparator<Section>() {

			@Override
			public int compare(Section o1, Section o2) {
				return o1.distance-o2.distance < 0 ? -1 : 1;
			}
		});
		for (int i = 0; i < numberOfVertice(); i++) {
			if (i != v0) {
				heap.add(sections[i]);
			}
		}
		
		while (!heap.isEmpty()) {
			Section cur = heap.poll();
			cur.known = true;
			int start = cur.id;
			
			List<EdgeData> neighbours = getNeighbours(start);
			for (int i = 0; i < neighbours.size(); i++) {
				EdgeData neighbour = neighbours.get(i);
				int next = neighbour.v2;
				if (!sections[next].known && 
						cur.distance + neighbour.weight < sections[next].distance) {
					heap.remove(sections[next]);
					sections[next].distance = cur.distance + neighbour.weight;
					sections[next].pre = start;
					heap.add(sections[next]);
				}
			}
		}
		
		List<List<E>> res = new SeqList<>(numberOfVertice());
		transformSections(sections, res);
		return res;
	}
	/**
	 * 将最短路径信息转化为最短路径上点的列表
	 * @param sections
	 * @param res
	 */
	private void transformSections(Section[] sections, List<List<E>> res) {
		for(int i=0;i<numberOfVertice();i++){
			List<E> temp=new SeqList<>();
			if (sections[i].distance!=MAX_WEIGHT) {
				//v0到cur有路径
				for(int cur=i;cur!=-1;cur=sections[cur].pre){
					temp.add(getVertex(cur));
				}
			}
			//将v0为终点的路径反向输出为v0为起点的路径
			List<E> path=new SeqList<>();
			for(int j=temp.size()-1;j>=0;j--){
				path.add(temp.get(j));
			}
			res.add(path);
		}
	}
	/**
	 * prim算法构造无向连通图的最小生成树，从下标0的节点开始构造。每次寻找离生成树最近的节点加入生成树。
	 * @return 最小生成树的边
	 */
	public Edge[] prim(){
		Edge[] minTree=new Edge[numberOfVertice()-1];
		//closeEdges[i]:未加入生成树的节点i和已加入生成树的节点最近的一条边,null表示已加入生成树
		EdgeData[] closestEdges=new EdgeData[numberOfVertice()];
		for(int i=1;i<closestEdges.length;i++){
			closestEdges[i]=new EdgeData(0, getWeight(0, i));
		}
		for(int i=0;i<minTree.length;i++){
			//寻找距离生成树最近的节点
			int minVertex=-1;
			double minWeight=MAX_WEIGHT;
			for(int j=1;j<closestEdges.length;j++){
				if(closestEdges[j]==null) continue;
				if (closestEdges[j].weight<minWeight) {
					minVertex=j;
					minWeight=closestEdges[minVertex].weight;
				}
			}
			
//			//非连通图
//			if(minWeight==MAX_WEIGHT) break;
			
			//加入生成树
			minTree[i]=new Edge(closestEdges[minVertex].v2, minVertex, closestEdges[minVertex].weight);
			closestEdges[minVertex]=null;
			//更新最近信息
			for(int j=0;j<closestEdges.length;j++){
				if(closestEdges[j]==null) continue;
				if (closestEdges[j].weight>getWeight(minVertex, j)) {
					closestEdges[j]=new EdgeData(minVertex, getWeight(minVertex, j));
				}
			}
		}
		return minTree;
	}
	
	/**
	 * kruskal算法构造无向连通图的最小生成树，从下标0开始构造。
	 * 每次选择连接不通连通分量的最短边加入生成树
	 * @return 最小生成树的边
	 */
	public Edge[] kruskal(){
		Edge[] minTree=new Edge[numberOfVertice()-1];
		//所有边存储在最小堆中
		PriorityQueue<Edge> edges=new PriorityQueue<>(numberOfVertice(), new Comparator<Edge>() {
			@Override
			public int compare(Edge o1, Edge o2) {
				return o1.weight-o2.weight>0?1:-1;
			}
		});
		try {
			for(int i=0;i<this.edges.size();i++){
				List<EdgeData> neighbours=this.edges.get(i);
				for(int j=0;j<neighbours.size();j++){
					if (neighbours.get(j).v2<i) {
						edges.add(new Edge(i, neighbours.get(j).v2, neighbours.get(j).weight));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//同一个连通分量中的下一个节点，next[i]=-1表示是最后一个节点
		int[] next=new int[numberOfVertice()];
		for(int i=0;i<next.length;i++) next[i]=-1;
		
		//选择连接两个连通分量的最短边并且将他们连通
		for(int i=0;i<minTree.length;i++){
			Edge min=edges.poll();
			int end1=getEnd(next, min.v1);
			int end2=getEnd(next, min.v2);
			if (end1==end2) {
				i--;
			}
			else {
				minTree[i]=min;
				next[end1]=end2;
			}
		}
		return minTree;
	}
	/**
	 * i节点所在连通分量的最后一个节点
	 * @param next
	 * @param i
	 * @return
	 */
	private int getEnd(int[] next,int i){
		while(next[i]!=-1) i=next[i];
		return i;
	}
	
	/**
	 * 寻找无向连通图的关节点
	 * @return 关节点的下标
	 */
	public List<E> findArticulation(){
		//前序遍历的序号
		int[] num=new int[numberOfVertice()];
		//w为v在深度优先生成树上的孩子节点
		//low[v]:深度优先生成树上v的子孙通过最多一条回边（包括指向双亲的树边）所能到达的最小的节点编号
		//是一下三项的最小值:
		//num[v]，low[w]，num[w：是v回边连接的先祖节点]
		//可由后序遍历求得
		int[] low=new int[numberOfVertice()];
		num[0]=count=1;
		res=new SeqList<>();
		
		try {
			//根节点的邻接节点
			List<EdgeData> neighbours=getNeighbours(0);
			int next=neighbours.get(0).v2;
			articulation(num, low, next);
			//如果根节点有至少两棵子树，则根为关节点
			if (count<numberOfVertice()) {
				res.add(getVertex(0));
			}
			for(int i=1;i<neighbours.size();i++){
				next=neighbours.get(i).v2;
				if(num[next]==0) articulation(num, low, next);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	private int count;
	private List<E> res;
	/**
	 * 从v开始深度优先遍历
	 * @param num 前序遍历求得
	 * @param low 后序遍历求得
	 * @param v
	 */
	private void articulation(int[] num,int[] low,int v){
		try {
			num[v]=++count;
			int min=num[v];
			List<EdgeData> neighbours=getNeighbours(v);
			for(int i=0;i<neighbours.size();i++){
				int w=neighbours.get(i).v2;
				if(num[w]==0){
					articulation(num, low, w);
					min=Math.min(min, low[w]);
					//w及其子孙无指向v先祖的回边，即v是关节点
					if (low[w]>=num[v]) {
						res.add(getVertex(w));
					}
				}
				//w已访问且是v在生成树上的祖先
				else if (num[w]<min) {
					min=num[w];
				}
			}
			low[v]=min;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 邻接表中存储边的信息
	 * @author hjg
	 *
	 */
	private static class EdgeData{
		private int v2;
		private double weight;
		
		private EdgeData(int next,double weight){
			this.v2=next;
			this.weight=weight;
		}
		@SuppressWarnings("unused")
		public void print(){
			System.out.println(v2+":"+weight);
		}
	}
	public static class Edge{
		private int v1;
		private int v2;
		private double weight;
		
		public Edge(int v1,int v2,double weight){
			this.v1=v1;
			this.v2=v2;
			this.weight=weight;
		}
		public int getV1() {
			return v1;
		}
		public int getV2() {
			return v2;
		}
		public double getWeight() {
			return weight;
		}
		public void print(){
			System.out.println(v1+"-"+v2+":"+weight);
		}
	}
	/**
	 * 堆优化dijkstra中储存路段信息
	 * @author hjg
	 *
	 */
	public static class Section{
		private int id;
		private boolean known;
		private int pre;
		private double distance;
		/**
		 * @param known 是否已知到该节点的最短路径
		 * @param pre 最短路径上的前置节点,-1表示为v0，没有前置节点
		 * @param distance v0到该节点的距离
		 */
		public Section(int id,boolean known,int pre,double distance){
			this.id=id;
			this.known=known;
			this.pre=pre;
			this.distance=distance;
		}
	}

}
