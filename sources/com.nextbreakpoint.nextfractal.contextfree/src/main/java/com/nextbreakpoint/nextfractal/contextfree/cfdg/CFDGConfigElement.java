/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.cfdg;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElement;
import com.nextbreakpoint.nextfractal.core.common.BooleanElement;
import com.nextbreakpoint.nextfractal.core.common.ColorElement;
import com.nextbreakpoint.nextfractal.core.common.FloatElement;
import com.nextbreakpoint.nextfractal.core.common.StringElement;
import com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ListConfigElement;
import com.nextbreakpoint.nextfractal.core.util.Color32bit;
import com.nextbreakpoint.nextfractal.core.util.Colors;

/**
 * @author Andrea Medeghini
 */
public class CFDGConfigElement extends AbstractConfigElement {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "CFDG";
	private final StringElement startshapeElement = new StringElement("");
	private final StringElement variationElement = new StringElement("ABCD");
	private final StringElement baseDirElement = new StringElement(System.getProperty("user.home"));
	private final BooleanElement useSizeElement = new BooleanElement(false);
	private final BooleanElement useTileElement = new BooleanElement(false);
	private final FloatElement xElement = new FloatElement(0f);
	private final FloatElement yElement = new FloatElement(0f);
	private final FloatElement widthElement = new FloatElement(1f);
	private final FloatElement heightElement = new FloatElement(1f);
	private final FloatElement tileWidthElement = new FloatElement(1f);
	private final FloatElement tileHeightElement = new FloatElement(1f);
	private final ColorElement backgroundElement = new ColorElement(new Color32bit(0xFFFFFFFF));
	private final ListConfigElement<FigureConfigElement> figureListElement = new ListConfigElement<FigureConfigElement>("figureListElement");

	/**
	 * Constructs a new element.
	 */
	public CFDGConfigElement() {
		super(CFDGConfigElement.CLASS_ID);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#setContext(com.nextbreakpoint.nextfractal.core.config.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		startshapeElement.setContext(context);
		variationElement.setContext(context);
		baseDirElement.setContext(context);
		useSizeElement.setContext(context);
		useTileElement.setContext(context);
		xElement.setContext(context);
		yElement.setContext(context);
		widthElement.setContext(context);
		heightElement.setContext(context);
		tileWidthElement.setContext(context);
		tileHeightElement.setContext(context);
		backgroundElement.setContext(context);
		figureListElement.setContext(context);
	}

	/**
	 * @return
	 */
	@Override
	public CFDGConfigElement clone() {
		final CFDGConfigElement element = new CFDGConfigElement();
		element.setStartshape(getStartshape());
		element.setVariation(getVariation());
		element.setBaseDir(getBaseDir());
		element.setUseSize(isUseSize());
		element.setUseTile(isUseTile());
		element.setX(getX());
		element.setY(getY());
		element.setWidth(getWidth());
		element.setHeight(getHeight());
		element.setTileWidth(getTileWidth());
		element.setTileHeight(getTileHeight());
		element.setBackground(getBackground());
		element.figureListElement.copyFrom(getFigureListElement());
		return element;
	}
	
	/**
	 *
	 */
	@Override
	public void copyFrom(ConfigElement source) {
		CFDGConfigElement cfdgElement = (CFDGConfigElement) source;
		setStartshape(cfdgElement.getStartshape());
		setVariation(cfdgElement.getVariation());
		setBaseDir(cfdgElement.getBaseDir());
		setUseSize(cfdgElement.isUseSize());
		setUseTile(cfdgElement.isUseTile());
		setX(cfdgElement.getX());
		setY(cfdgElement.getY());
		setWidth(cfdgElement.getWidth());
		setHeight(cfdgElement.getHeight());
		setTileWidth(cfdgElement.getTileWidth());
		setTileHeight(cfdgElement.getTileHeight());
		setBackground(cfdgElement.getBackground());
		figureListElement.copyFrom(cfdgElement.getFigureListElement());
	}

	/**
	 * @return
	 */
	public StringElement getStartshapeElement() {
		return startshapeElement;
	}
	
