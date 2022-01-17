package hr.fer.zemris.apr;

import java.util.ArrayList;

public class Euler implements IIntegration {
	private Matrica A;
	private Matrica B;
	private boolean r;
	private Matrica x0;
	private double T;
	private double tmax;
	

	public Euler(Matrica a, Matrica b, boolean r, Matrica x0, double t, double tmax) {
		this.A = a;
		this.B = b;
		this.r = r;
		this.x0 = x0;
		this.T = t;
		this.tmax = tmax;
	}


	@Override
	public ArrayList<Matrica> solve(boolean error) {		
		ArrayList<Matrica> xValues = new ArrayList<Matrica>();
		xValues.add(x0);
		Matrica x = x0.copy();
		Matrica U = Matrica.unitMatrix(A.getRows(), A.getColumns());
		double diff = 0;
		
		for(double t = T; t <= tmax; t += T) {
			Matrica xNew = null;
			Matrica M = U.add(A.scalarMultiply(T));
			if(B != null) {
				Matrica N = B.scalarMultiply(T);
				double[][] rdata = new double[2][1];
				if(r) {
					rdata = new double[][] {{1}, {1}};
				} else {
					rdata = new double[][] {{t}, {t}};
				}
				Matrica r = new Matrica(rdata);
				xNew = M.multiply(x).add(N.multiply(r).scalarMultiply(t));
			} else {
				xNew = M.multiply(x);
			}
			
			if(error) {
				double trueX1 = x0.get(1, 1) * Math.cos(t) + x0.get(2, 1) * Math.sin(t);
				double trueX2 = x0.get(2, 1) * Math.cos(t) - x0.get(1, 1) * Math.sin(t);
				diff += (Math.abs(trueX1 - xNew.get(1, 1)) + Math.abs(trueX2 - xNew.get(2, 1)))/2;
			}
			
			xValues.add(xNew);
			x = xNew.copy();
		}		
		System.out.println("Konačni x u t = " + tmax);
		xValues.get(xValues.size() - 1).print();
		
		if(error) {
			System.out.println("Ukupna kumulativna pogreška: " + diff);
		}
		return xValues;		
	}

}
