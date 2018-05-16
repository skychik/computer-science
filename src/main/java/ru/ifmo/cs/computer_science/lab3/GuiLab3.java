package ru.ifmo.cs.computer_science.lab3;

import org.jfree.ui.RefineryUtilities;
import ru.ifmo.cs.computer_science.lab2.utils.YXFunctionWithNameAndRelativePath;
import ru.ifmo.cs.computer_science.lab3.utils.MyGraphic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import ru.ifmo.cs.computer_science.lab4.utils.Point;

import static java.awt.GridBagConstraints.REMAINDER;

class GuiLab3 {
	private static ArrayList<YXFunctionWithNameAndRelativePath> YXFunctionsWithNamesAndRelativePaths;
	private static ArrayList<YXFunctionWithNameAndRelativePath> getYXFunctionsWithNamesAndRelativePaths() {
		return YXFunctionsWithNamesAndRelativePaths;
	}

	private static JFrame frame;
	private static Dimension frameSize = new Dimension(450, 200);
	private static YXFunctionWithNameAndRelativePath selectedFunction;
	private static double selectedX;
	private static JTable table;
	private static DefaultTableModel tableModel;
	private static JFormattedTextField inputXForTableField;
	private static JFormattedTextField setXField;

	static void gui(ArrayList<YXFunctionWithNameAndRelativePath> functions) {
		YXFunctionsWithNamesAndRelativePaths = functions;
		frame = createFrame();
		frame.pack();
		frame.setVisible(true);
	}

	private static JFrame createFrame() {
		JFrame frame = new JFrame("Newton Polynomial Application");

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

		addAllFunctions(container);
		addInputXForTable(container);
		addTable(container);
		addMakeGraphicButton(container);
		addSettingX(container);
	}

	private static void addAllFunctions(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1;
		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton[] buttons = new JRadioButton[getYXFunctionsWithNamesAndRelativePaths().size()];
		for (int i = 0; i < getYXFunctionsWithNamesAndRelativePaths().size(); i++) {
			YXFunctionWithNameAndRelativePath functionAndPhoto = getYXFunctionsWithNamesAndRelativePaths().get(i);

			// Add photo of radio button
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = i;
			constraints.gridy = 0;

			container.add(new JLabel(new ImageIcon(System.getProperty("user.dir") +
							"/src/main/resources/lab3/formula_photos/" +
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

			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = i;
			constraints.gridy = 1;
			//constraints.anchor = SwingConstants.CENTER;

			container.add(buttons[i], constraints);
		}
	}

	private static void addInputXForTable(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 2;

		container.add(new JLabel("x of new point:"), constraints);

		constraints.gridx = 1;

		NumberFormat format = DecimalFormat.getInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(1);
		format.setMaximumIntegerDigits(5);
		format.setMinimumIntegerDigits(1);

		inputXForTableField = new JFormattedTextField(new NumberFormatter(format));
		inputXForTableField.setColumns(11);
		inputXForTableField.setValue(0.0f);

		container.add(inputXForTableField, constraints);

		constraints.gridx = 2;

		JButton addingPointButton = new JButton("Add");
		addingPointButton.addActionListener((e -> {
			try {
				if (selectedFunction == null) throw new IllegalArgumentException("Choose function");

				double x = ((Number) inputXForTableField.getValue()).doubleValue();
				Vector<String> v = new Vector<>();
				v.add(Double.toString(x));
				v.add(Double.toString(selectedFunction.getFunction().getY(x)));
				v.add("delete");
				if (!tableModel.getDataVector().contains(v)) {
					tableModel.addRow(new String[]{
							Double.toString(x),
							Double.toString(selectedFunction.getFunction().getY(x)),
							"delete"
					});

					frameSize.height += 20;
					frame.setSize(frameSize);

//					TableRowSorter sorter = new TableRowSorter<>(tableModel);
//					table.setRowSorter(sorter);
//					sorter.setSortsOnUpdates(true);
//					tableModel.fireTableDataChanged();
				}
			} catch (IllegalArgumentException e1) {
				// Show answer
				JOptionPane.showMessageDialog(frame, new JLabel(e1.getMessage()), "Answer",
						JOptionPane.ERROR_MESSAGE);
			} catch (ClassCastException e2) {
				JOptionPane.showMessageDialog(frame, new JLabel("Incorrect format of input number:\n" +
						"Should be real"), "Answer", JOptionPane.ERROR_MESSAGE);
			}
		}));

		container.add(addingPointButton, constraints);
	}

	private static void addSettingX(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 3;

		container.add(new JLabel("x0:"), constraints);

		constraints.gridx = 1;

		NumberFormat format = DecimalFormat.getInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(1);
		format.setMaximumIntegerDigits(5);
		format.setMinimumIntegerDigits(1);

		setXField = new JFormattedTextField(new NumberFormatter(format));
		setXField.setColumns(11);
		setXField.setValue(0.0f);

		container.add(setXField, constraints);

		constraints.gridx = 2;

		JButton settingXButton = new JButton("Set");
		settingXButton.addActionListener(e -> selectedX = ( (Number) setXField.getValue()).doubleValue());

		container.add(settingXButton, constraints);
	}

	private static void addMakeGraphicButton(Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = REMAINDER;

		JButton makeGraphicButton = new JButton("Make graphic"); // TODO: graphic as internal frame
		makeGraphicButton.addActionListener(e -> {
			if (table.getRowCount() == 0) {
				// Show answer
				JOptionPane.showMessageDialog(frame, new JLabel("Add points"), "Answer",
						JOptionPane.ERROR_MESSAGE);
			} else {
				List<Point<Double, Double>> points = new ArrayList<>(table.getRowCount());
//				double[] xCoordinates = new double[table.getRowCount()];
//				double[] yCoordinates = new double[table.getRowCount()];
				for (int i = 0; i < table.getModel().getRowCount(); i++) {
					points.add(new Point<>(Double.valueOf(table.getModel().getValueAt(i, 0).toString()),
							Double.valueOf(table.getModel().getValueAt(i, 1).toString())));
//					xCoordinates[i] = Double.valueOf(table.getModel().getValueAt(i, 0).toString());
//					yCoordinates[i] = Double.valueOf(table.getModel().getValueAt(i, 1).toString());
				}
				MyGraphic graphic = new MyGraphic("Graphic", points,
						selectedFunction.getFunction(), selectedX);
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
		constraints.gridy = 4;
		constraints.gridwidth = REMAINDER;

		tableModel = new DefaultTableModel(new String[][]{}, new String[]{"y", "x", ""}) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(tableModel);
		table.setCellSelectionEnabled(false);
		table.addMouseListener(new MouseListener() {
			int columnPressed;
			int rowPressed;

			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				columnPressed = table.columnAtPoint(e.getPoint());
				rowPressed = table.rowAtPoint(e.getPoint());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				int column = table.columnAtPoint(e.getPoint());
				int row = table.rowAtPoint(e.getPoint());

				if (column == columnPressed && row == rowPressed &&
						e.getButton() == MouseEvent.BUTTON1 && column == 2) {
					tableModel.removeRow(row);
					frameSize.height -= 20;
					frame.setSize(frameSize);
				}
			}
		});

		container.add(table, constraints);
	}
}
