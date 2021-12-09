package hr.fer.zemris.apr;

public class Zadaci {

	public static void main(String[] args) throws InterruptedException {
		zadatak1();
		zadatak2();
		zadatak3();
		zadatak4();
		zadatak5();
	}

	private static void zadatak5() {
		System.out.println("-------------------------5. zadatak--------------------------------\n");
		
		System.out.println();
	}

	private static void zadatak4() {
		System.out.println("-------------------------4. zadatak--------------------------------\n");
		
		System.out.println();
	}

	private static void zadatak3() {
		System.out.println("-------------------------3. zadatak--------------------------------\n");
		
		System.out.println();
	}

	private static void zadatak2() throws InterruptedException {
		System.out.println("-------------------------2. zadatak--------------------------------\n");
		
		System.out.println("Primjena gradijentnog spusta na F1 i F2.\n"
				+ "F1(x) = 100 * (x2 - x1^2)^2 + (1 - x1)^2\n"
				+ "F2(x) = (x1 - 4)^2 + 4 * (x2 - 2)^2"
				+ "\n");
		
		IFunction f1 = new PrvaFunkcija();
		double[] x01 = {-1.9, 2};
		
		IFunction f2 = new DrugaFunkcija();
		double[] x02 = {0.1, 0.3};
		
		System.out.println("Rezultat metode gradijentnog spusta s optimalnim iznosom koraka:");
		double[] min1 = OptimizationMethods.GradientDescent(true, 1E-6, f1, false, x01);
		double[] min2 = OptimizationMethods.GradientDescent(true, 1E-6, f2, false, x02);
		System.out.print("\nMetoda            | Funkcija | Broj poziva funkcije | Broj poziva gradijenta | Broj poziva Hesseove matrice | Pronađeni minimum\n");
		System.out.print("---------------------------------------------------------------------------------------------------------------------------------\n");
		System.out.print("Gradijentni spust | F1       | " + f1.getTimesCalled()[0] + "               | " + f1.getTimesCalled()[1] + "                  | " + f1.getTimesCalled()[2] + "                            | " + OptimizationMethods.stringX(min1, 2) +"\n");
		System.out.print("Gradijentni spust | F2       | " + f2.getTimesCalled()[0] + "                 | " + f2.getTimesCalled()[1] + "                     | " + f2.getTimesCalled()[2] + "                            | " + OptimizationMethods.stringX(min2, 2) + "\n");
		
		f1.reset();
		f2.reset();
		
		min1 = OptimizationMethods.NewtonRaphson(true, 1E-6, f1, false, x01);
		min2 = OptimizationMethods.NewtonRaphson(true, 1E-6, f2, false, x02);
		
		System.out.print("Newton-Raphson    | F1       | " + f1.getTimesCalled()[0] + "                | " + f1.getTimesCalled()[1] + "                   | " + f1.getTimesCalled()[2] + "                         | " + OptimizationMethods.stringX(min1, 2) + "\n");
		System.out.print("Newton-Raphson    | F2       | " + f2.getTimesCalled()[0] + "                | " + f2.getTimesCalled()[1] + "                    | " + f2.getTimesCalled()[2] + "                          | " + OptimizationMethods.stringX(min2, 2) + "\n");
				
		System.out.println();
	}

	private static void zadatak1() throws InterruptedException {
		System.out.println("-------------------------1. zadatak--------------------------------\n");
		
		System.out.println("Primjena gradijentnog spusta na funkciju F(x) = (x1 - 2)^2 + (x2 + 3)^2\n");
		IFunction f3 = new TrecaFunkcija();
		double[] x0 = {0, 0};
		System.out.println("Rezultat metode gradijentnog spusta bez optimalnog iznosa koraka:");
		double[] min = OptimizationMethods.GradientDescent(false, 1E-6, f3, false, x0);
		System.out.print("\n");
		System.out.println("Rezultat metode gradijentnog spusta s optimalnim iznosom koraka:");
		min = OptimizationMethods.GradientDescent(true, 1E-6, f3, false, x0);
		
		System.out.println();
	}

}
