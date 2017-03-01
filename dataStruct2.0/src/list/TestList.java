package list;

public class TestList {
	public static void main(String[] args) {
		int[] a={2,5,6,34,7,9,34,56,77};
		List<Integer> list=new SeqList<>();
		try {
			for(int i=0;i<a.length;i++){
				list.add(a[i]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		list.print();
		
		try {
			System.out.println("remove 34:");
			list.remove(new Integer(34));
			list.print();
			
			System.out.println("remove last:");
			list.remove();
			list.print();
			
			System.out.println("add 99 at 0:");
			list.add(0,99);
			list.print();
			
			System.out.println("contain 2:"+list.contain(2));
			
			System.out.println("contain 0:"+list.contain(0));
			
			System.out.println("get index 1 :"+list.get(1));
			
			System.out.println("cur size:"+list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
