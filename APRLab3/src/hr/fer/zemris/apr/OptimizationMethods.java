package hr.fer.zemris.apr;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class OptimizationMethods {
	
	public static double[] GaussNewton(boolean useGoldenSection, double eps, IFunctionSystem s, boolean print, double ... x0) {
		double[] x = Arrays.copyOf(x0, x0.length);
		Matrica J = s.getJacobian(x);
		Matrica G = s.getG(x);
		Matrica g = J.transpose().multiply(G);
		double lambda = 1;
		
		do {
			J = s.getJacobian(x);
			G = s.getG(x);
			
			Matrica A = J.transpose().multiply(J);
			g = J.transpose().multiply(G);
			
			if(A.inv() == null) {
				System.out.println("Nemoguće pronaći inverz matrice A.");
				return x0;
			}			
			
			Matrica dx = A.inv().scalarMultiply(-1).multiply(g).transpose();
			
			if(useGoldenSection) {
				final double[] xFinal = Arrays.copyOf(x, x.length);
				final IFunctionSystem sFinal = s; 
				
				lambda = goldenSectionSearchUnimodal(1, 0, eps, new IFunction() {

					@Override
					public double at(double... x) {
						double l = x[0];
						double[] x0 = Arrays.copyOf(xFinal, xFinal.length);
						
						for(int i = 0; i < x0.length; i++) {
							x0[i] += l * dx.get(0, i);
						}												
						return sFinal.at(x0);
					}

					@Override
					public int[] getTimesCalled() {
						return null;
					}

					@Override
					public void reset() {
					}

					@Override
					public Matrica getGradient(double... x) {
						return null;
					}

					@Override
					public Matrica getHess(double... x) {
						return null;
					}					
				}, false);
			}
			
			for(int i = 0; i < x.length; i++) {
				x[i] += lambda * dx.get(0, i);
			}
			
		} while(g.vectorNorm() > eps);
		
		return x;
	}

	public static double[] NewtonRaphson(boolean useGoldenSection, double eps, IFunction f, boolean print, double ... x0) {
		double[] x = Arrays.copyOf(x0, x0.length);
		int divCounter = 0;
		double norm = 0;
		do {
			double lambda = -1;
			double prevNorm = norm;
			
			Matrica gradient = f.getGradient(x);
			Matrica hess = f.getHess(x);			
			
			if(useGoldenSection) {
				final IFunction fFinal = f;
				final double[] xFinal = Arrays.copyOf(x, x.length);
				final Matrica gradientFinal = gradient.copy();
				final Matrica hessFinal = hess.copy();
				
				lambda = goldenSectionSearchUnimodal(1, 0, eps, new IFunction() {

					@Override
					public double at(double... x) {
						double l = x[0];
						double[] x0 = Arrays.copyOf(xFinal, xFinal.length);
						
						Matrica negativeH = hessFinal.inv().scalarMultiply(-1);
						Matrica dx = negativeH.multiply(gradientFinal.transpose()).transpose();
						
						for(int i = 0; i < x0.length; i++) {
							x0[i] = x0[i] - l * dx.get(0, i);
						}
						return fFinal.at(x0);
					}

					@Override
					public int[] getTimesCalled() {
						return null;
					}

					@Override
					public void reset() {						
					}

					@Override
					public Matrica getGradient(double... x) {
						return null;
					}

					@Override
					public Matrica getHess(double... x) {
						return null;
					}
					
				}, false);				
			}			
			
			Matrica negativeH = hess.inv().scalarMultiply(-1);
			Matrica dx = negativeH.multiply(gradient.transpose()).transpose();
			
			for(int i = 0; i < x.length; i++) {
				x[i] -= lambda * dx.get(0, i);
			}
			
			norm = dx.vectorNorm();
			
			if(Math.abs(prevNorm - norm) < eps) {
				divCounter++;
			}
			if(divCounter >= 100) {
				if(print) System.out.print("Newton-Raphson postupak je divergirao. Zadnje pronađeno rješenje je: " + stringX(x, 8) + "\n");
				return x;
			}			
		} while(norm > eps);
		if(print) System.out.print("Newton-Raphson postupak je uspješno pronašao rješenje. Točka minimuma: " + stringX(x, 8) + "\n");
		return x;			
	}
	
	public static double[] GradientDescent(boolean useGoldenSection, double eps, IFunction f, boolean print, double ... x0) {		
		double[] x = Arrays.copyOf(x0, x0.length);
		int divCounter = 0;
		double norm = f.getGradient(x).vectorNorm();;
		do {
			double lambda = -1;
			double prevNorm = norm;
			
			if(useGoldenSection) {
				final IFunction fFinal = f;
				final double[] xFinal = Arrays.copyOf(x, x.length);
				final Matrica gradient = f.getGradient(x);
				
				lambda = goldenSectionSearchUnimodal(1, 0, eps, new IFunction() {

					@Override
					public double at(double ... x) {
						double[] x0 = Arrays.copyOf(xFinal, xFinal.length);
						double l = x[0];
						for(int i = 0; i < x0.length; i++) {
							x0[i] = x0[i] + l * gradient.get(0, i);
						}						
						return fFinal.at(x0);
					}

					@Override
					public int[] getTimesCalled() {
						return null;
					}

					@Override
					public void reset() {						
					}

					@Override
					public Matrica getGradient(double ... x) {
						return null;
					}

					@Override
					public Matrica getHess(double ... x) {
						return null;
					}
					
				}, false);
			}		
			
			if(print) System.out.print("x = " + stringX(x, 15) + " lambda = " + lambda);
			
			Matrica dx = f.getGradient(x).scalarMultiply(lambda);			
			
			for(int i = 0; i < x.length; i++) {
				x[i] += dx.get(0, i);
			}
			
			if(print) System.out.println(" novi x = " + stringX(x, 15));
			
			norm = f.getGradient(x).vectorNorm();
			
			if(Math.abs(prevNorm - norm) < eps) {
				divCounter++;
			}
			if(divCounter >= 100) {
				if(print) System.out.print("Algoritam gradijentnog spusta je divergirao. Zadnje pronađeno rješenje je: " + stringX(x, 8) + "\n");
				return x;
			}		
			
		} while(norm > eps);
		if(print) System.out.print("Algoritam gradijentnog spusta je uspješno pronašao rješenje. Točka minimuma: " + stringX(x, 8) + "\n");
		return x;		
	}
	
	public static String stringX(double[] x, int precision) {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(int i = 0; i < x.length - 1; i++) {
			sb.append(String.format("%." + precision +"f", x[i]) + ", ");
		}
		sb.append(String.format("%." + precision + "f", x[x.length - 1]));
		sb.append(")");
		return sb.toString();		
	}

	public static double goldenSectionSearch(double a, double b, double e, IFunction f, boolean print) {
		double k = 0.5 * (Math.sqrt(5) - 1);
		double c = b - k * (b - a);
		double d = a + k * (b - a);
		double fc = f.at(c);
		double fd = f.at(d);
		
		while((b - a) > e) {
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
		return (a + b) / 2;
	}
	
	public static double[] findUnimodalInterval(double h, double tocka, IFunction f, boolean print) {
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
	
	public static double goldenSectionSearchUnimodal(double h, double tocka, double e, IFunction f, boolean print) {
		double[] lr = findUnimodalInterval(h, tocka, f, print);
		double a = lr[0];
		double b = lr[1];
		
		return goldenSectionSearch(a, b, e, f, print);
	}
	
	
}
