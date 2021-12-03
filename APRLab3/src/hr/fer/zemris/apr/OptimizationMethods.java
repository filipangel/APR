package hr.fer.zemris.apr;

public class OptimizationMethods {

	public static double[] NewtonMethod() {
		return null;
	}
	
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
	
	
}
