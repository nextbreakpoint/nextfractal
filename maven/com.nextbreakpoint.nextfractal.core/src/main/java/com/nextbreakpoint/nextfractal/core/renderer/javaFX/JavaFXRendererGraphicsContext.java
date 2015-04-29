/*
 * NextFractal 1.0.3
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.core.renderer.javaFX;

import javafx.scene.canvas.GraphicsContext;

import com.nextbreakpoint.nextfractal.core.renderer.RendererAffine;
import com.nextbreakpoint.nextfractal.core.renderer.RendererColor;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFont;
import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;
import com.nextbreakpoint.nextfractal.core.renderer.RendererImage;

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
