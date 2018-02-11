package ru.ifmo.cs.computer_science.lab3.utils;

import ru.ifmo.cs.computer_science.lab1.utils.Pair;
import ru.ifmo.cs.computer_science.lab2.utils.YXFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class NewtonPolynomial implements YXFunction {
	private double[] xCoordinates;
	private double[] yCoordinates;
	private int length;

	public NewtonPolynomial(double[] xCoordinates, double[] yCoordinates) {
		if (xCoordinates.length != yCoordinates.length) throw new IllegalArgumentException(
				"Different number of x and y coordinates");
		List<Pair<Double, Double>> points = new ArrayList<>();
		for (int i = 0; i < xCoordinates.length; i++) {
			points.add(new Pair<>(xCoordinates[i], yCoordinates[i]));
		}
		points.sort(Comparator.comparing(Pair::getFirst));

		this.xCoordinates = new double[xCoordinates.length];
		this.yCoordinates = new double[xCoordinates.length];
		for (int i = 0; i < xCoordinates.length; i++) {
			Pair<Double, Double> point = points.remove(0);
			this.xCoordinates[i] = point.getFirst();
			this.yCoordinates[i] = point.getSecond();
		}
		this.length = xCoordinates.length;
	}

	@Override
	public double getY(double x) {
		double ans = 0;
		for (int i = 0; i < length; i++) {
			double part = f(0, i);
			for (int j = 0; j <= i - 1; j++) {
				part *= x - xCoordinates[j];
			}
			ans += part;
		}
		return ans;
	}

	private double f(int beginIndex, int endIndex) {
		if (beginIndex == endIndex) return yCoordinates[beginIndex];
		if (endIndex - beginIndex == 1) return (yCoordinates[endIndex] - yCoordinates[beginIndex]) /
				(xCoordinates[endIndex] - xCoordinates[beginIndex]);
		return (f(beginIndex + 1, endIndex) - f(beginIndex, endIndex - 1)) /
				(xCoordinates[endIndex] - xCoordinates[beginIndex]);
	}
}
