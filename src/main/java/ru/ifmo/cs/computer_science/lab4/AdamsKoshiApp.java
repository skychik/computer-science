package ru.ifmo.cs.computer_science.lab4;

import ru.ifmo.cs.computer_science.lab4.utils.FYXFunctionSystemWithNameAndRelativePath;

import javax.swing.*;
import java.util.ArrayList;

public class AdamsKoshiApp {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(AdamsKoshiApp::gui);
	}

	private static void gui() {
		GuiLab4.gui(initYXFunctionsWithNamesAndRelativePaths());
	}

	private static ArrayList<FYXFunctionSystemWithNameAndRelativePath> initYXFunctionsWithNamesAndRelativePaths() {
		ArrayList<FYXFunctionSystemWithNameAndRelativePath> functions = new ArrayList<>();

		functions.add(new FYXFunctionSystemWithNameAndRelativePath(
				(double x, double y) -> x, "f(x,y) = x", "func2.png"));
		functions.add(new FYXFunctionSystemWithNameAndRelativePath(
				(double x, double y) -> y + x, "f(x,y) = y+x", "func1.png"));

		return functions;
	}
}
