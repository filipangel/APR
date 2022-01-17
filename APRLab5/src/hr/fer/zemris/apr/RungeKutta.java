package hr.fer.zemris.apr;

import java.util.ArrayList;

public class RungeKutta implements IIntegration {
	private Matrica A;
	private Matrica B;
	private boolean r;
	private Matrica x0;
	private double T;
	private double tmax;
	
	@Override
	public ArrayList<Matrica> solve(boolean error) {
		return null;
		// TODO Auto-generated method stub
		
	}

	public RungeKutta(Matrica a, Matrica b, boolean r, Matrica x0, double t, double tmax) {
		super();
		A = a;
		B = b;
		this.r = r;
		this.x0 = x0;
		T = t;
		this.tmax = tmax;
	}

}
