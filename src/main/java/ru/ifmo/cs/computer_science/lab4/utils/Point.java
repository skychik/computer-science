package ru.ifmo.cs.computer_science.lab4.utils;

import ru.ifmo.cs.computer_science.lab1.utils.Pair;

public class Point<X extends Number, Y extends Number> {
	private final Pair<X, Y> point;

	public Point(X x, Y y) {
		point = new Pair<>(x, y);
	}

	public X getX() {
		return point.getFirst();
	}

	public Y getY() {
		return point.getSecond();
	}

	public String toString()
	{
		return "(" + getX() + ", " + getY() + ")";
	}
}
