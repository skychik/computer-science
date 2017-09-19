package ru.ifmo.cs.computer_science.lab1;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GaussSeidelTest {
	private ArrayList<Pair> log = new ArrayList<>();
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
	}

	@Test
	void gaussSeidel() {

	}
}