package hr.fer.zemris.apr;

public class CetvrtiZadatakSustav implements IFunctionSystem {
	private int timesCalled = 0;
	private int timesJacobianCalled = 0;
	private int timesGCalled = 0;
	
	@Override
	public double at(double... x) {
		double x1 = x[0];
		double x2 = x[1];
		timesCalled++;
		return Math.pow((Math.pow(x1, 2) + Math.pow(x2, 2) - 1), 2) + Math.pow((x2 - Math.pow(x1, 2)), 2);
	}

	@Override
	public Matrica getJacobian(double... x) {
		double x1 = x[0];
		double x2 = x[1];
		
		double[][] jacobianArray = {
				{2*x1, 2*x2},
				{-2*x1, 1}
		};
		timesJacobianCalled++;
		return new Matrica(jacobianArray);
	}

	@Override
	public Matrica getG(double... x) {
		double x1 = x[0];
		double x2 = x[1];
		
		double[][] gArray = {
				{Math.pow(x1, 2) + Math.pow(x2, 2) - 1},
				{x2 - Math.pow(x1, 2)}
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
