package graph;

import java.util.Arrays;

import binaryTree.Visit;
import list.List;
import list.SeqList;
import queue.LinQueue;
import queue.Queue;
import stack.SeqStack;
import stack.Stack;
/**
 * 邻接矩阵实现有向图
 * @author hjg
 *
 * @param <E>
 */
public class DiGraph<E> implements Graph<E> {
	private static final double MAX_WEIGHT=Double.MAX_VALUE;
	/**
	 * 存储节点的列表
	 */
	private List<E> vertice;
	/**
	 * 存储弧的矩阵
	 */
	private double[][] arcs;
	/**
	 * 弧的数量
	 */
	private int numberOfArcs;
	
	public DiGraph(List<E> vertice, Arc[] arcs){
		this.vertice=new SeqList<>(vertice.size());
		this.arcs=new double[vertice.size()][vertice.size()];
		for(int i=0;i<vertice.size();i++){
			for(int j=0;j<vertice.size();j++){
				this.arcs[i][j]=i==j?0:MAX_WEIGHT;
			}
		}
		init(vertice, arcs);
	}
	private void init(List<E> vertice, Arc[] arcs) {
		for(int i=0;i<vertice.size();i++){
			try {
				this.vertice.add(vertice.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(Arc arc:arcs){
			this.arcs[arc.tail][arc.head]=arc.weight;
			numberOfArcs++;
		}
	}
	@Override
	public int numberOfVertice() {
		return vertice.size();
	}

	@Override
	public int numberOfEdges() {
		return numberOfArcs;
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
	public double getWeight(int tail, int head) {
		return arcs[tail][head];
	}

	/**
	 * 节点v的第一个邻接节点下标
	 * @param v
	 * @return 不存在则返回-1
	 */
	private int firstNeighbour(int v) {
		return nextNeighbour(v, -1);
	}

	/**
	 * 节点v1在v2之后的下一个邻接节点下标
	 * @param v1
	 * @param v2 v2=-1时相当于得到第一个邻接节点
	 * @return 不存在返回-1
	 */
	private int nextNeighbour(int v1, int v2) {
		for(int j=v2+1;j<numberOfVertice();j++){
			if (arcs[v1][j]!=0&&arcs[v1][j]!=MAX_WEIGHT) {
				return j;
			}
		}
		return -1;
	}
	/**
	 * 返回v的邻接节点
	 * @param v
	 * @return 邻接节点的下标
	 */
	private List<Integer> getNeighbours(int v){
		List<Integer> res=new SeqList<>();
		for(int next=firstNeighbour(v);next!=-1;next=nextNeighbour(v, next)){
			res.add(next);
		}
		return res;
	}
	public void depthFirstSearch(Visit visit){
		boolean[] visited=new boolean[numberOfVertice()];
		for(int i=0;i<numberOfVertice();i++){
			dfs(visit, i, visited);
		}
	}
	/**
	 * 从节点v1开始深度优先搜索
	 * @param visit
	 * @param v1
	 * @param visited
	 */
	private void dfs(Visit visit,int v1,boolean[] visited){
		if(visited[v1]) return;
		visit.print(getVertex(v1));
		visited[v1]=true;
		int v2=firstNeighbour(v1);
		while(v2!=-1){
			dfs(visit, v2, visited);
			v2=nextNeighbour(v1, v2);
		}
	}
	public void breadthFirstSearch(Visit visit){
		boolean[] visited=new boolean[numberOfVertice()];
		for(int i=0;i<numberOfVertice();i++){
			if(!visited[i]) bfs(visit, i, visited);
		}
	}
	/**
	 * 从节点v1开始广度优先搜索
	 * @param visit
	 * @param v
	 * @param visited
	 */
	private void bfs(Visit visit, int v1,boolean[] visited){
		//queue存放已经访问过的节点下标
		Queue<Integer> queue=new LinQueue<>();
		visit.print(getVertex(v1));
		visited[v1]=true;
		queue.add(v1);
		while(!queue.isEmpty()){
			try {
				v1=queue.poll();
				for(int v2=firstNeighbour(v1);v2!=-1;v2=nextNeighbour(v1, v2)){
					if (!visited[v2]) {
						visit.print(getVertex(v2));
						visited[v2]=true;
						queue.add(v2);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public List<E> topologicalSort(){
		List<Integer> temp=topologicalSort(false, null);
		List<E> res=new SeqList<>(temp.size());
		try {
			for(int i=0;i<temp.size();i++){
				res.add(getVertex(temp.get(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 拓扑排序(1、选择入度为0的顶点；2、删除该顶点和以他为尾的弧)<p>
	 * 在入度减为0的时候计算最早完成时间
	 * @param isEarly 是否计算最早完成时间
	 * @param earliest 最早完成时间数组（初始值为0）
	 * @return 拓扑排序的下标列表,如果该列表的长度小于节点总数，则该有向图有环
	 */
	private List<Integer> topologicalSort(boolean isEarly,double[] earliest){
		int[] indegree=getIndegree();
		List<Integer> res=new SeqList<>(numberOfVertice());
		//存放入度为0的定点下标
		Stack<Integer> stack=new SeqStack<>();
		for(int i=0;i<indegree.length;i++){
			if (indegree[i]==0) {
				stack.push(i);
			}
		}
		try {
			while(!stack.isEmpty()){
				int v=stack.pop();
				res.add(v);
				List<Integer> neighbours=getNeighbours(v);
				for(int i=0;i<neighbours.size();i++){
					int next=neighbours.get(i);
					if (--indegree[next]==0) {
						if (isEarly) {
							earliest[next]=Math.max(earliest[next], earliest[v]+getWeight(v, next));
						}
						stack.push(next);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * 求每个顶点的入度
	 * @return
	 */
	private int[] getIndegree(){
		int[] res=new int[numberOfVertice()];
		for(int i=0;i<numberOfVertice();i++){
			for(int j=0;j<numberOfVertice();j++){
				if (arcs[i][j]!=0&&arcs[i][j]<MAX_WEIGHT) {
					res[j]++;
				}
			}
		}
		return res;
	}
	/**
	 * 寻找工程的关键路径（最长的路径，最早完成时间=最迟完成时间）。<p>
	 * AOV网：顶点表示事件，弧表示活动。<p>
	 * 工程的AOV网一般只有1个源点和一个汇点，用第一个和最后一个节点表示
	 * @return
	 */
	public List<E> criticalPath(){
		//事件的最早完成时间
		double[] earliest=new double[numberOfVertice()];
		//事件的topo顺序
		List<Integer> toOrder=topologicalSort(true, earliest);
		//事件的最迟完成时间
		double[] latest=new double[numberOfVertice()];
		//汇点一定在关键路径上，并且最迟完成时间最大
		Arrays.fill(latest, earliest[earliest.length-1]);
		
		try {
			//按照拓扑逆序计算最迟完成时间
			for(int i=toOrder.size()-1;i>=0;i--){
				int v=toOrder.get(i);
				List<Integer> neighbours=getNeighbours(v);
				for(int j=0;j<neighbours.size();j++){
					int next=neighbours.get(j);
					latest[v]=Math.min(latest[v], latest[next]-getWeight(v, next));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<E> res=new SeqList<>();
		for(int i=0;i<earliest.length;i++){
			if (earliest[i]==latest[i]) {
				res.add(getVertex(i));
			}
		}
		return res;
	} 
	
	/**
	 * dijkstra算法求有向连通图源点v0到各个顶点的最短路径<p>
	 * 每次寻找离V0最近的未找到最短路径的节点
	 * @return 最短路径信息：从v0开始到各个顶点的最短路径上的所有下标
	 */
	public List<List<E>> dijkstra(int v0){
		//各个节点的最短路径信息
		Section[] sections=new Section[numberOfVertice()];
		for(int i=0;i<sections.length;i++){
			if (i==v0) {
				sections[i]=new Section(true, -1, 0);
			}
			else {
				double distance=getWeight(v0, i);
				sections[i]=new Section(false, v0, distance);
			}
		}
		
		for(int i=0;i<sections.length-1;i++){
			//寻找未找到最短路径的节点中距离v0最进的节点
			int minVertex=-1;
			double minDistance=MAX_WEIGHT;
			for(int j=0;j<sections.length;j++){
				if (!sections[j].known&&sections[j].distance<minDistance) {
					minVertex=j;
					minDistance=sections[j].distance;
				}
			}
			
			//从v0出发非连通图
			if(minVertex==-1) continue;
			
			//将找到的节点加入最短路径并且更新未加入最短路径的节点section信息
			sections[minVertex].known=true;
			for(int j=0;j<sections.length;j++){
				if (!sections[j].known&&
						sections[minVertex].distance+getWeight(minVertex, j)<sections[j].distance) {
					sections[j].pre=minVertex;
					sections[j].distance=sections[minVertex].distance+getWeight(minVertex, j);
				}
			}
		}
		
		//转换形式
		List<List<E>> res=new SeqList<>(numberOfVertice());
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
	
	public List<List<E>> bellmanFord(int v0) {
		Section[] sections=new Section[numberOfVertice()];
		for (int i=0;i<sections.length;i++) {
			if (i==v0) {
				sections[i]=new Section(false, -1, 0);
			}else {
				sections[i]=new Section(false, v0, getWeight(v0, i));
			}
		}
		//每条边松弛操作
		for (int i=0;i<numberOfVertice();i++) {
			for (int tail=0;tail<numberOfVertice();tail++) {
				List<Integer> neighbours=getNeighbours(tail);
				for (int k=0;k<neighbours.size();k++) {
					int head=neighbours.get(k);
					if (sections[tail].distance!=MAX_WEIGHT) {//v0到tail有路
						//边tail->head
						double distance=getWeight(tail, head);
						if (sections[tail].distance+distance<sections[head].distance) {
							sections[head].distance=sections[tail].distance+distance;
							sections[head].pre=tail;
						}
					}
				}
			}
		}

		List<List<E>> res=new SeqList<>(numberOfVertice());
		
		//检查是否有负权回路
		for (int j=0;j<numberOfVertice();j++) {
			List<Integer> neighbours=getNeighbours(j);
			for (int k=0;k<neighbours.size();k++) {
				if (sections[j].distance!=MAX_WEIGHT) {
					if (sections[j].distance+getWeight(j, k)<sections[k].distance) {
						return res;
					}
				}
			}
		}
		transformSections(sections, res);
		
		return res;
	}
	/**
	 * floyd算法计算有向连通图所有节点之间最短路径（可包括负值的权重）
	 * @return i-j的最短路径下标
	 */
	public List<List<List<E>>> floyd(){
		Path[][] paths=new Path[numberOfVertice()][numberOfVertice()];
		for(int i=0;i<numberOfVertice();i++){
			for(int j=0;j<numberOfVertice();j++){
				paths[i][j]=new Path(j, getWeight(i, j));
			}
		}
		//寻找i->k->j的距离比当前i->paths[i][j].via->j的距离短的k
		for(int k=0;k<numberOfVertice();k++){
			for(int i=0;i<numberOfVertice();i++){
				for(int j=0;j<numberOfVertice();j++){
					if (paths[i][k].distance+paths[k][j].distance<paths[i][j].distance) {
						paths[i][j].via=k;
						paths[i][j].distance=paths[i][k].distance+paths[k][j].distance;
					}
				}
			}
		}
		List<List<List<E>>> res=new SeqList<>(numberOfVertice());
		for(int i=0;i<numberOfVertice();i++){
			List<List<E>> temp=new SeqList<>(numberOfVertice());
			for(int j=0;j<numberOfVertice();j++){
				List<E> path=new SeqList<>();
				if (i==j) {
					path.add(getVertex(i));
				}else {
					getPath(i, j, paths, path);
					if (path.size()==0&&getWeight(i, j)!=MAX_WEIGHT) {
						path.add(getVertex(i));
						path.add(getVertex(j));
					}else if (path.size()!=0) {
						path.add(0,getVertex(i));
						path.add(getVertex(j));
					}
				
				}
				temp.add(path);
			}
			res.add(temp);
		}
		return res;
	}
	/**
	 * 将i->之间的路径记录在path中（不包括i，j）
	 * @param i
	 * @param j
	 * @param paths
	 * @param path
	 */
	private void getPath(int i,int j,Path[][] paths,List<E> path){
		if (paths[i][j].via==j) {
			return;
		}
		getPath(i, paths[i][j].via, paths, path);
		path.add(getVertex(paths[i][j].via));
		getPath(paths[i][j].via, j, paths, path);
	} 
	public static class Arc{
		private int tail;
		private int head;
		private double weight;
		
		public Arc(int tail,int head,double weight) {
			this.tail=tail;
			this.head=head;
			this.weight=weight;
		}
		public int getTail() {
			return tail;
		}
		public int getHead() {
			return head;
		}
		public double getWeight() {
			return weight;
		}
		public void print(){
			System.out.println(tail+"->"+head+":"+weight);
		}
	}
	/**
	 * dijkstra,bellmanFord中储存路段信息
	 * @author hjg
	 *
	 */
	public static class Section{
		private boolean known;
		private int pre;
		private double distance;
		/**
		 * @param known 是否已知到该节点的最短路径
		 * @param pre 最短路径上的前置节点,-1表示为v0，没有前置节点
		 * @param distance v0到该节点的距离
		 */
		public Section(boolean known,int pre,double distance){
			this.known=known;
			this.pre=pre;
			this.distance=distance;
		}
	}
	
	/**
	 * floyd中储存路段信息
	 * @author hjg
	 *
	 */
	public static class Path{
		private int via;
		private double distance;
		/**
		 * @param via i->j需要经过的节点下标
		 * @param distance i->j的距离
		 */
		public Path(int via,double distance) {
			this.via=via;
			this.distance=distance;
		}
	}
	public static void main(String[] args) {
		double i=Double.MAX_VALUE;
		System.out.println(i);
		i+=Double.MAX_VALUE/10;
		System.out.println(i);
		System.out.println(i>Double.MAX_VALUE);
		System.out.println(i==Double.MAX_VALUE);
	}
}
