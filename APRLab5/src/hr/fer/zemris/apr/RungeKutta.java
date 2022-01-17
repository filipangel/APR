package hr.fer.zemris.apr;

import java.util.ArrayList;

public class RungeKutta implements IIntegration {
	private Matrica A;
	private Matrica B;
	private boolean r;
	private Matrica x0;
	private double T;
	private double tmax;
	
	public RungeKutta(Matrica a, Matrica b, boolean r, Matrica x0, double t, double tmax) {
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
		
		double diff = 0;
		
		for(double t = T; t <= tmax; t += T) {
			Matrica xNew = null;
			Matrica m1, m2, m3, m4;
			
			if(B != null) {
				double[][] rdata1 = new double[2][1];
				double[][] rdata2 = new double[2][1];
				double[][] rdata3 = new double[2][1];
				if(r) {
					rdata1 = new double[][] {{1}, {1}};
					rdata2 = new double[][] {{1}, {1}};
					rdata3 = new double[][] {{1}, {1}};
				} else {
					rdata1 = new double[][] {{t}, {t}};
					rdata2 = new double[][] {{t + T/2}, {t + T/2}};
					rdata3 = new double[][] {{t + T}, {t + T}};
				}
				Matrica r1 = new Matrica(rdata1);
				Matrica r2 = new Matrica(rdata2);
				Matrica r3 = new Matrica(rdata3);
				
				m1 = A.multiply(x).add(B.multiply(r1));
				m2 = A.multiply(x.add(m1.scalarMultiply(T/2))).add(B.multiply(r2));
				m3 = A.multiply(x.add(m2.scalarMultiply(T/2))).add(B.multiply(r2));
				m4 = A.multiply(x.add(m3.scalarMultiply(T))).add(B.multiply(r3));
				
				xNew = x.add(m1.add(m2.scalarMultiply(2)).add(m3.scalarMultiply(2)).add(m4).scalarMultiply(T/6));		
			} else {
				m1 = A.multiply(x);
				m2 = A.multiply(x.add(m1.scalarMultiply(T/2)));
				m3 = A.multiply(x.add(m2.scalarMultiply(T/2)));
				m4 = A.multiply(x.add(m3.scalarMultiply(T)));
				
				xNew = x.add(m1.add(m2.scalarMultiply(2)).add(m3.scalarMultiply(2)).add(m4).scalarMultiply(T/6));
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
