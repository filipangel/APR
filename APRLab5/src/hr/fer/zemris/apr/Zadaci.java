package hr.fer.zemris.apr;

import java.util.ArrayList;

public class Zadaci {
	
	public static void main(String[] args) {
		zadatak1();
		zadatak2();
		zadatak3();
		zadatak4();
	}

	private static void zadatak1() {
		System.out.println("--------------------------1. zadatak--------------------------");
		ArrayList<Matrica> xValues;
		Matrica A = new Matrica(new double[][] {{0, 1},{-1,0}});
		Matrica B = null;
		boolean r = false;
		Matrica x0 = new Matrica(new double[][] {{1},{1}});
		double T = 0.01;
		double tmax = 10;
		
		System.out.println("Eulerova metoda\n");
		IIntegration euler = new Euler(A, B, r, x0, T, tmax);
		xValues = euler.solve(true);		
		System.out.println();
		
		System.out.println("Obrnuta Eulerova metoda\n");
		IIntegration reverseEuler = new ReverseEuler(A, B, r, x0, T, tmax);
		xValues = reverseEuler.solve(true);
		System.out.println();
		
		System.out.println("Trapezni postupak\n");
		IIntegration trapese = new Trapese(A, B, r, x0, T, tmax);
		xValues = trapese.solve(true);
		System.out.println();
		
		System.out.println("Runge-Kutta 4. reda\n");
		IIntegration rungeKutta = new RungeKutta(A, B, r, x0, T, tmax);
		xValues = rungeKutta.solve(true);
		System.out.println();
		
		System.out.println("PECE: prediktor Euler, korektor trapezni postupak\n");
		IIntegration pece = new PECE(A, B, r, x0, T, tmax, true);
		xValues = pece.solve(true);
		System.out.println();
		
		System.out.println("PE(CE)^2: prediktor Euler, korektor obrnuti Euler\n");
		pece = new PECE(A, B, r, x0, T, tmax, false);
		xValues = pece.solve(true);
		
		System.out.println();
	}
	
	private static void zadatak2() {
		System.out.println("--------------------------2. zadatak--------------------------");
		ArrayList<Matrica> xValues;
		Matrica A = new Matrica(new double[][] {{0, 1},{-200, -102}});
		Matrica B = null;
		boolean r = false;
		Matrica x0 = new Matrica(new double[][] {{1},{-2}});
		double T = 0.1;
		double tmax = 1;
		
		System.out.println("Eulerova metoda\n");
		IIntegration euler = new Euler(A, B, r, x0, T, tmax);
		xValues = euler.solve(false);
		System.out.println();
		
		System.out.println("Obrnuta Eulerova metoda\n");
		IIntegration reverseEuler = new ReverseEuler(A, B, r, x0, T, tmax);
		xValues = reverseEuler.solve(false);
		System.out.println();
		
		System.out.println("Trapezni postupak\n");
		IIntegration trapese = new Trapese(A, B, r, x0, T, tmax);
		xValues = trapese.solve(false);
		System.out.println();
		
		System.out.println("Testiranje Runge-Kutta 4. reda za različite periode integracije");
		for(double t = 0.01; t < 0.1; t += 0.01) {
			System.out.println("\nT = " + t + "\n");
			IIntegration rungeKutta = new RungeKutta(A, B, r, x0, t, tmax);
			xValues = rungeKutta.solve(false);
		}
		System.out.println();
		
		System.out.println("PECE: prediktor Euler, korektor trapezni postupak\n");
		IIntegration pece = new PECE(A, B, r, x0, T, tmax, true);
		xValues = pece.solve(false);
		System.out.println();
		
		System.out.println("PE(CE)^2: prediktor Euler, korektor obrnuti Euler\n");
		pece = new PECE(A, B, r, x0, T, tmax, false);
		xValues = pece.solve(false);
		
		System.out.println();
	}
	
	private static void zadatak3() {
		System.out.println("--------------------------3. zadatak--------------------------");
		ArrayList<Matrica> xValues;
		Matrica A = new Matrica(new double[][] {{0, -2},{1, -3}});
		Matrica B = new Matrica(new double[][] {{2, 0},{0, 3}});
		boolean r = true;
		Matrica x0 = new Matrica(new double[][] {{1},{3}});
		double T = 0.01;
		double tmax = 10;
		
		System.out.println("Eulerova metoda\n");
		IIntegration euler = new Euler(A, B, r, x0, T, tmax);
		xValues = euler.solve(false);
		System.out.println();
		
		System.out.println("Obrnuta Eulerova metoda\n");
		IIntegration reverseEuler = new ReverseEuler(A, B, r, x0, T, tmax);
		xValues = reverseEuler.solve(false);
		System.out.println();
		
		System.out.println("Trapezni postupak\n");
		IIntegration trapese = new Trapese(A, B, r, x0, T, tmax);
		xValues = trapese.solve(false);
		System.out.println();
		
		System.out.println("Runge-Kutta 4. reda\n");
		IIntegration rungeKutta = new RungeKutta(A, B, r, x0, T, tmax);
		xValues = rungeKutta.solve(false);
		System.out.println();
		
		System.out.println("PECE: prediktor Euler, korektor trapezni postupak\n");
		IIntegration pece = new PECE(A, B, r, x0, T, tmax, true);
		xValues = pece.solve(false);
		System.out.println();
		
		System.out.println("PE(CE)^2: prediktor Euler, korektor obrnuti Euler\n");
		pece = new PECE(A, B, r, x0, T, tmax, false);
		xValues = pece.solve(false);
		
		System.out.println();
	}
	
	private static void zadatak4() {
		System.out.println("--------------------------4. zadatak--------------------------");
		ArrayList<Matrica> xValues;
		Matrica A = new Matrica(new double[][] {{1, -5},{1, -7}});
		Matrica B = new Matrica(new double[][] {{5, 0},{0, 3}});
		boolean r = false;
		Matrica x0 = new Matrica(new double[][] {{-1},{3}});
		double T = 0.01;
		double tmax = 1;
		
		System.out.println("Eulerova metoda\n");
		IIntegration euler = new Euler(A, B, r, x0, T, tmax);
		xValues = euler.solve(false);
		System.out.println();
		
		System.out.println("Obrnuta Eulerova metoda\n");
		IIntegration reverseEuler = new ReverseEuler(A, B, r, x0, T, tmax);
		xValues = reverseEuler.solve(false);
		System.out.println();
		
		System.out.println("Trapezni postupak\n");
		IIntegration trapese = new Trapese(A, B, r, x0, T, tmax);
		xValues = trapese.solve(false);
		System.out.println();
		
		System.out.println("Runge-Kutta 4. reda\n");
		IIntegration rungeKutta = new RungeKutta(A, B, r, x0, T, tmax);
		xValues = rungeKutta.solve(false);
		System.out.println();
		
		System.out.println("PECE: prediktor Euler, korektor trapezni postupak\n");
		IIntegration pece = new PECE(A, B, r, x0, T, tmax, true);
		xValues = pece.solve(false);
		for(Matrica x : xValues) {
			x.transpose().print();
		}
		System.out.println();
		
		System.out.println("PE(CE)^2: prediktor Euler, korektor obrnuti Euler\n");
		pece = new PECE(A, B, r, x0, T, tmax, false);
		xValues = pece.solve(false);
		for(Matrica x : xValues) {
			x.transpose().print();
		}
		
		System.out.println();
	}
	
}
