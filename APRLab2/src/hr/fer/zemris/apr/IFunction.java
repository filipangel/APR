package hr.fer.zemris.apr;

public interface IFunction {
	public double at(double ... x);
	public int getTimesCalled();
}
