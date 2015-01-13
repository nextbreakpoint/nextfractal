package com.nextbreakpoint.nextfractal.render.javaFX;

import javafx.scene.transform.Affine;

import com.nextbreakpoint.nextfractal.render.RenderAffine;
import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;

public class JavaFXRenderAffine implements RenderAffine {
	private Affine affine = new Affine();
	
	public JavaFXRenderAffine(Affine affine) {
		this.affine = affine;
	}

	@Override
	public void setAffine(RenderGraphicsContext context) {
		((JavaFXRenderGraphicsContext)context).getGraphicsContext().setTransform(affine);
	}

	@Override
	public void append(RenderAffine affine) {
		this.affine.append(((JavaFXRenderAffine)affine).affine);
	}
}
