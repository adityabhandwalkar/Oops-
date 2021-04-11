package exercise1;

import java.util.Scanner;

import org.mariuszgromada.math.mxparser.Function;



public class MainClass {
	static double b = 610.135;
	
	

	public static void main(String[] args) {
		Scanner scan = new Scanner (System.in);
		System.out.print(" enter the value of a");
		double vala = scan.nextDouble();
		System.out.print(" enter frequency ");
		double freq = scan.nextDouble();
		Function function = new Function ("f(t)=" +vala + "*(t)+ sin(2*" + freq + "*pi*t)");
		System.out.println(" the power function is" + function.getFunctionExpressionString());
		IntegrationMethod integration = new IntegrationMethod(function);
		double answer = integration.Method3(0, 20,  function);
		System.out.println(" integration is" + answer);
		double result = answer- b; 
		//3.05
		double result1 = result/b;
		System.out.println( " absolute error is" + result);
		System.out.println( " relative error is" + result1);
		
		
	}

}
