/*
� * NextFractal 7.0 
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
package com.nextbreakpoint.nextfractal.contextfree.test;

import static junit.framework.Assert.fail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeConfig;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRuntime;
import com.nextbreakpoint.nextfractal.contextfree.parser.ContextFreeParser;
import com.nextbreakpoint.nextfractal.contextfree.renderer.ContextFreeRenderer;
import com.nextbreakpoint.nextfractal.contextfree.renderer.DefaultContextFreeRenderer;
import com.nextbreakpoint.nextfractal.core.runtime.tree.RootNode;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.twister.renderer.java2D.Java2DRenderFactory;

public class TestContextFreeRenderer {
	private static final int IMAGE_HEIGHT = 500;
	private static final int IMAGE_WIDTH = 500;

	@Test
	public void parse() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(System.getProperty("cfdgFile"))));
			String line = null;
			StringBuilder builder = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}
			String text = builder.toString();
			System.out.println(text);
			ContextFreeParser parser = new ContextFreeParser();
			ContextFreeConfig config = parser.parseConfig(new File(System.getProperty("user.home")), text);
			RootNode rootNode = new RootNode("contextfree");
			ContextFreeConfigNodeBuilder nodeBuilder = new ContextFreeConfigNodeBuilder(config);
			nodeBuilder.createNodes(rootNode);
			System.out.println(rootNode);
			CFDGBuilder cfdgBuilder = new CFDGBuilder();
			config.getCFDG().toCFDG(cfdgBuilder);
			System.out.println(cfdgBuilder.toString());
			ContextFreeRuntime runtime = new ContextFreeRuntime(config);
			Java2DRenderFactory renderFactory = new Java2DRenderFactory();
			ContextFreeRenderer renderer = new DefaultContextFreeRenderer(Thread.MIN_PRIORITY);
			IntegerVector2D imageSize = new IntegerVector2D(IMAGE_WIDTH, IMAGE_HEIGHT);
			IntegerVector2D nullSize = new IntegerVector2D(0, 0);
			Tile tile = new Tile(imageSize, imageSize, nullSize, nullSize);
			renderer.setRenderFactory(renderFactory);
			renderer.setTile(tile);
			IntegerVector2D bufferSize = new IntegerVector2D(tile.getTileSize().getX() + tile.getTileBorder().getX() * 2, tile.getTileSize().getY() + tile.getTileBorder().getY() * 2);
			Surface surface = new Surface(bufferSize.getX(), bufferSize.getY());
			renderer.setRuntime(runtime);
			renderer.start();
			try {
				renderer.startRenderer();
				renderer.joinRenderer();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			Graphics2D g2d = surface.getGraphics2D();
			RenderGraphicsContext gc = renderFactory.createGraphicsContext(g2d);
			renderer.drawImage(gc);
			g2d.setColor(Color.WHITE);
			g2d.drawRect(0, 0, surface.getWidth() - 1, surface.getHeight() - 1);
			ImageIO.write(surface.getImage(), "png", new File(System.getProperty("cfdgFile").replace(".cfdg", ".png")));
			renderer.stop();
			renderer.dispose();
			rootNode.dispose();
			runtime.dispose();
			config.dispose();
			surface.dispose();
		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
