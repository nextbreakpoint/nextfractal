/*
 * NextFractal 1.0.3
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.core;

import java.awt.BasicStroke;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class CFPathAttribute implements Cloneable {
	private CFPathCommand command = CFPathCommand.STROKE;
	private CFModification modification = new CFModification();
	private Point2D.Float centroid = new Point2D.Float();
	private int windRule = Path2D.WIND_EVEN_ODD;
	private int lineCap = BasicStroke.CAP_ROUND;
	private int lineJoin = BasicStroke.JOIN_MITER;
	private float lineWidth = 1f;
	private int count = 0;
	private BasicStroke stroke;

	public CFPathAttribute(CFPathCommand command) {
		this.command = command;
	}

	public CFPathAttribute(CFPathCommand command, CFModification modification, String ruleParam) {
		this.command = command;
		this.windRule = getRule(ruleParam);
		this.modification = modification;
	}

	public CFPathAttribute(CFPathCommand command, CFModification modification, String capParam, String joinParam, float lineWidth) {
		this.command = command;
		this.lineCap = getCap(capParam);
		this.lineJoin = getJoin(joinParam);
		this.lineWidth = lineWidth;
		this.modification = modification;
	}

	public CFPathAttribute(CFPathCommand command, CFModification modification, int count) {
		this.command = command;
		this.count = count;
		this.modification = modification;
	}

	private int getRule(String rule) {
		if ("evenodd".equals(rule)) { 
			return Path2D.WIND_EVEN_ODD;
		} else if ("nonzero".equals(rule)) { 
			return Path2D.WIND_NON_ZERO;
		}
		throw new IllegalArgumentException("Rule not supported " + rule);
	}

	private int getCap(String cap) {
		if ("buttcap".equals(cap)) {
			return BasicStroke.CAP_BUTT;
		} else if ("roundcap".equals(cap)) {
			return BasicStroke.CAP_ROUND;
		} else if ("squarecap".equals(cap)) {
			return BasicStroke.CAP_SQUARE;
		}
		throw new IllegalArgumentException("Cap not supported " + cap);
	}

	private int getJoin(String join) {
		if ("miterjoin".equals(join)) {
			return BasicStroke.JOIN_MITER;
		} else if ("roundjoin".equals(join)) {
			return BasicStroke.JOIN_ROUND;
		} else if ("beveljoin".equals(join)) {
			return BasicStroke.JOIN_BEVEL;
		}
		throw new IllegalArgumentException("Join not supported " + join);
	}

	public BasicStroke getStroke() {
		if (stroke == null) {
			stroke = new BasicStroke(lineWidth, lineCap, lineJoin);
		}
		return stroke;
	}

	public CFPathCommand getCommand() {
		return command;
	}

	public int getWindingRule() {
		return windRule;
	}
	
	public int getLineCap() {
		return lineCap;
	}
	
	public int getLineJoin() {
		return lineJoin;
	}
	
	public float getLineWidth() {
		return lineWidth;
	}
	
	public int getCount() {
		return count;
	}
	
	public CFModification getModification() {
		return modification;
	}
	
	public double area() {
		return Math.abs(modification.getTransform().getDeterminant());
	}
	
	public Point2D getCentroid() {
		return centroid;
	}

	@Override
	public CFPathAttribute clone() {
		CFPathAttribute pa = new CFPathAttribute(command);
		pa.centroid = centroid;
		pa.count = count;
		pa.stroke = stroke;
		pa.windRule = windRule;
		pa.lineCap = lineCap;
		pa.lineJoin = lineJoin;
		pa.lineWidth = lineWidth;
		pa.modification = modification.clone();
		return pa;
	}

	@Override
	public String toString() {
		return "CFPathAttribute [command=" + command + ", count=" + count + ", modification=" + modification + ", centroid=" + centroid + ", lineCap=" + lineCap + ", lineJoin=" + lineJoin + ", lineWidth=" + lineWidth + ", windRule=" + windRule + "]";
	}

	public void setModification(CFModification modification) {
		this.modification = modification;
	}
}
