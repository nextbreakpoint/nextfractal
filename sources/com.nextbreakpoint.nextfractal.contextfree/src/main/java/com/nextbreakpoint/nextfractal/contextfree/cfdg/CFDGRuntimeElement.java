/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.cfdg;

import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.figure.FigureRuntimeElement;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.runtime.ListConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.ListRuntimeElement;
import com.nextbreakpoint.nextfractal.core.runtime.RuntimeElement;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.util.Color32bit;

/**
 * @author Andrea Medeghini
 */
 public class CFDGRuntimeElement extends RuntimeElement {
	private CFDGConfigElement cfdgElement;
	private String startshape;
	private StartshapeListener startshapeListener;
	private String variation;
	private VariationListener variationListener;
	private String baseDir;
	private BaseDirListener baseDirListener;
	private Boolean useSize;
	private UseSizeListener useSizeListener;
	private Boolean useTile;
	private UseTileListener useTileListener;
	private Float x;
	private XListener xListener;
	private Float y;
	private YListener yListener;
	private Float width;
	private WidthListener widthListener;
	private Float height;
	private HeightListener heightListener;
	private Float tileWidth;
	private TileWidthListener tileWidthListener;
	private Float tileHeight;
	private TileHeightListener tileHeightListener;
	private Color32bit background;
	private BackgroundListener backgroundListener;
	private ListRuntimeElement<FigureRuntimeElement> figureListElement;
	private FigureListElementListener figureListElementListener;

	/**
	 * Constructs a new CFDGRuntimeElement.
	 * 
	 * @param registry
	 * @param CFDGRuntimeElementElement
	 */
	public CFDGRuntimeElement(final CFDGConfigElement cfdgElement) {
		if (cfdgElement == null) {
			throw new IllegalArgumentException("cfdgElement is null");
		}
		this.cfdgElement = cfdgElement;
		setStartshape(cfdgElement.getStartshape());
		startshapeListener = new StartshapeListener();
		cfdgElement.getStartshapeElement().addChangeListener(startshapeListener);
		setVariation(cfdgElement.getVariation());
		variationListener = new VariationListener();
		cfdgElement.getVariationElement().addChangeListener(variationListener);
		setBaseDir(cfdgElement.getBaseDir());
		baseDirListener = new BaseDirListener();
		cfdgElement.getBaseDirElement().addChangeListener(baseDirListener);
		setUseSize(cfdgElement.isUseSize());
		useSizeListener = new UseSizeListener();
		cfdgElement.getUseSizeElement().addChangeListener(useSizeListener);
		setUseTile(cfdgElement.isUseTile());
		useTileListener = new UseTileListener();
		cfdgElement.getUseTileElement().addChangeListener(useTileListener);
		setX(cfdgElement.getX());
		xListener = new XListener();
		cfdgElement.getXElement().addChangeListener(xListener);
		setY(cfdgElement.getY());
		yListener = new YListener();
		cfdgElement.getYElement().addChangeListener(yListener);
		setWidth(cfdgElement.getWidth());
		widthListener = new WidthListener();
		cfdgElement.getWidthElement().addChangeListener(widthListener);
		setHeight(cfdgElement.getHeight());
		heightListener = new HeightListener();
		cfdgElement.getHeightElement().addChangeListener(heightListener);
		setTileWidth(cfdgElement.getTileWidth());
		tileWidthListener = new TileWidthListener();
		cfdgElement.getTileWidthElement().addChangeListener(tileWidthListener);
		setTileHeight(cfdgElement.getTileHeight());
		tileHeightListener = new TileHeightListener();
		cfdgElement.getTileHeightElement().addChangeListener(tileHeightListener);
		setBackground(cfdgElement.getBackground());
		backgroundListener = new BackgroundListener();
		cfdgElement.getBackgroundElement().addChangeListener(backgroundListener);
		figureListElement = new ListRuntimeElement<FigureRuntimeElement>();
		for (int i = 0; i < cfdgElement.getFigureConfigElementCount(); i++) {
			figureListElement.appendElement(new FigureRuntimeElement(cfdgElement.getFigureConfigElement(i)));
		}
		figureListElementListener = new FigureListElementListener();
		cfdgElement.getFigureListElement().addChangeListener(figureListElementListener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if ((cfdgElement != null) && (startshapeListener != null)) {
			cfdgElement.getStartshapeElement().removeChangeListener(startshapeListener);
		}
		startshapeListener = null;
		if ((cfdgElement != null) && (variationListener != null)) {
			cfdgElement.getVariationElement().removeChangeListener(variationListener);
		}
		variationListener = null;
		if ((cfdgElement != null) && (baseDirListener != null)) {
			cfdgElement.getBaseDirElement().removeChangeListener(baseDirListener);
		}
		baseDirListener = null;
		if ((cfdgElement != null) && (useSizeListener != null)) {
			cfdgElement.getUseSizeElement().removeChangeListener(useSizeListener);
		}
		useSizeListener = null;
		if ((cfdgElement != null) && (useTileListener != null)) {
			cfdgElement.getUseTileElement().removeChangeListener(useTileListener);
		}
		useTileListener = null;
		if ((cfdgElement != null) && (xListener != null)) {
			cfdgElement.getXElement().removeChangeListener(xListener);
		}
		xListener = null;
		if ((cfdgElement != null) && (yListener != null)) {
			cfdgElement.getYElement().removeChangeListener(yListener);
		}
		yListener = null;
		if ((cfdgElement != null) && (widthListener != null)) {
			cfdgElement.getWidthElement().removeChangeListener(widthListener);
		}
		widthListener = null;
		if ((cfdgElement != null) && (heightListener != null)) {
			cfdgElement.getHeightElement().removeChangeListener(heightListener);
		}
		heightListener = null;
		if ((cfdgElement != null) && (tileWidthListener != null)) {
			cfdgElement.getTileWidthElement().removeChangeListener(tileWidthListener);
		}
		tileWidthListener = null;
		if ((cfdgElement != null) && (tileHeightListener != null)) {
			cfdgElement.getTileHeightElement().removeChangeListener(tileHeightListener);
		}
		tileHeightListener = null;
		if ((cfdgElement != null) && (backgroundListener != null)) {
			cfdgElement.getBackgroundElement().removeChangeListener(backgroundListener);
		}
		backgroundListener = null;
		if ((cfdgElement != null) && (figureListElementListener != null)) {
			cfdgElement.getFigureListElement().removeChangeListener(figureListElementListener);
		}
		figureListElement.dispose();
		figureListElementListener = null;
		cfdgElement = null;
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		boolean cfdgChanged = false;
		return super.isChanged() || cfdgChanged;
	}

	/**
	 * @return the startshape.
	 */
	public String getStartshape() {
		return startshape;
	}

	private void setStartshape(final String startshape) {
		this.startshape = startshape;
	}
	
	private class StartshapeListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setStartshape((String) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the variation.
	 */
	public String getVariation() {
		return variation;
	}

	private void setVariation(final String variation) {
		this.variation = variation;
	}
	
	private class VariationListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setVariation((String) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the baseDir.
	 */
	public String getBaseDir() {
		return baseDir;
	}

	private void setBaseDir(final String baseDir) {
		this.baseDir = baseDir;
	}
	
	private class BaseDirListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setBaseDir((String) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the useSize.
	 */
	public Boolean isUseSize() {
		return useSize;
	}

	private void setUseSize(final Boolean useSize) {
		this.useSize = useSize;
	}
	
	private class UseSizeListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setUseSize((Boolean) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the useTile.
	 */
	public Boolean isUseTile() {
		return useTile;
	}

	private void setUseTile(final Boolean useTile) {
		this.useTile = useTile;
	}
	
	private class UseTileListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setUseTile((Boolean) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the x.
	 */
	public Float getX() {
		return x;
	}

	private void setX(final Float x) {
		this.x = x;
	}
	
	private class XListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setX((Float) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the y.
	 */
	public Float getY() {
		return y;
	}

	private void setY(final Float y) {
		this.y = y;
	}
	
	private class YListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setY((Float) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the width.
	 */
	public Float getWidth() {
		return width;
	}

	private void setWidth(final Float width) {
		this.width = width;
	}
	
	private class WidthListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setWidth((Float) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the height.
	 */
	public Float getHeight() {
		return height;
	}

	private void setHeight(final Float height) {
		this.height = height;
	}
	
	private class HeightListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setHeight((Float) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the tileWidth.
	 */
	public Float getTileWidth() {
		return tileWidth;
	}

	private void setTileWidth(final Float tileWidth) {
		this.tileWidth = tileWidth;
	}
	
	private class TileWidthListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setTileWidth((Float) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the tileHeight.
	 */
	public Float getTileHeight() {
		return tileHeight;
	}

	private void setTileHeight(final Float tileHeight) {
		this.tileHeight = tileHeight;
	}
	
	private class TileHeightListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setTileHeight((Float) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * @return the background.
	 */
	public Color32bit getBackground() {
		return background;
	}

	private void setBackground(final Color32bit background) {
		this.background = background;
	}
	
	private class BackgroundListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setBackground((Color32bit) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * Returns a figure element.
	 * 
	 * @param index the figure index.
	 * @return the figure.
	 */
	public FigureRuntimeElement getFigureElement(final int index) {
		return figureListElement.getElement(index);
	}

	/**
	 * Returns a figure element index.
	 * 
	 * @param figureElement the figure element.
	 * @return the index.
	 */
	public int indexOfFigureElement(final FigureRuntimeElement figureElement) {
		return figureListElement.indexOfElement(figureElement);
	}

	/**
	 * Returns the number of figure elements.
	 * 
	 * @return the number of figure elements.
	 */
	public int getFigureElementCount() {
		return figureListElement.getElementCount();
	}

	private void setFigureElement(final int index, FigureRuntimeElement element) {
		figureListElement.setElement(index, element);
	}

	private void appendFigureElement(final FigureRuntimeElement figureElement) {
		figureListElement.appendElement(figureElement);
	}

	private void insertFigureElementAfter(final int index, final FigureRuntimeElement figureElement) {
		figureListElement.insertElementAfter(index, figureElement);
	}

	private void insertFigureElementBefore(final int index, final FigureRuntimeElement figureElement) {
		figureListElement.insertElementBefore(index, figureElement);
	}

	private void removeFigureElement(final int index) {
		figureListElement.removeElement(index);
	}

	private void moveUpFigureElement(final int index) {
		figureListElement.moveElementUp(index);
	}

	private void moveDownFigureElement(final int index) {
		figureListElement.moveElementDown(index);
	}
	
	private class FigureListElementListener implements ElementChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					appendFigureElement(new FigureRuntimeElement ((FigureConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insertFigureElementAfter(((Integer) e.getParams()[1]).intValue(), new FigureRuntimeElement ((FigureConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insertFigureElementBefore(((Integer) e.getParams()[1]).intValue(), new FigureRuntimeElement ((FigureConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					removeFigureElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUpFigureElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDownFigureElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					setFigureElement(((Integer) e.getParams()[1]).intValue(), new FigureRuntimeElement ((FigureConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
}
