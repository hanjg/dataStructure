package applyment;

public class HannoTower {
	public static void tower(int n,char from,char to,char aid){
		if (n==1) {
			System.out.println("move"+1+":"+from+"->"+to);
			return;
		}
		tower(n-1, from, aid, to);
		System.out.println("move"+n+":"+from+"->"+to);
		tower(n-1, aid, to, from);
	}
	public static void main(String[] args) {
		tower(4, 'A', 'C', 'B');
	}

}
