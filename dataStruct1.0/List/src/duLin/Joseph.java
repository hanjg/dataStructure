package duLin;
//无头结点的循环双向链表解法

class JoNode{
	int index;
	int key;
	JoNode next;
	JoNode prior;
	
	public JoNode(int index,int key,JoNode prior,JoNode next) {
		this.key=key;
		this.index=index;
		this.next=next;
		this.prior=prior;
	}
}

public class Joseph {
	JoNode head;
	JoNode current;
	int size;
	
	public Joseph(int n,int[] key) {
		head=current=new JoNode(0, key[0], null,null);
		head.next=head;
		head.prior=head;
		size=1;
		for(int i=1;i<n;i++){
			JoNode temp=new JoNode(i, key[i],current, current.next);
			current.next=temp;
			temp.next.prior=temp;
			current=current.next;
			size++;
		}
	}
	
	public void kickout(){
		current=head;
		int count=1;
		int m=20;
		System.out.println();
		for(;size>1;size--){
			for(count=1;count<m;count++){
				current=current.next;
			}
			JoNode pJoNode=current.prior;
			pJoNode.next=current.next;
			current.next.prior=pJoNode;
			current.prior=current.next=null;
			m=current.key;
			System.out.print(current.index+1+",");
			current=pJoNode.next;
		}
		System.out.println(current.index+1+"");
	}
	
	public void print() throws Exception{
		if (size==0) {
			throw new Exception("empty");
		}
		current=head;
		for(int i=0;i<this.size;i++){
			System.out.print(current.index+"["+current.key+"]"+",");
			current=current.next;
		}
	}
	
	public static void main(String[] args) {
		int[] key={3,1,7,2,4,8,4};
		Joseph josephcircle=new Joseph(key.length, key);
		try {
			josephcircle.print();
		} catch (Exception e) {
		}
		josephcircle.kickout();
	}

}
