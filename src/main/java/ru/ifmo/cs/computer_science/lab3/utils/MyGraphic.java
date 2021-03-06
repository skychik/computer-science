package ru.ifmo.cs.computer_science.lab3.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import ru.ifmo.cs.computer_science.lab2.utils.YXFunction;
import ru.ifmo.cs.computer_science.lab4.utils.Point;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.List;

public class MyGraphic extends ApplicationFrame {
	private final Dimension graphicSize = new Dimension(1600, 800);

	public MyGraphic(String title, List<Point<Double, Double>> points, YXFunction function, double x) {
		super(title);

		//if (xCoordinates.length != yCoordinates.length) throw new IllegalArgumentException("Different number of x and y coordinates");

		XYSeries pointsSeries = new XYSeries("Points");
		for (Point<Double, Double> p : points) {
			pointsSeries.add(p.getX(), p.getY());
		}

			XYSeries expectedFunctionSeries = new XYSeries("Expected function");
			double min = points.stream().map(Point::getX).min(Double::compareTo).get();
			double max = points.stream().map(Point::getX).max(Double::compareTo).get();
		if (function != null) {
			for (double i = min - (max - min) / 10; i < max + (max - min) / 10; i += (max - min) / 1000) {
				expectedFunctionSeries.add(i, function.getY(i));
			}
		}

		NewtonPolynomial polynomial = new NewtonPolynomial(points);
		XYSeries polynomialSeries = new XYSeries("Polynomial");
		for (double i = min; i < max; i += (max - min) / 1000) {
			polynomialSeries.add(i, polynomial.getY(i));
		}


		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(pointsSeries);
		dataset.addSeries(expectedFunctionSeries);
		dataset.addSeries(polynomialSeries);

		final JFreeChart chart = createChart(dataset, x, polynomial);
		final ChartPanel chartPanel = new ChartPanel(chart);

		chartPanel.setPreferredSize(graphicSize);
		setContentPane(chartPanel);
	}

	private JFreeChart createChart(final XYDataset dataset, double x, NewtonPolynomial polynomial) {
		final JFreeChart chart = ChartFactory.createXYLineChart(
				"f(" + Double.toString(x) + ") = " + Double.toString(polynomial.getY(x)),       // chart title
				"X",                      // domain axis label
				"Y",                // range axis label
				dataset,                   // data
				PlotOrientation.VERTICAL, true, true, false
		);

		chart.setBackgroundPaint(Color.white);

		return chart;
	}

//	private double min(double[] mas){
//		double minim = mas[0];
////		int index = 0;
//		for (int i = 1; i < mas.length; i++) {
//			if (mas[i] < minim) {
//				minim = mas[i];
////				index = i;
//			}
//		}
//		return minim;
//	}
//
//	private double max(double[] mas){
//		double maxim = mas[0];
////		int index = 0;
//		for (int i = 1; i < mas.length; i++) {
//			if (mas[i] > maxim) {
//				maxim = mas[i];
////				index = i;
//			}
//		}
//		return maxim;
//	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.dispose();
	}
}
