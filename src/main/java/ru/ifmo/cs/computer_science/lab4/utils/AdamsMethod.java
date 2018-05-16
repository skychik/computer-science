package ru.ifmo.cs.computer_science.lab4.utils;

import java.util.ArrayList;
import java.util.List;

public class AdamsMethod {
	private final FYXFunction f;
	private final Point<Double, Double> startPoint;
	private final double lastX;
	private List<Point<Double, Double>> answer;
	private final int rungeK = 15; // TODO: ?: 2^4 - 1

	public AdamsMethod(FYXFunction f, Point<Double, Double> startPoint, double lastX, double epsilon) {
		this.f = f;
		this.startPoint = startPoint;
		this.lastX = lastX;

		double maxDelta;
		int partsNumber = 1;
		//long finalPartsNumber = (long) Math.ceil(Math.abs(lastX - startPoint.getX()) / epsilon);
		List<Point<Double, Double>> pointList = method(partsNumber/*finalPartsNumber*/);

//		Point<Double, Double> lastPoint = pointList.get(pointList.size() - 1);
//		double maxStep = maxStepSizeForAdams(f.getF(lastPoint.getX(), lastPoint.getY()), epsilon);
//		double previousMaxStep = 0;


		do {
			partsNumber *= 2;
			List<Point<Double, Double>> previousYPointList = new ArrayList<>(pointList);
			pointList = method(partsNumber);
//			System.out.println(partsNumber);
//			System.out.println("previous=" + previousYPointList.toString());
//			System.out.println("current=" + yPointList.toString());

			maxDelta = 0;
			for (int i = 1; i <= partsNumber / 2; i++) {
				double diff = Math.abs(pointList.get(2 * i).getY() - previousYPointList.get(i).getY());
				if (diff > maxDelta) {
					maxDelta = diff;
				}
			}
			System.out.println("parts=" + partsNumber + " max diff=" + maxDelta);
		} while (maxDelta / rungeK > epsilon);
		System.out.println("finished!");
		answer = pointList;
	}

	private double delta(double h, double yDerivative) {
		return 251/720 * Math.pow(h, 5) * Math.pow(yDerivative, 5);
	}

	public static double maxStepSizeForAdams(double yDerivative, double epsilon) {
		return Math.abs(Math.pow(epsilon * 720 / 251 / Math.pow(yDerivative, 5), 1.0 / 5));
	}

	public List<Point<Double, Double>> getAnswer() {
		return answer;
	}

	private List<Point<Double, Double>> method(long partsNumber) {
		double step = (lastX - startPoint.getX()) / partsNumber;

		List<Point<Double, Double>> yPointList = new ArrayList<>();
		yPointList.add(0, new Point<>(startPoint.getX(), startPoint.getY()));

		for (int i = 1; i < 4; i++) {
			if (i <= partsNumber) yPointList.add(i, findNextPointRungeKutta(yPointList, step, i));
		}
		for (int i = 4; i <= partsNumber; i++) {
			yPointList.add(i, findNextPointAdams(yPointList, step, i));
		}

		return yPointList;
	}

	private Point<Double, Double> findNextPointRungeKutta(List<Point<Double, Double>> points, double step, int i) {
		Point<Double, Double> previous = points.get(i - 1);

		double k1 = f.getF(previous.getX(), previous.getY());
		double k2 = f.getF(previous.getX() + step / 2, previous.getY() + step / 2 * k1);
		double k3 = f.getF(previous.getX() + step / 2, previous.getY() + step / 2 * k2);
		double k4 = f.getF(previous.getX() + step, previous.getY() + step * k3);

		return new Point<>(previous.getX() + step, previous.getY() + step * (k1 + 2 * k2 + 2 * k3 + k4) / 6);
	}

	private Point<Double, Double> findNextPointAdams(List<Point<Double, Double>> points, double step, int i) { // TODO: ?
		Point<Double, Double> previous = points.get(i - 1);

		return new Point<>(previous.getX() + step,
				previous.getY() +
					(55 * step * getF(previous) -
					59 * Math.pow(step, 2) * (getF(previous) - getF(points.get(i - 2))) +
					37 * Math.pow(step, 3) * (getF(previous) - 2 * getF(points.get(i - 2)) + getF(points.get(i - 3))) -
					9 * Math.pow(step, 4) * (getF(previous) - 3 * getF(points.get(i - 2)) + 3 * getF(points.get(i - 3)) - getF(points.get(i - 4))))
							/ 24);
	}

	private double getF(Point<Double, Double> point) {
		return f.getF(point.getX(), point.getY());
	}
}
