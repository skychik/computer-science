package ru.ifmo.cs.computer_science.lab3.utils;

import ru.ifmo.cs.computer_science.lab1.utils.Pair;
import ru.ifmo.cs.computer_science.lab2.utils.YXFunction;

import java.util.ArrayList;
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
	// y(x)= sum(ai*multiply(x-xj)), 0 <= i < length; 0 <= j < i - 1
	public double getY(double x) {
		double y = 0;
		for (int i = 0; i < length; i++) {
			double part = a(0, i);
			for (int j = 0; j <= i - 1; j++) {
				part *= x - xCoordinates[j];
			}
			y += part;
		}
		return y;
	}

	// разделенная разница a(xn, xk) = (a(xk, xn+1) - a(xk-1, xn)) / (xk - xn)
	private double a(int beginIndex, int endIndex) {
		if (beginIndex == endIndex) return yCoordinates[beginIndex];
		if (endIndex - beginIndex == 1)
			return (yCoordinates[endIndex] - yCoordinates[beginIndex]) /
					(xCoordinates[endIndex] - xCoordinates[beginIndex]);
		return (a(beginIndex + 1, endIndex) - a(beginIndex, endIndex - 1)) /
				(xCoordinates[endIndex] - xCoordinates[beginIndex]);
	}
}
