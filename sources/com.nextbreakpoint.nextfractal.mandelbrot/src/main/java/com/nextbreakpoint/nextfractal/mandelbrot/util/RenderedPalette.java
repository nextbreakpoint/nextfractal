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
package com.nextbreakpoint.nextfractal.mandelbrot.util;

import java.util.Arrays;

import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.util.Colors;
import com.nextbreakpoint.nextfractal.core.util.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.extension.PaletteRendererFormulaExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class RenderedPalette implements Palette {
	private static final long serialVersionUID = 1L;
	private final RenderedPaletteParam[] params;

	/**
	 * @param params
	 */
	public RenderedPalette(final RenderedPaletteParam[] params) {
		if (params == null) {
			throw new NullPointerException("params == null");
		}
		if (params.length < 1) {
			throw new IllegalArgumentException("params.length < 1");
		}
		this.params = params;
	}

	/**
	 * @throws CoreException
	 */
	private static int[] rebuild(final RenderedPaletteParam[] params, final int size) throws ExtensionException {
		final int[] table = new int[size];
		double length = 0;
		int o = 0;
		for (int i = 0; i < params.length; i++) {
			final RenderedPaletteParam param = params[i];
			length = (param.getSize() * size) / 100.0;
			int l = (int) Math.round(length);
			if ((i == params.length - 1) || (o + l > size)) {
				l = size - o;
			}
			if (l > 0) {
				PaletteRendererFormulaExtensionRuntime formula = null;
				formula = MandelbrotRegistry.getInstance().getPaletteRendererFormulaExtension(param.getFormula(0).getExtensionId()).createExtensionRuntime();
				final double[] AV = formula.renderPalette(l);
				formula = MandelbrotRegistry.getInstance().getPaletteRendererFormulaExtension(param.getFormula(1).getExtensionId()).createExtensionRuntime();
				final double[] RV = formula.renderPalette(l);
				formula = MandelbrotRegistry.getInstance().getPaletteRendererFormulaExtension(param.getFormula(2).getExtensionId()).createExtensionRuntime();
				final double[] GV = formula.renderPalette(l);
				formula = MandelbrotRegistry.getInstance().getPaletteRendererFormulaExtension(param.getFormula(3).getExtensionId()).createExtensionRuntime();
				final double[] BV = formula.renderPalette(l);
				Colors.fillTable(table, o, l, param.getColor(0), param.getColor(1), AV, RV, GV, BV);
			}
			o = o + l;
		}
		return table;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.Palette#renderTable(int)
	 */
	@Override
	public int[] renderTable(final int size) {
		try {
			return RenderedPalette.rebuild(params, size);
		}
		catch (final ExtensionException e) {
			e.printStackTrace();
		}
		return new int[size];
	}

	/**
	 * @return
	 */
	public int getParamCount() {
		return params.length;
	}

	/**
	 * @return
	 */
	public RenderedPaletteParam getParam(final int index) {
		return params[index];
	}

	/**
	 * @param buffer
	 * @return
	 */
	protected StringBuilder dump(final StringBuilder buffer) {
		buffer.append("[ (");
		params[0].dump(buffer);
		buffer.append(")");
		for (int i = 1; i < params.length; i++) {
			buffer.append(", (");
			params[i].dump(buffer);
			buffer.append(")");
		}
		buffer.append("]");
		return buffer;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return dump(new StringBuilder()).toString();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.Palette#getName()
	 */
	@Override
	public String getName() {
		final StringBuilder builder = new StringBuilder();
		builder.append("[");
		if (params.length > 1) {
			builder.append(params.length);
			builder.append(" blocks]");
		}
		else {
			builder.append("1 block]");
		}
		return builder.toString();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final RenderedPalette other = (RenderedPalette) obj;
		if (!Arrays.equals(params, other.params)) {
			return false;
		}
		return true;
	}
}
