package exercise1;

import java.awt.Color;
import java.util.ArrayList;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import grafica.GLayer;
import grafica.GPlot;
import grafica.GPoint;
import grafica.GPointsArray;
import processing.core.PApplet;

public class FunctionPlotter1D {
	private Function functionToPlot;
	private double start;
	private double end;
	private int numberOfGridPoints = 500;
	private GPointsArray points;
	private GPlot plot;
	private ArrayList<LineSegmentOption> additionalLines;
	private ArrayList<PointOption> additionalPoints;

	public FunctionPlotter1D(Function f, double a, double b) {
		this.functionToPlot = f;
		this.start = a;
		this.end = b;
		this.additionalLines = new ArrayList<LineSegmentOption>();
		this.additionalPoints = new ArrayList<PointOption>();
		this.calcPoints();
	}

	public void setNumberOfGridPoints(int numberOfGridPoints) {
		this.numberOfGridPoints = numberOfGridPoints;
	}

	// calculate the function points
	private void calcPoints() {
		this.points = new GPointsArray(numberOfGridPoints);

		// initialize points and stepwidth
		double dx = (this.end - this.start) / (this.numberOfGridPoints - 1);

		// save function name for expression
		String functionName = this.functionToPlot.getFunctionName();

		// calculate points
		for (int i = 0; i < this.numberOfGridPoints; i++) {
			double x = this.start + i * dx;
			String sExpression = functionName + "(" + Double.toString(x) + ")";
			Expression functionArgument = new Expression(sExpression, this.functionToPlot);
			double fx = functionArgument.calculate();

			// this.fMin = java.lang.Math.min(fx, fMin);
			// this.fMax = java.lang.Math.max(fx, fMax);

			this.points.add((float) x, (float) fx);
		}

		// this.firstView = true;
	}

	/**
	 * plot the function
	 * 
	 * @param app
	 *            Processing window
	 */
	public void plot(PApplet app) {
		String layerId = "layer1";
		
		//If the plot is empty, generate a new one with specialized options
		if (this.plot == null) {
			this.plot = new GPlot(app);
			this.plot.setPos(25, 25); //position of the plot in the window
			this.plot.setDim((float) app.width - 150, (float) app.height - 150); //Dimension of the plot relative to window size
			this.plot.setTitleText(this.functionToPlot.getFunctionName()); //Title of the plot
			this.plot.getXAxis().setAxisLabelText("x"); //Naming of x axis
			this.plot.getYAxis().setAxisLabelText("f(x)"); //Naming of y axis
			this.plot.addLayer(layerId, this.points); //Add a new plotting layer
			this.plot.activatePanning(); //activate panning by left click drag
			this.plot.activateZooming(); //activate zoom by single click
		}

		//a the points for the function, which shall be plotted
		GLayer firstLayer = this.plot.getLayer(layerId);
		firstLayer.setPoints(this.points);
		firstLayer.setPointSize(0f);

		//default drawing (scale, x and y axis, etc.)
		this.plot.defaultDraw();

		// draw additional lines
		this.plot.beginDraw();
		for (LineSegmentOption pointPair : this.additionalLines) {
			this.plot.drawLine(pointPair.pointA, pointPair.pointB, pointPair.lineColor.getRGB(), 1f);
		}

		//draw all additional points
		for (PointOption option : this.additionalPoints) {
			this.plot.drawPoint(option.pointPosition, option.pointColor.getRGB(), option.pointDiameter);
		}

		//draw the zero lines for orientation
		this.plot.drawHorizontalLine(0);
		this.plot.drawVerticalLine(0);
		
		//end of drawing
		this.plot.endDraw();
	}

	/**
	 * add an additional line to the plot
	 */
	public void addLine(GPoint pointA, GPoint pointB, Color lineColor) {
		LineSegmentOption option = new LineSegmentOption(pointA, pointB, lineColor);
		this.additionalLines.add(option);
	}

	/**
	 * remove all additional lines
	 */
	public void clearLines() {
		this.additionalLines.clear();
	}

	/**
	 * Add a new point for the drawing
	 * @param pointPosition
	 * @param pointColor
	 * @param pointDiameter
	 */
	public void addPoint(GPoint pointPosition, Color pointColor, float pointDiameter) {
		PointOption option = new PointOption(pointPosition, pointColor, pointDiameter);
		this.additionalPoints.add(option);
	}

	/**
	 *Removes all additional points
	 */
	public void clearPoints() {
		this.additionalPoints.clear();
	}

	/**
	 * nested class for the representation of line segment elements
	 * 
	 * @author ripo9018
	 *
	 */
	private class LineSegmentOption {
		private GPoint pointA;
		private GPoint pointB;
		private Color lineColor;

		public LineSegmentOption(GPoint pointA, GPoint pointB, Color lineColor) {
			this.pointA = pointA;
			this.pointB = pointB;
			this.lineColor = lineColor;
		}
	}
	
	/**
	 * Nested class for point options, like color and diameter
	 * @author ripo9018
	 *
	 */
	private class PointOption {
		private GPoint pointPosition;
		private Color pointColor;
		private float pointDiameter;

		public PointOption(GPoint pointPosition, Color pointColor, float pointDiameter) {
			this.pointPosition = pointPosition;
			this.pointColor = pointColor;
			this.pointDiameter = pointDiameter;
		}
	}

}
