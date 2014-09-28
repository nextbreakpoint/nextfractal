/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.figure;

import com.nextbreakpoint.nextfractal.contextfree.figure.extension.FigureExtensionRuntime;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFBuilder;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFPath;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFRule;

/**
 * @author Andrea Medeghini
 */
public class TriangleFigureRuntime extends FigureExtensionRuntime<TriangleFigureConfig> {
	public String getName() {
		return "TRIANGLE";
	}

	public void process(CFBuilder builder) {
		int shapeType = builder.encodeShapeName(getName());
		CFRule rule = new CFRule(shapeType, 1);
		float a = (float) (0.5 * Math.tan(Math.PI / 6.0));
		float b = (float) (0.5 * Math.tan(Math.PI / 3.0));
		CFPath path = new CFPath();
		path.moveRel(-0.5f, -a);
		path.lineRel(+1f, +0f);
		path.lineRel(-0.5f, b);
		path.closePath(false);
		rule.setPath(path);
		builder.addRule(rule);
	}
}
