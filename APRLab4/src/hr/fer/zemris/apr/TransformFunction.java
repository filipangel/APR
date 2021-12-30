package hr.fer.zemris.apr;

import java.util.ArrayList;

public class TransformFunction implements IFunction {
	private IFunction f;
	private ArrayList<IFunction> gs;
	private ArrayList<IFunction> hs;
	private double t;
	
	public TransformFunction(IFunction f, ArrayList<IFunction> gs, ArrayList<IFunction> hs) {
		this.f = f;
		this.gs = gs;
		this.hs = hs;
	}
	
	public void setT(double t) {
		this.t = t;
	}
	
	@Override
	public double at(Double ... x) {
		double F = f.at(x);
		if(gs.size() > 0) {
			for(IFunction g : gs) {
				if(g.at(x) < 0) {
					return Double.POSITIVE_INFINITY;
				} else {
					F = F - (1/t) * Math.log(g.at(x));
				}
			}
		}
		if(hs.size() > 0) {
			for(IFunction h : hs) {
				F = F + t * Math.pow(h.at(x), 2);
			}
		}
		return F;
	}

	@Override
	public Matrica getGradient(double... x) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrica getHess(double... x) {
		// TODO Auto-generated method stub
		return null;
	}

}
