package application;

public class TestBank {
	public static void main(String[] args) {
		BankSimulation bankSimulation;
		for (int i=0;i<10;i++) {
			bankSimulation=new BankSimulation();
			bankSimulation.start();
			System.out.println(bankSimulation.averageTime());}
		}
}
