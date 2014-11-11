/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathReplacement.PathReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.core.elements.BooleanElement;
import com.nextbreakpoint.nextfractal.core.elements.FloatElement;
import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;

/**
 * @author Andrea Medeghini
 */
public class ArcToPathReplacementConfig extends PathReplacementExtensionConfig {
	private static final long serialVersionUID = 1L;
	private FloatElement xElement;
	private FloatElement yElement;
	private FloatElement rxElement;
	private FloatElement ryElement;
	private FloatElement rElement;
	private BooleanElement sweepElement;
	private BooleanElement largeElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		xElement = new FloatElement(0f);
		yElement = new FloatElement(0f);
		rxElement = new FloatElement(0f);
		ryElement = new FloatElement(0f);
		rElement = new FloatElement(0f);
		sweepElement = new BooleanElement(false);
		largeElement = new BooleanElement(false);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(xElement);
		elements.add(yElement);
		elements.add(rxElement);
		elements.add(ryElement);
		elements.add(rElement);
		elements.add(sweepElement);
		elements.add(largeElement);
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
	public FloatElement getRxElement() {
		return rxElement;
	}
	
	/**
	 * @return
	 */
	public Float getRx() {
		return rxElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setRx(final Float value) {
		rxElement.setValue(value);
	}
	/**
	 * @return
	 */
	public FloatElement getRyElement() {
		return ryElement;
	}
	
	/**
	 * @return
	 */
	public Float getRy() {
		return ryElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setRy(final Float value) {
		ryElement.setValue(value);
	}
	/**
	 * @return
	 */
	public FloatElement getRElement() {
		return rElement;
	}
	
	/**
	 * @return
	 */
	public Float getR() {
		return rElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setR(final Float value) {
		rElement.setValue(value);
	}
	/**
	 * @return
	 */
	public BooleanElement getSweepElement() {
		return sweepElement;
	}
	
	/**
	 * @return
	 */
	public Boolean isSweep() {
		return sweepElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setSweep(final Boolean value) {
		sweepElement.setValue(value);
	}
	/**
	 * @return
	 */
	public BooleanElement getLargeElement() {
		return largeElement;
	}
	
	/**
	 * @return
	 */
	public Boolean isLarge() {
		return largeElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setLarge(final Boolean value) {
		largeElement.setValue(value);
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
		final ArcToPathReplacementConfig other = (ArcToPathReplacementConfig) obj;
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
		if (rxElement == null) {
			if (other.rxElement != null) {
				return false;
			}
		}
		else if (!rxElement.equals(other.rxElement)) {
			return false;
		}
		if (ryElement == null) {
			if (other.ryElement != null) {
				return false;
			}
		}
		else if (!ryElement.equals(other.ryElement)) {
			return false;
		}
		if (rElement == null) {
			if (other.rElement != null) {
				return false;
			}
		}
		else if (!rElement.equals(other.rElement)) {
			return false;
		}
		if (sweepElement == null) {
			if (other.sweepElement != null) {
				return false;
			}
		}
		else if (!sweepElement.equals(other.sweepElement)) {
			return false;
		}
		if (largeElement == null) {
			if (other.largeElement != null) {
				return false;
			}
		}
		else if (!largeElement.equals(other.largeElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	@Override
	public ArcToPathReplacementConfig clone() {
		final ArcToPathReplacementConfig config = new ArcToPathReplacementConfig();
		config.setX(getX());
		config.setY(getY());
		config.setRx(getRx());
		config.setRy(getRy());
		config.setR(getR());
		config.setSweep(isSweep());
		config.setLarge(isLarge());
		return config;
	}

	@Override
	public void toCFDG(CFDGBuilder builder) {
		builder.appendTabs();
		builder.append("ARCTO {");
		if (xElement.getValue() != null) {
			builder.append(" x ");
			builder.append(xElement.getValue());
		}
		if (yElement.getValue() != null) {
			builder.append(" y ");
			builder.append(yElement.getValue());
		}
		if (rElement.getValue() != null) {
			builder.append(" r ");
			builder.append(rElement.getValue() * 180f / Math.PI);
		} 
		if (rxElement.getValue() != null) {
			builder.append(" rx ");
			builder.append(rxElement.getValue());
		}
		if (ryElement.getValue() != null) {
			builder.append(" ry ");
			builder.append(ryElement.getValue());
		}
		if (sweepElement.getValue() != null) {
			builder.append(sweepElement.getValue() ? " p cw" : "");
		}
		if (largeElement.getValue() != null) {
			builder.append(largeElement.getValue() ? " p large" : "");
		}
		builder.append(" }");
	}
}
