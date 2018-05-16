package ru.ifmo.cs.computer_science.lab5.utils;

import ru.ifmo.cs.computer_science.lab4.utils.AdamsMethod;
import ru.ifmo.cs.computer_science.lab4.utils.Point;

import java.util.List;

import static jdk.nashorn.internal.objects.Global.Infinity;

public class ShootingMethod {
	private FXYZFunction f1;
	private FXYZFunction f2;
	private double a;
	private double ya;
	private double b;
	private double zb;
	private double epsilon;
	List<Point3<Double, Double, Double>> answer;

	public ShootingMethod(FXYZFunction f1, FXYZFunction f2, double a, double ya, double b, double zb, double epsilon) {
		this.f1 = f1;
		this.f2 = f2;
		this.a = a;
		this.ya = ya;
		this.b = b;
		this.zb = zb;
		this.epsilon = epsilon;

		System.out.println(Double.MAX_VALUE / 2);
		System.out.println(-Double.MAX_VALUE / 2);
		answer = method(-Double.MAX_VALUE, Double.MAX_VALUE);
	}

	public List<Point3<Double, Double, Double>> getAnswer() {
		return answer;
	}

	private List<Point3<Double, Double, Double>> method(double zaMin, double zaMax) {
		RungeKuttaForSystem koshi = new RungeKuttaForSystem(f1, f2, new Point3<>(a, ya, (zaMax/2 + zaMin/2)),
				b, epsilon / 2);
		List<Point3<Double, Double, Double>> ans = koshi.getAnswer();
		double zb1 = ans.get(ans.size() - 1).getZ();

		System.out.println("min=" + zaMin + ", max=" + zaMax);
		if (Math.abs(zb1 - zb) <= epsilon/2) {
			return ans;
		} else if (zb1 < zb) {
			return method(zaMax/2 + zaMin/2, zaMax);
		} else {
			return method(zaMin, zaMax/2 + zaMin/2);
		}
	}
}
