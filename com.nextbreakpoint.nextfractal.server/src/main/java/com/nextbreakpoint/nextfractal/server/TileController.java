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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbreakpoint.nextfractal.core.Bundle;
import com.nextbreakpoint.nextfractal.core.FileManagerEntry;
import com.nextbreakpoint.nextfractal.core.FileManifest;
import com.nextbreakpoint.nextfractal.core.ImageComposer;
import com.nextbreakpoint.nextfractal.core.Plugins;
import com.nextbreakpoint.nextfractal.core.Session;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
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
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/tile")
public class TileController {
	private static final Logger logger = Logger.getLogger(TileController.class.getName());
	private static final ExecutorService executor = Executors.newFixedThreadPool(32);
	private static final Cache cache = new Cache();
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public DeferredResult<ResponseEntity<byte[]>> createTile(@RequestParam(value="size", required=true) Integer size, @RequestParam(value="rows", required=true) Integer rows, @RequestParam(value="cols", required=true) Integer cols, @RequestParam(value="row", required=true) Integer row, @RequestParam(value="col", required=true) Integer col, @RequestParam(value="manifest", required=true) String encodedManifest, @RequestParam(value="script", required=true) String encodedScript, @RequestParam(value="metadata", required=true) String encodedMetadata) {
		DeferredResult<ResponseEntity<byte[]>> deferredResult = new DeferredResult<>();
		try {
			Bundle bundle = decodeData(encodedManifest, encodedScript, encodedMetadata);
			TileRequest request = new TileRequest();
			request.setRows(rows);
			request.setCols(cols);
			request.setRow(row);
			request.setCol(col);
			request.setSize(size);
			request.setSession(bundle.getSession());
			validateRequest(request);
			RemoteJob job = createJob(request);
			String key = generateKey(size, rows, cols, row, col, encodedManifest, encodedScript, encodedMetadata);
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

	private String generateKey(Integer size, Integer rows, Integer cols, Integer row, Integer col, String encodedManifest, String encodedScript, String encodedMetadata) {
		StringBuilder builder = new StringBuilder();
		builder.append(encodedManifest);
		builder.append("-");
		builder.append(encodedScript);
		builder.append("-");
		builder.append(encodedMetadata);
		builder.append("-");
		builder.append(size);
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

	private void validateRequest(TileRequest request) {
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
		if (request.getRows() == 1 && request.getCols() == 1 && (request.getSize() < 32 || request.getSize() > 1024)) {
			throw new ValidationException("Invalid image size");
		}
		if ((request.getRows() > 1 || request.getCols() > 1) && (request.getSize() < 32 || request.getSize() > 256)) {
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

	private RemoteJob createJob(TileRequest request) {
		final RemoteJob job = new RemoteJob();
		final int tileSize = request.getSize();
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
		job.setSession(request.getSession());
		return job;
	}

	private Bundle decodeData(String encodedManifest, String encodedScript, String encodedMetadata) throws Exception {
		FileManagerEntry manifest = new FileManagerEntry("manifest", Base64.getDecoder().decode(encodedManifest));
		FileManagerEntry script = new FileManagerEntry("script", Base64.getDecoder().decode(encodedScript));
		FileManagerEntry metadata = new FileManagerEntry("metadata", Base64.getDecoder().decode(encodedMetadata));
		List<FileManagerEntry> entries = Arrays.asList(manifest, script, metadata);
		ObjectMapper mapper = new ObjectMapper();
		FileManifest decodedManifest = mapper.readValue(manifest.getData(), FileManifest.class);
		return Plugins.tryFindFactory(decodedManifest.getPluginId()).flatMap(factory -> factory.createFileManager().loadEntries(entries)).orThrow();
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

	private byte[] createImage(RemoteJob job) throws Exception {
		Session session = job.getSession();
		ThreadFactory threadFactory = new DefaultThreadFactory(TileController.class.getName(), true, Thread.MIN_PRIORITY);
		ImageComposer composer = Plugins.tryFindFactory(session.getPluginId()).map(factory -> factory.createImageComposer(threadFactory, job.getTile(), false)).orThrow();
		IntBuffer pixels = composer.renderImage(session.getScript(), session.getMetadata());
		byte[] pngImageData = getImageAsPNG(job.getTile().getTileSize(), pixels);
		return pngImageData;
	}

	private class ProcessingTask implements Runnable {
		private DeferredResult<ResponseEntity<byte[]>> deferredResult;
		private RemoteJob job;
		private String key;

		public ProcessingTask(DeferredResult<ResponseEntity<byte[]>> deferredResult, RemoteJob job, String key) {
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
