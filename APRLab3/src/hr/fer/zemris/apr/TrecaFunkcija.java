package hr.fer.zemris.apr;

public class TrecaFunkcija implements IFunction {

	private int timesCalled = 0;
	
	@Override
	public double at(double... x) {
		double x1 = x[0];
		double x2 = x[1];
		
		timesCalled++;
		return Math.pow(x1 - 2, 2) + Math.pow(x2 + 3, 2);
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
				{2 * x1 - 4},
				{2 * x2 + 6}
		};
		Matrica gradient = new Matrica(gradientArray); 
		return gradient;
	}
	
}
