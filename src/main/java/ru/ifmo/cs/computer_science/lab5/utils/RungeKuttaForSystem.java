package ru.ifmo.cs.computer_science.lab5.utils;

import ru.ifmo.cs.computer_science.lab4.utils.Point;

import java.util.ArrayList;
import java.util.List;

import static ru.ifmo.cs.computer_science.lab4.utils.AdamsMethod.maxStepSizeForAdams;

public class RungeKuttaForSystem {
	private FXYZFunction yx;
	private FXYZFunction zx;
	private Point3<Double, Double, Double> startPoint;
	private double lastX;
	//private double epsilon;
	private final int rungeK = 15;
	private List<Point3<Double, Double, Double>> answer;

	public RungeKuttaForSystem(FXYZFunction yx, FXYZFunction zx, Point3<Double, Double, Double> startPoint, double lastX, double epsilon) {
		this.yx = yx;
		this.zx = zx;
		this.startPoint = startPoint;
		this.lastX = lastX;
		//this.epsilon = epsilon;

		double maxDelta;
		int partsNumber = 1;
		List<Point3<Double, Double, Double>> pointList = method(partsNumber);

		do {
			partsNumber *= 2;
			List<Point3<Double, Double, Double>> previousYPointList = new ArrayList<>(pointList);
			pointList = method(partsNumber);
//			System.out.println(partsNumber);
//			System.out.println("previous=" + previousYPointList.toString());
//			System.out.println("current=" + yPointList.toString());

			maxDelta = 0;
			for (int i = 0; i < partsNumber / 2; i++) {
				double diff = Math.abs(pointList.get(2 * i).getZ() - previousYPointList.get(i).getZ());
				if (diff > maxDelta) {
					maxDelta = diff;
				}
			}
			System.out.println("parts=" + partsNumber + ", max z diff=" + maxDelta);
		} while (maxDelta / rungeK > epsilon);

//		Point3<Double, Double, Double> lastPoint = pointList.get(pointList.size() - 1);
//		while (lastPoint.getZ() > maxStepSizeForAdams(zx.getF(lastPoint.getX(), lastPoint.getY(), lastPoint.getZ()), epsilon)) {
//			pointList = method((long) Math.ceil(Math.abs(lastX - startPoint.getX()) /
//					maxStepSizeForAdams(zx.getF(lastPoint.getX(), lastPoint.getY(), lastPoint.getZ()), epsilon)));
//			lastPoint = pointList.get(pointList.size() - 1);
//		}

		answer = pointList;
	}

	public List<Point3<Double, Double, Double>> getAnswer() {
		return answer;
	}

	private List<Point3<Double, Double, Double>> method(long partsNumber) {
		double step = (lastX - startPoint.getX()) / partsNumber;

		List<Point3<Double, Double, Double>> pointList = new ArrayList<>();
		pointList.add(0, new Point3<>(startPoint.getX(), startPoint.getY(), startPoint.getZ()));

		for (int i = 1; i < partsNumber; i++) {
			pointList.add(i, findNextPointRungeKutta(pointList, step, i));
		}

		return pointList;
	}

	private Point3<Double, Double, Double> findNextPointRungeKutta(List<Point3<Double, Double, Double>> points, double step, int i) {
		Point3<Double, Double, Double> previous = points.get(i - 1);

		double k1y = yx.getF(previous.getX(), previous.getY(), previous.getZ());
		double k1z = zx.getF(previous.getX(), previous.getY(), previous.getZ());
		double k2y = yx.getF(previous.getX() + step / 2, previous.getY() + step / 2 * k1y, previous.getZ() + step / 2 * k1z);
		double k2z = zx.getF(previous.getX() + step / 2, previous.getY() + step / 2 * k1y, previous.getZ() + step / 2 * k1z);
		double k3y = yx.getF(previous.getX() + step / 2, previous.getY() + step / 2 * k2y, previous.getZ() + step / 2 * k2z);
		double k3z = zx.getF(previous.getX() + step / 2, previous.getY() + step / 2 * k2y, previous.getZ() + step / 2 * k2z);
		double k4y = yx.getF(previous.getX() + step, previous.getY() + step * k3y, previous.getZ() + step * k3z);
		double k4z = zx.getF(previous.getX() + step, previous.getY() + step * k3y, previous.getZ() + step * k3z);

		return new Point3<>(previous.getX() + step,
				previous.getY() + step * (k1y + 2 * k2y + 2 * k3y + k4y) / 6,
				previous.getZ() + step * (k1z + 2 * k2z + 2 * k3z + k4z) / 6);
	}

}
