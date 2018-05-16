package ru.ifmo.cs.computer_science.lab3.utils;

import ru.ifmo.cs.computer_science.lab2.utils.YXFunction;
import ru.ifmo.cs.computer_science.lab4.utils.Point;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NewtonPolynomial implements YXFunction {
	//private double[] xCoordinates;
	//private double[] yCoordinates;
	//private int length;
	private List<Point<Double, Double>> sortedPoints;

	public NewtonPolynomial(List<Point<Double, Double>> points) {
		//if (xCoordinates.length != yCoordinates.length) throw new IllegalArgumentException(
		//		"Different number of x and y coordinates");
//		List<Point<Double, Double>> points = new ArrayList<>();
//		for (int i = 0; i < xCoordinates.length; i++) {
//			points.add(new Point<Double, Double>(xCoordinates[i], yCoordinates[i]));
//		}
		this.sortedPoints = new ArrayList<>(points);
		this.sortedPoints.sort(Comparator.comparing(Point::getX));

//		this.xCoordinates = new double[xCoordinates.length];
//		this.yCoordinates = new double[xCoordinates.length];
//		for (int i = 0; i < xCoordinates.length; i++) {
//			Point<Double, Double> point = points.remove(0);
//			this.xCoordinates[i] = point.getX();
//			this.yCoordinates[i] = point.getY();
//		}
//		this.length = xCoordinates.length;
	}

	@Override
	// y(x)= sum(ai*multiply(x-xj)), 0 <= i < length; 0 <= j < i - 1
	public double getY(double x) {
		double y = 0;
		for (int i = 0; i < sortedPoints.size(); i++) {
			double part = a(0, i);
			for (int j = 0; j <= i - 1; j++) {
				part *= x - sortedPoints.get(j).getX();
			}
			y += part;
		}
		return y;
	}

	// разделенная разница a(xn, xk) = (a(xk, xn+1) - a(xk-1, xn)) / (xk - xn)
	private double a(int beginIndex, int endIndex) {
		if (beginIndex == endIndex) return sortedPoints.get(beginIndex).getY();
		if (endIndex - beginIndex == 1)
			return (sortedPoints.get(endIndex).getY() - sortedPoints.get(beginIndex).getY()) /
					(sortedPoints.get(endIndex).getX() - sortedPoints.get(beginIndex).getX());
		return ( a(beginIndex + 1, endIndex) - a(beginIndex, endIndex - 1) ) /
				(sortedPoints.get(endIndex).getX() - sortedPoints.get(beginIndex).getX());
	}
}
