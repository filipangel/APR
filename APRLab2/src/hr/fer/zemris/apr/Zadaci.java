package hr.fer.zemris.apr;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Zadaci {

	public static void main(String[] args) throws IOException {		
		zadatak1();
		zadatak2();
		zadatak3();
		zadatak4();
		zadatak5();
	}

	private static void zadatak5() {
		System.out.println();
		System.out.println("--------------------------5. zadatak--------------------------");
		
		IFunction f = new SchafferFunkcija();
		Random rand = new Random();
		
		double e = 1E-6;
		
		for(int i = 0; i < 20; i++) {
			double[] x0 = {rand.nextDouble(-50, 50), rand.nextDouble(-50, 50)};
			System.out.print("Pocetna tocka: " + Arrays.toString(x0));
			System.out.println(" Minimum: " + Arrays.toString(OptimizationMethods.hookeJeeves(e, f, 0.5, false, x0)) + " Poziva funkcije: " + f.getTimesCalled());			
		}
	}

	private static void zadatak4() {
		System.out.println();
		System.out.println("--------------------------4. zadatak--------------------------");
		
		IFunction f = new RosenbrockBanana();
		double[] x0 = {0.5, 0.5};
		
		double e = 1E-6;
		double alpha = 1;
		double beta = 0.5;
		double gamma = 2;
		double sigma = 0.5;
		
		System.out.println("Trazim minimum s pocetnom tockom " + Arrays.toString(x0));
		
		for(int i = 1; i <= 20; i++) {
			System.out.println("Za inicijalni pomak " + i + " metoda simpleksa nalazi minimum " + Arrays.toString(OptimizationMethods.simplex(alpha,  beta,  gamma,  sigma,  i,  e,  f,  false, x0)) + " u " + f.getTimesCalled() + " poziva funkcije.");
		}
		
		double[] x1 = {20, 20};
		System.out.println("Trazim minimum s pocetnom tockom " + Arrays.toString(x1));
		for(int i = 1; i <= 20; i++) {
			System.out.println("Za inicijalni pomak " + i + " metoda simpleksa nalazi minimum " + Arrays.toString(OptimizationMethods.simplex(alpha,  beta,  gamma,  sigma,  i,  e,  f,  false, x1)) + " u " + f.getTimesCalled() + " poziva funkcije.");
		}
	}

	private static void zadatak3() {
		System.out.println();
		System.out.println("--------------------------3. zadatak--------------------------");
		
		IFunction f = new JakobovicFunkcija();
		double[] x0 = {5,5};
		
		double e = 1E-6;
		double alpha = 1;
		double beta = 0.5;
		double gamma = 2;
		double sigma = 0.5;
		double dx = 1;
		
		System.out.println("Metodom simpleksa pronaden je minimum " + Arrays.toString(OptimizationMethods.simplex(alpha, beta, gamma, sigma, dx, e, f, false, x0)) + " Poziva funkcije: " + f.getTimesCalled());
		f.reset();
		System.out.println("Metodom Hooke-Jeeves pronaden je minimum " + Arrays.toString(OptimizationMethods.hookeJeeves(e, f, 0.5, false, x0)) + " Poziva funkcije: " + f.getTimesCalled());
	}

	private static void zadatak2() {
		System.out.println();
		System.out.println("--------------------------2. zadatak--------------------------");
		
		IFunction f1 = new RosenbrockBanana();
		double[] x1 = {-1.9, 2};
		IFunction f2 = new DrugaFunkcija();
		double[] x2 = {0.1, 0.3};
		IFunction f3 = new TrecaFunkcija();
		double[] x3 = {0,0,0,0,0};
		IFunction f4 = new JakobovicFunkcija();
		double[] x4 = {5.1, 1.1};
		
		double e = 1E-6;
		double alpha = 1;
		double beta = 0.5;
		double gamma = 2;
		double sigma = 0.5;
		double dx = 1;
		
		System.out.println("Pretrazivanje po koordinatnim osima: ");
		System.out.println("F1: minimum: " + Arrays.toString(OptimizationMethods.coordinateSearch(e, f1, false, x1)) + " Poziva: " + f1.getTimesCalled()
		+ " F2: minimum: " + Arrays.toString(OptimizationMethods.coordinateSearch(e, f2, false, x2)) + " Poziva: " + f2.getTimesCalled()
		+ " F3: minimum: " + Arrays.toString(OptimizationMethods.coordinateSearch(e, f3, false, x3)) + " Poziva: " + f3.getTimesCalled()
		+ " F4: minimum: " + "Nemoguće pronaći" + " Poziva: " + "0"
		);
		
		f1.reset();
		f2.reset();
		f3.reset();
		f4.reset();
		
		System.out.println("Simpleks po Nelderu i Meadu: ");
		System.out.println("F1: minimum: " + Arrays.toString(OptimizationMethods.simplex(alpha, beta, gamma, sigma, dx, e, f1, false, x1)) + " Poziva: " + f1.getTimesCalled()
		+ " F2: minimum: " + Arrays.toString(OptimizationMethods.simplex(alpha, beta, gamma, sigma, dx, e, f2, false, x2)) + " Poziva: " + f2.getTimesCalled()
		+ " F3: minimum: " + Arrays.toString(OptimizationMethods.simplex(alpha, beta, gamma, sigma, dx, e, f3, false, x3)) + " Poziva: " + f3.getTimesCalled()
		+ " F3: minimum: " + Arrays.toString(OptimizationMethods.simplex(alpha, beta, gamma, sigma, dx, e, f4, false, x4)) + " Poziva: " + f4.getTimesCalled()
		);
		
		f1.reset();
		f2.reset();
		f3.reset();
		f4.reset();
		
		System.out.println("Hooke-Jeeves metoda: ");
		System.out.println("F1: minimum: " + Arrays.toString(OptimizationMethods.hookeJeeves(e, f1, 0.5, false, x1)) + " Poziva: " + f1.getTimesCalled()
		+ " F2: minimum: " + Arrays.toString(OptimizationMethods.hookeJeeves(e, f2, 0.5, false, x2)) + " Poziva: " + f2.getTimesCalled()
		+ " F3: minimum: " + Arrays.toString(OptimizationMethods.hookeJeeves(e, f3, 0.5, false, x3)) + " Poziva: " + f3.getTimesCalled()
		+ " F4: minimum: " + Arrays.toString(OptimizationMethods.hookeJeeves(e, f4, 0.5, false, x4)) + " Poziva: " + f4.getTimesCalled()
		);
	}

	private static void zadatak1() {
		System.out.println("--------------------------1. zadatak--------------------------");		
		IFunction f = new IFunction() {
			private int timesCalled = 0;
			@Override
			public double at(double... x) {
				timesCalled++;
				return Math.pow(x[0] - 3, 2);
			}

			@Override
			public int getTimesCalled() {
				// TODO Auto-generated method stub
				return timesCalled;
			}

			@Override
			public void reset() {
				timesCalled = 0;				
			}			
		};
		
		double x0 = 10;
		
		double e = 1E-6;
		double alpha = 1;
		double beta = 0.5;
		double gamma = 2;
		double sigma = 0.5;
		double dx = 1;
		
		System.out.println("x0 = " + x0);
		
		System.out.println("Zlatni rez: minimum: " + OptimizationMethods.zlatniRez2(1, x0, e, f, false) + " Poziva funkcije: " + f.getTimesCalled());
		f.reset();
		System.out.println("Simpleks: minimum: " + Arrays.toString(OptimizationMethods.simplex(alpha, beta, gamma, sigma, dx, e, f, false, x0)) + " Poziva funkcije: " + f.getTimesCalled());
		f.reset();
		System.out.println("Pretrazivanje po koordinatnim osima: minimum: " + Arrays.toString(OptimizationMethods.coordinateSearch(e, f, false, x0)) + " Poziva funkcije: " + f.getTimesCalled());
		f.reset();
		System.out.println("Pretrazivanje Hooke-Jeeves: minimum: " + Arrays.toString(OptimizationMethods.hookeJeeves(e, f, dx, false, x0)) + " Poziva funkcije: " + f.getTimesCalled());
		
		double x1 = 20;
		
		System.out.println("x1 = " + x1);
		
		System.out.println("Zlatni rez: minimum: " + OptimizationMethods.zlatniRez2(1, x1, e, f, false) + " Poziva funkcije: " + f.getTimesCalled());
		f.reset();
		System.out.println("Simpleks: minimum: " + Arrays.toString(OptimizationMethods.simplex(alpha, beta, gamma, sigma, dx, e, f, false, x1)) + " Poziva funkcije: " + f.getTimesCalled());
		f.reset();
		System.out.println("Pretrazivanje po koordinatnim osima: minimum: " + Arrays.toString(OptimizationMethods.coordinateSearch(e, f, false, x1)) + " Poziva funkcije: " + f.getTimesCalled());
		f.reset();
		System.out.println("Pretrazivanje Hooke-Jeeves: minimum: " + Arrays.toString(OptimizationMethods.hookeJeeves(e, f, dx, false, x1)) + " Poziva funkcije: " + f.getTimesCalled());
		
		double x2 = 50;
		
		System.out.println("x1 = " + x2);
		
		System.out.println("Zlatni rez: minimum: " + OptimizationMethods.zlatniRez2(1, x2, e, f, false) + " Poziva funkcije: " + f.getTimesCalled());
		f.reset();
		System.out.println("Simpleks: minimum: " + Arrays.toString(OptimizationMethods.simplex(alpha, beta, gamma, sigma, dx, e, f, false, x2)) + " Poziva funkcije: " + f.getTimesCalled());
		f.reset();
		System.out.println("Pretrazivanje po koordinatnim osima: minimum: " + Arrays.toString(OptimizationMethods.coordinateSearch(e, f, false, x2)) + " Poziva funkcije: " + f.getTimesCalled());
		f.reset();
		System.out.println("Pretrazivanje Hooke-Jeeves: minimum: " + Arrays.toString(OptimizationMethods.hookeJeeves(e, f, dx, false, x2)) + " Poziva funkcije: " + f.getTimesCalled());
	}
}
