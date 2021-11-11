package hr.fer.zemris.apr;

public class TrecaFunkcija implements IFunction {

	private int timesCalled = 0;
	
	@Override
	public double at(double... x) {
		timesCalled++;
		double value = 0;
		int i = 1;
		for(double xi : x) {
			value += Math.pow(xi - i, 2);
			i++;
		}
		return value;
	}

	@Override
	public int getTimesCalled() {
		return timesCalled;
	}

}
