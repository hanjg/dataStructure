package stack;

public class TestStack {
	public static void main(String[] args) {

		int[] a={2,5,6,34,7,9,34,56,77};
		Stack<Integer> stack=new LinStack<>();
		try {
			for(int i=0;i<a.length;i++){
				stack.push(a[i]);
			}
			stack.print();
			
			System.out.println("push 2:");
			stack.push(2);
			stack.print();
			
			System.out.println("peek:");
			System.out.println(stack.peek());
			
			System.out.println("pop:");
			stack.pop();
			stack.print();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
}
