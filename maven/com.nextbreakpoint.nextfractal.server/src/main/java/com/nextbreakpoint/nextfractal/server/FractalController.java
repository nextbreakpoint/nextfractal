/*
 * NextFractal 1.1.0
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
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotDataStore;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotImageGenerator;

@Controller
@RequestMapping("/fractal")
public class FractalController {
	private static Map<String, RemoteFractal> sessions = new HashMap<>();
	private static ExecutorService executor = Executors.newFixedThreadPool(200);
	
    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody RemoteFractal renderFractal(@RequestParam(value="xml", required=true) String xml) {
    	RemoteFractal fractal = new RemoteFractal();
    	try {
			FractalSession session = new FractalSession();
			fractal.setUUID(UUID.randomUUID().toString());
			fractal.setTileSize(session.getTileSize());
			fractal.setJobsCount(session.getJobsCount());
			fractal.setJobs(session.getJobs());
			fractal.setSource(xml);
			synchronized (sessions) {
				if (sessions.size() > 100) {
					throw new Exception("Too many sessions");
				}
				sessions.put(fractal.getUUID(), fractal);
				cleanupSessions();
			}
		} catch (Exception e) {
			fractal.setError("Cannot render fractal: " + e.getMessage());
		}
        return fractal;
    }
	
    @RequestMapping(method=RequestMethod.GET)
    public DeferredResult<ResponseEntity<byte[]>> getFractal(@RequestParam(value="UUID", required=true) String UUID, @RequestParam(value="index", required=true) int index) {
		DeferredResult<ResponseEntity<byte[]>> deferredResult = new DeferredResult<>();
		RemoteFractal fractal = null;
		synchronized (sessions) {
			fractal = sessions.get(UUID);
		}
		if (fractal != null) {
			List<RemoteJob> jobs = fractal.getJobs();
			if (index >= 0 && index < jobs.size()) {
				RemoteJob job = jobs.get(index);
				if (job != null) {
				    ProcessingTask task = new ProcessingTask(deferredResult, fractal, job);
				    synchronized (executor) {
				    	executor.execute(task);
					}
				} else {
					deferredResult.setResult(null);
				}
			} else {
				deferredResult.setResult(null);
			}
		} else {
			deferredResult.setResult(null);
		}
        return deferredResult;
    }

	private void cleanupSessions() {
		for (Iterator<RemoteFractal> i = sessions.values().iterator(); i.hasNext();) {
			RemoteFractal fractal = i.next();
			if (System.currentTimeMillis() - fractal.getTimestamp() > 5 * 60 * 1000) {
				i.remove();
			}
		}
	}

	private byte[] getImageAsPNG(RemoteProfile profile, RendererTile tile, IntBuffer pixels) {
		try {
			RendererSize tileSize = tile.getTileSize();
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

	public class ProcessingTask implements Runnable {
		private DeferredResult<ResponseEntity<byte[]>> deferredResult;
		private ResponseEntity<byte[]> response;
		private RemoteFractal fractal;
		private RemoteJob job;

		public ProcessingTask(DeferredResult<ResponseEntity<byte[]>> deferredResult, RemoteFractal fractal, RemoteJob job) {
			this.deferredResult = deferredResult;
			this.fractal = fractal;
			this.job = job;
		}

		@Override
		public void run() {
			try {
				ThreadFactory threadFactory = new DefaultThreadFactory("FractalController", true, Thread.MIN_PRIORITY);
				RendererFactory renderFactory = new JavaFXRendererFactory();
				ImageGenerator generator = new MandelbrotImageGenerator(threadFactory, renderFactory, job.getTile());
	    		MandelbrotDataStore ds = new MandelbrotDataStore();
				MandelbrotData data = ds.loadFromReader(new StringReader(fractal.getSource()));
				IntBuffer pixels = generator.renderImage(data);
				byte[] pngImageData = getImageAsPNG(job.getProfile(), job.getTile(), pixels);
				HttpHeaders headers = new HttpHeaders();
				headers.add("content-type", "image/png");
		    	response = new ResponseEntity<>(pngImageData, headers, HttpStatus.OK);
				if (!deferredResult.isSetOrExpired()) {
					deferredResult.setResult(response);
				}
			}  catch (Exception e) {
			}
		}
	}
}
