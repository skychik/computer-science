package ru.ifmo.cs.computer_science.lab1;

public class GaussSeidel {
	private double[][] a;
	private double[] b;
	private double[] x0;
	private double e;

	public GaussSeidel(double[][] a, double[] b, double[] x0, double e) throws Exception {
		check(a, b, x0, e);
		this.a = a;
		this.b = b;
		this.x0 = x0;
		this.e = e;
	}

//	public GaussSeidel(double[][] a, double[] b) throws Exception {
//		double[] x0 = new double[a.length];
//		for (int i = 0; i < a.length; i++) {
//			x0[i] = 0.0;
//		}
//		check(a, b, x0, 0);
//		this.a = a;
//		this.b = b;
//		this.x0 = x0;
//		this.e = 0;
//	}

	// n<=20
	// e - точность
	public double[] gaussSeidel() {
		// TODO: если есть нули в главной диагонали, переставить, запомнить перераспределение иксов

		int size = a.length;
		double[] guessX = x0.clone();
		double[] previousGuessX;

		try {
			do {
				previousGuessX = guessX;
				for (int i = 0; i < size; i++) {
					double sum = 0.0;
					for (int j = 0; j < size; j++) {
						if (i != j) {
							sum += a[i][j]* guessX[j];
						}
					}
					guessX[i] = (b[i] - sum) / a[i][i];
				}
			} while (!convergence(previousGuessX, guessX, e));
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}

		return guessX.clone();
	}

	private void check(double[][] a, double[] b, double[] x0, double e) throws Exception {
		if (a.length != b.length || b.length != x0.length || a.length > 20 ||  e < 0.0)
			throw new Exception("Wrong arguments");
	}

	private boolean convergence(double[] previousGuessX, double[] guessX, double e) throws Exception {
		if (previousGuessX.length != guessX.length) throw new Exception();
		for (int i = 0; i < guessX.length; i++) {
			if (Math.abs(guessX[i] - previousGuessX[i]) > e)
				return false;
		}
		return true;
	}

//	public boolean isPredominance(double[][] a) {
//		double[][] b = new double[a.length][a.length];
//		for (int i = 0; i < a.length; i++) {
//			double s = 0;
//			boolean f = false;
//			for (double k1=-5; k1<=10; k1++) {
//				for (double k2=-5; k2<=10; k2++) {
//					for (double k3=-5; k3<=10; k3++) {
//						for (double k4=-5; k4<=10; k4++) {
//							s = 0;
//							for (int j = 0; j < a.length; j++) {
//								b[i][j] = k1 * a[0][j] + k2 * a[1][j] + k3 * a[2][j] + k4 * a[3][j];
//								if (j != i)
//									s += Math.abs(b[i][j]);
//							}
//							if (Math.abs(b[i][i]) > s) {
//								k[i][0]=k1;k[i][1]=k2;k[i][2]=k3;k[i][3]=k4;
//								f=true;
//
//								for (int j = 0; j < a.length; j++) {
//									cout<<b[i][j]<<"\t";
//								}
//								cout<<endl;
//								break;
//							}
//						}
//						if (f) break;
//					}
//					if (f) break;
//				}
//				if (f) break;
//			}
//		}
//	}

	public static boolean isDiagDomin(double[][] a) {
		for (int i = 0; i < a.length; i++) {
			double sum = 0;
			for (int j = 0; j < a.length; j++) {
				sum += Math.abs(a[i][j]);
			}
			if (Math.abs(a[i][i]) < Math.abs(sum-Math.abs(a[i][i]))) {
				return true;
			}
		}
		return false;
	}

	public double[][] getA() {
		return a;
	}

	public double[] getB() {
		return b;
	}

	public double[] getX0() {
		return x0;
	}

	public double getE() {
		return e;
	}
}
