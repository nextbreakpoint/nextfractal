/*
 * NextFractal 1.0.4
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
package com.nextbreakpoint.nextfractal.contextfree.renderer;


/**
 * @author Andrea Medeghini
 */
public class DefaultContextFreeRenderer extends AbstractContextFreeRenderer {
//	private static final Logger logger = Logger.getLogger(DefaultContextFreeRenderer.class.getName());
//	
//	/**
//	 * @param threadPriority
//	 */
//	public DefaultContextFreeRenderer(final int threadPriority) {
//		super(threadPriority);
//	}
//
//	@Override
//	protected void doRender(boolean dynamicZoom) {
//		long totalTime = System.nanoTime();
//		long time = totalTime;
//		float border = 10f;
//		updateTransform();
//		float offsetX = (getBufferWidth() - getTile().getImageSize().getX()) / 2;
//		float offsetY = (getBufferHeight() - getTile().getImageSize().getY()) / 2;
//		int width = getTile().getImageSize().getX();
//		int height = getTile().getImageSize().getY();
//		Color32bit background = cfdgRuntime.getBackground();
//		String startshape = cfdgRuntime.getStartshape();
//		String variation = cfdgRuntime.getVariation();
//		CFContext context = new CFContext();
//		AffineTransform tileTransform = new AffineTransform();
//		if (cfdgRuntime.isUseTile() || cfdgRuntime.isUseSize()) {
//			tileTransform.translate(cfdgRuntime.getX(), cfdgRuntime.getY());
//			tileTransform.scale(cfdgRuntime.getWidth(), cfdgRuntime.getHeight());
//			context.setTiled(tileTransform, cfdgRuntime.getTileWidth(), cfdgRuntime.getTileHeight(), cfdgRuntime.isUseTile(), cfdgRuntime.isUseSize());
//		}
//		CFBuilder builder = new CFBuilder(context);
//		for (int i = 0; i < cfdgRuntime.getFigureElementCount(); i++) {
//			FigureRuntimeElement figure = cfdgRuntime.getFigureElement(i);
//			figure.process(builder);
//		}
//		context.reloadRules();
//		int initialShapeType = context.encodeShapeName(startshape);
//		CFRenderer renderer = new CFRenderer(context, variation.hashCode(), width, height, border, 0.03f);
//		CFModification worldState = new CFModification();
//		boolean partialDraw = true;
//		int reportAt = 10000;
//		CFShape shape = new CFShape(initialShapeType, worldState);
//		shape.getModification().getColorTarget().setAlpha(1);
//		shape.getModification().getColor().setAlpha(1);
//		context.setBackground(new CFColor(background.getARGB()));
//		renderer.processShape(shape);
//		if (logger.isLoggable(Level.FINE)) {
//			long elapsed = (System.nanoTime() - time) / 1000000;
//			logger.fine("Build time " + elapsed + "ms");
//		}
//		percent = 20;
//		for (;;) {
//			if (isInterrupted()) {
//				break;
//			}
//			if (renderer.getUnfinishedCount() == 0) {
//				break;
//			}
//			if ((renderer.getFinishedCount() + renderer.getUnfinishedCount()) > CFRenderer.MAX_SHAPES) {
//				break;
//			}
//			CFShape s = renderer.nextUnfinishedShape();
//			renderer.executeShape(s);
//			if (renderer.getFinishedCount() > reportAt) {
//				if (partialDraw) {
//					render(renderer, offsetX, offsetY, true);
//				}
//				reportAt = 2 * renderer.getFinishedCount();
//			}
//		}
//        renderer.sortShapes();
//		percent = 70;
//		if (logger.isLoggable(Level.FINE)) {
//			logger.fine("Total shapes " + renderer.getFinishedCount());
//		}
//		time = System.nanoTime();
//		render(renderer, offsetX, offsetY, false);
//		if (logger.isLoggable(Level.FINE)) {
//			long elapsed = (System.nanoTime() - time) / 1000000;
//			logger.fine("Render time " + elapsed + "ms");
//		}
//		percent = 100;
//		if (logger.isLoggable(Level.FINE)) {
//			logger.fine("Total time " + (System.nanoTime() - totalTime) / 1000000 + "ms");
//		}
//	}
//
//	private void render(CFRenderer renderer, float offsetX, float offsetY, boolean partial) {
////TODO restore		Graphics2D g2d = getGraphics();
////		configure(g2d);
////		AffineTransform tmpTransform = g2d.getTransform();
////		g2d.translate(offsetX, offsetY);
////		renderer.render(g2d, partial);
////		g2d.setTransform(tmpTransform);
////		swapImages();
//	}
//
//	protected void configure(Graphics2D g2d) {
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
//		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
//		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
//		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
//		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
//	}
}
