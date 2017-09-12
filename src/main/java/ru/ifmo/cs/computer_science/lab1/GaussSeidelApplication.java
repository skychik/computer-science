package ru.ifmo.cs.computer_science.lab1;

import java.util.Scanner;

public class GaussSeidelApplication {
	public static void main(String[] args) {
		System.out.println("Do you want to enter data manually?\n" +
				"y or n?:");
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		switch (str.toLowerCase()) {
			case "y":
			case "yes":
				manualInput();
				break;
			case "n":
			case "no":
				fileInput();
				break;
			default:
				System.out.println("Wrong answer: " + str + "\n" +
						"Try again");
				main(args);
		}
	}

	private static void fileInput() {
	}

	private static void manualInput() {
	}
}
