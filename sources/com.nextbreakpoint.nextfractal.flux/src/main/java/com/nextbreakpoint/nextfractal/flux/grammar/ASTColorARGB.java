package com.nextbreakpoint.nextfractal.flux.grammar;

public class ASTColorARGB {
	private float[] components = new float[4];

	public ASTColorARGB(String argb) {
		int x = Integer.parseInt(argb);
		components[0] = (0xFF & (x >> 24)) / 255;
		components[1] = (0xFF & (x >> 16)) / 255;
		components[2] = (0xFF & (x >> 8)) / 255;
		components[3] = (0xFF & (x >> 0)) / 255;
	}

	public ASTColorARGB(String a, String r, String g, String b) {
		components[0] = Float.parseFloat(a);
		components[1] = Float.parseFloat(r);
		components[2] = Float.parseFloat(g);
		components[3] = Float.parseFloat(b);
	}

	public float[] getComponents() {
		return components;
	}
}
