/*
 * NextFractal 1.1.3
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
package com.nextbreakpoint.nextfractal.contextfree.core;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class CFBounds {
	private double minX = Double.MAX_VALUE;
	private double minY = Double.MAX_VALUE;
	private double maxX = Double.MIN_VALUE;
	private double maxY = Double.MIN_VALUE;

	public CFBounds() {
	}
	
	public CFBounds(CFPath path, CFPathAttribute attribute, float dilation, float scale) {
	    float[] src = new float[2];
	    float[] dst = new float[2];
	    src[0] = (float) attribute.getCentroid().getX();
	    src[1] = (float) attribute.getCentroid().getY();
	    attribute.getModification().transform(src, dst);
	    double centroidX = dst[0];
	    double centroidY = dst[1];

	    Rectangle2D bounds = path.getBounds(attribute.getModification().getTransform(), scale);
	    minX = bounds.getMinX();
	    maxX = bounds.getMaxX();
	    minY = bounds.getMinY();
	    maxY = bounds.getMaxY();
	    
	    if (isValid() && dilation != 1.0) {
	        minX = dilation * (minX - centroidX) + centroidX;
	        maxX = dilation * (maxX - centroidX) + centroidX;
	        minY = dilation * (minY - centroidY) + centroidY;
	        maxY = dilation * (maxY - centroidY) + centroidY;
	    }
	}

	public double getMinX() {
		return minX;
	}

	public double getMinY() {
		return minY;
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public double getSizeX() {
		return maxX - minX;
	}

	public double getSizeY() {
		return maxY - minY;
	}
	
	public void merge(CFBounds bounds) {
		if (!bounds.isValid()) return;
		
	    if (!isValid()) {
	        maxX = bounds.maxX;
	        minX = bounds.minX;
	        maxY = bounds.maxY;
	        minY = bounds.minY;
			return;
	    }

		if (maxX < bounds.maxX) maxX = bounds.maxX;
		if (minX > bounds.minX) minX = bounds.minX;
		if (maxY < bounds.maxY) maxY = bounds.maxY;
		if (minY > bounds.minY) minY = bounds.minY;
	}

	public boolean isValid() {
		return (maxX - minX) > 0 || (maxY - minY) > 0;
	}

	@Override
	public String toString() {
		return "CFBounds [minX=" + minX + ", maxX=" + maxX + ", minY=" + minY + ", maxY=" + maxY + "]";
	}

	public void update(CFPath path, CFPathAttribute attribute, float scale) {
		  merge(new CFBounds(path, attribute, 1.0f, scale));
	}

	public float computeScale(int width, int height, float border, boolean exact) {
		return (float) computeScale(width, height, border, null, exact);
	}

	public CFBounds interpolate(CFBounds other, double alpha) {
		double beta = 1.0 - alpha;
		
		CFBounds result = new CFBounds();
		if (!isValid() || !other.isValid()) return result;
		
		result.maxX = beta * maxX + alpha * other.maxX;
		result.minX = beta * minX + alpha * other.minX;
		result.maxY = beta * maxY + alpha * other.maxY;
		result.minY = beta * minY + alpha * other.minY;
		
		return result;
	}
	
	public CFBounds slewCenter(CFBounds other, double alpha) {
		CFBounds result = new CFBounds();
		if (!isValid() || !other.isValid()) return result;
		
		double offsetX = alpha * ((other.maxX + other.minX) - (maxX + minX)) / 2.0;
		double offsetY = alpha * ((other.maxY + other.minY) - (maxY + minY)) / 2.0;
	
		double absX = Math.abs(offsetX);
		double absY = Math.abs(offsetY);
		
		result.maxX = maxX + absX + offsetX;
		result.minX = minX - absX + offsetX;
		result.maxY = maxY + absY + offsetY;
		result.minY = minY - absY + offsetY;
		
		return result;
	}
	
	public void gather(CFBounds other, double weight) {
		if (!other.isValid()) return;
		
	    if (!isValid()) {
	        maxX = weight * other.maxX;
	        minX = weight * other.minX;
	        maxY = weight * other.maxY;
	        minY = weight * other.minY;
			return;
	    }
	
		maxX += weight * other.maxX;
		minX += weight * other.minX;
		maxY += weight * other.maxY;
		minY += weight * other.minY;
	}
	
	public double computeScale(int width, int height, float border, AffineTransform transform, boolean exact) {
	    double scale;
	    double virtual_width = maxX - minX;
	    double virtual_height = maxY - minY;
	    double target_width = width - 2 * border;
	    double target_height = height - 2 * border;
	    	
	    double center_x = (maxX + minX) / 2.0;
	    double center_y = (maxY + minY) / 2.0;
	    
	    if (!isValid()) {
	    	virtual_width = virtual_height = 1;
	    	center_x = 0;
	    	center_y = 0;
	    }
	    
		int newWidth = width;
		int newHeight = height;
		
	    if (virtual_width / target_width > virtual_height / target_height) {
	        scale = target_width / virtual_width;
			newHeight = (int) Math.floor(scale * virtual_height + 2 * border + 0.5);
			if (!exact) {
	            newHeight = newHeight + ((newHeight ^ height) & 0x1);
			}
	    } else {
	        scale = target_height / virtual_height;
			newWidth = (int) Math.floor(scale * virtual_width + 2 * border + 0.5);
			if (!exact) {
	            newWidth = newWidth + ((newWidth ^ width) & 0x1);
			}
	    }
	
	    if (transform != null) {
	        double offsetX = center_x - (target_width / 2.0) / scale;
	        double offsetY = center_y - (target_height / 2.0) / scale;
	        transform.setToScale(scale, scale);
	        transform.translate(-offsetX, -offsetY);
	    }

	    return scale;
	}

	public void setMinX(double value) {
		this.minX = value;
	}

	public void setMaxX(double value) {
		this.maxX = value;
	}

	public void setMinY(double value) {
		this.minY = value;
	}

	public void setMaxY(double value) {
		this.maxY = value;
	}
	
	public double getCenterX() {
		return (maxX + minX) / 2;
	}

	public double getCenterY() {
		return (maxY + minY) / 2;
	}
}
