/*
 * NextFractal 1.2.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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

import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDG;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDGRenderer;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CommandInfo;
import com.nextbreakpoint.nextfractal.contextfree.grammar.SimpleCanvas;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class V3RenderTest extends AbstractBaseTest {
	@Parameterized.Parameters
	public static Iterable<Object[]> parameters() {
		List<Object[]> params = new ArrayList<>();
		params.add(new Object[] { "/v3-single-shape.cfdg", "/v3-single-shape.png" });
		return params;
	}

	@Parameterized.Parameter(0)
	public String sourceName;

	@Parameterized.Parameter(1)
	public String imageName;

	@Test
	public void shouldRenderImage() throws IOException {
		TestCanvas canvas = new TestCanvas(200, 200);
		CFDG cfdg = parseSource(sourceName);
		cfdg.rulesLoaded();
		CFDGRenderer renderer = cfdg.renderer(200, 200, 1, 0, 0.1);
		renderer.run(canvas, false);
		BufferedImage actualImage = canvas.getImage();
		saveImage("tmp" + imageName, actualImage);
		BufferedImage expectedImage = loadImage(imageName);
		assertThat(compareImages(expectedImage, actualImage), is(equalTo(0.0)));
	}

	private double compareImages(BufferedImage expectedImage, BufferedImage actualImage) {
		int[] expexctedPixels = new int[expectedImage.getWidth() * expectedImage.getHeight()];
		int[] actualPixels = new int[actualImage.getWidth() * actualImage.getHeight()];
		expectedImage.getRGB(0, 0, expectedImage.getWidth(), expectedImage.getHeight(), expexctedPixels, 0, expectedImage.getWidth());
		actualImage.getRGB(0, 0, actualImage.getWidth(), actualImage.getHeight(), actualPixels, 0, actualImage.getWidth());
		return error(convertFormat(expexctedPixels), convertFormat(actualPixels));
	}

	private byte[] convertFormat(int[] data) {
		byte[] buffer = new byte[data.length * 4];
		for (int j = 0; j < data.length; j += 1) {
			buffer[j * 4 + 0] = (byte)(data[j] >> 24);
			buffer[j * 4 + 1] = (byte)(data[j] >> 16);
			buffer[j * 4 + 2] = (byte)(data[j] >> 8);
			buffer[j * 4 + 3] = (byte)(data[j] >> 0);
		}
		return buffer;
	}

	private double error(byte[] data1, byte[] data2) {
		double error = 0;
		for (int j = 0; j < data1.length; j += 4) {
			error += distance(data1, data2, j);
		}
		return error / (data1.length / 4);
	}

	private double distance(byte[] data1, byte[] data2, int i) {
		return Math.sqrt(Math.pow(data1[i + 0] - data2[i + 0], 2) + Math.pow(data1[i + 1] - data2[i + 1], 2) + Math.pow(data1[i + 2] - data2[i + 2], 2) + Math.pow(data1[i + 3] - data2[i + 3], 2));
	}

	private void saveImage(String imageName, BufferedImage image) throws IOException {
		File file = new File(imageName);
		System.out.println(file.getAbsoluteFile());
		ImageIO.write(image, "png", file);
	}

	private BufferedImage loadImage(String imageName) throws IOException {
		return ImageIO.read(getResourceAsStream(imageName));
	}

	private class TestCanvas extends SimpleCanvas {
		private BufferedImage image;
		private Graphics2D g2d;

		private TestCanvas(int width, int height) {
			super(width, height);
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		}

		public void primitive(int shapeType, double[] color, AffineTransform transform) {
			g2d.setColor(new Color((float)color[0], (float)color[1], (float)color[2], (float)color[3]));
			AffineTransform oldTransform = g2d.getTransform();
			g2d.setTransform(transform);
			switch (shapeType) {
				case 1:
					GeneralPath path = new GeneralPath();
					path.moveTo(-0.5, -0.5);
					path.lineTo(0.5, -0.5);
					path.lineTo(0.5, 0.5);
					path.lineTo(-0.5, 0.5);
					path.lineTo(-0.5, -0.5);
					path.closePath();
					g2d.fill(path);

			}
			g2d.setTransform(oldTransform);
		}

		public void start(boolean first, double[] backgroundColor, int currWidth, int currHeight) {
			g2d = image.createGraphics();
			g2d.setColor(new Color((float)backgroundColor[0], (float)backgroundColor[1], (float)backgroundColor[2], (float)backgroundColor[3]));
			g2d.fillRect(0, 0, currWidth, currHeight);
		}

		public void end() {
			g2d.dispose();
		}

		public void path(double[] color, AffineTransform tr, CommandInfo attr) {
		}

		public BufferedImage getImage() {
			return image;
		}
	}
}
