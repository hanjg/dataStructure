package queue;

public class TestQueue {

	public static void main(String[] args) {
		try {
			int[] a={2,5,6,34,7,9,34,56,77};
			Queue<Integer> queue=new LinQueue<>();
			for(int i:a){
				queue.add(i);
			}
			queue.print();
			
			System.out.println("peek:");
			System.out.println(queue.peek());
			queue.print();
			
			System.out.println("poll:");
			queue.poll();
			queue.print();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
