/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
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
package com.nextbreakpoint.nextfractal.twister.extensions.effect;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.DataBufferInt;

import com.nextbreakpoint.nextfractal.core.media.gfx.EffectFactory;
import com.nextbreakpoint.nextfractal.core.media.gfx.SpotData;
import com.nextbreakpoint.nextfractal.core.util.Colors;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.twister.effect.extension.EffectExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class SpotRuntime extends EffectExtensionRuntime<SpotConfig> {
	private static final int MIN_SPOT_SIZE = 32;
	private SpotData spotData;
	private DoubleVector2D pos;
	private IntegerVector2D size;
	private Surface spotSurface;
	private Surface dataSurface;
	private Surface tempSurface;
	private int spotSize;

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.effect.extension.EffectExtensionRuntime#renderImage(com.nextbreakpoint.nextfractal.core.util.Surface)
	 */
	@Override
	public void renderImage(final Surface dst) {
		final int[] dstBuffer = ((DataBufferInt) (dst.getImage().getRaster().getDataBuffer())).getData();
		final int[] dataBuffer = ((DataBufferInt) (dataSurface.getImage().getRaster().getDataBuffer())).getData();
		final int[] tempBuffer = ((DataBufferInt) (tempSurface.getImage().getRaster().getDataBuffer())).getData();
		EffectFactory.spotFX(spotData, tempBuffer, dstBuffer, dataBuffer, dataSurface.getWidth(), dataSurface.getHeight());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.effect.extension.EffectExtensionRuntime#prepareEffect()
	 */
	@Override
	public void prepareEffect() {
		final int newSpotSize = SpotRuntime.MIN_SPOT_SIZE + ((Math.max(SpotRuntime.MIN_SPOT_SIZE, spotSurface.getHeight()) - SpotRuntime.MIN_SPOT_SIZE) * getConfig().getIntensity().intValue()) / 100;
		if (spotSize != newSpotSize) {
			spotSize = newSpotSize;
			final int[] spotBuffer = ((DataBufferInt) (spotSurface.getImage().getRaster().getDataBuffer())).getData();
			int offset = 0;
			for (int i = 0; i < spotSurface.getHeight(); i++) {
				for (int j = 0; j < spotSurface.getWidth(); j++) {
					final double nx = (j - spotSurface.getWidth() / 2d) / (spotSize / 2d);
					final double ny = (i - spotSurface.getHeight() / 2d) / (spotSize / 2d);
					final double d = Math.sqrt(nx * nx + ny * ny);
					if ((d >= -1) && (d <= 1)) {
						// int alpha = (int) Math.rint(255d * Math.exp(-(Math.pow(d, 2))));
						final int alpha = (int) Math.rint(255d * Math.cos(0.5d * Math.PI * d));
						spotBuffer[offset + j] = Colors.gray(alpha, alpha);
					}
					else {
						// spotBuffer[offset + j] = 0;
						final int alpha = (int) Math.rint(255d * Math.cos(0.5d * Math.PI * 1));
						spotBuffer[offset + j] = Colors.gray(alpha, alpha);
					}
				}
				offset += size.getX();
			}
		}
		final DoubleVector2D newPos = getConfig().getCenter();
		if (pos != newPos) {
			if (getConfig().getCenter() != null) {
				pos = getConfig().getCenter();
			}
			else {
				pos = new DoubleVector2D(0.5, 0.5);
			}
			final Graphics2D g2d = tempSurface.getGraphics2D();
			final int alpha = (int) Math.rint(255d * Math.cos(0.5d * Math.PI * 1));
			g2d.setColor(new Color(Colors.gray(alpha, alpha), true));
			// g2d.setColor(new Color(0, true));
			g2d.setComposite(AlphaComposite.Src);
			g2d.fillRect(0, 0, tempSurface.getWidth(), tempSurface.getHeight());
			g2d.drawImage(spotSurface.getImage(), (int) Math.rint(pos.getX() * tempSurface.getWidth() - spotSurface.getWidth() / 2), (int) Math.rint(pos.getY() * tempSurface.getHeight() - spotSurface.getHeight() / 2), null);
		}
		fireChanged();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.effect.extension.EffectExtensionRuntime#setTile(com.nextbreakpoint.nextfractal.twister.ImageTile)
	 */
	@Override
	public void setSize(final IntegerVector2D size) {
		if (this.size != size) {
			this.size = size;
			final int s = Math.max(size.getX(), size.getY());
			tempSurface = new Surface(size.getX(), size.getY());
			dataSurface = new Surface(size.getX(), size.getY());
			spotSurface = new Surface(s, s);
			spotData = new SpotData();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.effect.extension.EffectExtensionRuntime#reset()
	 */
	@Override
	public void reset() {
	}
}
