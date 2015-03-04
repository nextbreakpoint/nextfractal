package com.nextbreakpoint.nextfractal.renderer.java2D;

import java.awt.geom.AffineTransform;

import com.nextbreakpoint.nextfractal.renderer.RendererAffine;
import com.nextbreakpoint.nextfractal.renderer.RendererGraphicsContext;

public class Java2DRendererAffine implements RendererAffine {
	private AffineTransform affine = new AffineTransform();
	
	public Java2DRendererAffine(AffineTransform affine) {
		this.affine = affine;
	}

	@Override
	public void setAffine(RendererGraphicsContext context) {
		((Java2DRendererGraphicsContext)context).getGraphicsContext().setTransform(affine);
	}

	@Override
	public void append(RendererAffine affine) {
		this.affine.concatenate(((Java2DRendererAffine)affine).affine);
	}
}
