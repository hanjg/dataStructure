package adjMatrixGraph;

/**
 * 用邻接矩阵结构存储图
 * @author hjg
 *
 */
public class AdjMGraph {
	private static final int maxWeight=100000;//设置为整型最大值可能溢出(floyd算法时)
	
	private SeqList vertices;//存储节点
	private int[][] Arc;//存储边信息的二维数组
	private int numOfArc;
	private int count;//用于寻找关节点的计数
	
	public AdjMGraph(int maxV){//maxV为节点个数
		vertices=new SeqList(maxV);
		Arc=new int[maxV][maxV];
		for(int i=0;i<maxV;i++){
			for(int j=0;j<maxV;j++){
				if (i==j) {
					Arc[i][j]=0;
				}
				else {
					Arc[i][j]=maxWeight;
				}
			}
		}
		numOfArc=0;
	}
	
	public static void createGraph(AdjMGraph graph,Object[] vertices,int NumOfVertices,
			Arc[] Arcs,int NumOfArc){
		for(int i=0;i<NumOfVertices;i++){
			graph.insertVertex(vertices[i]);
		}
		for(int j=0;j<NumOfArc;j++){
			graph.insertArc(Arcs[j].row, Arcs[j].col, Arcs[j].weight);
		}
	}

	public int getNumOfVertices(){
		return vertices.getSize();
	}
	
	public int getNumOfArc(){
		return numOfArc;
	}
	
	public Object getValue(int v){//返回下标为v的节点的值
		return vertices.getData(v);
	}
	
	public int getWeight(int v1,int v2){//返回<v1,v2>的权重
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		return Arc[v1][v2];
	}
	
	public void insertVertex(Object vertex){
		vertices.insert(vertex);
	}
	
	public void insertArc(int v1,int v2,int weight){
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		Arc[v1][v2]=weight;
		numOfArc++;
	}
	
	public void deleteArc(int v1,int v2) throws Exception{
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		if (Arc[v1][v2]==maxWeight||v1==v2) {//v1==v2等价于Arc[v1][v2]==0
			throw new Exception("no Arc");
		}
		Arc[v1][v2]=maxWeight;
		numOfArc--;
	}
	
	/**
	 * 找节点v的第一个邻接节点，存在返回其下标，不存在返回-1
	 * @param v
	 * @return
	 */
	public int getFirstNeighbor(int v){
		if (v<0||v>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v error");
		}
		for(int col=0;col<vertices.getSize();col++){
			if (Arc[v][col]>0&&Arc[v][col]<maxWeight) {
				return col;
			}
		}
		return -1;
	}
	
	/**
	 * 找节点v1的邻接节点v2后的邻接节点，存在返回下标，否则-1
	 * @param v1
	 * @param v2
	 * @return
	 */
	public int getNextNeighbor(int v1,int v2){
		if (v1<0||v1>=vertices.getSize()||v2<0||v2>=vertices.getSize()) {
			throw new IndexOutOfBoundsException("v1,v2 error");
		}
		for(int col=v2+1;col<vertices.getSize();col++){
			if (Arc[v1][col]>0&&Arc[v1][col]<maxWeight) {
				return col;
			}
		}
		return -1;
	}
	
	public void depthFirstSearch(Visit vs){//深度优先搜寻
		boolean[] visited=new boolean[getNumOfVertices()];
		for(int i=0;i<getNumOfVertices();i++){
			if (!visited[i]) {
				depthFirst(i, visited, vs);
			}
		}
		System.out.println();
	}
	
	public void broadFirstSearch(Visit vs){//广度优先搜索
		boolean[] visited=new boolean[getNumOfVertices()];//表示节点是否已经访问
		for(int i=0;i<getNumOfVertices();i++){
			visited[i]=false;
		}
		for(int i=0;i<getNumOfVertices();i++){
			if (!visited[i]) {
				broadFirst(i, visited, vs);
			}
		}
		System.out.println();
	}
	
