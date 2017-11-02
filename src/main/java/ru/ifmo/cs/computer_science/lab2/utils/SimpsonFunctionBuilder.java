package ru.ifmo.cs.computer_science.lab2.utils;

public class SimpsonFunctionBuilder {
	public SimpsonFunctionBuilder() {}

	public SimpsonFunction buildFunction(YXFunction function) {
		return new SimpsonFunction() {
				@Override
				public double getY(double x) {
					return function.getY(x);
				}
			};
	}
}
