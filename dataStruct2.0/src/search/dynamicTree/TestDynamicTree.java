package search.dynamicTree;

public class TestDynamicTree {
	public static void main(String[] args) {
		int[] data={73,57,8,36,4,71,18,83,17,22,7,50,86,55,92,89,51,78,27,25};
		BMinusTree<Integer> tree=new BMinusTree<>(3);
		for(int i:data){
			tree.insert(i);
			tree.print();
		}
//		for(int i=0;i<20;i++){
//			System.out.print((int)(Math.random()*100)+",");
//		}100)+",");
//		}
	}
}
