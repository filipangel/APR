package hr.fer.zemris.apr;

public class TreciZadatakSustav implements IFunctionSystem {
	private int timesCalled = 0;
	private int timesJacobianCalled = 0;
	private int timesGCalled = 0;
	
	@Override
	public double at(double... x) {
		double x1 = x[0];
		double x2 = x[1];
		timesCalled++;
		return 100 * Math.pow((x2 - Math.pow(x1, 2)), 2) + Math.pow((1 - x1), 2);
	}

	@Override
	public Matrica getJacobian(double... x) {
		double x1 = x[0];
		double x2 = x[1];
		
		double[][] jacobianArray = {
				{-20 * x1, 10},
				{-1, 0}
		};
		timesJacobianCalled++;
		return new Matrica(jacobianArray);
	}

	@Override
	public Matrica getG(double... x) {
		double x1 = x[0];
		double x2 = x[1];
		
		double[][] gArray = {
				{10 * (x2 - Math.pow(x1, 2))},
				{1 - x1}
		};
		timesGCalled++;
		return new Matrica(gArray);
	}
	
	public void reset() {
		timesCalled = 0;
		timesJacobianCalled = 0;
		timesGCalled = 0;
	}

	public int[] getTimesCalled() {
		int[] timesCalledArray = {timesCalled, timesJacobianCalled, timesGCalled};
		return timesCalledArray;
	}
}
