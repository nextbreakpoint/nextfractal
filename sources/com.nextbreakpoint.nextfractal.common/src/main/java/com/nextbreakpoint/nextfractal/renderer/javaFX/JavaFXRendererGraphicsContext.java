package com.nextbreakpoint.nextfractal.renderer.javaFX;

import javafx.scene.canvas.GraphicsContext;

import com.nextbreakpoint.nextfractal.renderer.RendererAffine;
import com.nextbreakpoint.nextfractal.renderer.RendererColor;
import com.nextbreakpoint.nextfractal.renderer.RendererFont;
import com.nextbreakpoint.nextfractal.renderer.RendererGraphicsContext;
import com.nextbreakpoint.nextfractal.renderer.RendererImage;

public class JavaFXRendererGraphicsContext implements RendererGraphicsContext {
	private GraphicsContext gc;

	public JavaFXRendererGraphicsContext(GraphicsContext gc) {
		this.gc = gc;
	}

	public void setStroke(RendererColor color) {
		color.setStroke(this);
	}

	public void setFill(RendererColor color) {
		color.setFill(this);
	}

	public void setFont(RendererFont font) {
		font.setFont(this);
	}

	public void rect(int x, int y, int width, int height) {
		gc.rect(x, y, width, height);
	}
	
	public void beginPath() {
		gc.beginPath();
	}

	public void closePath() {
		gc.closePath();
	}

	public void stroke() {
		gc.stroke();
	}

	public void fill() {
		gc.fill();
	}

	public void clip() {
		gc.clip();
	}

	public void strokeRect(int x, int y, int width, int height) {
		gc.strokeRect(x, y, width, height);
	}
	
	public void fillRect(int x, int y, int width, int height) {
		gc.fillRect(x, y, width, height);
	}
	
	public void strokeText(String text, int x, int y) {
		gc.strokeText(text, x, y);
	}

	public void fillText(String text, int x, int y) {
		gc.fillText(text, x, y);
	}

	public void drawImage(RendererImage image, int x, int y) {
		image.draw(this, x, y);
	}

	public void drawImage(RendererImage image, int x, int y, int w, int h) {
		image.draw(this, x, y, w, h);
	}

	public void clearRect(int x, int y, int width, int height) {
		gc.clearRect(x, y, width, height);
	}

	public void setAffine(RendererAffine affine) {
		affine.setAffine(this);
	}

	public void save() {
		gc.save();
	}

	public void restore() {
		gc.restore();
	}

	public GraphicsContext getGraphicsContext() {
		return gc;
	}

	public void setClip(int x, int y, int width, int height) {
		gc.beginPath();
		gc.rect(x, y, width, height);
		gc.clip();
	}

	public void moveTo(int x, int y) {
		gc.moveTo(x, y);
	}

	public void lineTo(int x, int y) {
		gc.lineTo(x, y);
	}

	@Override
	public void setAlpha(double alpha) {
		gc.setGlobalAlpha(alpha);
	}
}
