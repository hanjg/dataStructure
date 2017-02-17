package duLin;
//套用现成的有头结点的循环双向链表解法

class Person{
	int index;
	int key;
	
	public Person(int index,int key){
		this.index=index;
		this.key=key;
	}
}

public class JosephCircle {
	private DuLinkList circle;
	
	public JosephCircle(int[] keys){
		circle=new DuLinkList();
		for(int i=0;i<keys.length;i++){
			circle.insert(i, new Person(i, keys[i]));
		}
	}
	
	public void kickOut(int firstKey) throws Exception{
		int m=firstKey;//报数上限
		int toDelete=0;//将要删除学生下标
		while(circle.getSize()>1){
			toDelete=(toDelete+m-1)%circle.getSize();
			Person dePerson=(Person) circle.delete(toDelete);
			m=dePerson.key;
			System.out.print(dePerson.index+1+" ");
		}
		System.out.println();
		System.out.println("最后一个人是：第"+(((Person)circle.getData(0)).index+1)+"个");
	}
	public static void main(String[] args) {
		int[] keys={3,1,7,2,4,8,4};
		JosephCircle jCircle=new JosephCircle(keys);
		try {
			jCircle.kickOut(20);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
