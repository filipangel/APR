package hr.fer.zemris.apr;

public class PrvaFunkcija implements IFunction {

	private int timesCalled = 0;
	private int timesGradientCalled = 0;
	private int timesHessCalled = 0;
	
	@Override
	public double at(double ... x) {
		if(x.length != 2) {
			throw new IllegalArgumentException();
		}
		timesCalled++;
		double x1 = x[0];
		double x2 = x[1];
		return 100 * Math.pow(x2 - Math.pow(x1, 2), 2) + Math.pow(1 - x1,2);
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
				{-400 * x1 * (x2 - Math.pow(x1, 2)) - 2 * (1 - x1), 200 * (x2 - Math.pow(x1, 2))}
		};
		
		timesGradientCalled++;
		return new Matrica(gradientArray);
	}

	@Override
	public Matrica getHess(double ... x) {
		if(x.length != 2) {
			throw new IllegalArgumentException();
		}
		double x1 = x[0];
		double x2 = x[1];
		
		double[][] hessArray = {
				{(-400 * (x2 - Math.pow(x1, 2)) + 800 * Math.pow(x1, 2) + 2), (-400 * x1)},
				{(-400 * x1), (200)}
		};
		timesHessCalled++;
		return new Matrica(hessArray);
	}

}
