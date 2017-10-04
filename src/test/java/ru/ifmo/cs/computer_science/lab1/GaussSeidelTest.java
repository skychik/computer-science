package ru.ifmo.cs.computer_science.lab1;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.SchemaOutputResolver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GaussSeidelTest {
/*	private ArrayList<Pair> log = new ArrayList<>();
	FileInputStream is;
	FileOutputStream os;

	@BeforeEach
	void setUp() {
		// init is from input.txt
		try {
			is = new FileInputStream(System.getProperty("user.dir") +
					"/src/main/resources/input.txt");
		} catch (FileNotFoundException e) {
			log.add(new Pair(e, "input file wasn't found"));
			return;
		}

		// init os in buffered_input.txt
		try {
			os = new FileOutputStream(System.getProperty("user.dir") +
					"/src/test/java/ru/ifmo/cs/computer_science/lab1/buffered_input.txt");
		} catch (IOException e) {
			log.add(new Pair(e, "can't create buffered_input.txt"));
			return;
		}

		// buffering input.txt
		byte[] buffer = new byte[1024];
		int length;
		try {
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} catch (IOException e) {
			log.add(new Pair(e, "Can't buffer input.txt"));
			return;
		}

		try {
			os.close();
			is.close();
		} catch (IOException e) {
			log.add(new Pair(e, "Can't close input or output in buffering input.txt"));
		}
	}

	@AfterEach
	void tearDown() {
		// init is from buffered_input.txt
		try {
			is = new FileInputStream(System.getProperty("user.dir") +
					"/src/test/java/ru/ifmo/cs/computer_science/lab1/buffered_input.txt");
		} catch (FileNotFoundException e) {
			log.add(new Pair(e, "buffered_input file wasn't found"));
			return;
		}

		// init os in input.txt
		try {
			os = new FileOutputStream(System.getProperty("user.dir") +
					"/src/main/resources/input.txt");
		} catch (IOException e) {
			log.add(new Pair(e, "can't create input.txt"));
			return;
		}

		// making input.txt back
		byte[] buffer = new byte[1024];
		int length;
		try {
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} catch (IOException e) {
			log.add(new Pair(e, "Can't make input.txt back"));
			return;
		}

		try {
			os.close();
			is.close();
		} catch (IOException e) {
			log.add(new Pair(e, "Can't close input or output in making input.txt back"));
		}
	}*/

	@Test
	void gaussSeidel1() {
		int testNumber = 1;
		double[][] a = {{ 5.0, 1.0, 1.0},
						{ 1.0, 5.0, 1.0},
						{ 1.0, 1.0, 5.0}};
		double[] b = {7.0, 7.0, 7.0};
		double[] x0 = {1.4, 1.4, 1.4};
		double e = 0.0001;
		double[] expected = {1.0, 1.0, 1.0};

		doGaussSeidelTesting(testNumber, a, b, x0, e, expected);
	}

	@Ignore
	@Test
	void gaussSeidel2() {
		int testNumber = 2;
		double[][] a = {{ 55, 1 , 2 , 3  , 4 },
						{ 6 , 64, 7 , 8  , 9 },
						{ 11, 12, 99, -13, 14 },
						{ -1, -2, -3, 87 , 4.5},
						{ 6 , 7 , 8 , 9  , 73 }};
		double[] b = {5, 10, 8, 5, 12};
		double[] x0 = {1, 2, 3, 4, 5};
		double e = 0.001;
		double[] expected = {0.074215, 0.118257, 0.046445, 0.055661, 0.134992};

		doGaussSeidelTesting(testNumber, a, b, x0, e, expected);
	}

	@Test
	void gaussSeidel3() {
		int testNumber = 3;
		double[][] a = {{ 15.0, 3.0,  4.0,  5.0},
				{ 2.0,  16.0, 4.0,  5.0},
				{ 2.0,  3.0,  17.0, 5.0},
				{ 2.0,  3.0,  4.0,  18.0},};
		double[] b = {13.0, -1.0, 17.0, -50.0};
		double[] x0 = {0.0, 0.0, 0.0, 0.0};
		double e = 0.0001;
		double[] expected = {(double) 170/117, (double) 44/117, (double) 206/117, (double) -397/117};
		doGaussSeidelTesting(testNumber, a, b, x0, e, expected);
	}

	private void doGaussSeidelTesting(int testNumber, double[][] a, double[] b, double[] x0, double e, double[] expected) {
		System.out.println("test" + testNumber + ":");
		GaussSeidel gs;
		try {
			gs = new GaussSeidel(a, b, x0, e);
		} catch (Exception e1) {
			System.out.println("Incorrect test" + testNumber + e1.getMessage());
			return;
		}

		double[] answer = gs.gaussSeidel();
		GaussSeidelApplication.outputArray(answer, "Answer");

		double minDelta = 0.0;
		for (int i = 0; i < a.length; i++) {
			double abs = Math.abs(expected[i] - answer[i]);
			if (abs > minDelta) minDelta = abs;
		}

		assertTrue(minDelta < e, "Incorrect answer" + testNumber);
		System.out.println("Test " + testNumber + ": " + (minDelta < e));
	}
}