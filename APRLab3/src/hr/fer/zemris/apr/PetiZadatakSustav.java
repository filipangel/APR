package hr.fer.zemris.apr;

public class PetiZadatakSustav implements IFunctionSystem {
	private int timesCalled = 0;
	private int timesJacobianCalled = 0;
	private int timesGCalled = 0;
	
	@Override
	public double at(double... x) {
		double x1 = x[0];
		double x2 = x[1];
		double x3 = x[2];
		timesCalled++;
		return Math.pow((x1 * Math.exp(x2) + x3 - 3), 2)
				+ Math.pow((x1 * Math.exp(2 * x2)	+ x3 - 4), 2)
				+ Math.pow((x1 * Math.exp(3 * x2) + x3 - 4), 2)
				+ Math.pow((x1 * Math.exp(5 * x2) + x3 - 5), 2)
				+ Math.pow((x1 * Math.exp(6 * x2) + x3 - 6), 2)
				+ Math.pow((x1 * Math.exp(7 * x2) + x3 - 8), 2);
	}

	@Override
	public Matrica getJacobian(double... x) {
		double x1 = x[0];
		double x2 = x[1];
		double x3 = x[2];
		
		double[][] jacobianArray = {
				{Math.exp(x2), x1 * Math.exp(x2), 1},
				{Math.exp(2 * x2), 2 * x1 * Math.exp(2 * x2), 1},
				{Math.exp(3 * x2), 3 * x1 * Math.exp(3 * x2), 1},
				{Math.exp(5 * x2), 5 * x1 * Math.exp(5 * x2), 1},
				{Math.exp(6 * x2), 6 * x1 * Math.exp(6 * x2), 1},
				{Math.exp(7 * x2), 7 * x1 * Math.exp(7 * x2), 1}
		};
		timesJacobianCalled++;
		return new Matrica(jacobianArray);
	}

	@Override
	public Matrica getG(double... x) {
		double x1 = x[0];
		double x2 = x[1];
		double x3 = x[2];
		
		double[][] gArray = {
				{x1 * Math.exp(x2) + x3 - 3},
				{x1 * Math.exp(2 * x2) + x3 - 4},
				{x1 * Math.exp(3 * x2) + x3 - 4},
				{x1 * Math.exp(5 * x2) + x3 - 5},
				{x1 * Math.exp(6 * x2) + x3 - 6},
				{x1 * Math.exp(7 * x2) + x3 - 8}
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
		return new int[] {timesCalled, timesJacobianCalled, timesGCalled};
	}
}
