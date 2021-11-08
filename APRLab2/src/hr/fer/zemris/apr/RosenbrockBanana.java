package hr.fer.zemris.apr;

public class RosenbrockBanana implements IFunction {

	private int timesCalled = 0;
	
	@Override
	public double at(double... x) {
		if(x.length != 2) {
			throw new IllegalArgumentException();
		}
		double x1 = x[0];
		double x2 = x[1];
		return 100 * Math.pow(x2 - Math.pow(x1, 2), 2) + Math.pow(1 - x1,2);
	}

	@Override
	public int getTimesCalled() {
		return timesCalled;
	}
}
