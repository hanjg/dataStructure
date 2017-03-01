package application;

import application.JosephCircle.Person;

public class TestJoseph {
	public static void main(String[] args) {
		int[] keys={3,1,7,2,4,8,4};
		JosephCircle circle=new JosephCircle(keys);
		int key=20;
		while (circle.size()>1) {
			Person person=circle.remove(key);
			System.out.print(person.getIndex()+" ");
			key=person.getKey();
		}
		System.out.println();
		System.out.println("last one is index:"+circle.remove(1).getIndex());
	}
}
