package hr.fer.zemris.apr;

public class Zadaci {

	public static void main(String[] args) {
		zadatak1();
		zadatak2();
		zadatak3();
		zadatak4();
		zadatak5();
	}

	private static void zadatak5() {
		System.out.println("-------------------------5. zadatak--------------------------------\n");
		System.out.println("Primjena Gauss-Newtonove metode na sustav iz zadatka\n"
				+ "g1(x) = x1 * exp(x2) + x3 - 3\n"
				+ "g2(x) = x1 * exp(2 * x2) + x3 - 4\n"
				+ "g3(x) = x1 * exp(3 * x2) + x3 - 4\n"
				+ "g4(x) = x1 * exp(5 * x2) + x3 - 5\n"
				+ "g5(x) = x1 * exp(6 * x2) + x3 - 6\n"
				+ "g6(x) = x1 * exp(7 * x2) + x3 - 8\n");
		
		IFunctionSystem s1 = new PetiZadatakSustav();
		double[] x0 = {1, 1, 1};
		
		System.out.println("Rezultat metode Gauss-Newton bez optimalnog iznosa koraka:");
		double[] xmin = OptimizationMethods.GaussNewton(false, 1E-6, s1, false, x0);
		int[] timesCalled = s1.getTimesCalled();
		System.out.println("Pronađeno rješenje: " + OptimizationMethods.stringX(xmin, 9) + " F(xmin) = " + s1.at(xmin) + " Broj poziva: f: " + timesCalled[0] + " j: " + timesCalled[1] + " g: " + timesCalled[2]);
		
		s1.reset();
		
		System.out.println("Rezultat metode Gauss-Newton s optimalnim iznosom koraka:");
		xmin = OptimizationMethods.GaussNewton(true, 1E-6, s1, false, x0);
		timesCalled = s1.getTimesCalled();
		System.out.println("Pronađeno rješenje: " + OptimizationMethods.stringX(xmin, 9) + " F(xmin) = " + s1.at(xmin)+ " Broj poziva: f: " + timesCalled[0] + " j: " + timesCalled[1] + " g: " + timesCalled[2]);
		System.out.println();
	}

	private static void zadatak4() {
		System.out.println("-------------------------4. zadatak--------------------------------\n");
		System.out.println("Primjena Gauss-Newtonove metode na sustav iz zadatka:\n"
				+ "g1(x) = x1^2 + x2^2 - 1\n"
				+ "g2(x) = x2 - x1^2\n");
		
		IFunctionSystem s1 = new CetvrtiZadatakSustav();
		double[] x01 = {-2.0, 0.0};
		double[] x02 = {2.0, 0.0};
		
		System.out.println("Rezultat metode Gauss-Newton s optimalnim iznosom koraka za početnu točku (-2,0):");
		double[] xmin = OptimizationMethods.GaussNewton(true, 1E-6, s1, false, x01);
		int[] timesCalled = s1.getTimesCalled();
		System.out.println("Pronađeno rješenje: " + OptimizationMethods.stringX(xmin, 9) + " F(xmin) = " + s1.at(xmin) + " Broj poziva: f: " + timesCalled[0] + " j: " + timesCalled[1] + " g: " + timesCalled[2]);
		
		s1.reset();
		
		System.out.println("Rezultat metode Gauss-Newton s optimalnim iznosom koraka za početnu točku (2,0):");
		xmin = OptimizationMethods.GaussNewton(true, 1E-6, s1, false, x02);
		timesCalled = s1.getTimesCalled();
		System.out.println("Pronađeno rješenje: " + OptimizationMethods.stringX(xmin, 9) + " F(xmin) = " + s1.at(xmin) + " Broj poziva: f: " + timesCalled[0] + " j: " + timesCalled[1] + " g: " + timesCalled[2]);
		
		System.out.println();
	}

	private static void zadatak3() {
		System.out.println("-------------------------3. zadatak--------------------------------\n");
		System.out.println("Primjena Gauss-Newtonove metode na sustav dobiven iz F1:\n"
				+ "g1(x) = 10 * (x2 - x1^2)\n"
				+ "g2(x) = 1 - x1\n");
		
		IFunctionSystem s1 = new TreciZadatakSustav();
		double[] x0 = {-1.9, 2.0};
		
		System.out.println("Rezultat metode Gauss-Newton bez optimalnog iznosa koraka:");
		double[] xmin = OptimizationMethods.GaussNewton(false, 1E-6, s1, false, x0);
		int[] timesCalled = s1.getTimesCalled();
		System.out.println("Pronađeno rješenje: " + OptimizationMethods.stringX(xmin, 9) + " F(xmin) = " + s1.at(xmin) + " Broj poziva: f: " + timesCalled[0] + " j: " + timesCalled[1] + " g: " + timesCalled[2]);
		
		s1.reset();
		
		xmin = OptimizationMethods.GaussNewton(true, 1E-6, s1, false, x0);
		timesCalled = s1.getTimesCalled();
		System.out.println("Pronađeno rješenje: " + OptimizationMethods.stringX(xmin, 9) + " F(xmin) = " + s1.at(xmin) + " Broj poziva: f: " + timesCalled[0] + " j: " + timesCalled[1] + " g: " + timesCalled[2]);
		
		System.out.println();
	}

	private static void zadatak2() {
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
		System.out.print("Gradijentni spust | F1       | " + f1.getTimesCalled()[0] + "               | " + f1.getTimesCalled()[1] + "                  | " + f1.getTimesCalled()[2] + "                            | " + OptimizationMethods.stringX(min1, 9) +"\n");
		System.out.print("Gradijentni spust | F2       | " + f2.getTimesCalled()[0] + "                  | " + f2.getTimesCalled()[1] + "                     | " + f2.getTimesCalled()[2] + "                            | " + OptimizationMethods.stringX(min2, 9) + "\n");
		
		f1.reset();
		f2.reset();
		
		min1 = OptimizationMethods.NewtonRaphson(true, 1E-6, f1, false, x01);
		min2 = OptimizationMethods.NewtonRaphson(true, 1E-6, f2, false, x02);
		
		System.out.print("Newton-Raphson    | F1       | " + f1.getTimesCalled()[0] + "                  | " + f1.getTimesCalled()[1] + "                     | " + f1.getTimesCalled()[2] + "                           | " + OptimizationMethods.stringX(min1, 9) + "\n");
		System.out.print("Newton-Raphson    | F2       | " + f2.getTimesCalled()[0] + "                   | " + f2.getTimesCalled()[1] + "                      | " + f2.getTimesCalled()[2] + "                            | " + OptimizationMethods.stringX(min2, 9) + "\n");
				
		System.out.println();
	}

	private static void zadatak1() {
		System.out.println("-------------------------1. zadatak--------------------------------\n");
		
		System.out.println("Primjena gradijentnog spusta na funkciju F(x) = (x1 - 2)^2 + (x2 + 3)^2\n");
		IFunction f3 = new TrecaFunkcija();
		double[] x0 = {0, 0};
		System.out.println("Rezultat metode gradijentnog spusta bez optimalnog iznosa koraka:");
		double[] min = OptimizationMethods.GradientDescent(false, 1E-6, f3, false, x0);
		System.out.print("Pronađeno rješenje: " + OptimizationMethods.stringX(min, 9) + "\n");
		System.out.println("Rezultat metode gradijentnog spusta s optimalnim iznosom koraka:");
		min = OptimizationMethods.GradientDescent(true, 1E-6, f3, false, x0);
		System.out.print("Pronađeno rješenje: " + OptimizationMethods.stringX(min, 9) + "\n");
		System.out.println();
	}

}
