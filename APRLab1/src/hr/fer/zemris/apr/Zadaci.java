package hr.fer.zemris.apr;

public class Zadaci {

	public static void main(String[] args) {
		Matrica A = new Matrica("A.txt");
		
		if(A.LUP(false) != null) A.LUP(false).print();;
	}
}
