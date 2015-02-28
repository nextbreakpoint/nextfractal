package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.util.Map;

public class PrimShape {
	private GeneralPath path = new GeneralPath(GeneralPath.WIND_NON_ZERO);
	
	public static boolean isPrimShape(int shapeType) {
		return shapeType < 4;
	}

	public static Map<Integer, PrimShape> getShapeMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public int vertex(double[] x, double[] y) {
		// TODO Auto-generated method stub
		return 0;
	}

	public PathIterator getPathIterator() {
		return path.getPathIterator(new AffineTransform());
	}
}
