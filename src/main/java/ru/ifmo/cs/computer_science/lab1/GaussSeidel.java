package java.ru.ifmo.cs.computer_science.lab1;

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

	public GaussSeidel(double[][] a, double[] b) throws Exception {
		double[] x0 = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			x0[i] = 0.0;
		}
		check(a, b, x0, 0);
		this.a = a;
		this.b = b;
		this.x0 = x0;
		this.e = 0;
	}

	// n<=20
	// e - точность
	public double[] gaussSeidel() {
		// если есть нули в главной диагонали, переставить, запомнить перераспределение иксов

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
					guessX[i] = (b[i] - sum)/a[i][i];
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
