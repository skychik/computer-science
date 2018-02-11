package ru.ifmo.cs.computer_science.lab2.utils;

public class DefinedIntegralSimpsonMethod {
	private final YXFunction function;
	private final double lowLimit;
	private final double highLimit;
	private final double epsilon;

	private double definedIntegral;
	public double getDefinedIntegral() {
		return definedIntegral;
	}

	private int numberOfSplits;
	public int getNumberOfSplits() {
		return numberOfSplits;
	}

	private double rungeEpsilon; // Рунге
	public double getRungeEpsilon() {
		return rungeEpsilon;
	}

	public DefinedIntegralSimpsonMethod(YXFunction function, double lowLimit, double highLimit, double epsilon)
			throws IllegalArgumentException {
		this.function = function;
		this.lowLimit = lowLimit;
		this.highLimit = highLimit;
		this.epsilon = epsilon;
	}

	public void integrate()
			throws IllegalArgumentException {
		double previousIntegral;
		numberOfSplits = 1;
		definedIntegral = getDefinedIntegral(numberOfSplits);
		do {
			numberOfSplits *= 2;
			previousIntegral = definedIntegral;
			definedIntegral = getDefinedIntegral(numberOfSplits);
			rungeEpsilon = (Math.abs(definedIntegral - previousIntegral)) / 15;
		} while (rungeEpsilon > epsilon);
	}

	public double getDefinedIntegral(int n) throws IllegalArgumentException {
		if (n < 1) throw new IllegalArgumentException("n should be >= 1");
		if (!Double.isFinite(lowLimit)) throw new IllegalArgumentException("lower limit should be a real number");
		if (!Double.isFinite(highLimit)) throw new IllegalArgumentException("higher limit should be a real number");

		double h = (highLimit - lowLimit) / n / 2;

		double sumEven = 0;
		for (int i = 1; i <= n; i++) {
			sumEven += function.getY(lowLimit + (2 * i - 1) * h);
		}

		double sumUneven = 0;
		for (int i = 1; i <= n - 1; i++) {
			sumUneven += function.getY(lowLimit + (2 * i) * h);
		}

		return h / 3 * (function.getY(lowLimit) + 4*sumEven + 2*sumUneven + function.getY(highLimit));
	}
}
