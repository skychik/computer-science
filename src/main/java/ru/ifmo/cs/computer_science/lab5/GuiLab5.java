package ru.ifmo.cs.computer_science.lab5;

import ru.ifmo.cs.computer_science.lab4.utils.Point;
import ru.ifmo.cs.computer_science.lab5.utils.FXYZFunctionWithNameAndRelativePath;
import ru.ifmo.cs.computer_science.lab5.utils.Point3;
import ru.ifmo.cs.computer_science.lab5.utils.ShootingMethod;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;

import static java.awt.GridBagConstraints.REMAINDER;

public class GuiLab5 {
	private static ArrayList<FXYZFunctionWithNameAndRelativePath> FXYZFunctionWithNameAndRelativePath;
	private static ArrayList<FXYZFunctionWithNameAndRelativePath> getFXYZFunctionWithNameAndRelativePaths() {
		return FXYZFunctionWithNameAndRelativePath;
	}
	private static JFrame frame;
	private static Dimension frameSize = new Dimension(550, 300);
	private static FXYZFunctionWithNameAndRelativePath selectedFunction;
	private static double selectedX;
	private static JTable table;
	private static DefaultTableModel tableModel;
	private static JFormattedTextField AField;
	private static JFormattedTextField YAField;
	private static JFormattedTextField BField;
	private static JFormattedTextField ZBField;
	private static JFormattedTextField EpsilonField;


	public static void gui(ArrayList<FXYZFunctionWithNameAndRelativePath> functions) {
		FXYZFunctionWithNameAndRelativePath = functions;
		frame = createFrame();
		frame.pack();
		frame.setVisible(true);
	}

	private static JFrame createFrame() {
		JFrame frame = new JFrame("Shooting method");

		createContentPane(frame);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int locationX = (screenSize.width - frameSize.width) / 2;
		int locationY = (screenSize.height - frameSize.height) / 2;
		frame.setBounds(locationX, locationY, frameSize.width, frameSize.height);
		frame.setPreferredSize(frameSize);
		//frame.setLocationRelativeTo(null);//по центру экрана
		frame.setResizable(false);

		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose();
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

		addFunctionLabel(container);
		addAllFunctions(container);
		addA(container);
		addYA(container);
		addB(container);
		addZB(container);
		addEpsilon(container);
		addProceedShootingMethodButton(container);
		//addMakeGraphicButton(container);
		addTable(container);
	}

	private static void addFunctionLabel(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 99;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.ipadx = 2;
		constraints.ipady = 5;
		constraints.gridx = 0;
		constraints.gridy = 0;
		JLabel label = new JLabel("y’ = f(x,y,z); z’ = g(x,y,z)");
		label.setBorder(BorderFactory.createRaisedBevelBorder());
		container.add(label, constraints);
	}

	private static void addAllFunctions(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.NONE;
		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton[] buttons = new JRadioButton[getFXYZFunctionWithNameAndRelativePaths().size()];
		for (int i = 0; i < getFXYZFunctionWithNameAndRelativePaths().size(); i++) {
			FXYZFunctionWithNameAndRelativePath functionAndPhoto = getFXYZFunctionWithNameAndRelativePaths().get(i);

			// Add photo of radio button
			constraints.gridx = i;
			constraints.gridy = 1;
			constraints.ipady = 50;

			container.add(new JLabel(new ImageIcon(System.getProperty("user.dir") +
							"/src/main/resources/lab5.formula_photos/" +
							functionAndPhoto.getRelativePath())),
					constraints);

			// Add radio button
			buttons[i] = new JRadioButton(functionAndPhoto.getName());
			buttonGroup.add(buttons[i]);
			buttons[i].addChangeListener(e -> {
				if (((JRadioButton) e.getSource()).getModel().isPressed()) {
					if (functionAndPhoto.equals(selectedFunction)) tableModel.setNumRows(0);
					selectedFunction = functionAndPhoto;
				}
			});

			constraints.gridx = i;
			constraints.gridy = 2;
			constraints.ipady = 0;

			container.add(buttons[i], constraints);
		}
	}

	private static void addA(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 3;

		container.add(new JLabel("a:"), constraints);

		constraints.gridx = 1;

		NumberFormat format = DecimalFormat.getInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(1);
		format.setMaximumIntegerDigits(5);
		format.setMinimumIntegerDigits(1);

		AField = new JFormattedTextField(new NumberFormatter(format));
		AField.setColumns(11);
		AField.setValue(0.0f);

		container.add(AField, constraints);
	}

