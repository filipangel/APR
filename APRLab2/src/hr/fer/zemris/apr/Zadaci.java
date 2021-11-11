package hr.fer.zemris.apr;

public class Zadaci {

	public static void main(String[] args) {
		IFunction f= new DrugaFunkcija();
		double[] x0 = {0.1, 0.3};
		double[] min = OptimizationMethods.hookeJeeves(1E-6, f, 0.5, false, x0);
	}
}
