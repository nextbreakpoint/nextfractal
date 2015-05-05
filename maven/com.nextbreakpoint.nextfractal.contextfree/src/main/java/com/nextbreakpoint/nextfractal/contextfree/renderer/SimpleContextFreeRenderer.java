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
public final class SimpleContextFreeRenderer extends DefaultContextFreeRenderer {
//	private static final Logger logger = Logger.getLogger(SimpleContextFreeRenderer.class.getName());
//	
//	/**
//	 * 
//	 */
//	public SimpleContextFreeRenderer(final int threadPriority) {
//		super(threadPriority);
//	}
//
//	@Override
//	protected void doRender(boolean dynamicZoom) {
////		long totalTime = System.nanoTime();
////		long time = System.nanoTime();
////		updateTransform();
////		float offsetX = (getBufferWidth() - getTile().getImageSize().getX()) / 2;
////		float offsetY = (getBufferHeight() - getTile().getImageSize().getY()) / 2;
////		int width = getTile().getImageSize().getX();
////		int height = getTile().getImageSize().getY();
////		Color32bit background = cfdgRuntime.getBackground();
////		String startshape = cfdgRuntime.getStartshape();
////		String variation = cfdgRuntime.getVariation();
////		CFContext context = new CFContext();
////		CFBuilder builder = new CFBuilder(context);
////		for (int i = 0; i < cfdgRuntime.getFigureElementCount(); i++) {
////			FigureRuntimeElement figure = cfdgRuntime.getFigureElement(i);
////			figure.process(builder);
////		}
////		context.reloadRules();
////		int initialShapeType = context.encodeShapeName(startshape);
////		CFRenderer renderer = new CFRenderer(context, variation.hashCode(), width, height, 0f, 0.5f);
////		CFModification worldState = new CFModification();
////		boolean partialDraw = true;
////		int reportAt = 250;
////		CFShape shape = new CFShape(initialShapeType, worldState);
////		shape.getModification().getColorTarget().setAlpha(1);
////		shape.getModification().getColor().setAlpha(1);
////		context.setBackground(new CFColor(background.getARGB()));
////		renderer.processShape(shape);
////		if (logger.isDebugEnabled()) {
////			long elapsed = (System.nanoTime() - time) / 1000000;
////			logger.debug("Build time " + elapsed + "ms");
////		}
////		percent = 20;
////		for (;;) {
////			if (renderer.getUnfinishedCount() == 0) {
////				break;
////			}
////			if ((renderer.getFinishedCount() + renderer.getUnfinishedCount()) > CFRenderer.MAX_SHAPES) {
////				break;
////			}
////			CFShape s = renderer.nextUnfinishedShape();
////			renderer.executeShape(s);
////			if (renderer.getFinishedCount() > reportAt) {
////				if (partialDraw) {
////				}
////				reportAt = 2 * renderer.getFinishedCount();
////			}
////		}
////        renderer.sortShapes();
////		percent = 70;
////		if (logger.isDebugEnabled()) {
////			logger.debug("Total shapes " + renderer.getFinishedCount());
////		}
////		time = System.nanoTime();
////		Graphics2D g2d = getGraphics();
////		configure(g2d);
////		AffineTransform tmpTransform = g2d.getTransform();
////		g2d.translate(offsetX, offsetY);
////		renderer.render(g2d);
////		g2d.setTransform(tmpTransform);
////		swapImages();
////		if (logger.isDebugEnabled()) {
////			long elapsed = (System.nanoTime() - time) / 1000000;
////			logger.debug("Render time " + elapsed + "ms");
////		}
////		percent = 100;
////		if (logger.isDebugEnabled()) {
////			logger.debug("Total time " + (System.nanoTime() - totalTime) / 1000000 + "ms");
////		}
//	}
}
