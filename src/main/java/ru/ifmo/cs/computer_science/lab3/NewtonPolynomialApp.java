package ru.ifmo.cs.computer_science.lab3;

import ru.ifmo.cs.computer_science.lab2.utils.YXFunctionWithNameAndRelativePath;

import javax.swing.*;
import java.util.ArrayList;

public class NewtonPolynomialApp {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(NewtonPolynomialApp::gui);
	}

	private static void gui() {
		GuiLab3.gui(initYXFunctionsWithNamesAndRelativePaths());
	}

	private static ArrayList<YXFunctionWithNameAndRelativePath> initYXFunctionsWithNamesAndRelativePaths() {
		ArrayList<YXFunctionWithNameAndRelativePath> functions = new ArrayList<>();
		functions.add(new YXFunctionWithNameAndRelativePath(
				(double x) -> x / (Math.pow(x, 4) + 4), "x/(x^4+4)", "func1.png"));
		functions.add(new YXFunctionWithNameAndRelativePath(
				(double x) -> x, "x", "func2.png"));
		functions.add(new YXFunctionWithNameAndRelativePath(
				Math::sin, "sin(x)", "func3.png"));
//		functions.add(new YXFunctionWithNameAndRelativePath(
//              (double x) -> Math.exp(x) / x, "e^x/x", "func4.png"));
//		functions.add(new YXFunctionWithNameAndRelativePath(
//              (double x) -> Math.atan(x) / x, "arctg(x)/x", "func5.png"));
		return functions;
	}
}
