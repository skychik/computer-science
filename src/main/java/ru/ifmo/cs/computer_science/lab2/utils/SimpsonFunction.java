package ru.ifmo.cs.computer_science.lab2.utils;

import java.io.IOException;

// This is the function y(x); integral calculates using Simpson method
public abstract class SimpsonFunction implements YXFunction {
	public double getDefiniteIntegral(double lowerLimit, double higherLimit, int n) throws IOException {
		System.out.println("defint " + lowerLimit + ' ' + higherLimit + ' ' + n);
		if (n < 1) throw new IOException("n should be >= 1");
		if (!Double.isFinite(lowerLimit)) throw new IOException("lower limit should be a real number");
		if (!Double.isFinite(higherLimit)) throw new IOException("higher limit should be a real number");

		double h = (higherLimit - lowerLimit) / n / 2;

		double sum1 = 0;
		for (int i = 1; i <= n; i++) {
			sum1 += getY(lowerLimit + (2 * i - 1) * h);
		}
		sum1 *= 4;

		double sum2 = 0;
		for (int i = 1; i <= n - 1; i++) {
			sum2 += getY(lowerLimit + (2 * i) * h);
		}
		sum2 *= 2;

		System.out.println(h / 3 * (getY(lowerLimit) + sum1 + sum2 + getY(higherLimit)));
		return h / 3 * (getY(lowerLimit) + sum1 + sum2 + getY(higherLimit));
	}
}
