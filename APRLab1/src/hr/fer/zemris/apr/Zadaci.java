package hr.fer.zemris.apr;

public class Zadaci {
	
	private static double eps = 1E-9;

	public static void main(String[] args) {
		zadatak2();
		zadatak3();
		zadatak4();
		zadatak5();
		zadatak6();
		zadatak7();
		zadatak8();
		zadatak9();
		zadatak10();
	}

	private static void zadatak10() {
		System.out.println("-----------10. Zadatak-----------");
		Matrica A = new Matrica("Zadaci/10A.txt");
		
		System.out.println("Trazim determinantu matrice A:");
		A.print();
		System.out.println();
		
		double det = A.det();
		if(det > 0) {
			System.out.println("Determinanta od A iznosi " + det); 
		}
		System.out.println();
	}

	private static void zadatak9() {
		System.out.println("-----------9. Zadatak-----------");
		Matrica A = new Matrica("Zadaci/9A.txt");
		
		System.out.println("Trazim determinantu matrice A:");
		A.print();
		System.out.println();
		
		double det = A.det();
		if(det > 0) {
			System.out.println("Determinanta od A iznosi " + det); 
		}
		System.out.println();
	}

	private static void zadatak8() {
		System.out.println("-----------8. Zadatak-----------");
		Matrica A = new Matrica("Zadaci/8A.txt");
		
		System.out.println("Trazim inverz matrice A:");
		A.print();
		System.out.println();
		
		if(A.inv() != null) {
			A.inv().print();
		}
		System.out.println();
	}

	private static void zadatak7() {
		System.out.println("-----------7. Zadatak-----------");
		Matrica A = new Matrica("Zadaci/7A.txt");
		
		System.out.println("Trazim inverz matrice A:");
		A.print();
		System.out.println();
		
		if(A.inv() != null) {
			A.inv().print();
		}
		System.out.println();
	}

	private static void zadatak6() {
		System.out.println("-----------6. Zadatak-----------");
		Matrica A = new Matrica("Zadaci/6A.txt");
		Matrica b = new Matrica("Zadaci/6b.txt");
		
		System.out.println("Matrica A:");
		A.print();
		System.out.println();
		
		System.out.println("Slobodni vektor b:");
		b.print();
		System.out.println();
		
		System.out.println("Pokusavam LU dekompoziciju...");
		int[] rowSwaps = {0};
		Matrica LU = A.LUP(false, null, rowSwaps, 1E-6);
		if(LU != null) LU.print();
		System.out.println();
		
		System.out.println("Pokusavam LUP dekompoziciju...");
		Matrica P = new Matrica(A.getRows(), A.getColumns());
		LU = A.LUP(true, P, rowSwaps, 1E-6);
		if(LU == null) {
			System.out.println("Sustav se ne moze rijesiti LUP dekompozicijom");
			System.out.println();
			return;
		}
		
		Matrica y = LU.supUnaprijed(P.multiply(b));
		Matrica x = LU.supUnatrag(y);
		
		System.out.println("Rjesenja x: ");
		x.print();
		System.out.println();
	}

	private static void zadatak5() {
		System.out.println("-----------5. Zadatak-----------");
		Matrica A = new Matrica("Zadaci/5A.txt");
		Matrica b = new Matrica("Zadaci/5b.txt");
		
		System.out.println("Matrica A:");
		A.print();
		System.out.println();
		
		System.out.println("Slobodni vektor b:");
		b.print();
		System.out.println();
		
		System.out.println("Pokusavam LU dekompoziciju...");
		int[] rowSwaps = {0};
		Matrica LU = A.LUP(false, null, rowSwaps, eps);
		if(LU != null) LU.print();
		System.out.println();
		
		System.out.println("Pokusavam LUP dekompoziciju...");
		Matrica P = new Matrica(A.getRows(), A.getColumns());
		LU = A.LUP(true, P, rowSwaps, eps);
		
		Matrica y = LU.supUnaprijed(P.multiply(b));
		Matrica x = LU.supUnatrag(y);
		
		System.out.println("Rjesenja x: ");
		x.print();
		System.out.println();
	}

	private static void zadatak4() {
		System.out.println("-----------4. Zadatak-----------");
		Matrica A = new Matrica("Zadaci/4A.txt");
		Matrica b = new Matrica("Zadaci/4b.txt");
		
		System.out.println("Matrica A:");
		A.print();
		System.out.println();
		
		System.out.println("Slobodni vektor b:");
		b.print();
		System.out.println();
		
		System.out.println("Pokusavam LU dekompoziciju...");
		int[] rowSwaps = {0};
		Matrica LU = A.LUP(false, null, rowSwaps, eps);
		if(LU != null) {
			Matrica y = LU.supUnaprijed(b);
			Matrica x = LU.supUnatrag(y);
			System.out.println("Rjesenja x pomocu LU dekompozicije:");
			x.print();
			System.out.println();
		}
		
		System.out.println("Pokusavam LUP dekompoziciju...");
		Matrica P = new Matrica(A.getRows(), A.getColumns());
		Matrica LUP = A.LUP(true, P, rowSwaps, eps);	
		
		if(LU == null) {
			System.out.println("Sustav se ne moze rijesiti");
			System.out.println();
			return;
		}
		
		Matrica y = LUP.supUnaprijed(P.multiply(b));
		Matrica x = LUP.supUnatrag(y);
		
		System.out.println("Rjesenja x pomocu LUP dekompozicije: ");
		x.print();
		System.out.println();		
	}

	private static void zadatak3() {
		System.out.println("-----------3. Zadatak-----------");
		Matrica A = new Matrica("Zadaci/3A.txt");
		Matrica b = new Matrica("Zadaci/3b.txt");
		
		System.out.println("Matrica A:");
		A.print();
		System.out.println();
		
		System.out.println("Slobodni vektor b:");
		b.print();
		System.out.println();
		
		System.out.println("Pokusavam LU dekompoziciju...");
		int[] rowSwaps = {0};
		Matrica LU = A.LUP(false, null, rowSwaps, eps);
		if(LU != null) LU.print();
		
		System.out.println("Pokusavam LUP dekompoziciju...");
		Matrica P = new Matrica(A.getRows(), A.getColumns());
		LU = A.LUP(true, P, rowSwaps, eps);	
		
		if(LU == null) {
			System.out.println("Sustav se ne moze rijesiti");
			return;
		}
		Matrica y = LU.supUnaprijed(P.multiply(b));
		Matrica x = LU.supUnatrag(y);
		
		System.out.println("Rjesenja x: ");
		x.print();
		System.out.println();
		
		System.out.println();
	}

	private static void zadatak2() {
		System.out.println("-----------2. Zadatak-----------");
		Matrica A = new Matrica("Zadaci/2A.txt");
		Matrica b = new Matrica("Zadaci/2b.txt");
		
		System.out.println("Matrica A:");
		A.print();
		System.out.println();
		
		System.out.println("Slobodni vektor b:");
		b.print();
		System.out.println();
		
		System.out.println("Pokusavam LU dekompoziciju...");
		int[] rowSwaps = {0};
		Matrica LU = A.LUP(false, null, rowSwaps, eps);
		if(LU != null) LU.print();
		
		System.out.println("Pokusavam LUP dekompoziciju...");
		Matrica P = new Matrica(A.getRows(), A.getColumns());
		LU = A.LUP(true, P, rowSwaps, eps);	
		
		Matrica y = LU.supUnaprijed(P.multiply(b));
		Matrica x = LU.supUnatrag(y);
		
		System.out.println("Rjesenja x: ");
		x.print();
		System.out.println();
	}
}
