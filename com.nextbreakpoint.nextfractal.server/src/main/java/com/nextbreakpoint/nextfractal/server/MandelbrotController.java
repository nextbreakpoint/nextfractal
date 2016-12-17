/*
 * NextFractal 2.0.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
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

import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.Session;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotImageGenerator;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import javax.imageio.ImageIO;
import javax.validation.ValidationException;
import javax.xml.bind.JAXB;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/mandelbrot")
public class MandelbrotController {
	private static final Logger logger = Logger.getLogger(MandelbrotController.class.getName());
	private static final ExecutorService executor = Executors.newFixedThreadPool(32);
	private static final Cache cache = new Cache();
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public DeferredResult<ResponseEntity<byte[]>> createTile(@RequestParam(value="tileSize", required=true) Integer tileSize, @RequestParam(value="rows", required=true) Integer rows, @RequestParam(value="cols", required=true) Integer cols, @RequestParam(value="row", required=true) Integer row, @RequestParam(value="col", required=true) Integer col, @RequestParam(value="data", required=true) String encodedData) {
		DeferredResult<ResponseEntity<byte[]>> deferredResult = new DeferredResult<>();
		try {
			MandelbrotSession data = decodeData(encodedData);
			MandelbrotRequest request = new MandelbrotRequest();
			request.setRows(rows);
			request.setCols(cols);
			request.setRow(row);
			request.setCol(col);
			request.setTileSize(tileSize);
			request.setSession(data);
			validateRequest(request);
			RemoteJob<MandelbrotSession> job = createJob(request);
			String key = generateKey(tileSize, rows, cols, row, col, encodedData);
		    ProcessingTask task = new ProcessingTask(deferredResult, job, key);
		    synchronized (executor) {
		    	executor.execute(task);
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "Cannot render tile", e);
			ResponseEntity<byte[]> response = createErrorResponse(e.getMessage());
			if (!deferredResult.isSetOrExpired()) {
				deferredResult.setResult(response);
			}
		}
        return deferredResult;
    }

	private String generateKey(Integer tileSize, Integer rows, Integer cols, Integer row, Integer col, String encodedData) {
		StringBuilder builder = new StringBuilder();
		builder.append(encodedData);
		builder.append("-");
		builder.append(tileSize);
		builder.append("-");
		builder.append(rows);
		builder.append("-");
		builder.append(cols);
		builder.append("-");
		builder.append(row);
		builder.append("-");
		builder.append(col);
		return builder.toString();
	}

	private void validateRequest(MandelbrotRequest request) {
		if (request.getRows() < 0 || request.getRows() > 16) {
			throw new ValidationException("Invalid rows number");
		}
		if (request.getCols() < 0 || request.getCols() > 16) {
			throw new ValidationException("Invalid cols number");
		}
		if (request.getRow() < 0 || request.getRow() > request.getRows() - 1) {
			throw new ValidationException("Invalid row index");
		}
		if (request.getCol() < 0 || request.getCol() > request.getCols() - 1) {
			throw new ValidationException("Invalid col index");
		}
		if (request.getRows() == 1 && request.getCols() == 1 && (request.getTileSize() < 32 || request.getTileSize() > 1024)) {
			throw new ValidationException("Invalid image size");
		}
		if ((request.getRows() > 1 || request.getCols() > 1) && (request.getTileSize() < 32 || request.getTileSize() > 256)) {
			throw new ValidationException("Invalid image size");
		}
		if (request.getSession() == null) {
			throw new ValidationException("Invalid data");
		}
	}

	private ResponseEntity<byte[]> createErrorResponse(String error) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "image/png");
		headers.add("x-server-error", error);
		return new ResponseEntity<>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<byte[]> createResponse(byte[] pngImage) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "image/png");
		return new ResponseEntity<>(pngImage, headers, HttpStatus.OK);
	}

	private RemoteJob<MandelbrotSession> createJob(MandelbrotRequest request) {
		final RemoteJob<MandelbrotSession> job = new RemoteJob<>();
		final int tileSize = request.getTileSize();
		final int rows = request.getRows();
		final int cols = request.getCols();
		final int row = request.getRow();
		final int col = request.getCol();
		job.setQuality(1);
		job.setImageWidth(tileSize * cols);
		job.setImageHeight(tileSize * rows);
		job.setTileWidth(tileSize);
		job.setTileHeight(tileSize);
		job.setTileOffsetX(tileSize * col);
		job.setTileOffsetY(tileSize * row);
		job.setBorderWidth(0);
		job.setBorderHeight(0);
		job.setData(request.getSession());
		return job;
	}

	private MandelbrotSession decodeData(String encodedData) {
		return JAXB.unmarshal(new ByteArrayInputStream(Base64.getDecoder().decode(encodedData)), MandelbrotSession.class);
	}

	private byte[] getImageAsPNG(RendererSize tileSize, IntBuffer pixels) throws IOException {
		int tileWidth = tileSize.getWidth();
		int tileHeight = tileSize.getHeight();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedImage image =  new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
		int[] buffer = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		System.arraycopy(pixels.array(), 0, buffer, 0, tileWidth * tileHeight);
		ImageIO.write(image, "PNG", baos);
		return baos.toByteArray();
	}

	private byte[] createImage(RemoteJob<? extends Session> job) throws IOException {
		ThreadFactory threadFactory = new DefaultThreadFactory(MandelbrotController.class.getName(), true, Thread.MIN_PRIORITY);
		RendererFactory renderFactory = new JavaFXRendererFactory();
		RendererTile tile = job.getTile();
		Session session = job.getData();
		ImageGenerator generator = new MandelbrotImageGenerator(threadFactory, renderFactory, tile, false);
		IntBuffer pixels = generator.renderImage(session.getScript(), session.getMetadata());
		byte[] pngImageData = getImageAsPNG(tile.getTileSize(), pixels);
		return pngImageData;
	}

	private class ProcessingTask implements Runnable {
		private DeferredResult<ResponseEntity<byte[]>> deferredResult;
		private RemoteJob<? extends Session> job;
		private String key;

		public ProcessingTask(DeferredResult<ResponseEntity<byte[]>> deferredResult, RemoteJob<? extends Session> job, String key) {
			this.deferredResult = deferredResult;
			this.job = job;
			this.key = key;
		}

		@Override
		public void run() {
			try {
				CacheEntry cacheEntry = cache.get(key);
				if (cacheEntry == null) {
					logger.log(Level.INFO, "Generate image [cache size = {0}]", new Object[] { cache.size() });
					byte[] pngImage = createImage(job);
					logger.log(Level.INFO, "Image size {0} bytes", pngImage.length);
					cacheEntry = new CacheEntry(pngImage);
					cache.put(key, cacheEntry);
				} else {
					logger.log(Level.INFO, "Cached image found [cache size = {0}]", new Object[] { cache.size() });
				}
				ResponseEntity<byte[]> response = createResponse(cacheEntry.getImage());
				if (!deferredResult.isSetOrExpired()) {
					deferredResult.setResult(response);
				}
			}  catch (Exception e) {
				logger.log(Level.WARNING, "Cannot create image", e);
				ResponseEntity<byte[]> response = createErrorResponse("Cannot create image");
				if (!deferredResult.isSetOrExpired()) {
					deferredResult.setResult(response);
				}
			}
		}
	}

	private static class Cache {
		private static final int MAX_CACHE_SIZE = 50;
		private Map<String, CacheEntry> map = new HashMap<>();

		public synchronized void put(String key, CacheEntry entry) {
			map.put(key, entry);
			while (map.size() > MAX_CACHE_SIZE) {
				Entry<String, CacheEntry> olderEntry = null;
				for (Entry<String, CacheEntry> mapEntry : map.entrySet()) {
					if (olderEntry == null || mapEntry.getValue().getTimestamp() < olderEntry.getValue().getTimestamp()) {
						olderEntry = mapEntry;
					}
				}
				if (olderEntry != entry) {
					map.remove(olderEntry.getKey());
				}
			}
		}

		public synchronized int size() {
			return map.size();
		}

		public synchronized CacheEntry get(String key) {
			return map.get(key);
		}
	}
	
	private static class CacheEntry {
		private volatile long timestamp;
		private final byte[] pngImage;
		
		public CacheEntry(byte[] pngImage) {
			this.timestamp = System.nanoTime();
			this.pngImage = pngImage;
		}
		
		public byte[] getImage() {
			timestamp = System.nanoTime();
			return pngImage;
		}

		public long getTimestamp() {
			return timestamp;
		}
	}
}
