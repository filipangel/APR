package hr.fer.zemris.apr;

import java.util.ArrayList;

public class Trapese implements IIntegration {
	private Matrica A;
	private Matrica B;
	private boolean r;
	private Matrica x0;
	private double T;
	private double tmax;
	
	@Override
	public ArrayList<Matrica> solve(boolean error) {
		ArrayList<Matrica> xValues = new ArrayList<Matrica>();
		xValues.add(x0);
		
		Matrica x = x0.copy();
		Matrica U = Matrica.unitMatrix(A.getRows(), A.getColumns());
		
		double diff = 0;
		
		for(double t = T; t <= tmax; t += T) {
			Matrica xNew = null;
			Matrica R = U.subtract(A.scalarMultiply(T/2)).inv().multiply(U.add(A.scalarMultiply(T/2)));
			if(B != null) {
				double[][] rdata1 = new double[2][1];
				double[][] rdata2 = new double[2][1];
				if(r) {
					rdata1 = new double[][] {{1}, {1}};
					rdata2 = new double[][] {{1}, {1}};
				} else {
					rdata1 = new double[][] {{t}, {t}};
					rdata2 = new double[][] {{t + T}, {t + T}};
				}
				Matrica r1 = new Matrica(rdata1);
				Matrica r2 = new Matrica(rdata2);
				Matrica S = U.subtract(A.scalarMultiply(T/2)).inv().scalarMultiply(T/2).multiply(B);
				xNew = R.multiply(x).add(S.multiply(r1.add(r2)));
			} else {
				xNew = R.multiply(x);
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

	public Trapese(Matrica a, Matrica b, boolean r, Matrica x0, double t, double tmax) {
		super();
		A = a;
		B = b;
		this.r = r;
		this.x0 = x0;
		T = t;
		this.tmax = tmax;
	}

}
