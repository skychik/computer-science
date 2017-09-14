package ru.ifmo.cs.computer_science.lab1;

import java.util.Random;
import java.util.Scanner;

public class GaussSeidelApplication {
	private static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		double[] x;
		if (yesNoQuestion("Do you want to enter data manually?")) {
			x = manualInput();
		} else {
			x = fileInput();
		}

		// output answer
		outputArray(x, "X");
	}

	private static double[] fileInput() {
		return new double[0];
	}

	private static double[] manualInput() {
		int size;
		double[][] a;
		double[] b;
		double[] x0;
		double e;
		final Random random = new Random();

		// entering size, A and B
		if (yesNoQuestion("Do you want to generate random size and indexes for A and B?")) {
			size = random.nextInt(19) + 1;

			a = new double[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					a[i][j] = (Math.random() - 0.5) * 2000000; // between -10^6 and 10^6
				}
			}

			b = new double[size];
			for (int i = 0; i < size; i++) {
				b[i] = (Math.random() - 0.5) * 2000000; // between -10^6 and 10^6
			}
		} else {
			size = inputSize();
			a = inputA(size);
			b = inputArray(size, "B");
		}

		//entering x0
		if (yesNoQuestion("Do you want to generate random default values for x?")) {
			x0 = new double[size];
			for (int i = 0; i < size; i++) {
				x0[i] = (Math.random() - 0.5) * 2000000; // between -10^6 and 10^6
			}
		} else {
			x0 = inputArray(size, "X0");
		}

		// entering epsilon
		if (yesNoQuestion("Do you want to generate random epsilon")) {
			e = Math.pow(random.nextDouble(), 3) * 100;
		} else {
			e = inputEpsilon();
		}

		return GaussSeidel.gaussSeidel(a, b, x0, e);
	}

	private static boolean yesNoQuestion(String msg) {
		System.out.println(msg);
		System.out.println("y or n?:");
		String input = sc.nextLine();
		switch (input.trim().toLowerCase()) {
			case "y":
			case "yes":
				return true;
			case "n":
			case "no":
				return false;
			default:
				System.out.println("Wrong answer: " + input + "\n" +
						"Try again");
				return yesNoQuestion(msg);
		}
	}

	private static int inputSize() {
		System.out.print("Enter size of A: ");
		if (!sc.hasNextInt()) {
			System.out.println("You have to enter size of matrix. Size should be integer, 1<=size<=20");
			return inputSize();
		}
		int size = sc.nextInt();
		if (size <= 0 || size > 20) {
			System.out.println("\n Wrong size: " + size + ". Size should be between 1 and 20");
			return inputSize();
		}

		System.out.println("size=" + size);
		return size;
	}

	private static double[][] inputA(int size) {
		System.out.println("Enter A: ");
		double[][] a = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!sc.hasNextDouble()) {
					System.out.println("There is no real number next.\n" +
							"You have to enter matrix " + size + 'x' + size + " with real numbers\n" +
							"Try one more time");
					return inputA(size);
				}
				a[i][j] = sc.nextDouble();
			}
		}

		outputMatrix(a, "A");
		return a;
	}

	private static double[] inputArray(int size, String arrayName) {
		System.out.println("Enter " + arrayName + ": ");
		double[] mas = new double[size];
		for (int i = 0; i < size; i++) {
			if (!sc.hasNextDouble()) {
				System.out.println("There is no real number next.\n" +
						"You have to enter array with " + size + " real numbers\n" +
						"Try one more time");
				return inputArray(size, arrayName);
			}
			mas[i] = sc.nextDouble();
		}

		outputArray(mas, arrayName);
		return mas;
	}

	private static double inputEpsilon() {
		System.out.print("Enter epsilon: ");
		if (!sc.hasNextDouble()) {
			System.out.println("You have to enter double value");
			return inputEpsilon();
		}
		double epsilon = sc.nextDouble();

		System.out.println("epsilon=" + epsilon);
		return epsilon;
	}

	private static void outputArray(double[] array, String arrayName) {
		System.out.println(arrayName + ":");
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]);
			if (i < array.length - 1) {
				System.out.println(", ");
			}
		}
		System.out.println();
	}

	private static void outputMatrix(double[][] matrix, String arrayName) {
		System.out.println(arrayName + ":");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j]);
				if (i < matrix.length - 1) {
					System.out.println(", ");
				}
			}
			System.out.println();
		}
	}
}
