package exercise1;

import org.mariuszgromada.math.mxparser.Function;

import processing.core.PApplet;

public class plotter extends PApplet{
	FunctionPlotter1D plotter;
	 
	
	public static void main (String[] args) {
		PApplet.main(exercise1.plotter.class);
		
	}
	public void settings() {
		size(800,500,P3D);
	
	}
public void setup() {
	Function myFunction = new Function(" f(x) = 3.056*x + sin 4*3.14*x");
	plotter = new FunctionPlotter1D(myFunction,0,20);
}
public void draw () {
	background(0);
	plotter.plot(this); 
}
}