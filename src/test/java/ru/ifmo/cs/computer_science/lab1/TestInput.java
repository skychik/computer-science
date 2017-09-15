package java.ru.ifmo.cs.computer_science.lab1;

import com.google.gson.stream.JsonReader;
import sun.plugin.javascript.navig.Array;

import java.io.*;
import java.util.ArrayList;

public class TestInput {
	private static ArrayList<Pair> log = new ArrayList<>();

	public static void main(String[] args) {
		FileInputStream is;
		FileOutputStream os;
		try {
			is = new FileInputStream(System.getProperty("user.dir") +
					"/src/main/resources/input.txt");
		} catch (FileNotFoundException e) {
			log.add(new Pair(e, "input file wasn't find"));
			return;
		}

		try {
			os = new FileOutputStream(System.getProperty("user.dir") +
						"/src/test/java/ru/ifmo/cs/computer_science/lab1/buffered_input.txt");
		} catch (IOException e) {
			log.add(new Pair(e, "can't create buffered_input.txt"));
			return;
		}

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
}
