package hr.fer.zemris.apr;

public interface IFunction {
	public double at(Double ... x);
	public Matrica getGradient(double ... x);
	public Matrica getHess(double ... x);
}