	private static void addYA(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 4;

		container.add(new JLabel("y(a):"), constraints);

		constraints.gridx = 1;

		NumberFormat format = DecimalFormat.getInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(1);
		format.setMaximumIntegerDigits(5);
		format.setMinimumIntegerDigits(1);

		YAField = new JFormattedTextField(new NumberFormatter(format));
		YAField.setColumns(11);
		YAField.setValue(0.0f);

		container.add(YAField, constraints);
	}

	private static void addB(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 5;

		container.add(new JLabel("b:"), constraints);

		constraints.gridx = 1;

		NumberFormat format = DecimalFormat.getInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(1);
		format.setMaximumIntegerDigits(5);
		format.setMinimumIntegerDigits(1);

		BField = new JFormattedTextField(new NumberFormatter(format));
		BField.setColumns(11);
		BField.setValue(0.0f);

		container.add(BField, constraints);
	}

	private static void addZB(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 6;

		container.add(new JLabel("z(b):"), constraints);

		constraints.gridx = 1;

		NumberFormat format = DecimalFormat.getInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(1);
		format.setMaximumIntegerDigits(5);
		format.setMinimumIntegerDigits(1);

		ZBField = new JFormattedTextField(new NumberFormatter(format));
		ZBField.setColumns(11);
		ZBField.setValue(0.0f);

		container.add(ZBField, constraints);
	}

	private static void addEpsilon(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 7;

		container.add(new JLabel("Epsilon:"), constraints);

		constraints.gridx = 1;

		NumberFormat format = DecimalFormat.getInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(1);
		format.setMaximumIntegerDigits(5);
		format.setMinimumIntegerDigits(1);

		EpsilonField = new JFormattedTextField(new NumberFormatter(format));
		EpsilonField.setColumns(11);
		EpsilonField.setValue(0.0f);

		container.add(EpsilonField, constraints);
	}

	private static void addProceedShootingMethodButton(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 8;

		JButton proceedShootingMethodButton = new JButton("Proceed Shooting method"); // TODO: graphic as internal frame
		proceedShootingMethodButton.addActionListener(e -> {
			try {
				if (selectedFunction == null) throw new IllegalArgumentException("Choose function");

				ShootingMethod method = new ShootingMethod(selectedFunction.getFirstFunction(), selectedFunction.getSecondFunction(),
						((Number) AField.getValue()).doubleValue(), ((Number) YAField.getValue()).doubleValue(),
						((Number) BField.getValue()).doubleValue(), ((Number) ZBField.getValue()).doubleValue(),
						((Number) EpsilonField.getValue()).doubleValue());

				// TODO ...
				for (int i = 0; i < method.getAnswer().size(); i++) {
					Vector<String> v = new Vector<>();
					if (i >= 40) {
						// v.add("And " + (method.getAnswer().size() - 10) + " more...");
						tableModel.addRow(new String[] {"And " + (method.getAnswer().size() - 40) + " more...", ""});
						frameSize.height += 20;
						frame.setSize(frameSize);
						break;
					}
					Point3<Double, Double, Double> p = method.getAnswer().get(i);
					v.add(Double.toString(p.getX()));
					v.add(Double.toString(p.getY()));
					v.add(Double.toString(p.getZ()));
					if (!tableModel.getDataVector().contains(v)) {
						tableModel.addRow(new String[] {Double.toString(p.getX()), Double.toString(p.getY()), Double.toString(p.getZ())});
						frameSize.height += 15;
						frame.setSize(frameSize);
					}
				}
			} catch (IllegalArgumentException e1) {
				// Show answer
				JOptionPane.showMessageDialog(frame, new JLabel(e1.getMessage()), "Answer",
						JOptionPane.ERROR_MESSAGE);
			} catch (ClassCastException e2) {
				JOptionPane.showMessageDialog(frame, new JLabel("Incorrect format of input number:\n" +
						"Should be real"), "Answer", JOptionPane.ERROR_MESSAGE);
			}
		});

		container.add(proceedShootingMethodButton, constraints);
	}

	private static void addTable(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 9;
		constraints.gridwidth = REMAINDER;

		tableModel = new DefaultTableModel(new String[][][]{}, new String[]{"x", "y", "z"}) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(tableModel);
		table.setCellSelectionEnabled(false);

		container.add(table, constraints);
	}
}
