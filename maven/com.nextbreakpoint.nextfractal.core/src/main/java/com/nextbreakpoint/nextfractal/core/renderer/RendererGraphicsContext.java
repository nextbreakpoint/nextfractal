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
package com.nextbreakpoint.nextfractal.core.renderer;

public interface RendererGraphicsContext {
	public void setStroke(RendererColor c);

	public void setFill(RendererColor c);
	
	public void setFont(RendererFont font);
	
	public void rect(int x, int y, int width, int height);
	
	public void stroke();
	
	public void fill();

	public void clip();

	public void beginPath();

	public void closePath();

	public void moveTo(int x, int y);
	
	public void lineTo(int x, int y);
	
	public void strokeRect(int x, int y, int width, int height);
	
	public void fillRect(int x, int y, int width, int height);
	
	public void strokeText(String text, int x, int y);

	public void fillText(String text, int x, int y);

	public void drawImage(RendererImage image, int x, int y);

	public void drawImage(RendererImage image, int x, int y, int w, int h);

	public void clearRect(int x, int y, int width, int height);

	public void setAffine(RendererAffine t);

	public void save();

	public void restore();

	public void setClip(int x, int y, int width, int height);

	public void setAlpha(double alpha);
}