	/**
	 * @return
	 */
	public String getStartshape() {
		return startshapeElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setStartshape(final String value) {
		startshapeElement.setValue(value);
	}
	/**
	 * @return
	 */
	public StringElement getVariationElement() {
		return variationElement;
	}
	
	/**
	 * @return
	 */
	public String getVariation() {
		return variationElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setVariation(final String value) {
		variationElement.setValue(value);
	}
	/**
	 * @return
	 */
	public StringElement getBaseDirElement() {
		return baseDirElement;
	}
	
	/**
	 * @return
	 */
	public String getBaseDir() {
		return baseDirElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setBaseDir(final String value) {
		baseDirElement.setValue(value);
	}
	/**
	 * @return
	 */
	public BooleanElement getUseSizeElement() {
		return useSizeElement;
	}
	
	/**
	 * @return
	 */
	public Boolean isUseSize() {
		return useSizeElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setUseSize(final Boolean value) {
		useSizeElement.setValue(value);
	}
	/**
	 * @return
	 */
	public BooleanElement getUseTileElement() {
		return useTileElement;
	}
	
	/**
	 * @return
	 */
	public Boolean isUseTile() {
		return useTileElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setUseTile(final Boolean value) {
		useTileElement.setValue(value);
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
	public FloatElement getWidthElement() {
		return widthElement;
	}
	
	/**
	 * @return
	 */
	public Float getWidth() {
		return widthElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setWidth(final Float value) {
		widthElement.setValue(value);
	}
	/**
	 * @return
	 */
	public FloatElement getHeightElement() {
		return heightElement;
	}
	
	/**
	 * @return
	 */
	public Float getHeight() {
		return heightElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setHeight(final Float value) {
		heightElement.setValue(value);
	}
	/**
	 * @return
	 */
	public FloatElement getTileWidthElement() {
		return tileWidthElement;
	}
	
	/**
	 * @return
	 */
	public Float getTileWidth() {
		return tileWidthElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setTileWidth(final Float value) {
		tileWidthElement.setValue(value);
	}
	/**
	 * @return
	 */
	public FloatElement getTileHeightElement() {
		return tileHeightElement;
	}
	
	/**
	 * @return
	 */
	public Float getTileHeight() {
		return tileHeightElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setTileHeight(final Float value) {
		tileHeightElement.setValue(value);
	}
	/**
	 * @return
	 */
	public ColorElement getBackgroundElement() {
		return backgroundElement;
	}
	
	/**
	 * @return
	 */
	public Color32bit getBackground() {
		return backgroundElement.getValue();
	}

	/**
	 * @param value
	 */
	public void setBackground(final Color32bit value) {
		backgroundElement.setValue(value);
	}
	/**
	 * @return
	 */
	public ListConfigElement<FigureConfigElement> getFigureListElement() {
		return figureListElement;
	}

	/**
	 * Returns a figure element.
	 * 
	 * @param index the figure index.
	 * @return the figure.
	 */
	public FigureConfigElement getFigureConfigElement(final int index) {
		return figureListElement.getElement(index);
	}

	/**
	 * Returns a figure element index.
	 * 
	 * @param figureElement the figure element.
	 * @return the index.
	 */
	public int indexOfFigureConfigElement(final FigureConfigElement figureElement) {
		return figureListElement.indexOfElement(figureElement);
	}

	/**
	 * Returns the number of figure elements.
	 * 
	 * @return the number of figure elements.
	 */
	public int getFigureConfigElementCount() {
		return figureListElement.getElementCount();
	}

	/**
	 * Adds a figure element.
	 * 
	 * @param figureElement the figure to add.
	 */
	public void appendFigureConfigElement(final FigureConfigElement figureElement) {
		figureListElement.appendElement(figureElement);
	}

	/**
	 * Adds a figure element.
	 * 
	 * @param index the index.
	 * @param figureElement the figure to add.
	 */
	public void insertFigureConfigElementAfter(final int index, final FigureConfigElement figureElement) {
		figureListElement.insertElementAfter(index, figureElement);
	}

	/**
	 * Adds a figure element.
	 * 
	 * @param index the index.
	 * @param figureElement the figure to add.
	 */
	public void insertFigureConfigElementBefore(final int index, final FigureConfigElement figureElement) {
		figureListElement.insertElementBefore(index, figureElement);
	}

	/**
	 * Removes a figure element.
	 * 
	 * @param index the element index to remove.
	 */
	public void removeFigureConfigElement(final int index) {
		figureListElement.removeElement(index);
	}

	/**
	 * Removes a figure element.
	 * 
	 * @param figureElement the figure to remove.
	 */
	public void removeFigureConfigElement(final FigureConfigElement figureElement) {
		figureListElement.removeElement(figureElement);
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
		final CFDGConfigElement other = (CFDGConfigElement) obj;
		if (startshapeElement == null) {
			if (other.startshapeElement != null) {
				return false;
			}
		}
		else if (!startshapeElement.equals(other.startshapeElement)) {
			return false;
		}
		if (variationElement == null) {
			if (other.variationElement != null) {
				return false;
			}
		}
		else if (!variationElement.equals(other.variationElement)) {
			return false;
		}
		if (baseDirElement == null) {
			if (other.baseDirElement != null) {
				return false;
			}
		}
		else if (!baseDirElement.equals(other.baseDirElement)) {
			return false;
		}
		if (useSizeElement == null) {
			if (other.useSizeElement != null) {
				return false;
			}
		}
		else if (!useSizeElement.equals(other.useSizeElement)) {
			return false;
		}
		if (useTileElement == null) {
			if (other.useTileElement != null) {
				return false;
			}
		}
		else if (!useTileElement.equals(other.useTileElement)) {
			return false;
		}
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
		if (widthElement == null) {
			if (other.widthElement != null) {
				return false;
			}
		}
		else if (!widthElement.equals(other.widthElement)) {
			return false;
		}
		if (heightElement == null) {
			if (other.heightElement != null) {
				return false;
			}
		}
		else if (!heightElement.equals(other.heightElement)) {
			return false;
		}
		if (tileWidthElement == null) {
			if (other.tileWidthElement != null) {
				return false;
			}
		}
		else if (!tileWidthElement.equals(other.tileWidthElement)) {
			return false;
		}
		if (tileHeightElement == null) {
			if (other.tileHeightElement != null) {
				return false;
			}
		}
		else if (!tileHeightElement.equals(other.tileHeightElement)) {
			return false;
		}
		if (backgroundElement == null) {
			if (other.backgroundElement != null) {
				return false;
			}
		}
		else if (!backgroundElement.equals(other.backgroundElement)) {
			return false;
		}
		if (figureListElement == null) {
			if (other.figureListElement != null) {
				return false;
			}
		}
		else if (!figureListElement.equals(other.figureListElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		startshapeElement.dispose();
		variationElement.dispose();
		baseDirElement.dispose();
		useSizeElement.dispose();
		useTileElement.dispose();
		xElement.dispose();
		yElement.dispose();
		widthElement.dispose();
		heightElement.dispose();
		tileWidthElement.dispose();
		tileHeightElement.dispose();
		backgroundElement.dispose();
		figureListElement.dispose();
		super.dispose();
	}
	
	public void toCFDG(CFDGBuilder builder) {
		if (getStartshape() != null) {
			builder.append("startshape ");
			builder.append(getStartshape());
			builder.append("\n\n");
		}
		if (isUseSize()) {
			builder.append("size {");
			if ((getWidth() != 1f) || (getHeight() != 1f)) {
				builder.append(" s");
				if (getWidth() != 1f) {
					builder.append(" ");
					builder.append(getWidth());
				}
				if (getHeight() != 1f) {
					builder.append(" ");
					builder.append(getHeight());
				}
			}
			if (getX() != 0f) {
				builder.append(" x ");
				builder.append(getX());
			}
			if (getY() != 0f) {
				builder.append(" y ");
				builder.append(getY());
			}
			builder.append(" }\n\n");
		}
		if (isUseTile()) {
			builder.append("tile { ");
			builder.append("s");
			if (getTileWidth() != 1f) {
				builder.append(" ");
				builder.append(getTileWidth());
			}
			if (getTileHeight() != 1f) {
				builder.append(" ");
				builder.append(getTileHeight());
			}
			builder.append(" }\n\n");
		}
		if (getBackground() != null) {
			builder.append("background { ");
			if (getBackground().getAlpha() != 255) {
				builder.append("a ");
				builder.append(1f - getBackground().getAlpha() / 255f);
				builder.append(" ");
			}
			float[] hsbvals = new float[3];
			Colors.toHSB(getBackground().getARGB(), hsbvals );
			if (hsbvals[0] != 0) {
				builder.append("h ");
				builder.append(0f - hsbvals[0] * 360f);
			}
			if (hsbvals[2] != 0) {
				builder.append("b ");
				builder.append(1f - hsbvals[2]);
			}
			if (hsbvals[1] != 0) {
				builder.append("sat ");
				builder.append(0f - hsbvals[1]);
			}
			builder.append(" }\n\n");
		}
		for (int i = 0; i < figureListElement.getElementCount(); i++) {
			figureListElement.getElement(i).toCFDG(builder);
			builder.append("\n");
		}
	}
}
