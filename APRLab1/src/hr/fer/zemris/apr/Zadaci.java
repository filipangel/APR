package hr.fer.zemris.apr;

public class Zadaci {

	public static void main(String[] args) {
		Matrica A = new Matrica("A.txt");
		A.print();
		System.out.println();
		A.inv().print();
	}
}
