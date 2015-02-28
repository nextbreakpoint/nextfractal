package com.nextbreakpoint.nextfractal.render.java2D;

import java.awt.geom.AffineTransform;

import com.nextbreakpoint.nextfractal.render.RenderAffine;
import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;

public class Java2DRenderAffine implements RenderAffine {
	private AffineTransform affine = new AffineTransform();
	
	public Java2DRenderAffine(AffineTransform affine) {
		this.affine = affine;
	}

	@Override
	public void setAffine(RenderGraphicsContext context) {
		((Java2DRenderGraphicsContext)context).getGraphicsContext().setTransform(affine);
	}

	@Override
	public void append(RenderAffine affine) {
		this.affine.concatenate(((Java2DRenderAffine)affine).affine);
	}
}
