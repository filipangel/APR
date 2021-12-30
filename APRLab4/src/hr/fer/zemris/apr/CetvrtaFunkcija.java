package hr.fer.zemris.apr;

public class CetvrtaFunkcija implements IFunction {
	
	@Override
	public double at(Double... x) {
		double x1 = x[0];
		double x2 = x[1];
		
		return Math.pow(x1 - 3, 2) + Math.pow(x2, 2);
	}

	@Override
	public Matrica getGradient(double ... x) {
		return null;
	}

	@Override
	public Matrica getHess(double ... x) {
		return null;
	}
	
}
