package hr.fer.zemris.apr;

public class g2 implements IFunction {

	@Override
	public double at(Double... x) {
		double x1 = x[0];
		double x2 = x[1];
		return 2 - x1;
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
