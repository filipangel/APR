package hr.fer.zemris.apr;

public class SchafferFunkcija implements IFunction {

	private int timesCalled = 0;
	
	@Override
	public double at(double... x) {
		timesCalled++;
		int N = x.length;
		double xSum = 0;
		for(int i = 0; i < N; i++) {
			xSum += Math.pow(x[i], 2);
		}		
		return 0.5 + (Math.pow(Math.sin(Math.sqrt(xSum)), 2) - 0.5)/Math.pow((1 + 0.001*xSum),2);
	}

	@Override
	public int getTimesCalled() {
		return timesCalled;
	}
	
	@Override
	public void reset() {
		timesCalled = 0;
	}
}
