package ru.ifmo.cs.computer_science.lab1;

public class GaussSeidel {
	public static double[] gaussSeidel(double[][] a, double[] b) throws ArithmeticException {
		double[] x0 = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			x0[i] = 0.0;
		}
		return gaussSeidel(a, b, x0, 0);
	}

	// n<=20
	// e - точность
	public static double[] gaussSeidel(double[][] a, double[] b, double[] x0, double e) {
		check(a, b, x0, e);

		// если есть нули в главной диагонали, переставить, запомнить перераспределение иксов

		int n = a.length;
		double[] guessX = x0.clone();
		double[] previousGuessX;

		do {
			previousGuessX = guessX;
			for (int i = 0; i < n; i++) {
				double sum = 0;
				for (int j = 0; j < n; j++) {
					if (i != j) {
						sum += a[i][j]* guessX[j];
					}
				}
				guessX[i] = (b[i] - sum)/a[i][i];
			}
		} while (!convergence(previousGuessX, guessX, e));

		return guessX.clone();
	}

	private static void check(double[][] a, double[] b, double[] x0, double e) throws ArithmeticException {
		if (a.length != b.length || b.length != x0.length || a.length > 20 ||  e < 0.0)
			throw new ArithmeticException("Wrong arguments");
	}

	private static boolean convergence(double[] previousGuessX, double[] guessX, double e) throws ArithmeticException {
		if (previousGuessX.length != guessX.length) throw new ArithmeticException();
		for (int i = 0; i < guessX.length; i++) {
			if (Math.abs(guessX[i] - previousGuessX[i]) > e)
				return false;
		}
		return true;
	}
}
