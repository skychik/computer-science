package ru.ifmo.cs.computer_science.lab2;

import ru.ifmo.cs.computer_science.lab1.utils.Pair;
import ru.ifmo.cs.computer_science.lab2.utils.DefiniteIntegral;
import ru.ifmo.cs.computer_science.lab2.utils.SimpsonFunctionBuilder;
import ru.ifmo.cs.computer_science.lab2.utils.YXFunction;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class SimpsonApplication {
	private static ArrayList<Pair<YXFunction, String>> functionsAndPhotos;
	private static SimpsonFunctionBuilder builder = new SimpsonFunctionBuilder();
	private static Pair<YXFunction, String> selectedFunction;
	private static JFrame frame;
	private static JFormattedTextField epsilon;
	private static JFormattedTextField higherLimit;
	private static JFormattedTextField lowerLimit;

	public static void main(String[] args) {
		initFunctionsAndPhotos();

		//GUI
		SwingUtilities.invokeLater(SimpsonApplication::gui);
	}

	private static void gui() {
		frame = createFrame();


		//frame.setLocationRelativeTo(null);//по центру экрана
		frame.setResizable(false);

		frame.pack();
		frame.setVisible(true);
	}

	private static JFrame createFrame() {
		JFrame frame = new JFrame("Simpson Application");

		createContentPane(frame);

		Dimension frameSize = new Dimension(450, 200);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int locationX = (screenSize.width - frameSize.width) / 2;
		int locationY = (screenSize.height - frameSize.height) / 2;
		frame.setBounds(locationX, locationY, frameSize.width, frameSize.height);
		frame.setPreferredSize(frameSize);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		return frame;
	}

	private static void createContentPane(JFrame frame) {
		Container container = frame.getContentPane();
		container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagLayout layout = new GridBagLayout();
		container.setLayout(layout);
		container.getInsets().set(20, 20, 20, 20);
		//UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);

		addAllFunctions(container);
		addLowerLimit(container);
		addHigherLimit(container);
		addEpsilon(container);
		addButton(container);
	}

	private static void addAllFunctions(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton[] buttons = new JRadioButton[getFunctionsAndPhotos().size()];
		for (int i = 0; i < getFunctionsAndPhotos().size(); i++) {
			Pair<YXFunction, String> functionAndPhoto = getFunctionsAndPhotos().get(i);

			// Add photo of radio button
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = i;
			constraints.gridy = 0;

			container.add(new JLabel(new ImageIcon(System.getProperty("user.dir") +
							"/src/main/java/ru/ifmo/cs/computer_science/lab2/formula_photos/" +
							functionAndPhoto.getSecond())),
					constraints);

			// Add radio button
			buttons[i] = new JRadioButton(functionAndPhoto.getSecond());
			buttonGroup.add(buttons[i]);
			buttons[i].addChangeListener(e -> {
				if (((JRadioButton) e.getSource()).getModel().isPressed()) {
					selectedFunction = functionAndPhoto;
				}
			});

			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = i;
			constraints.gridy = 1;

			container.add(buttons[i], constraints);
		}
	}

	private static void addLowerLimit(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 2;

		container.add(new JLabel("Lower limit:"), constraints);

		NumberFormat format = DecimalFormat.getInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(1);
		format.setMaximumIntegerDigits(5);
		format.setMinimumIntegerDigits(1);

		JFormattedTextField lowerLimitField = new JFormattedTextField(new NumberFormatter(format));
		lowerLimitField.setColumns(11);
		lowerLimitField.setValue(0.0f);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 100;

		container.add(lowerLimitField, constraints);

		lowerLimit = lowerLimitField;
	}

	private static void addHigherLimit(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 3;

		container.add(new JLabel("Higher limit:"), constraints);

		NumberFormat format = DecimalFormat.getInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(1);
		format.setMaximumIntegerDigits(5);
		format.setMinimumIntegerDigits(1);

		JFormattedTextField higherLimitField = new JFormattedTextField(new NumberFormatter(format));
		higherLimitField.setColumns(11);
		higherLimitField.setValue(0.0f);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 100;

		container.add(higherLimitField, constraints);

		higherLimit = higherLimitField;
	}

	private static void addEpsilon(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 4;

		container.add(new JLabel("Expected epsilon:"), constraints);

		NumberFormat format = DecimalFormat.getInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(1);
		format.setMaximumIntegerDigits(5);
		format.setMinimumIntegerDigits(1);

		JFormattedTextField epsilonField = new JFormattedTextField(new NumberFormatter(format));
		epsilonField.setColumns(11);
		epsilonField.setValue(1.0f);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.gridwidth = 100;

		container.add(epsilonField, constraints);

		epsilon = epsilonField;
	}

	private static void addButton(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = GridBagConstraints.RELATIVE;
		constraints.gridy = 5;
		constraints.gridwidth = 1000; // just a big number

		JButton button = new JButton("Calculate the definite integral");
		button.addActionListener(e -> {
			try {
				if (selectedFunction == null) throw new IOException("Choose function");
				//if ((float) epsilon.getValue() <= 0.0f) throw new IOException("epsilon should be > 0");
				DefiniteIntegral integral = new DefiniteIntegral(builder.buildFunction(selectedFunction.getFirst()),
						((Number) lowerLimit.getValue()).doubleValue(), ((Number) higherLimit.getValue()).doubleValue(),
						((Number) epsilon.getValue()).doubleValue());
				JTextArea answer = new JTextArea();
				answer.setOpaque(false);
//				answer.append("function = " + selectedFunction.getSecond() + '\n');
//				answer.append("lower limit = " + ((Number) lowerLimit.getValue()).doubleValue() + '\n');
//				answer.append("higher limit = " + ((Number) higherLimit.getValue()).doubleValue() + '\n');
//				answer.append("expected epsilon = " + ((Number) epsilon.getValue()).doubleValue() + '\n');
				answer.append("----Answer:----\n");
				answer.append("integral = " + integral.getDefiniteIntegral() + '\n');
				answer.append("number of splits = " + integral.getNumberOfSplits() + '\n');
				answer.append("epsilon = " + integral.getRungeEpsilon());
				// Show answer
				JOptionPane.showMessageDialog(frame, answer, "Answer",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e1) {
				// Show answer
				JOptionPane.showMessageDialog(frame, new JLabel(e1.getMessage()), "Answer",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		container.add(button,constraints);
	}

	private static void initFunctionsAndPhotos() {
		functionsAndPhotos = new ArrayList<>();
		functionsAndPhotos.add(new Pair<>((double x) -> x / (Math.pow(x, 4) + 4),
				"func1.png"));
		functionsAndPhotos.add(new Pair<>((double x) -> x,
				"func2.png"));
		functionsAndPhotos.add(new Pair<>(Math::sin,
				"func3.png"));
		functionsAndPhotos.add(new Pair<>((double x) -> Math.exp(x) / x,
				"func4.png"));
		functionsAndPhotos.add(new Pair<>((double x) -> Math.atan(x) / x,
				"func5.png"));
	}

	private static ArrayList<Pair<YXFunction, String>> getFunctionsAndPhotos() {
		return functionsAndPhotos;
	}
}
