package hr.fer.zemris.apr;

import java.util.Arrays;

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
				};
				x[i] = zlatniRez2(1, 0, e, f_temp, print);
			}
			if(print) System.out.println("Iteracija br. " + iterCount++ + " trenutni minimum: " + Arrays.toString(x));
		} while(diff(x, xs) > e);
		
		System.out.println("Minimum " + Arrays.toString(x) + " pronaden pretrazivanjem po koordinatnim osima. Poziva funkcije f(x):" + f.getTimesCalled());
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
		System.out.println("Metodom Hooke-Jeeves pronaden je minimum " + Arrays.toString(xb) + ". Potrebno poziva f(x): " + f.getTimesCalled());
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
	
	public static double[] Simplex(double alpha, double beta, double gamma, double e, IFunction f, boolean print, double ... x0) {
		
		return null;
	}
}
