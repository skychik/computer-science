package ru.ifmo.cs.computer_science.lab4;

import org.jfree.ui.RefineryUtilities;
import ru.ifmo.cs.computer_science.lab4.utils.AdamsMethod;
import ru.ifmo.cs.computer_science.lab4.utils.SimpleGraphic;
import ru.ifmo.cs.computer_science.lab4.utils.FYXFunctionSystemWithNameAndRelativePath;
import ru.ifmo.cs.computer_science.lab4.utils.Point;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static java.awt.GridBagConstraints.REMAINDER;

// --------------------------------------------------
// ------------- TODO: timeout - 10 sec -------------
// --------------------------------------------------


class GuiLab4 {
	private static ArrayList<FYXFunctionSystemWithNameAndRelativePath> FYXFunctionsWithNamesAndRelativePaths;
	private static ArrayList<FYXFunctionSystemWithNameAndRelativePath> getFYXFunctionsWithNamesAndRelativePaths() {
		return FYXFunctionsWithNamesAndRelativePaths;
	}
	private static JFrame frame;
	private static Dimension frameSize = new Dimension(550, 300);
	private static FYXFunctionSystemWithNameAndRelativePath selectedFunction;
	private static double selectedX;
	private static JTable table;
	private static DefaultTableModel tableModel;
	private static JFormattedTextField X0Field;
	private static JFormattedTextField Y0Field;
	private static JFormattedTextField LastXField;
	private static JFormattedTextField EpsilonField;

	static void gui(ArrayList<FYXFunctionSystemWithNameAndRelativePath> functions) {
		FYXFunctionsWithNamesAndRelativePaths = functions;
		frame = createFrame();
		frame.pack();
		frame.setVisible(true);
	}

	private static JFrame createFrame() {
		JFrame frame = new JFrame("Koshi(Adams) Application");

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
		addX0(container);
		addY0(container);
		addLastX(container);
		addEpsilon(container);
		addProceedAdamsMethodButton(container);
		addMakeGraphicButton(container);
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
		JLabel label = new JLabel("Cauchy problem: y’ + f(x,y) = 0");
		label.setBorder(BorderFactory.createRaisedBevelBorder());
		container.add(label, constraints);
	}

