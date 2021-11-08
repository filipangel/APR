package hr.fer.zemris.apr;

public class JakobovicFunkcija implements IFunction {
	private int timesCalled = 0;
	
	@Override
	public double at(double... x) {
		if(x.length != 2) {
			throw new IllegalArgumentException();
		}
		timesCalled++;
		double x1 = x[0];
		double x2 = x[1];
		return Math.abs((x1 - x2)*(x1+x2)) + Math.sqrt(Math.pow(x1, 2) + Math.pow(x2, 2));
	}

	@Override
	public int getTimesCalled() {
		return timesCalled;
	}
}
