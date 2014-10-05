package com.nextbreakpoint.nextfractal.twister.renderer.javaFX;

import javafx.scene.transform.Affine;

import com.nextbreakpoint.nextfractal.twister.renderer.RenderAffine;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext;

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
