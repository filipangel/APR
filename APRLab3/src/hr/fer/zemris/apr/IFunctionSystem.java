package hr.fer.zemris.apr;

public interface IFunctionSystem {
	public double at(double ... x);
	public Matrica getJacobian(double ... x);
	public Matrica getG(double ... x);
	public int[] getTimesCalled();
	public void reset();
}
