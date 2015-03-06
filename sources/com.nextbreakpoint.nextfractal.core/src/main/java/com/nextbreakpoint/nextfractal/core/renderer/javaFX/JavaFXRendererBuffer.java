package com.nextbreakpoint.nextfractal.core.renderer.javaFX;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import com.nextbreakpoint.nextfractal.core.renderer.RendererBuffer;
import com.nextbreakpoint.nextfractal.core.renderer.RendererImage;

public class JavaFXRendererBuffer implements RendererBuffer {
	private WritableImage image;
	private PixelWriter writer;
	
	public JavaFXRendererBuffer(int widh, int height) {
		image = new WritableImage(widh, height);
		writer = image.getPixelWriter();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererBuffer.renderer.RenderBuffer#dispose()
	 */
	@Override
	public void dispose() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererBuffer.renderer.RenderBuffer#clear()
	 */
	@Override
	public void clear() {
//		int[] pixels = new int[getWidth() * getHeight()];
//		for (int i = 0; i < pixels.length; i++) {
//			pixels[i] = 0xFF000000; 
//		}
//		writer.setPixels(0, 0, getWidth(), getHeight(), PixelFormat.getIntArgbInstance(), pixels, 0, getWidth());
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				writer.setArgb(x, y, 0xFF000000);
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererBuffer.renderer.RenderBuffer#update(int[])
	 */
	@Override
	public void update(int[] pixels) {
		if (pixels.length <= getWidth() * getHeight()) {
			writer.setPixels(0, 0, getWidth(), getHeight(), PixelFormat.getIntArgbInstance(), pixels, 0, getWidth());
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererBuffer.renderer.RenderBuffer#getWidth()
	 */
	@Override
	public int getWidth() {
		return (int)image.getWidth();
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.RendererBuffer.renderer.RenderBuffer#getHeight()
	 */
	@Override
	public int getHeight() {
		return (int)image.getHeight();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererBuffer.renderer.RenderBuffer#getImage()
	 */
	@Override
	public RendererImage getImage() {
		return new JavaFXRendererImage(image);
	}
}
