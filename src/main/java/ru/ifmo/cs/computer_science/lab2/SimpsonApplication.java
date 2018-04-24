package ru.ifmo.cs.computer_science.lab2;

import ru.ifmo.cs.computer_science.lab2.utils.DefinedIntegralSimpsonMethod;
import ru.ifmo.cs.computer_science.lab2.utils.YXFunctionWithNameAndRelativePath;
import ru.ifmo.cs.computer_science.lab3.GuiLab3;
import ru.ifmo.cs.computer_science.lab3.NewtonPolynomialApp;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import static java.awt.GridBagConstraints.REMAINDER;

public class SimpsonApplication {

	public static void main(String[] args) {
		//GUI
		SwingUtilities.invokeLater(SimpsonApplication::gui);
	}

	private static void gui() {
		GuiLab2.gui(initYXFunctionsWithNamesAndRelativePaths());
	}

	private static ArrayList<YXFunctionWithNameAndRelativePath> initYXFunctionsWithNamesAndRelativePaths() {
		ArrayList<YXFunctionWithNameAndRelativePath> functions = new ArrayList<>();
		functions.add(new YXFunctionWithNameAndRelativePath((double x) -> x / (Math.pow(x, 4) + 4),
				"x/(x^4+4)", "func1.png"));
		functions.add(new YXFunctionWithNameAndRelativePath((double x) -> x,
				"x", "func2.png"));
		functions.add(new YXFunctionWithNameAndRelativePath(Math::sin,
				"sin(x)", "func3.png"));
		functions.add(new YXFunctionWithNameAndRelativePath((double x) -> Math.exp(x) / x,
				"e^x/x", "func4.png"));
		functions.add(new YXFunctionWithNameAndRelativePath((double x) -> Math.atan(x) / x,
				"arctg(x)/x", "func5.png"));

		return functions;
	}
}
