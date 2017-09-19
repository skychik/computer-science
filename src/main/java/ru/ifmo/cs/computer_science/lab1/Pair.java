package ru.ifmo.cs.computer_science.lab1;

import java.io.Serializable;
import java.util.Objects;

public class Pair implements Serializable {
	private Exception first;
	private String second;

	Pair(Exception first, String second) {
		super();
		this.first = first;
		this.second = second;
	}

//		/**
//		 * @param str - "(first, second)"
//		 */
//		public Pair(String str) throws IllegalArgumentException {
//			super();
//			Pattern p = Pattern.compile("\\(\\w*, \\w*\\)");
//			if (!p.matcher(str).matches()) throw new IllegalArgumentException();
//
//			p = Pattern.compile("[,()]");
//			String[] ans = p.split(str);
//			first = ans[1];
//			second = ans[2];
//		}

	public int hashCode() {
		int hashFirst = first != null ? first.hashCode() : 0;
		int hashSecond = second != null ? second.hashCode() : 0;

		return (hashFirst + hashSecond) * hashSecond + hashFirst;
	}

	public boolean equals(Object other) {
		if (other instanceof Pair) {
			Pair otherPair = (Pair) other;
			return
					((Objects.equals(this.first, otherPair.first) ||
							( this.first != null && otherPair.first != null &&
									this.first.equals(otherPair.first))) &&
							(Objects.equals(this.second, otherPair.second) ||
									( this.second != null && otherPair.second != null &&
											this.second.equals(otherPair.second))) );
		}

		return false;
	}

	/**
	 *
	 * @return "(first, second)"
	 */
	public String toString()
	{
		return "(" + first + ", " + second + ")";
	}

	public Exception getFirst() {
		return first;
	}

	public String getSecond() {
		return second;
	}
}