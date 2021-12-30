package hr.fer.zemris.apr;

public class h1 implements IFunction {

	@Override
	public double at(Double... x) {
		Double x2 = x[1];
		return x2 - 1;
	}

	@Override
	public Matrica getGradient(double... x) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrica getHess(double... x) {
		// TODO Auto-generated method stub
		return null;
	}

}
