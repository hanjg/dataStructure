package adjListGraph;

class EdgeData {//存储节点到邻接节点的信息,这些将存储在该节点的adj链表里
	int dest;
	int weight;
	
	public EdgeData(int dest,int weight){
		this.dest=dest;
		this.weight=weight;
	}
}

class DuLinkList {//存储边的双向链表
	public class Node{
		public Object element;
		public Node next;
		public Node prior;
		
		public Node() {
		}
		
		public Node(Object element) {
			this.element=element;
		}
		
		public Node(Object element,Node prior,Node next){
			this.element=element;
			this.prior=prior;
			this.next=next;
		}
	}
	
	int size;
	Node head;
	Node current;
	
	public DuLinkList() {
		head=new Node(null);
		head.prior=head;
		head.next=head;
		size=0;
	}
	
	public EdgeData getEdge(int dest){
		current=head.next;
		int j=0;
		while (j<size&&((EdgeData)current.element).dest<dest){
			current=current.next;
			j++;
		}
		if (j==size) {
			return new EdgeData(-1, AdjLGraph.maxWeight);
		}
		else {
			return (EdgeData)current.element;
		}
	}
	
	public int getFirstEdge(){
		if (!isEmpty()) {
			current=head.next;
			return ((EdgeData)current.element).dest;
		}
		return -1;
	}
	
	public int getNextEdge(int dest){
		if (getEdge(dest).dest!=-1&&current.next!=head) {//当dest边存在且不是最后一条边
			current=current.next;
			return ((EdgeData)current.element).dest;
		}
		return -1;
	}
	
	public void insert(int i,EdgeData e){
		if (i<0||i>size) {
			throw new IndexOutOfBoundsException("none");
		}
		Node p=head.next;
		int j=0;
		while(j<i){
			p=p.next;
			j++;
		}
		Node node=new Node(e, p.prior, p);
		p.prior.next=node;
		p.prior=node;
		size++;
	}
	
	public void orderInsert( EdgeData obj){
		if (isEmpty()) {
			insert(0, obj);
		}
		else {
			current=head.next;
			int j=0;
			while(j<size&&obj.dest>((EdgeData)current.element).dest){
				current=current.next;
				j++;
			}
			insert(j, obj);
		}
	}
	
	public void delete(int dest) throws Exception{
		Node p=head.next;
		int j=0;
		while (j<size&&((EdgeData)p.element).dest!=dest){
			p=p.next;
			j++;
		}
		if (j==size) {
			throw new Exception("no edge");
		}
		p.prior.next=p.next;
		p.next.prior=p.prior;
		size--;
	}
	
	public int size(){
		return size;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public void print() throws Exception{
		if (isEmpty()) {
			throw new Exception("empty");
		}
		current=head.next;
		for(int i=0;i<this.size;i++){
			System.out.print(((EdgeData)current.element).dest+",");
			current=current.next;
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
	}

}
