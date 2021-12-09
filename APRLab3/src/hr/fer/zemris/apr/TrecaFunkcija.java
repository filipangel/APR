package hr.fer.zemris.apr;

public class TrecaFunkcija implements IFunction {

	private int timesCalled = 0;
	private int timesGradientCalled = 0;
	private int timesHessCalled = 0;
	
	@Override
	public double at(double... x) {
		double x1 = x[0];
		double x2 = x[1];
		
		timesCalled++;
		return Math.pow(x1 - 2, 2) + Math.pow(x2 + 3, 2);
	}

	@Override
	public int[] getTimesCalled() {
		return new int[] {timesCalled, timesGradientCalled, timesHessCalled};
	}

	@Override
	public void reset() {
		timesCalled = 0;	
		timesGradientCalled = 0;	
		timesHessCalled = 0;	
	}

	@Override
	public Matrica getGradient(double ... x) {
		if(x.length != 2) {
			throw new IllegalArgumentException();
		}
		double x1 = x[0];
		double x2 = x[1];
		
		double[][] gradientArray = {
				{2 * x1 - 4, 2 * x2 + 6}
		};
		Matrica gradient = new Matrica(gradientArray); 
		timesGradientCalled++;
		return gradient;
	}

	@Override
	public Matrica getHess(double ... x) {
		if(x.length != 2) {
			throw new IllegalArgumentException();
		}
		double x1 = x[0];
		double x2 = x[1];
		
		double[][] hessArray = {
				{(2), (0)},
				{(0), (2)}
		};
		timesHessCalled++;
		return new Matrica(hessArray);
	}
	
}
