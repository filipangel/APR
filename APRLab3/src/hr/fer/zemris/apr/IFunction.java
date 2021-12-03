package hr.fer.zemris.apr;

public interface IFunction {
	public double at(double ... x);
	public int getTimesCalled();
	public void reset();
	public Matrica getGradient(double ... x);
}
