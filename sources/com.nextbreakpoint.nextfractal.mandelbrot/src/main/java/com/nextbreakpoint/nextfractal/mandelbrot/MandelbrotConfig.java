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
package com.nextbreakpoint.nextfractal.mandelbrot;

import com.nextbreakpoint.nextfractal.core.common.BooleanElement;
import com.nextbreakpoint.nextfractal.core.common.ComplexElement;
import com.nextbreakpoint.nextfractal.core.common.IntegerElement;
import com.nextbreakpoint.nextfractal.core.common.RectangleElement;
import com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.SingleConfigElement;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector4D;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector4D;
import com.nextbreakpoint.nextfractal.core.util.Rectangle;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalConfigElement;
import com.nextbreakpoint.nextfractal.twister.common.SpeedElement;
import com.nextbreakpoint.nextfractal.twister.common.ViewElement;
import com.nextbreakpoint.nextfractal.twister.util.Speed;
import com.nextbreakpoint.nextfractal.twister.util.View;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotConfig extends AbstractConfigElement {
	public static final String CLASS_ID = "MandelbrotConfig";
	private static final long serialVersionUID = 1L;
	private final SingleConfigElement<MandelbrotFractalConfigElement> fractalSingleElement = new SingleConfigElement<MandelbrotFractalConfigElement>("MandelbrotFractalSingleElement");
	private final ViewElement viewElement = new ViewElement(new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 1, 0), new DoubleVector4D(0, 0, 0, 0)));
	private final IntegerElement imageModeElement = new IntegerElement(MandelbrotImageConfig.IMAGE_MODE_MANDELBROT);
	private final IntegerElement inputModeElement = new IntegerElement(MandelbrotImageConfig.INPUT_MODE_ZOOM);
	private final ComplexElement constantElement = new ComplexElement(new DoubleVector2D(0, 0));
	private final BooleanElement showOrbitElement = new BooleanElement(false);
	private final BooleanElement showPreviewElement = new BooleanElement(true);
	private final BooleanElement showOrbitTrapElement = new BooleanElement(false);
	private final RectangleElement rectangleElement = new RectangleElement(new Rectangle(0.73, 0.03, 0.25, 0.25));
	private final SpeedElement speedElement = new SpeedElement(new Speed(new DoubleVector4D(0, 0, 50, 1), new DoubleVector4D(0, 0, 50, 0)));

	/**
	 * 
	 */
	public MandelbrotConfig() {
		super(MandelbrotConfig.CLASS_ID);
	}

	/**
	 * @return the mandelbrotFractalElement
	 */
	public MandelbrotFractalConfigElement getMandelbrotFractal() {
		return fractalSingleElement.getValue();
	}

	/**
	 * @param fractalElement the fractalElement to set
	 */
	public void setMandelbrotFractal(final MandelbrotFractalConfigElement fractalElement) {
		fractalSingleElement.setValue(fractalElement);
	}

	/**
	 * @param value
	 */
	public void setView(final View value) {
		viewElement.setValue(value);
	}

	/**
	 * @return
	 */
	public View getView() {
		return viewElement.getValue();
	}

	/**
	 * @return
	 */
	public Boolean isMandelbrotMode() {
		return imageModeElement.getValue() == 0;
	}

	/**
	 * @return
	 */
	public Integer getImageMode() {
		return imageModeElement.getValue();
	}

	/**
	 * @param mode
	 */
	public void setImageMode(final Integer mode) {
		imageModeElement.setValue(mode);
	}

	/**
	 * @return
	 */
	public Integer getInputMode() {
		return inputModeElement.getValue();
	}

	/**
	 * @param inputMode
	 */
	public void setInputMode(final Integer inputMode) {
		inputModeElement.setValue(inputMode);
	}

	/**
	 * @return
	 */
	public DoubleVector2D getConstant() {
		return constantElement.getValue();
	}

	/**
	 * @param constant
	 */
	public void setConstant(final DoubleVector2D constant) {
		constantElement.setValue(constant);
	}

	/**
	 * @return
	 */
	public Boolean getShowPreview() {
		return showPreviewElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setShowPreview(final Boolean value) {
		showPreviewElement.setValue(value);
	}

	/**
	 * @return
	 */
	public Boolean getShowOrbit() {
		return showOrbitElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setShowOrbit(final Boolean value) {
		showOrbitElement.setValue(value);
	}

	/**
	 * @return
	 */
	public Boolean getShowOrbitTrap() {
		return showOrbitTrapElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setShowOrbitTrap(final Boolean value) {
		showOrbitTrapElement.setValue(value);
	}

	/**
	 * @return
	 */
	public Rectangle getPreviewArea() {
		return rectangleElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setPreviewArea(final Rectangle value) {
		rectangleElement.setValue(value);
	}

	/**
	 * @return
	 */
	public Speed getSpeed() {
		return speedElement.getValue();
	}

	/**
	 * @param speed
	 */
	public void setSpeed(final Speed speed) {
		speedElement.setValue(speed);
	}

	/**
	 * @return
	 */
	@Override
	public MandelbrotConfig clone() {
		final MandelbrotConfig config = new MandelbrotConfig();
		config.setView(getView());
		config.setSpeed(getSpeed());
		config.setImageMode(getImageMode());
		config.setInputMode(getInputMode());
		config.setConstant(getConstant());
		config.setShowOrbit(getShowOrbit());
		config.setShowOrbitTrap(getShowOrbitTrap());
		config.setShowPreview(getShowPreview());
		config.setPreviewArea(getPreviewArea());
		config.setMandelbrotFractal(getMandelbrotFractal().clone());
		return config;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
	 */
	@Override
	public void copyFrom(ConfigElement source) {
		MandelbrotConfig config = (MandelbrotConfig) source;
		setView(config.getView());
		setSpeed(config.getSpeed());
		setImageMode(config.getImageMode());
		setInputMode(config.getInputMode());
		setConstant(config.getConstant());
		setShowOrbit(config.getShowOrbit());
		setShowOrbitTrap(config.getShowOrbitTrap());
		setShowPreview(config.getShowPreview());
		setPreviewArea(config.getPreviewArea());
		getFractalSingleElement().copyFrom(config.getFractalSingleElement());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#setContext(com.nextbreakpoint.nextfractal.core.config.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		viewElement.setContext(getContext());
		speedElement.setContext(getContext());
		imageModeElement.setContext(getContext());
		inputModeElement.setContext(getContext());
		constantElement.setContext(getContext());
		showOrbitElement.setContext(getContext());
		showOrbitTrapElement.setContext(getContext());
		showPreviewElement.setContext(getContext());
		rectangleElement.setContext(getContext());
		fractalSingleElement.setContext(getContext());
	}

	/**
	 * @return
	 */
	public ViewElement getViewElement() {
		return viewElement;
	}

	/**
	 * @return
	 */
	public SpeedElement getSpeedElement() {
		return speedElement;
	}

	/**
	 * @return
	 */
	public IntegerElement getImageModeElement() {
		return imageModeElement;
	}

	/**
	 * @return
	 */
	public IntegerElement getInputModeElement() {
		return inputModeElement;
	}

	/**
	 * @return
	 */
	public BooleanElement getShowOrbitElement() {
		return showOrbitElement;
	}

	/**
	 * @return
	 */
	public BooleanElement getShowOrbitTrapElement() {
		return showOrbitTrapElement;
	}

	/**
	 * @return
	 */
	public BooleanElement getShowPreviewElement() {
		return showPreviewElement;
	}

	/**
	 * @return
	 */
	public RectangleElement getPreviewAreaElement() {
		return rectangleElement;
	}

	/**
	 * @return
	 */
	public ComplexElement getConstantElement() {
		return constantElement;
	}

	/**
	 * @return the fractalSingleElement
	 */
	public SingleConfigElement<MandelbrotFractalConfigElement> getFractalSingleElement() {
		return fractalSingleElement;
	}

	/**
	 * @return the rectangleElement
	 */
	public RectangleElement getRectangleElement() {
		return rectangleElement;
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
		final MandelbrotConfig other = (MandelbrotConfig) obj;
		if (constantElement == null) {
			if (other.constantElement != null) {
				return false;
			}
		}
		else if (!constantElement.equals(other.constantElement)) {
			return false;
		}
		if (fractalSingleElement == null) {
			if (other.fractalSingleElement != null) {
				return false;
			}
		}
		else if (!fractalSingleElement.equals(other.fractalSingleElement)) {
			return false;
		}
		if (inputModeElement == null) {
			if (other.inputModeElement != null) {
				return false;
			}
		}
		else if (!inputModeElement.equals(other.inputModeElement)) {
			return false;
		}
		if (imageModeElement == null) {
			if (other.imageModeElement != null) {
				return false;
			}
		}
		else if (!imageModeElement.equals(other.imageModeElement)) {
			return false;
		}
		if (rectangleElement == null) {
			if (other.rectangleElement != null) {
				return false;
			}
		}
		else if (!rectangleElement.equals(other.rectangleElement)) {
			return false;
		}
		if (showOrbitElement == null) {
			if (other.showOrbitElement != null) {
				return false;
			}
		}
		else if (!showOrbitElement.equals(other.showOrbitElement)) {
			return false;
		}
		if (showOrbitTrapElement == null) {
			if (other.showOrbitTrapElement != null) {
				return false;
			}
		}
		else if (!showOrbitTrapElement.equals(other.showOrbitTrapElement)) {
			return false;
		}
		if (showPreviewElement == null) {
			if (other.showPreviewElement != null) {
				return false;
			}
		}
		else if (!showPreviewElement.equals(other.showPreviewElement)) {
			return false;
		}
		if (viewElement == null) {
			if (other.viewElement != null) {
				return false;
			}
		}
		else if (!viewElement.equals(other.viewElement)) {
			return false;
		}
		if (speedElement == null) {
			if (other.speedElement != null) {
				return false;
			}
		}
		else if (!speedElement.equals(other.speedElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		fractalSingleElement.dispose();
		viewElement.dispose();
		speedElement.dispose();
		imageModeElement.dispose();
		inputModeElement.dispose();
		constantElement.dispose();
		showOrbitElement.dispose();
		showOrbitTrapElement.dispose();
		showPreviewElement.dispose();
		rectangleElement.dispose();
		super.dispose();
	}
}
