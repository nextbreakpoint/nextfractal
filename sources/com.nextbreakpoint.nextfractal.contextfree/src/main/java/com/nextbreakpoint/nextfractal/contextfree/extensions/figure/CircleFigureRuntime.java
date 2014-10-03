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
public class CircleFigureRuntime extends FigureExtensionRuntime<CircleFigureConfig> {
	public String getName() {
		return "CIRCLE";
	}

	@Override
	public void process(CFBuilder builder) {
		int shapeType = builder.encodeShapeName(getName());
		CFRule rule = new CFRule(shapeType, 1);
		CFPath path = new CFPath();
		path.circle();
		rule.setPath(path);
		builder.addRule(rule);
	}
}
