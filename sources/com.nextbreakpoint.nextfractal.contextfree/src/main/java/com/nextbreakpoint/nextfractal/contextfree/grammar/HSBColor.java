package com.nextbreakpoint.nextfractal.contextfree.grammar;

public class HSBColor {
	private double[] values = new double[4];
	
	public HSBColor(double h, double s, double b, double a) {
		this.values[0] = h;
		this.values[1] = s;
		this.values[2] = b;
		this.values[3] = a;
	}
	
	public double hue() {
		return values[0];
	}
	
	public double bright() {
		return values[1];
	}
	
	public double sat() {
		return values[2];
	}
	
	public double alpha() {
		return values[3];
	}
	
	public double[] values() {
		return values;
	}

	public static double adjustHue(double base, double adjustment) {
		// TODO Auto-generated method stub
		return 0.0;
	}

	public static double adjust(double base, double adjustment) {
		// TODO Auto-generated method stub
		return 0.0;
	}

	public static double adjustHue(double base, double adjustment, EAssignmentType useTarget, double target) {
		// TODO Auto-generated method stub
		return 0.0;
	}

	public static double adjust(double base, double adjustment, EAssignmentType useTarget, double target) {
		// TODO Auto-generated method stub
		return 0.0;
	}
}
