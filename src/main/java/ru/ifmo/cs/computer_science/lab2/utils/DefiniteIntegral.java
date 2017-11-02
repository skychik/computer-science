package ru.ifmo.cs.computer_science.lab2.utils;

import java.io.IOException;

public class DefiniteIntegral {
	private double definiteIntegral;
	private int numberOfSplits;
	private double rungeEpsilon; // Рунге

	public DefiniteIntegral(SimpsonFunction function, double lowerLimit, double higherLimit,
	                        double epsilon) throws IOException {
		integrate(function, lowerLimit, higherLimit, epsilon);
	}

	private void integrate(SimpsonFunction function, double lowerLimit, double higherLimit, double epsilon) throws IOException {
		double previousIntegral;
		numberOfSplits = 1;
		definiteIntegral = function.getDefiniteIntegral(lowerLimit, higherLimit, numberOfSplits);
		do {
			numberOfSplits *= 2;
			previousIntegral = definiteIntegral;
			definiteIntegral = function.getDefiniteIntegral(lowerLimit, higherLimit, numberOfSplits);
			rungeEpsilon = (Math.abs(definiteIntegral - previousIntegral)) / 15;
		} while (rungeEpsilon > epsilon);
	}

	public double getDefiniteIntegral() {
		return definiteIntegral;
	}

	public int getNumberOfSplits() {
		return numberOfSplits;
	}

	public double getRungeEpsilon() {
		return rungeEpsilon;
	}
}
