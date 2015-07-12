/*
 * NextFractal 1.1.1
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
package com.nextbreakpoint.nextfractal.server;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.IntBuffer;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXB;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotImageGenerator;

@Controller
@RequestMapping("/mandelbrot")
public class MandelbrotController {
	private static final String PLUGINID = "mandelbrot";
	private static final Logger logger = Logger.getLogger(MandelbrotController.class.getName());
	private static ExecutorService executor = Executors.newFixedThreadPool(32);
	
	@RequestMapping(method=RequestMethod.GET)
    public DeferredResult<ResponseEntity<byte[]>> createTile(@RequestParam(value="request", required=true) String data) {
		DeferredResult<ResponseEntity<byte[]>> deferredResult = new DeferredResult<>();
		try {
			MandelbrotRequest request = decodeData(data);
			RemoteJob<MandelbrotData> job = createJob(request);
		    ProcessingTask<MandelbrotData> task = new ProcessingTask<>(deferredResult, job);
		    synchronized (executor) {
		    	executor.execute(task);
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "Cannot render tile", e);
			ResponseEntity<byte[]> response = createErrorResponse();
			if (!deferredResult.isSetOrExpired()) {
				deferredResult.setResult(response);
			}
		}
        return deferredResult;
    }

	private ResponseEntity<byte[]> createErrorResponse() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "image/png");
		return new ResponseEntity<>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<byte[]> createResponse(byte[] pngImageData) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "image/png");
		return new ResponseEntity<>(pngImageData, headers, HttpStatus.OK);
	}

	private RemoteJob<MandelbrotData> createJob(MandelbrotRequest request) {
		final RemoteJob<MandelbrotData> job = new RemoteJob<>();
		final int tileSize = request.getTileSize();
		final int rows = request.getRows();
		final int cols = request.getCols();
		final int row = request.getRow();
		final int col = request.getCol();
		job.setPluginId(PLUGINID);
		job.setQuality(1);
		job.setImageWidth(tileSize * cols);
		job.setImageHeight(tileSize * rows);
		job.setTileWidth(tileSize);
		job.setTileHeight(tileSize);
		job.setTileOffsetX(tileSize * col);
		job.setTileOffsetY(tileSize * row);
		job.setBorderWidth(0);
		job.setBorderHeight(0);
		job.setData(request.getMandelbrot());
		return job;
	}

	private MandelbrotRequest decodeData(String data) {
		System.out.println(new String(Base64.getDecoder().decode(data)));
		return JAXB.unmarshal(new ByteArrayInputStream(Base64.getDecoder().decode(data)), MandelbrotRequest.class);
	}

	private class ProcessingTask<T> implements Runnable {
		private DeferredResult<ResponseEntity<byte[]>> deferredResult;
		private RemoteJob<T> job;

		public ProcessingTask(DeferredResult<ResponseEntity<byte[]>> deferredResult, RemoteJob<T> job) {
			this.deferredResult = deferredResult;
			this.job = job;
		}

		@Override
		public void run() {
			try {
				ThreadFactory threadFactory = new DefaultThreadFactory("MandelbrotController", true, Thread.MIN_PRIORITY);
				RendererFactory renderFactory = new JavaFXRendererFactory();
				ImageGenerator generator = new MandelbrotImageGenerator(threadFactory, renderFactory, job.getTile());
				IntBuffer pixels = generator.renderImage(job.getData());
				byte[] pngImageData = getImageAsPNG(job, pixels);
				HttpHeaders headers = new HttpHeaders();
				headers.add("content-type", "image/png");
				if (pngImageData != null) {
					ResponseEntity<byte[]> response = createResponse(pngImageData);
					if (!deferredResult.isSetOrExpired()) {
						deferredResult.setResult(response);
					}
				} else {
					ResponseEntity<byte[]> response = createErrorResponse();
					if (!deferredResult.isSetOrExpired()) {
						deferredResult.setResult(response);
					}
				}
			}  catch (Exception e) {
			}
		}

		private byte[] getImageAsPNG(RemoteJob<T> job, IntBuffer pixels) {
			try {
				RendererSize tileSize = job.getTile().getTileSize();
				int tileWidth = tileSize.getWidth();
				int tileHeight = tileSize.getHeight();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedImage image =  new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
				int[] buffer = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
				int[] imagePixels = pixels.array();
				System.arraycopy(imagePixels, 0, buffer, 0, tileWidth * tileHeight);
				ImageIO.write(image, "PNG", baos);
				return baos.toByteArray();
			} catch (Exception e) {
			}
			return null;
		}
	}
}
