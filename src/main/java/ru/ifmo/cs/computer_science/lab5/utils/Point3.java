package ru.ifmo.cs.computer_science.lab5.utils;

import ru.ifmo.cs.computer_science.lab1.utils.Pair;

import java.io.Serializable;
import java.util.Objects;

public class Point3<X extends Number, Y extends Number, Z extends Number> implements Serializable {
	private final X x;
	private final Y y;
	private final Z z;

	public Point3(X x, Y y, Z z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public X getX() {
		return x;
	}

	public Y getY() {
		return y;
	}

	public Z getZ() {
		return z;
	}

	public String toString()
		{
			return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
		}

	public boolean equals(Object other) {
		if (other instanceof Point3) {
			Point3 otherPoint = (Point3) other;
			return ((Objects.equals(this.x, otherPoint.x) ||
							( this.x != null && otherPoint.x != null &&
									this.x.equals(otherPoint.x))) &&
					(Objects.equals(this.y, otherPoint.y) ||
							( this.y != null && otherPoint.y != null &&
									this.y.equals(otherPoint.y))) &&
					(Objects.equals(this.z, otherPoint.z) ||
							( this.z != null && otherPoint.z != null &&
									this.z.equals(otherPoint.z))) );
		}

		return false;
	}
}
