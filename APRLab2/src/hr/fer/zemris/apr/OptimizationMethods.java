package hr.fer.zemris.apr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class OptimizationMethods {
	public static double zlatniRez(double a, double b, double e, IFunction f, boolean print) {
		double k = 0.5 * (Math.sqrt(5) - 1);
		double c = b - k * (b - a);
		double d = a + k * (b - a);
		double fc = f.at(c);
		double fd = f.at(d);
		
		while(Math.abs(b - a) > e) {
			if(fc < fd) {
				b = d;
				d = c;
				c = b - k * (b - a);
				fd = fc;
				fc = f.at(c);
			} else {
				a = c;
				c = d;
				d = a + k * (b - a);
				fc = fd;
				fd = f.at(d);
			}
		}
		return (a+b)/2;
	}
	
	public static double[] unimodalInterval(double h, double tocka, IFunction f, boolean print) {
		double l = tocka - h;
		double r = tocka + h;
		double m = tocka;
		double fl, fm, fr;
		int step = 1;
		
		fm = f.at(tocka);
		fl = f.at(l);
		fr = f.at(r);
		
		if(fm < fr && fm < fl) {
			double[] lr = {l, r};
			return lr;
		}
		else if(fm > fr) {
			do {
				l = m; 
				m = r;
				fm = fr;
				r = tocka + h * (step *= 2);
				fr = f.at(r);
			} while(fm > fr);
		} else {
			do {
				r = m;
				m = l;
				fm = fl;
				l = tocka - h * (step *= 2);
				fl = f.at(l);
			} while(fm > fl);
		}
		double[] lr = {l, r};
		return lr;
	}
	
	public static double zlatniRez2(double h, double tocka, double e, IFunction f, boolean print) {
		double[] lr = unimodalInterval(h, tocka, f, print);
		double a = lr[0];
		double b = lr[1];
		
		return zlatniRez(a, b, e, f, print);
	}
	
	public static double[] coordinateSearch(double e, IFunction f, boolean print, double ... x0) {
		double[] x = Arrays.copyOf(x0, x0.length);
		if(print) System.out.println("Pozvano trazenje po koordinatnim osima za pocetnu tocku: " + Arrays.toString(x0));
		double[] xs;
		int iterCount = 1;
		do {
			xs = Arrays.copyOf(x, x.length);
			for(int i = 0; i < x0.length; i++) {
				final double[] xFinal = xs;
				final int iFinal = i;
				IFunction f_temp = new IFunction() {

					@Override
					public double at(double... x) {
						double[] xNew = Arrays.copyOf(xFinal, xFinal.length);
						xNew[iFinal] = x[0];
						return f.at(xNew);
					}

					@Override
					public int getTimesCalled() {
						return 0;
					}
					
					@Override
					public void reset() {
					}
				};
				x[i] = zlatniRez2(1, 0, e, f_temp, print);
			}
			if(print) System.out.println("Iteracija br. " + iterCount++ + " trenutni minimum: " + Arrays.toString(x));
		} while(diff(x, xs) > e);
		
		if(print) System.out.println("Minimum " + Arrays.toString(x) + " pronaden pretrazivanjem po koordinatnim osima. Poziva funkcije f(x):" + f.getTimesCalled());
		return x;
	}
	
	public static double diff(double[] first, double[] second) {
		int n = first.length;
		double diff = 0;
		for(int i = 0; i < n; i++) {
			diff += Math.abs(first[i] - second[i]);
		}
		return diff;
	}
	
	public static double[] hookeJeeves(double e, IFunction f, double dx, boolean print, double ... x0) {
		double[] xb = Arrays.copyOf(x0, x0.length);
		double[] xp = Arrays.copyOf(x0, x0.length);
		double[] xn = Arrays.copyOf(x0, x0.length);
		
		do {
			xn = explore(xp, dx, f);
			if(print) System.out.println("Bazna tocka: " + Arrays.toString(xb) + " Tocka pretrazivanja: " + Arrays.toString(xp) + " Tocka dobivena pretrazivanjem: " + Arrays.toString(xn));
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
		if(print) System.out.println("Metodom Hooke-Jeeves pronaden je minimum " + Arrays.toString(xb) + ". Potrebno poziva f(x): " + f.getTimesCalled());
		return xb;
	}
	
	public static double[] explore(double[] xp, double dx, IFunction f) {
		int n = xp.length;
		double[] x = Arrays.copyOf(xp, n);
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
	
	public static double[] simplex(double alpha, double beta, double gamma, double sigma, double dx, double e, IFunction f, boolean print, double ... xStart) {
		int n = xStart.length;
		Double[] x0 = new Double[n];
		int i = 0;
		for(double x : xStart) {
			x0[i++] = Double.valueOf(x);
		}
		
		ArrayList<Double[]> simplex = new ArrayList<Double[]>();
		simplex.add(x0);
		
		for(i = 0; i < n; i++) {
			Double[] temp = Arrays.copyOf(x0, n);
			temp[i] += dx;
			simplex.add(temp);
		}
		while(diff(simplex.get(0), simplex.get(simplex.size() - 1)) > e){
			simplex.sort(simplexComp(f));
			
			Double[] xc = findCentroid(simplex);
			Double[] xr = reflection(xc, simplex, alpha);
			double fxl = getValue(simplex.get(0), f);
			if(print) System.out.println("Centroid: " + Arrays.toString(xc) + " F(xl): " + fxl);
			if(getValue(xr, f) < fxl) {
				Double[] xe = expansion(xc, xr, gamma);
				if(getValue(xe, f) < fxl) {
					simplex.set(simplex.size() - 1, xe);
				} else {
					simplex.set(simplex.size() - 1, xr);
				}
			} else {
				boolean condition = true;
				for(int j = 0; j < simplex.size() - 1; j++) {
					if(getValue(xr, f) <= getValue(simplex.get(j), f)) {
						condition = false;
					}
				}
				if(condition) {
					if(getValue(xr, f) < getValue(simplex.get(simplex.size() - 1), f)) {
						simplex.set(simplex.size() - 1, xr);
					}
					Double[] xk = contraction(xc, simplex, beta);
					if(getValue(xk, f) < getValue(simplex.get(simplex.size() - 1), f)) {
						simplex.set(simplex.size() - 1, xk);
					} else {
						moveTowardsxL(simplex, sigma);
					}
				} else {
					simplex.set(simplex.size() - 1, xr);
				}
			}
		}
		double[] solution = Stream.of(simplex.get(0)).mapToDouble(Double::doubleValue).toArray();
		if(print) System.out.println("Metodom simpleksa pronaden je minimum: " + Arrays.toString(solution) + ". Potrebno poziva funkcije: " + f.getTimesCalled());
		return solution;
	}
	
	private static void moveTowardsxL(ArrayList<Double[]> simplex, double sigma) {
		Double[] xl = simplex.get(0);
		for(Double[] x : simplex) {
			for(int i = 0; i < xl.length; i++) {
				x[i] = sigma * (x[i] + xl[i]);
			}
		}
	}

	private static Double[] contraction(Double[] xc, ArrayList<Double[]> simplex, double beta) {
		Double[] xk = new Double[xc.length];
		
		for(int i = 0; i < xc.length; i++) {
			xk[i] = (1 - beta) * xc[i] + beta * simplex.get(simplex.size() - 1)[i];
		}
		return xk;
	}

	private static Double[] expansion(Double[] xc, Double[] xr, double gamma) {
		Double[] xe = new Double[xc.length];
		
		for(int i = 0; i < xc.length; i++) {
			xe[i] = (1 - gamma) * xc[i] + gamma * xr[i];
		}
		return xe;
	}

	private static Double[] reflection(Double[] xc, ArrayList<Double[]> simplex, double alpha) {
		Double[] xr = new Double[xc.length];
		
		for(int i = 0; i < xc.length; i++) {
			xr[i] = (1 + alpha) * xc[i] - alpha * simplex.get(simplex.size() - 1)[i];
		}
		return xr;
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

	private static double diff(Double[] x1, Double[] x2) {
		int n = x1.length;
		double diff = 0;
		for(int i = 0; i < n; i++) {
			diff += Math.abs(x1[i] - x2[i]);
		}
		return diff;
	}

	public static Comparator<Double[]> simplexComp(IFunction f){
		return new Comparator<Double[]>() {

			@Override
			public int compare(Double[] x1, Double[] x2) {
				double f1 = getValue(x1, f);
				double f2 = getValue(x2, f);
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
	
	public static double getValue(Double[] x, IFunction f) {
		double[] x1 = Stream.of(x).mapToDouble(Double::doubleValue).toArray();
		return f.at(x1);
	}
}
