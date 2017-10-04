package ru.ifmo.cs.computer_science.lab1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static ru.ifmo.cs.computer_science.lab1.GaussSeidel.isDiagDomin;

public class GaussSeidelApplication {
	private static Scanner sc = new Scanner(System.in);
	private static Gson gson;

	public static void main(String[] args) {
		GsonBuilder gbuilder = new GsonBuilder();
		gbuilder.serializeSpecialFloatingPointValues(); // mb needs to override
		gson = gbuilder.create();
//		// fill the file
//			GaussSeidel gs;
//			try {
//				double[][] a = new double[1][1];
//				a[0][0] = 0.0;
//				double[] b = new double[1];
//				b[0] = 0.0;
//				gs = new GaussSeidel(a, b);
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.exit(1);
//				return;
//			}
//			String json = gson.toJson(gs);
//			try
//			{
//				FileWriter gwriter = new FileWriter(System.getProperty("user.dir") + "/src/main/resources/input.txt");
//				gwriter.write(json);
//				gwriter.close();
//			}
//			catch(Exception e) {
//				e.printStackTrace();
//				System.exit(1);
//				return;
//			}
//		//
		GaussSeidel gs;
		if (yesNoQuestion("Do you want to enter data manually?")) {
			gs = manualInput();
		} else {
			gs = fileInput();
		}

		// output answer
		if (gs != null) {
			System.out.println("Number of iterations: " + gs.getIterationNumber());
			outputArray(gs.getAnswer(), "X");
		}
	}

	private static GaussSeidel fileInput() {
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(System.getProperty("user.dir") +
					"/src/main/resources/input.txt"));
		} catch (FileNotFoundException e1) {
			System.out.println("File not found");
			e1.printStackTrace();
			System.exit(1);
			return null;
		}

		GaussSeidel gs;
		try {
			gs = gson.fromJson(reader, GaussSeidel.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		outputMatrix(gs.getA(), "A");
		outputArray(gs.getB(), "B");
		outputArray(gs.getX0(), "First for X");
		System.out.println("Epsilon: " + gs.getE());
		System.out.println();

		return gs;
//		try {
//			StringBuilder str = new StringBuilder();
//			int c = reader.read();
//			while (c != -1) {
//				if (!Character.isWhitespace(c) && c != '{') {
//					str.append(c);
//				} else switch (str.toString().toLowerCase()) {
//					case "a":
//						if (a != null) {
//							System.out.println("There should be only one A definition. Correct input file");
//						}
//
//						break;
//				}
//				c = reader.read();
//			}
//		} catch (IOException e1) {
//			System.out.println("Can't read next character");
//			e1.printStackTrace();
//		}
//		return new double[0];
	}

	private static GaussSeidel manualInput() {
		int size;
		double[][] a;
		double[] b;
		double[] x0;
		double e;
		final Random random = new Random();

		// entering size, A and B
		if (yesNoQuestion("Do you want to generate random size and indexes for A and B?")) {
			size = random.nextInt(19) + 1;

			a = makeRandomDiagDominMatrix(size);

			b = new double[size];
			for (int i = 0; i < size; i++) {
				b[i] = (Math.random() - 0.5) * 2000000; // between -10^6 and 10^6
			}
		} else {
			size = inputSize();
			a = inputA(size);
			b = inputArray(size, "B");
		}

		// entering x0
		//     make zeros
		if (yesNoQuestion("Do you want to make '0' as default values for x?")) {
			x0 = new double[size];
			for (int i = 0; i < size; i++) {
				x0[i] = 0;
			}
		} else {
			// random x0
			if (yesNoQuestion("Do you want to generate random default values for x?")) {
				x0 = new double[size];
				for (int i = 0; i < size; i++) {
					x0[i] = (Math.random() - 0.5) * 2000000; // between -10^6 and 10^6
				}
			} else {
				x0 = inputArray(size, "X0");
			}
		}

		// entering epsilon
		if (yesNoQuestion("Do you want to generate random epsilon")) {
			e = Math.pow(random.nextDouble(), 3) * 100;
		} else {
			e = inputEpsilon();
		}

		try {
			return new GaussSeidel(a, b, x0, e);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			return null;
		}
	}

	private static double[][] makeRandomDiagDominMatrix(int size) {
		double[][] matrix = new double[size][size];
		for (int i = 0; i < size; i++) {
			LinkedList<Double> list = new LinkedList<>();
			for (int j = 0; j < size; j++) {
				list.add((Math.random() - 0.5) * 2000000); // between -10^6 and 10^6
			}

			// sort
			list.sort((x1, x2) -> x1 > x2 ? 1 : (Objects.equals(x1, x2) ? 0 : -1));
			for (int j = 0; j < size; j++) {
				matrix[i][(i + j) % size] = list.removeFirst();
			}
		}
		return matrix;
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

		if (!isDiagDomin(a)) {
			System.out.println("Matrix A should have diagonal'noe preobladanie. Change it");
			return inputA(size);
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

	public static void outputArray(double[] array, String arrayName) {
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
