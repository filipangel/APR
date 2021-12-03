package hr.fer.zemris.apr;

public class PrvaFunkcija implements IFunction {

	private int timesCalled = 0;
	
	@Override
	public double at(double... x) {
		if(x.length != 2) {
			throw new IllegalArgumentException();
		}
		timesCalled++;
		double x1 = x[0];
		double x2 = x[1];
		return 100 * Math.pow(x2 - Math.pow(x1, 2), 2) + Math.pow(1 - x1,2);
	}

	@Override
	public int getTimesCalled() {
		return timesCalled;
	}
	
	@Override
	public void reset() {
		timesCalled = 0;
	}

	@Override
	public Matrica getGradient(double ... values) {
		double x1 = values[0];
		double x2 = values[1];
		
		double[][] gradientArray = {
				{-400 * x1 * (x2 - x1*x1) - 2 * (1 - x1)},
				{200 * (x2 - x1*x1)}
		};
		Matrica gradient = new Matrica(gradientArray); 
		return gradient;
	}

}
