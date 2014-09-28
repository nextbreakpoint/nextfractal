/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.extension.PathReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.core.common.FloatElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;

/**
 * @author Andrea Medeghini
 */
public class CurveRelPathReplacementConfig extends PathReplacementExtensionConfig {
	private static final long serialVersionUID = 1L;
	private FloatElement xElement;
	private FloatElement yElement;
	private FloatElement x1Element;
	private FloatElement y1Element;
	private FloatElement x2Element;
	private FloatElement y2Element;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		xElement = new FloatElement(0f);
		yElement = new FloatElement(0f);
		x1Element = new FloatElement(0f);
		y1Element = new FloatElement(0f);
		x2Element = new FloatElement(0f);
		y2Element = new FloatElement(0f);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(xElement);
		elements.add(yElement);
		elements.add(x1Element);
		elements.add(y1Element);
		elements.add(x2Element);
		elements.add(y2Element);
		return elements;
	}

	/**
	 * @return
	 */
	public FloatElement getXElement() {
		return xElement;
	}
	
	/**
	 * @return
	 */
	public Float getX() {
		return xElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setX(final Float value) {
		xElement.setValue(value);
	}
	/**
	 * @return
	 */
	public FloatElement getYElement() {
		return yElement;
	}
	
	/**
	 * @return
	 */
	public Float getY() {
		return yElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setY(final Float value) {
		yElement.setValue(value);
	}
	/**
	 * @return
	 */
	public FloatElement getX1Element() {
		return x1Element;
	}
	
	/**
	 * @return
	 */
	public Float getX1() {
		return x1Element.getValue();
	}

	/**
	 * @param value
	 */
	public void setX1(final Float value) {
		x1Element.setValue(value);
	}
	/**
	 * @return
	 */
	public FloatElement getY1Element() {
		return y1Element;
	}
	
	/**
	 * @return
	 */
	public Float getY1() {
		return y1Element.getValue();
	}

	/**
	 * @param value
	 */
	public void setY1(final Float value) {
		y1Element.setValue(value);
	}
	/**
	 * @return
	 */
	public FloatElement getX2Element() {
		return x2Element;
	}
	
	/**
	 * @return
	 */
	public Float getX2() {
		return x2Element.getValue();
	}

	/**
	 * @param value
	 */
	public void setX2(final Float value) {
		x2Element.setValue(value);
	}
	/**
	 * @return
	 */
	public FloatElement getY2Element() {
		return y2Element;
	}
	
	/**
	 * @return
	 */
	public Float getY2() {
		return y2Element.getValue();
	}

	/**
	 * @param value
	 */
	public void setY2(final Float value) {
		y2Element.setValue(value);
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
		final CurveRelPathReplacementConfig other = (CurveRelPathReplacementConfig) obj;
		if (xElement == null) {
			if (other.xElement != null) {
				return false;
			}
		}
		else if (!xElement.equals(other.xElement)) {
			return false;
		}
		if (yElement == null) {
			if (other.yElement != null) {
				return false;
			}
		}
		else if (!yElement.equals(other.yElement)) {
			return false;
		}
		if (x1Element == null) {
			if (other.x1Element != null) {
				return false;
			}
		}
		else if (!x1Element.equals(other.x1Element)) {
			return false;
		}
		if (y1Element == null) {
			if (other.y1Element != null) {
				return false;
			}
		}
		else if (!y1Element.equals(other.y1Element)) {
			return false;
		}
		if (x2Element == null) {
			if (other.x2Element != null) {
				return false;
			}
		}
		else if (!x2Element.equals(other.x2Element)) {
			return false;
		}
		if (y2Element == null) {
			if (other.y2Element != null) {
				return false;
			}
		}
		else if (!y2Element.equals(other.y2Element)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public CurveRelPathReplacementConfig clone() {
		final CurveRelPathReplacementConfig config = new CurveRelPathReplacementConfig();
		config.setX(getX());
		config.setY(getY());
		config.setX1(getX1());
		config.setY1(getY1());
		config.setX2(getX2());
		config.setY2(getY2());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		builder.appendTabs();
		builder.append("CURVEREL {");
		if (xElement.getValue() != null) {
			builder.append(" x ");
			builder.append(xElement.getValue());
		}
		if (yElement.getValue() != null) {
			builder.append(" y ");
			builder.append(yElement.getValue());
		}
		if (x1Element.getValue() != null) {
			builder.append(" x1 ");
			builder.append(x1Element.getValue());
		}
		if (y1Element.getValue() != null) {
			builder.append(" y1 ");
			builder.append(y1Element.getValue());
		}
		if (x2Element.getValue() != null) {
			builder.append(" x2 ");
			builder.append(x2Element.getValue());
		}
		if (y2Element.getValue() != null) {
			builder.append(" y2 ");
			builder.append(y2Element.getValue());
		}
		builder.append(" }");
	}
}
