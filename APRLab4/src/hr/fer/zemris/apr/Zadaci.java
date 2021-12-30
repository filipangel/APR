package hr.fer.zemris.apr;

import java.util.ArrayList;
import java.util.Arrays;

public class Zadaci {

	public static void main(String[] args) {
		zadatak1();
		zadatak2();
		zadatak3();
	}

	private static void zadatak3() {
		System.out.println("--------------------------3. zadatak--------------------------");
		System.out.println("Minimiziram f4 uz ograničenja g3, g4 i h1");
		System.out.println("f1(x) = (x1 - 3)^2 + x2^2");
		System.out.println("g3(x) = 3 - x1 - x2 >= 0");
		System.out.println("g4(x) = 3 + 1.5 * x1 - x2 >= 0");
		System.out.println("h1(x) = x2 - 1 = 0");
		System.out.println("Konačna funkcija uz ugrađena ograničenja: F(x) = (x1 - 3)^2 + x2^2 - 1/t * (ln(3 - x1 - x2) + ln(2 - x1)) + t * (x2 - 1)");
		System.out.println();
		
		IFunction cetvrta = new CetvrtaFunkcija();
		ArrayList<IFunction> gs = new ArrayList<IFunction>();
		gs.add(new g3());
		gs.add(new g4());
		ArrayList<IFunction> hs = new ArrayList<IFunction>();
		hs.add(new h1());
		TransformFunction tf1 = new TransformFunction(cetvrta, gs, hs);
		Double[] x0 = {0.0, 0.0};
		Double[] sol = OptimizationMethods.PenaltyBarrier(tf1, gs, 1.0, 1E-6, x0);
		System.out.println("Početna točka: " + Arrays.toString(x0));
		System.out.println("Najbolje dobiveno riješenje metodom Hooke-Jeeves: " + Arrays.toString(sol) + " uz vrijednost " + tf1.at(sol));
		System.out.println();
		
		x0 = new Double[] {5.0, 5.0};
		System.out.println("Početna točka: " + Arrays.toString(x0));
		sol = OptimizationMethods.PenaltyBarrier(tf1, gs, 1.0, 1E-6, x0);
		System.out.println("Najbolje dobiveno riješenje metodom Hooke-Jeeves: " + Arrays.toString(sol) + " uz vrijednost " + tf1.at(sol));
		System.out.println();
	}

	private static void zadatak2() {
		System.out.println("--------------------------2. zadatak--------------------------");
		System.out.println("Minimiziram f1 uz ograničenja g1 i g2");
		System.out.println("f1(x) = 100 * (x2 - x1^2)^2 + (1 - x1)^2");
		System.out.println("g1(x) = x2 - x1 >= 0");
		System.out.println("g2(x) = 2 - x1 >= 0");
		System.out.println("Konačna funkcija uz ugrađena ograničenja: F(x) = 100 * (x2 - x1^2)^2 + (1 - x1)^2 - 1/t * (ln(x2 - x1) + ln(2 - x1))");
		System.out.println();
		IFunction prva = new PrvaFunkcija();
		ArrayList<IFunction> gs = new ArrayList<IFunction>();
		gs.add(new g1());
		gs.add(new g2());
		ArrayList<IFunction> hs = new ArrayList<IFunction>();
		TransformFunction tf1 = new TransformFunction(prva, gs, hs);
		Double[] x0 = {-1.9, 2.0};
		Double[] sol = OptimizationMethods.PenaltyBarrier(tf1, gs, 1.0, 1E-6, x0);
		System.out.println("Početna točka: " + Arrays.toString(x0));
		System.out.println("Najbolje dobiveno riješenje metodom Hooke-Jeeves: " + Arrays.toString(sol) + " uz vrijednost " + tf1.at(sol));
		System.out.println();
		
		System.out.println("Minimiziram f2 uz ograničenja g1 i g2");
		System.out.println("f1(x) = (x1 - 4)^2 + 4 * (x2 - 2)^2");
		System.out.println("g1(x) = x2 - x1 >= 0");
		System.out.println("g2(x) = 2 - x1 >= 0");
		System.out.println("Konačna funkcija uz ugrađena ograničenja: F(x) = (x1 - 4)^2 + 4 * (x2 - 2)^2 - 1/t * ln((x2 - x1) + (2 - x1))");
		System.out.println();
		IFunction druga = new DrugaFunkcija();
		TransformFunction tf2 = new TransformFunction(druga, gs, hs);
		x0 = new Double[] {-1.9, 2.0};
		sol = OptimizationMethods.PenaltyBarrier(tf2, gs, 1.0, 1E-6, x0);
		System.out.println("Početna točka: " + Arrays.toString(x0));
		System.out.println("Najbolje dobiveno riješenje metodom Hooke-Jeeves: " + Arrays.toString(sol) + " uz vrijednost " + tf2.at(sol));
		System.out.println();
	}

	private static void zadatak1() {		
		System.out.println("--------------------------1. zadatak--------------------------");	
		
		System.out.println("Minimiziram f1 uz ograničenja g1 i g2");
		System.out.println("f1(x) = 100 * (x2 - x1^2)^2 + (1 - x1)^2");
		System.out.println("g1(x) = x2 - x1 >= 0");
		System.out.println("g2(x) = 2 - x1 >= 0");
		
		IFunction f1 = new PrvaFunkcija();
		ArrayList<IFunction> g = new ArrayList<IFunction>();
		g.add(new g1());
		g.add(new g2());
		Double[] x0 = {-1.9, 2.0};
		Double[] sol = OptimizationMethods.Box(f1, g, 1.3, 1E-6, -100, 100, x0);
		
		System.out.println("Najbolje dobiveno riješenje postupkom po Boxu: " + Arrays.toString(sol) + " uz vrijednost " + f1.at(sol));
		System.out.println();
		
		System.out.println("Minimiziram f2 uz ograničenja g1 i g2");
		System.out.println("f1(x) = (x1 - 4)^2 + 4 * (x2 - 2)^2");
		System.out.println("g1(x) = x2 - x1 >= 0");
		System.out.println("g2(x) = 2 - x1 >= 0");
		
		IFunction f2 = new DrugaFunkcija();
		x0 = new Double[] {0.1, 0.3};
		sol = OptimizationMethods.Box(f2, g, 1.3, 1E-6, -100, 100, x0);
		
		System.out.println("Najbolje dobiveno riješenje postupkom po Boxu: " + Arrays.toString(sol) + " uz vrijednost " + f2.at(sol));
		
		System.out.println();
	}

}