	/**
	 * @param v 从节点v开始深度优先搜索
	 * @param visited 节点是否已被访问
	 * @param visit
	 */
	private void depthFirst(int v,boolean[] visited,Visit visit){
		visit.print(getValue(v));
		visited[v]=true;
		int w=getFirstNeighbor(v);
		while(w!=-1){
			if (!visited[w]) {
				depthFirst(w, visited, visit);
			}
			w=getNextNeighbor(v, w);
		}
	}

	/**
	 * @param v 从节点v开始广度优先搜索
	 * @param visited 节点是否已被访问
	 * @param visit
	 */
	private void broadFirst(int v,boolean[] visited,Visit visit){
		SeqQueue queue=new SeqQueue();
		visit.print(getValue(v));
		visited[v]=true;
		try {
			queue.append(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//u为出队列的元素,w为u的邻接节点
		int u,w;
		try {
			while(queue.notEmpty()){
				u=((Integer)(queue.delete()));
//				u=((Integer)(queue.delete())).intValue();
				w=getFirstNeighbor(u);
				while(w!=-1){
					if (!visited[w]) {
						visit.print(getValue(w));
						visited[w]=true;
						queue.append(w);
					}
					w=getNextNeighbor(u,w);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
		prim算法建立无向带权图graph的最小生成树vertices(从节点入手)
		集合U表示已经加入最小生成树的节点;
		集合V表示所有节点
		集合V-U表示未加入最小生成树的节点
	 * @param graph
	 * @param vertices 最小生成树的节点数组
	 */
	public static void prim(AdjMGraph graph,MinTreeVertex[] vertices){
		int numOfVertices=graph.getNumOfVertices();
		
		//初始化:U为加入最小生成树的节点，初始仅含有下标为0的节点
		//lowCost[i]表示U中所有节点到V-U中节点i相连的边的最小距离
		//lowCost[i]==-1表示i已加入U
		int[] lowCost=new int[numOfVertices];
		lowCost[0]=-1;
		for(int i=1;i<numOfVertices;i++){
			lowCost[i]=graph.getWeight(0, i);
		}
		vertices[0]=new MinTreeVertex(graph.getValue(0));
		
		//U中所有节点到U-V中的节点k距离最小，为minCost
		int minCost;
		int k=0;
		for(int i=1;i<numOfVertices;i++){
			minCost=maxWeight;
			for(int j=1;j<numOfVertices;j++){
				if (lowCost[j]>0&&lowCost[j]<minCost) {
					minCost=lowCost[j];
					k=j;
				}
			}
			
			if(minCost==maxWeight) return;//非连通图
			
			vertices[i]=new MinTreeVertex(graph.getValue(k), minCost, k);
			lowCost[k]=-1;
			
			//k加入U之后根据k维护lowCost
			for(int j=1;j<numOfVertices;j++){
				if (graph.getWeight(k, j)<lowCost[j]) {//weight>=0
					lowCost[j]=graph.getWeight(k, j);
				}
			}
		}
	}

	/**
	 * kruskal算法建立无向带权图的最小生成树 ArcOfMinTree(从边入手)
	 * @param graph
	 * @param ArcOfMinTree 最小生成树的弧
	 */
	public static void kruskal(AdjMGraph graph,Arc[] ArcOfMinTree){
		//存储graph中的边，因为graph无向图，只需一半边
		Arc[] Arcs=new Arc[graph.getNumOfArc()/2];
		int index=0;
		for(int i=0;i<graph.getNumOfVertices();i++){
			for(int j=0;j<i;j++){
				if (graph.getWeight(i, j)<maxWeight) {
					Arcs[index++]=new Arc(i, j, graph.getWeight(i, j));
				}
			}
		}
		
		//按权重排序边
		boolean done=false;//排序是否完成
		for(int i=1;i<index&&!done;i++){//排倒数第i个数
			done=true;
			for(int j=0;j<Arcs.length-i;j++){
				if (Arcs[j].weight>Arcs[j+1].weight) {
					done=false;
					Arc temp=Arcs[j];Arcs[j]=Arcs[j+1];Arcs[j+1]=temp;
				}
			}
		}
		
		//连通分量中节点i的下一个节点为link[i]，link[i]=0表示节点i为连通分量的最后一个节点
		int[] link=new int[graph.getNumOfVertices()];
		
		index=0;
		for(int i=0;i<Arcs.length&&index<ArcOfMinTree.length;i++){
			int end1=graph.getEnd(link, Arcs[i].row);
			int end2=graph.getEnd(link, Arcs[i].col);
			//判断Arcs[i]的两条边是否在同一个连通分量
			//若边的两个节点不属于一个连通分量，则将边加入生成树并且把两个连通分量连接
			if (end1!=end2) {
				ArcOfMinTree[index++]=new Arc(Arcs[i].row,Arcs[i].col, Arcs[i].weight);
				link[end2]=end1;
			}
		}
	}
	
	/**
	 * 返回节点i所处连通分量的最后一个节点
	 * @param link
	 * @param i
	 * @return
	 */
	private int getEnd(int[] link,int i){
		while(link[i]!=0){
			i=link[i];
		}
		return i;
	}

	/**
	 * 寻找图的关节点
	 */
	public void findArticulation(){
		int[] num=new int[getNumOfVertices()];
		int[] low=new int[getNumOfVertices()];
		num[0]=count=1;
		int w=getFirstNeighbor(0);
		//从节点0的第一个邻接节点开始查找关节点
		dfsArticulation(num, low, w);
		//深度优先搜索生成树的根至少有两棵子树，则根为关节点
		if (count<getNumOfVertices()) {
			System.out.println("articul:"+(Character)getValue(0));
			w=getNextNeighbor(0, w);
			while(w!=-1){
				if (num[w]==0) {
					dfsArticulation(num, low, w);
				}
				w=getNextNeighbor(0, w);
			}
		}
//		for(int i=0;i<getNumOfVertices();i++){
//			System.out.print((Character)vertices.getData(i)+" ");
//		}
//		System.out.println("above is data");
//		for(int i=0;i<getNumOfVertices();i++){
//			System.out.print(visited[i]+" ");
//		}
//		System.out.println("above is visited");
//		for(int i=0;i<getNumOfVertices();i++){
//			System.out.print(low[i]+" ");
//		}
//		System.out.println("above is low");
	}
	/**
	 * 从v0节点出发深度优先遍历连通无向图，查找并输出关节点
	 * @param num num[v0]:先序遍历时v0是的编号;
	 * @param low
	 * low[v0]=min{num[v0](不选边)	
	 * ,low[v0在深度优先生成树上的孩子节点](选取树的边和可能还有一条回边)	
	 * ,num[v0在深度优先生成树上回边连接的的先祖节点](选取一条回边)};	
	 * 即表示在深度优先搜索生成树上v0可以到达（通过n条边或者外加一条回边）的最低编号
	 * @param v0
	 */
	private void dfsArticulation(int[] num,int[] low,int v0){
		//System.out.println("visiting "+v0+":"+(Character)getValue(v0));
		int min=num[v0]=++count;
		int w=getFirstNeighbor(v0);//w为v0的邻接节点
		while(w!=-1){
			if (num[w]==0) {//w未访问
				dfsArticulation(num, low, w);
				if (low[w]<min) {
					min=low[w];
				}
				//若w及其子孙均无指向v0的先祖节点的回边,则v0是关节点
				if (low[w]>=num[v0]) {
					System.out.println("articul:"+(Character)getValue(v0));
				}
			}
			else if (num[w]<min) {//w已访问，是v0在生成树上的祖先
				min=num[w];
			}
			w=getNextNeighbor(v0, w);
		}
		low[v0]=min;
	}

	/**
	 * 输出关键活动路径估算工程完成时间;
	 * 在工程中一般只有一个0入度源点和0出度汇点
	 * @throws Exception
	 */
	public void criticalPath() throws Exception{
		SeqStack topoOrder=new SeqStack(getNumOfVertices());
		int[] earliest=new int[getNumOfVertices()];//事件顶点最早发生时间
		int[] latest=new int[getNumOfVertices()];//事件顶点最迟发生时间
		
		if (topologicalOrder(topoOrder, earliest)) return;
		
		for(int i=0;i<getNumOfVertices();i++){
			latest[i]=earliest[getNumOfVertices()-1];
		}
		
//		按照逆拓扑序求latest,根据<u,w>
		while(topoOrder.notEmpty()){
			int u=(Integer)topoOrder.pop();
			int w=getFirstNeighbor(u);
			while(w!=-1){
				if (latest[w]-getWeight(u, w)<latest[u]) {
					latest[u]=latest[w]-getWeight(u, w);
				}
				w=getNextNeighbor(u, w);
			}
		}
		
		int eventEarliest,eventLatest;//活动的最早和最迟时间
		System.out.println("s"+"\t"+"e"+"\t"+"dur"+"\t"+"ee"+"\t"+"el"+"\t");
		for(int i=0;i<getNumOfVertices();i++){
			int w=getFirstNeighbor(i);
			while(w!=-1){
				eventEarliest=earliest[i];
				eventLatest=latest[w]-getWeight(i, w);
				char tag=(eventEarliest==eventLatest)?'*':' ';
				System.out.println(i+"\t"+w+"\t"+getWeight(i, w)+"\t"+eventEarliest+"\t"
						+eventLatest+"\t"+tag);
				w=getNextNeighbor(i, w);
			}
		}
	}
	
	/**
	 * 若图无回路，输出拓扑序列和事件最早发生时间，返回false；若有回路，则返回true
	 * @param topoOrder 拓扑有序的顶点
	 * @param earlist 事件顶点最早发生时间
	 * @return
	 * @throws Exception
	 */
	private boolean topologicalOrder(SeqStack topoOrder,int[] earlist) throws Exception{
		//在工程中一般只有一个0入度源点和0出度汇点,即i为源点
		int[] inDegree=findInDegree();
		SeqStack in0=new SeqStack(getNumOfVertices());//存放0入度的顶点
		for(int i=0;i<inDegree.length;i++){
			if (inDegree[i]==0) {
				in0.push(i);
			}
		}
		int count=0;//对输出的顶点计数
		while(in0.notEmpty()){
			int u=((Integer)in0.pop()).intValue();
			topoOrder.push(u);//顶点i入拓扑有序栈
			count++;
			int w=getFirstNeighbor(u);//w为u的邻接节点,
			while(w!=-1){
				inDegree[w]--;
				if (inDegree[w]==0) {//i的每个邻接节点入度-1，若入度减为0，则入栈
					in0.push(w);
					if (earlist[u]+getWeight(u, w)>earlist[w]) {
						earlist[w]=earlist[u]+getWeight(u, w);
					}
				}
				w=getNextNeighbor(u, w);
			}
		}
		return count<getNumOfVertices();
	}

	/**
	 * 求每个顶点的入度
	 * @return
	 */
	private int[] findInDegree(){
		int[] inDegree=new int[getNumOfVertices()];
		for(int i=0;i<getNumOfVertices();i++){
			int count=0;
			for(int j=0;j<getNumOfVertices();j++){
				if (Arc[j][i]>0&&Arc[j][i]<maxWeight) {
					count++;
				}
			}
			inDegree[i]=count;
		}
		return inDegree;
	}

	/**
	 * dijkastra算法解决单源最短路径问题
	 * @param graph
	 * @param v0 源点下标
	 * @param distance 当前已知的从结点v0到结点i的最短距离
	 * @param pre 从v0到i节点的前一节点下标，-1表示无前一节点
	 */
	public static void dijkastra(AdjMGraph graph,int v0,int[] distance,int[] pre){
		int numOfVertices=graph.getNumOfVertices();
		//known表示i节点的最短路径是否已确定
		boolean[] known=new boolean[numOfVertices];
		
		//初始化(v0已找到最短路径):初始化distance,pre,known
		known[v0]=true;
		for(int i=0;i<numOfVertices;i++){
			distance[i]=graph.getWeight(v0, i);
			if (i!=v0&&graph.getWeight(v0, i)<maxWeight) {//v0到i有直接路径
				pre[i]=v0;
			}
			else {
				pre[i]=-1;
			}
		}
		
		int minDistance;
		int u=0;
		for(int i=numOfVertices-1;i>0;i--){
			//最短路径未确定的节点中选择distance最小的节点u
			minDistance=maxWeight;
			for(int j=0;j<numOfVertices;j++){
				if (!known[j]&&distance[j]<minDistance) {
					u=j;minDistance=distance[j];
				}
			}
			
			//对非连通图
			if (minDistance==maxWeight) return;
			
			//确定节点u并维护distance,pre
			known[u]=true;
			for(int j=0;j<numOfVertices;j++){
				if (!known[j]&&graph.getWeight(u, j)<maxWeight&&
						graph.getWeight(u, j)+distance[u]<distance[j]) {//v0通过u到达j更近
					distance[j]=graph.getWeight(u, j)+distance[u];
					pre[j]=u;
				}
			}
		}
	}

	/**
	 * 根据dijkastra算法输出从i到j的最短路径
	 * @param i	起点
	 * @param j	终点
	 * @param pre 
	 */
	public void printPath_d(int i,int j,int[] pre){
		while(j!=i){
			System.out.print((Character)getValue(j)+"<-");
			j=pre[j];
		}
		System.out.println((Character)getValue(j));
	}

	/**
	 * floyd算法计算最短路径(可包括负值边)
	 * 计算每一对节点之间的最短路径,可以调用dijkastra，也可以用floyd，时间复杂度都是n^3
	 * @param graph
	 * @param path 各对节点之间的最短路径
	 				path[i][j]=k:k是当前求得i->j最短路径上的顶点
	 * @param distance 带权长度
	 */
	public static void floyd(AdjMGraph graph,int[][] path,int[][] distance){
		for(int i=0;i<graph.getNumOfVertices();i++){//各对节点之间初始已知路径及距离
			for(int j=0;j<graph.getNumOfVertices();j++){
				distance[i][j]=graph.Arc[i][j];
				if (distance[i][j]<maxWeight) {//i->j存在路径
					path[i][j]=j;
				}
			}
		}
		for(int k=0;k<graph.getNumOfVertices();k++)
			for(int i=0;i<graph.getNumOfVertices();i++)
				for(int j=0;j<graph.getNumOfVertices();j++){
					if (distance[i][k]+distance[k][j]<distance[i][j]) {
						//i->k->j路径更短，此时i->k->j必然存在路径
						distance[i][j]=distance[i][k]+distance[k][j];
						path[i][j]=k;//路径点设置为经过下标为k的顶点
					}
				}
	}
	public void printPaht_F(int i,int j,int[][] path){//根据floyd算法输出i到j的最短路径
		if (path[i][j]==j) return;
		printPaht_F(i, path[i][j], path);
		System.out.print(getValue(path[i][j])+"->");
		printPaht_F(path[i][j], j, path);
	}

	public static void main(String[] args) {
//		Character[] characters={'A','B','C','D','E'};
//		Arc[] Arcs={new Arc(0, 1, 10),
//				new Arc(0, 4, 20),
//				new Arc(1, 3, 30),
//				new Arc(2, 1, 40),
//				new Arc(3, 2, 50)};
//		AdjMGraph graph=new AdjMGraph(characters.length);
//		AdjMGraph.createGraph(graph, characters, characters.length, Arcs, Arcs.length);
//		
//		graph.depthFirstSearch(new Visit());
//		graph.broadFirstSearch(new Visit());
//		graph.findArticulation();
		
	
//		Character[] characters={'A','B','C','D','E','F','G'};
//		Arc[] Arcs={new Arc(0, 1, 50),new Arc(1, 0, 50),
//				new Arc(0, 2, 60),new Arc(2, 0, 60),
//				new Arc(1, 3, 65),new Arc(3, 1, 65),
//				new Arc(1, 4, 40),new Arc(4, 1, 40),
//				new Arc(2, 3, 52),new Arc(3, 2, 52),
//				new Arc(2, 6, 45),new Arc(6, 2, 45),
//				new Arc(3, 4, 50),new Arc(4, 3, 50),
//				new Arc(3, 5, 30),new Arc(5, 3, 30),
//				new Arc(3, 6, 42),new Arc(6, 3, 42),
//				new Arc(4, 5, 70),new Arc(5, 4, 70)};
//		AdjMGraph graph=new AdjMGraph(characters.length);
//		createGraph(graph, characters, characters.length, Arcs, Arcs.length);
//		
//		MinTreeVertex[] minTreeVertexs=new MinTreeVertex[characters.length];
//		AdjMGraph.prim(graph, minTreeVertexs);
//		for(int i=0;i<characters.length;i++){
//			System.out.println(minTreeVertexs[i].vertexData+" "+minTreeVertexs[i].weight);
//		}
//		
//		Arc[] ArcOfMinTree=new Arc[graph.getNumOfVertices()-1];
//		AdjMGraph.kruskal(graph, ArcOfMinTree);
//		for(int i=0;i<ArcOfMinTree.length;i++){
//			System.out.println("row="+ArcOfMinTree[i].row+" col="+ArcOfMinTree[i].col+
//					" weight="+ArcOfMinTree[i].weight);
//		}
//		
//		graph.findArticulation();
		
		Character[] characters={'A','B','C','D','E','F'};
		Arc[] Arcs={new Arc(0, 2, 5),new Arc(0, 3, 30),
				new Arc(1, 0, 2),new Arc(1, 4, 8),
				new Arc(2, 1, 15),new Arc(2, 5, 7),
				new Arc(4, 3, 4),new Arc(5, 3, 10),
				new Arc(5, 4, 18)};
		AdjMGraph graph=new AdjMGraph(characters.length);
		createGraph(graph, characters, characters.length, Arcs, Arcs.length);
		
		int[] distance=new int[characters.length];
		int[] pre=new int[characters.length];
		int v0=1;//初始顶点为v0
		AdjMGraph.dijkastra(graph, v0, distance, pre);
		
		for(int i=0;i<characters.length;i++){
			System.out.print(graph.getValue(v0)+"->"+graph.getValue(i)+
					": minDistance is "+distance[i]+"\t");
			graph.printPath_d(v0, i, pre);
		}
		
		int[][] distance2=new int[characters.length][characters.length];
		int[][] path2=new int[characters.length][characters.length];
		floyd(graph, path2, distance2);
		
		System.out.print("minDis"+"\t");
		for(int i=0;i<characters.length;i++){
			System.out.print(characters[i]+"\t");
		}
		System.out.println();
		for(int i=0;i<characters.length;i++){
			System.out.print(graph.getValue(i)+"\t");
			for(int j=0;j<characters.length;j++){
				System.out.print(distance2[i][j]+"\t");
			}
			System.out.println();
		}
		
		for(int j=0;j<characters.length;j++){
			System.out.print(graph.getValue(v0)+"->"+graph.getValue(j)+":"+"\t");
			System.out.print(graph.getValue(v0)+"->");
			graph.printPaht_F(v0, j, path2);
			System.out.println(graph.getValue(j));
		}
		
		
		/*Character[] characters={'A','B','C','D','E','F'};
		Arc[] Arcs={new Arc(0, 1, 1),new Arc(0, 2, 1),
				new Arc(0, 3, 1),new Arc(2, 1, 1),
				new Arc(2, 4, 1),new Arc(3, 4, 1),
				new Arc(5, 3, 1),new Arc(5, 4, 1)
				};
		AdjMGraph graph=new AdjMGraph(characters.length);
		createGraph(graph, characters, characters.length, Arcs, Arcs.length);
		try {
			if (graph.topologicalSort()==1) {
				System.out.println(" sort is above");
			}
			else {
				System.out.println(" have circuit");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
//		Character[] characters={'a','b','c','d','e','f','g','h','i'};
//		Arc[] Arcs={new Arc(0, 1, 6),new Arc(0, 2, 4),new Arc(0, 3, 5),
//				new Arc(1, 4, 1),new Arc(2, 4, 1),new Arc(3, 5, 2),
//				new Arc(4, 6, 9),new Arc(4, 7, 7),new Arc(5, 7, 4),
//				new Arc(6, 8, 2),new Arc(7, 8, 4)
//				};
//		AdjMGraph graph=new AdjMGraph(characters.length);
//		createGraph(graph, characters, characters.length, Arcs, Arcs.length);
//		try {
//			graph.criticalPath();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
