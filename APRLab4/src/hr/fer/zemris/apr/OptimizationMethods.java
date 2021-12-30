package hr.fer.zemris.apr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class OptimizationMethods {
	
	public static Double[] PenaltyBarrier(TransformFunction f, ArrayList<IFunction> gs, double t0, double eps, Double ... x0) {
		Double[] x = Arrays.copyOf(x0, x0.length);
		if(!constraintsSatisfied(x, gs)) {
			System.out.println("Početna točka ne zadovoljava uvjete! Tražim točku unutar ograničenja...");
			x = insidePoint(gs, eps, x);
			System.out.println("Pronađena nova početna točka: " + Arrays.toString(x));
		}
		double t = t0;
		f.setT(t);
		double F = f.at(x);
		Double[] xs = Arrays.copyOf(x, x.length);
		
		do {
			xs = Arrays.copyOf(x, x.length);
			x = hookeJeeves(eps, f, 0.5, xs);
			t *= 10;
			f.setT(t);			
		} while(Math.abs(diff(xs, x)) > eps);
		return x;
	}
	
	private static Double[] insidePoint(ArrayList<IFunction> gs, double eps, Double[] x0) {
		Double[] x = Arrays.copyOf(x0, x0.length);
		Double[] xs = Arrays.copyOf(x, x.length);
		do {
			xs = Arrays.copyOf(x, x.length);
			x = hookeJeeves(eps, new IFunction() {

				@Override
				public double at(Double... x) {
					double G = 0;
					for(IFunction g : gs) {
						if(g.at(x) < 0) G = G - g.at(x);
					}
					return G;
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
				
			}, 0.5, x);
		} while(Math.abs(diff(xs, x)) > eps);
		return x;
	}

	public static Double[] hookeJeeves(double e, IFunction f, double dx, Double ... x0) {
		Double[] xb = Arrays.copyOf(x0, x0.length);
		Double[] xp = Arrays.copyOf(x0, x0.length);
		Double[] xn = Arrays.copyOf(x0, x0.length);
		
		do {
			xn = explore(xp, dx, f);
			if(f.at(xn) < f.at(xb)) {
				for(int i = 0; i < xn.length; i++) {
					xp[i] = 2*xn[i] - xb[i];
				}
				xb = Arrays.copyOf(xn, xn.length);
			} else {
				dx /= 2;
				xp = Arrays.copyOf(xb, xb.length);
			}
		} while(dx > e);
		return xb;
	}
	
	public static Double[] explore(Double[] xp, double dx, IFunction f) {
		int n = xp.length;
		Double[] x = Arrays.copyOf(xp, n);
		for(int i = 0; i < n; i++) {
			double P = f.at(x);
			x[i] += dx;
			double N = f.at(x);
			if(N > P) {
				x[i] -= 2*dx;
				N = f.at(x);
				if(N > P) {
					x[i] += dx;
				}
			}
		}
		return x;
	}
	
	public static Double[] Box(IFunction f, ArrayList<IFunction> g, double alpha, double eps, double lowerBound, double upperBound, Double ... xStart) {
		int n = xStart.length;
		Double[] xc = Arrays.copyOf(xStart, n);
		ArrayList<Double[]> simplex = new ArrayList<Double[]>();
		simplex.add(xStart);
		
		for(int t = 0; t < 2 * n; t++) {
			Double[] x = new Double[n];
			for(int i = 0; i < n; i++) {
				double R = Math.random();
				x[i] = lowerBound + R * (upperBound - lowerBound);
			}							
			simplex.add(x);			
		}
		
		for(Double[] x : simplex) {
			while(!constraintsSatisfied(x, g)) {
				for(int i = 0; i < n; i++) {
					x[i] = 0.5 * (x[i] + xc[i]);
				}
			}
		}
		
		int divergence = 0;
		double bestF = f.at(simplex.get(0));
		
		do {
			simplex.sort(simplexComp(f));
			
			xc = findCentroid(simplex);
			
			Double[] xr = reflection(xc, simplex, alpha);
			
			for(int i = 0; i < n; i++) {
				if(xr[i] < lowerBound) xr[i] = lowerBound;
				else if(xr[i] > upperBound) xr[i] = upperBound;
			}
			
			while(!constraintsSatisfied(xr, g)) {
				for(int i = 0; i < n; i++) {
					xr[i] = 0.5 * (xr[i] + xc[i]);
				}
			}
			
			if(f.at(xr) > f.at(simplex.get(simplex.size() - 2))) {
				for(int i = 0; i < n; i++) {
					xr[i] = 0.5 * (xr[i] + xc[i]);
				}
			}
			simplex.remove(simplex.size() - 1);
			simplex.add(xr);
			
			if(Math.abs(f.at(simplex.get(0)) - bestF) < eps) {
				divergence++;
			} else {
				divergence = 0;
			}
			if(divergence == 200) {
				return simplex.get(0);
			}
			
			bestF = f.at(simplex.get(0));
		} while(diff(simplex.get(0), simplex.get(simplex.size() - 1)) > eps);
		return simplex.get(0);
	}

	private static boolean constraintsSatisfied(Double[] x, ArrayList<IFunction> gs) {
		for(IFunction g : gs) {
			if(g.at(x) < 0) return false;
		}
		return true;
	}

	private static double diff(Double[] x1, Double[] x2) {
		int n = x1.length;
		double diff = 0;
		for(int i = 0; i < n; i++) {
			diff += Math.abs(x1[i] - x2[i]);
		}
		return diff;
	}
	
	private static Double[] findCentroid(ArrayList<Double[]> simplex) {
		int n = simplex.size();
		Double[] centroid = Arrays.copyOf(simplex.get(0), simplex.get(0).length);
		for(int i = 0; i < centroid.length; i++) {
			centroid[i] = 0.0;
		}
		for(int i = 0; i < simplex.size() - 1; i++) {
			for(int j = 0; j < centroid.length; j++) {
				centroid[j] += simplex.get(i)[j];
			}
		}
		for(int i = 0; i < centroid.length; i++) {
			centroid[i] /= simplex.size() - 1;
		}
		return centroid;
	}
	
	public static Comparator<Double[]> simplexComp(IFunction f){
		return new Comparator<Double[]>() {

			@Override
			public int compare(Double[] x1, Double[] x2) {
				double f1 = f.at(x1);
				double f2 = f.at(x2);
				if(f1 < f2) {
					return -1;
				} else if (f1 == f2) {
					return 0;
				} else {
					return 1;
				}
			}
			
		};
	}

	private static Double[] reflection(Double[] xc, ArrayList<Double[]> simplex, double alpha) {
		Double[] xr = new Double[xc.length];
		
		for(int i = 0; i < xc.length; i++) {
			xr[i] = (1 + alpha) * xc[i] - alpha * simplex.get(simplex.size() - 1)[i];
		}
		return xr;
	}
}
