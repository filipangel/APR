package hr.fer.zemris.apr;

public class DrugaFunkcija implements IFunction {
	private int timesCalled = 0;
	
	@Override
	public double at(double... x) {
		if(x.length != 2) {
			throw new IllegalArgumentException();
		}
		timesCalled++;
		double x1 = x[0];
		double x2 = x[1];
		return Math.pow(x1 - 4, 2) + 4 * Math.pow(x2 - 2, 2);
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
	public Matrica getGradient(double... x) {
		double x1 = x[0];
		double x2 = x[1];
		
		double[][] gradientArray = {
				{2 * x1 - 8},
				{8 * x2 - 16}
		};
		Matrica gradient = new Matrica(gradientArray); 
		return gradient;
	}
}
