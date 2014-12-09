package com.nextbreakpoint.nextfractal.flux.render.javaFX;

import java.util.Stack;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

import com.nextbreakpoint.nextfractal.flux.render.RenderAffine;
import com.nextbreakpoint.nextfractal.flux.render.RenderColor;
import com.nextbreakpoint.nextfractal.flux.render.RenderFont;
import com.nextbreakpoint.nextfractal.flux.render.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.flux.render.RenderImage;

public class JavaFXRenderGraphicsContext implements RenderGraphicsContext {
	private GraphicsContext gc;
	private Stack<Affine> stack = new Stack<>();

	public JavaFXRenderGraphicsContext(GraphicsContext gc) {
		this.gc = gc;
	}

	public void setStroke(RenderColor color) {
		color.setStroke(this);
	}

	public void setFill(RenderColor color) {
		color.setFill(this);
	}

	public void setFont(RenderFont font) {
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

	public void drawImage(RenderImage image, int x, int y) {
		image.draw(this, x, y);
	}

	public void drawImage(RenderImage image, int x, int y, int w, int h) {
		image.draw(this, x, y, w, h);
	}

	public void clearRect(int x, int y, int width, int height) {
		gc.clearRect(x, y, width, height);
	}

	public void setAffine(RenderAffine affine) {
		affine.setAffine(this);
	}

	public void saveTransform() {
		stack.push(gc.getTransform());
	}

	public void restoreTransform() {
		gc.setTransform(stack.pop());
	}

	public GraphicsContext getGraphicsContext() {
		return gc;
	}
}