	private static void addAllFunctions(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.NONE;
		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton[] buttons = new JRadioButton[getFYXFunctionsWithNamesAndRelativePaths().size()];
		for (int i = 0; i < getFYXFunctionsWithNamesAndRelativePaths().size(); i++) {
			FYXFunctionSystemWithNameAndRelativePath functionAndPhoto = getFYXFunctionsWithNamesAndRelativePaths().get(i);

			// Add photo of radio button
			constraints.gridx = i;
			constraints.gridy = 1;
			constraints.ipady = 50;

			container.add(new JLabel(new ImageIcon(System.getProperty("user.dir") +
							"/src/main/resources/lab4.formula_photos/" +
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

	private static void addX0(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 4;

		container.add(new JLabel("x0:"), constraints);

		constraints.gridx = 1;

		NumberFormat format = DecimalFormat.getInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(1);
		format.setMaximumIntegerDigits(5);
		format.setMinimumIntegerDigits(1);

		X0Field = new JFormattedTextField(new NumberFormatter(format));
		X0Field.setColumns(11);
		X0Field.setValue(0.0f);

		container.add(X0Field, constraints);
	}

	private static void addY0(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 5;

		container.add(new JLabel("y0:"), constraints);

		constraints.gridx = 1;

		NumberFormat format = DecimalFormat.getInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(1);
		format.setMaximumIntegerDigits(5);
		format.setMinimumIntegerDigits(1);

		Y0Field = new JFormattedTextField(new NumberFormatter(format));
		Y0Field.setColumns(11);
		Y0Field.setValue(0.0f);

		container.add(Y0Field, constraints);
	}

	private static void addLastX(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 6;

		container.add(new JLabel("Last X:"), constraints);

		constraints.gridx = 1;

		NumberFormat format = DecimalFormat.getInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(1);
		format.setMaximumIntegerDigits(5);
		format.setMinimumIntegerDigits(1);

		LastXField = new JFormattedTextField(new NumberFormatter(format));
		LastXField.setColumns(11);
		LastXField.setValue(0.0f);

		container.add(LastXField, constraints);
	}

	private static void addEpsilon(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 7;

		container.add(new JLabel("epsilon:"), constraints);

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

	private static void addProceedAdamsMethodButton(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 8;
		//constraints.gridwidth = REMAINDER;

		JButton proceedAdamsMethodButton = new JButton("Proceed Adams method"); // TODO: graphic as internal frame
		proceedAdamsMethodButton.addActionListener(e -> {
			try {
				if (selectedFunction == null) throw new IllegalArgumentException("Choose function");

				AdamsMethod method = new AdamsMethod(selectedFunction.getFunction(),
						new Point<>(((Number) X0Field.getValue()).doubleValue(),
								((Number) Y0Field.getValue()).doubleValue()),
						((Number) LastXField.getValue()).doubleValue(),
						((Number) EpsilonField.getValue()).doubleValue());

				int rowCount = tableModel.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					tableModel.removeRow(0);
					frameSize.height -= 15;
				}
				if (rowCount >= 40) {
					frameSize.height -= 5;
				}
				for (int i = 0; i < method.getAnswer().size(); i++) {
					Vector<String> v = new Vector<>();
					if (method.getAnswer().size() > 40  && i >= 20) {
						// v.add("And " + (method.getAnswer().size() - 10) + " more...");
						tableModel.addRow(new String[] {"And " + (method.getAnswer().size() - 40) + " more...", ""});
						frameSize.height += 20;
						frame.setSize(frameSize);

						for (int j = method.getAnswer().size() - 19; j < method.getAnswer().size(); j++) {
							Vector<String> v1 = new Vector<>();
							Point<Double, Double> p = method.getAnswer().get(j);
							v1.add(Double.toString(p.getX()));
							v1.add(Double.toString(p.getY()));
							if (!tableModel.getDataVector().contains(v1)) {
								tableModel.addRow(new String[] {Double.toString(p.getX()), Double.toString(p.getY())});
								frameSize.height += 15;
								frame.setSize(frameSize);
							}
						}
						break;
					}
					Point<Double, Double> p = method.getAnswer().get(i);
					v.add(Double.toString(p.getX()));
					v.add(Double.toString(p.getY()));
					if (!tableModel.getDataVector().contains(v)) {
						tableModel.addRow(new String[] {Double.toString(p.getX()), Double.toString(p.getY())});
						frameSize.height += 15;
						frame.setSize(frameSize);

	//					TableRowSorter sorter = new TableRowSorter<>(tableModel);
	//					table.setRowSorter(sorter);
	//					sorter.setSortsOnUpdates(true);
	//					tableModel.fireTableDataChanged();
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

		container.add(proceedAdamsMethodButton, constraints);
	}

	private static void addMakeGraphicButton(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 8;
		//constraints.gridwidth = REMAINDER;

		JButton makeGraphicButton = new JButton("Make graphic"); // TODO: graphic as internal frame
		makeGraphicButton.addActionListener(e -> {
			if (table.getRowCount() == 0) {
				// Show answer
				JOptionPane.showMessageDialog(frame, new JLabel("Add points"), "Answer",
						JOptionPane.ERROR_MESSAGE);
			} else {
				List<Point<Double, Double>> points = new ArrayList<>(table.getRowCount());
				for (int i = 0; i < table.getModel().getRowCount(); i++) {
					points.add(new Point<>(Double.valueOf(table.getModel().getValueAt(i, 0).toString()),
							Double.valueOf(table.getModel().getValueAt(i, 1).toString())));
				}
				SimpleGraphic graphic = new SimpleGraphic("Graphic", points);
				graphic.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

				graphic.pack();
				RefineryUtilities.centerFrameOnScreen(graphic);
				graphic.setVisible(true);
			}
		});

		container.add(makeGraphicButton, constraints);
	}

	private static void addTable(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 9;
		constraints.gridwidth = REMAINDER;

		tableModel = new DefaultTableModel(new String[][]{}, new String[]{"y", "x"}) {
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
