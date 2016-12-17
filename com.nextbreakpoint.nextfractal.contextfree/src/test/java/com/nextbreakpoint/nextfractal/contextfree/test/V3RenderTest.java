/*
 * NextFractal 2.0.0
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
import com.nextbreakpoint.nextfractal.contextfree.renderer.Renderer;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.java2D.Java2DRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class V3RenderTest extends AbstractBaseTest {
	@Parameterized.Parameters
	public static Iterable<Object[]> parameters() {
		List<Object[]> params = new ArrayList<>();
		params.add(new Object[] { "/v3-shape-square.cfdg", "/v3-shape-square.png" });
		params.add(new Object[] { "/v3-shape-triangle.cfdg", "/v3-shape-triangle.png" });
		params.add(new Object[] { "/v3-shape-circle.cfdg", "/v3-shape-circle.png" });
		params.add(new Object[] { "/v3-shape-transform.cfdg", "/v3-shape-transform.png" });
		params.add(new Object[] { "/v3-shape-multiple-primitives.cfdg", "/v3-shape-multiple-primitives.png" });
		params.add(new Object[] { "/v3-shape-initial-adjustment.cfdg", "/v3-shape-initial-adjustment.png" });
		params.add(new Object[] { "/v3-shape-options.cfdg", "/v3-shape-options.png" });
		params.add(new Object[] { "/v3-shapes-blah.cfdg", "/v3-shapes-blah.png" });
		params.add(new Object[] { "/v3-shapes-blah-random.cfdg", "/v3-shapes-blah-random.png" });
		params.add(new Object[] { "/v3-shape-variable.cfdg", "/v3-shape-variable.png" });
		params.add(new Object[] { "/v3-shape-function.cfdg", "/v3-shape-function.png" });
		params.add(new Object[] { "/v3-shape-path.cfdg", "/v3-shape-path.png" });
		params.add(new Object[] { "/v3-shape-loop.cfdg", "/v3-shape-loop.png" });
		params.add(new Object[] { "/v3-shape-if.cfdg", "/v3-shape-if.png" });
		params.add(new Object[] { "/v3-shape-path2.cfdg", "/v3-shape-path2.png" });
		params.add(new Object[] { "/v3-shape-switch.cfdg", "/v3-shape-switch.png" });
		params.add(new Object[] { "/v3-shape-trans.cfdg", "/v3-shape-trans.png" });
		params.add(new Object[] { "/v3-shape-parameters.cfdg", "/v3-shape-parameters.png" });
		params.add(new Object[] { "/v3-shape-include.cfdg", "/v3-shape-include.png" });
		params.add(new Object[] { "/v3-shape-tile.cfdg", "/v3-shape-tile.png" });
		params.add(new Object[] { "/v3-shape-size.cfdg", "/v3-shape-size.png" });
		params.add(new Object[] { "/v3-shape-clone.cfdg", "/v3-shape-clone.png" });
		params.add(new Object[] { "/v3-shape-symmetry-dihedral.cfdg", "/v3-shape-symmetry-dihedral.png" });
		params.add(new Object[] { "/v3-shape-symmetry-cyclic.cfdg", "/v3-shape-symmetry-cyclic.png" });
		params.add(new Object[] { "/v3-shape-symmetry-p11g.cfdg", "/v3-shape-symmetry-p11g.png" });
		params.add(new Object[] { "/v3-shape-symmetry-p11m.cfdg", "/v3-shape-symmetry-p11m.png" });
		params.add(new Object[] { "/v3-shape-symmetry-p1m1.cfdg", "/v3-shape-symmetry-p1m1.png" });
		params.add(new Object[] { "/v3-shape-symmetry-p2.cfdg", "/v3-shape-symmetry-p2.png" });
		params.add(new Object[] { "/v3-shape-symmetry-p2mg.cfdg", "/v3-shape-symmetry-p2mg.png" });
		params.add(new Object[] { "/v3-shape-symmetry-pm.cfdg", "/v3-shape-symmetry-pm.png" });
		params.add(new Object[] { "/v3-shape-symmetry-pg.cfdg", "/v3-shape-symmetry-pg.png" });
		params.add(new Object[] { "/v3-shape-symmetry-cm.cfdg", "/v3-shape-symmetry-cm.png" });
////		params.add(new Object[] { "/v3-shape-symmetry-pmm.cfdg", "/v3-shape-symmetry-pmm.png" });
////		params.add(new Object[] { "/v3-shape-symmetry-pmg.cfdg", "/v3-shape-symmetry-pmg.png" });
////		params.add(new Object[] { "/v3-shape-symmetry-pgg.cfdg", "/v3-shape-symmetry-pgg.png" });
////		params.add(new Object[] { "/v3-shape-symmetry-cmm.cfdg", "/v3-shape-symmetry-cmm.png" });
////		params.add(new Object[] { "/v3-shape-symmetry-p4m.cfdg", "/v3-shape-symmetry-p4.png" });
////		params.add(new Object[] { "/v3-shape-symmetry-p4m.cfdg", "/v3-shape-symmetry-p4m.png" });
////		params.add(new Object[] { "/v3-shape-symmetry-p4g.cfdg", "/v3-shape-symmetry-p4g.png" });
////		params.add(new Object[] { "/v3-shape-symmetry-p3.cfdg", "/v3-shape-symmetry-p3.png" });
		return params;
	}

	@Parameterized.Parameter(0)
	public String sourceName;

	@Parameterized.Parameter(1)
	public String imageName;

	@Test
	public void shouldRenderImage() throws IOException {
		System.out.println(sourceName);

		BufferedImage actualImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);

		DefaultThreadFactory threadFactory = new DefaultThreadFactory("ContextFreeHistoryImageGenerator", true, Thread.MIN_PRIORITY);
		Java2DRendererFactory rendererFactory = new Java2DRendererFactory();

		RendererTile tile = new RendererTile(new RendererSize(200, 200), new RendererSize(200, 200), new RendererPoint(0, 0), new RendererSize(0, 0));
		Renderer renderer = new Renderer(threadFactory, rendererFactory, tile);

		CFDG cfdg = parseSource(sourceName);

		renderer.setOpaque(true);
		renderer.setCFDG(cfdg);
		renderer.init();
		renderer.runTask();
		renderer.waitForTasks();

		renderer.drawImage(rendererFactory.createGraphicsContext(actualImage.createGraphics()), 0, 0);

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
}
