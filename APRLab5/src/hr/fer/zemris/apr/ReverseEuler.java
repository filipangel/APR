package hr.fer.zemris.apr;

import java.util.ArrayList;

public class ReverseEuler implements IIntegration {
	private Matrica A;
	private Matrica B;
	private boolean r;
	private Matrica x0;
	private double T;
	private double tmax;
	
	public ReverseEuler(Matrica a, Matrica b, boolean r, Matrica x0, double t, double tmax) {
		super();
		A = a;
		B = b;
		this.r = r;
		this.x0 = x0;
		T = t;
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
			Matrica P = U.subtract(A.scalarMultiply(T)).inv();
			if(B != null) {
				double[][] rdata = new double[2][1];
				if(r) {
					rdata = new double[][] {{1}, {1}};
				} else {
					rdata = new double[][] {{t + T}, {t + T}};
				}
				Matrica r = new Matrica(rdata);
				Matrica Q = U.subtract(A.scalarMultiply(T)).inv().scalarMultiply(T).multiply(B);
				xNew = P.multiply(x).add(Q.multiply(r));
			} else {
				xNew = P.multiply(x);
			}
			
			if(error) {
				double trueX1 = x0.get(1, 1) * Math.cos(t) + x0.get(2, 1) * Math.sin(t);
				double trueX2 = x0.get(2, 1) * Math.cos(t) - x0.get(1, 1) * Math.sin(t);
				diff += Math.abs(trueX1 - xNew.get(1, 1)) + Math.abs(trueX2 - xNew.get(2, 1));
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
