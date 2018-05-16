package ru.ifmo.cs.computer_science.lab5;

import ru.ifmo.cs.computer_science.lab4.utils.FYXFunctionSystemWithNameAndRelativePath;
import ru.ifmo.cs.computer_science.lab5.utils.FXYZFunctionWithNameAndRelativePath;

import javax.swing.*;
import java.util.ArrayList;

public class ShootingMethodApp {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(ShootingMethodApp::gui);
	}

	private static void gui() {
		GuiLab5.gui(initFXYZFunctionWithNameAndRelativePath());
	}

	private static ArrayList<FXYZFunctionWithNameAndRelativePath> initFXYZFunctionWithNameAndRelativePath() {
		ArrayList<FXYZFunctionWithNameAndRelativePath> functions = new ArrayList<>();

		// http://info.alnam.ru/book_clm.php?id=122
		functions.add(new FXYZFunctionWithNameAndRelativePath(
				(double x, double y, double z) -> z,
				(double x, double y, double z) -> 100*y + Math.pow(Math.E, x),
				"y\'(x) = z(x); z\'(x)=100y(x)+e^x", "func2.png"));

		return functions;
	}
}
