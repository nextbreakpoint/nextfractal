package com.nextbreakpoint.nextfractal.contextfree.renderer.support;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class SimpleShapeRenderer implements CFShapeRenderer {
	private Graphics2D g2d;
	
	public SimpleShapeRenderer(Graphics2D g2d, CFContext context) {
		this.g2d = g2d;
	}

	public void render(CFPath path, CFPathAttribute attribute) {
		AffineTransform tmpTransform = g2d.getTransform();
		Composite tmpComposite = g2d.getComposite();
		Color tmpColor = g2d.getColor();
		path.render(g2d, attribute, null);
		g2d.setTransform(tmpTransform);
		g2d.setComposite(tmpComposite);
		g2d.setColor(tmpColor);
	}
}
