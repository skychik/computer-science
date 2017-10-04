package ru.ifmo.cs.computer_science.lab1;

public class GaussSeidel {
	private double[][] a;
	private double[] b;
	private double[] x0;
	private double e;
	private int iterationNumber;
	private double[] answer;

	public GaussSeidel(double[][] a, double[] b, double[] x0, double e) throws Exception {
		check(a, b, x0, e);
		this.a = a;
		this.b = b;
		this.x0 = x0;
		this.e = e;

		doGaussSeidel();
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

	public double[] getAnswer() {
		return answer;
	}

	// n<=20
	// e - точность
	private void doGaussSeidel() throws Exception{
		int size = a.length;
		double[] guessX = x0.clone();
		double[] previousGuessX;
		iterationNumber = 0;

		do {
			iterationNumber++;
			previousGuessX = guessX.clone();
			for (int i = 0; i < size; i++) {
				double sum = 0.0;
				for (int j = 0; j < size; j++) {
					if (i != j) {
						sum += a[i][j]* guessX[j];
					}
				}
				guessX[i] = (b[i] - sum) / a[i][i];
			}
			//GaussSeidelApplication.outputArray(guessX, "X");
		} while (!convergence(previousGuessX, guessX, e));

		answer = guessX;
	}

	public int getIterationNumber() {
		return iterationNumber;
	}

	private void check(double[][] a, double[] b, double[] x0, double e) throws Exception {
		if (a.length != b.length) {
			throw new Exception("Wrong arguments: length of 'A' != length of 'B'");
		}
		if (b.length != x0.length) {
			throw new Exception("Wrong arguments: length of 'B' != length of X0");
		}
		if (a.length > 20) {
			throw new Exception("Wrong arguments: length of 'A' > 20");
		}
		if (e <= 0.0) {
			throw new Exception("Wrong arguments: e <= 0");
		}
		if (!isDiagDomin(a)) {
			if (hasOpportunityToDiagDomin(a)) {
				throw new Exception("Wrong arguments: change 'A' so it has diagonal'noe preobladanie");
			} else {
				throw new Exception("Wrong arguments: can't make 'A' with diagonal'noe preobladanie");
			}
		}
	}

	private boolean convergence(double[] previousGuessX, double[] guessX, double e) throws Exception {
		if (previousGuessX.length != guessX.length) throw new Exception("in convergence");
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
		if (a.length != a[0].length) {
			throw new IllegalArgumentException("isn't square matrix");
		}

		for (int i = 0; i < a.length; i++) {
			double sum = 0;
			for (int j = 0; j < a.length; j++) {
				sum += Math.abs(a[i][j]);
			}
			if (Math.abs(a[i][i]) < Math.abs(sum-Math.abs(a[i][i]))) {
				return false;
			}
		}
		return true;
	}

	public static boolean hasOpportunityToDiagDomin(double[][] a) throws Exception {
		if (a.length != a[0].length) {
			throw new Exception("isn't square matrix");
		}

		boolean[][] whichCanBeDiagElems = new boolean[a.length][a.length];

		for (int i = 0; i < a.length; i++) {
			double sum = 0.0;
			double maxAbs = 0.0;
			for (int j = 0; j < a.length; j++) {
				sum += Math.abs(a[i][j]);
			}
			for (int j = 0; j < a.length; j++) {
				whichCanBeDiagElems[i][j] = sum - Math.abs(a[i][j]) <= Math.abs(a[i][j]);
			}
		}

		boolean[] paintedColumns = new boolean[a.length];
		for (int i = 0; i < a.length; i++) {
				paintedColumns[i] = false;
		}
		return isAbleToPutOnesOnMainDiag(whichCanBeDiagElems, paintedColumns, -1);
	}

	private static boolean isAbleToPutOnesOnMainDiag(boolean[][] a, boolean[] paintedColumns, int previouslyChosenRow) {
		if (previouslyChosenRow + 1 == a.length) {
			return true;
		}

		for (int j = 1; j < a.length; j++) {
			if (a[previouslyChosenRow + 1][j] && !paintedColumns[j]) {
				paintedColumns[j] = true;
				if (!isAbleToPutOnesOnMainDiag(a, paintedColumns, previouslyChosenRow + 1)) {
					paintedColumns[j] = false;
				} else return true;
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
