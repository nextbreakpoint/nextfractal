package com.nextbreakpoint.nextfractal.renderer.javaFX;

import javafx.scene.transform.Affine;

import com.nextbreakpoint.nextfractal.renderer.RendererAffine;
import com.nextbreakpoint.nextfractal.renderer.RendererGraphicsContext;

public class JavaFXRendererAffine implements RendererAffine {
	private Affine affine = new Affine();
	
	public JavaFXRendererAffine(Affine affine) {
		this.affine = affine;
	}

	@Override
	public void setAffine(RendererGraphicsContext context) {
		((JavaFXRendererGraphicsContext)context).getGraphicsContext().setTransform(affine);
	}

	@Override
	public void append(RendererAffine affine) {
		this.affine.append(((JavaFXRendererAffine)affine).affine);
	}
}
