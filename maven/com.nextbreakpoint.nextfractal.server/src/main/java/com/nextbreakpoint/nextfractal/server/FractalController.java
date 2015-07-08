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
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.IntBuffer;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotDataStore;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotImageGenerator;

@Controller
@RequestMapping("/fractal")
public class FractalController {
	private static final Logger logger = Logger.getLogger(FractalController.class.getName());
	private static ExecutorService executor = Executors.newFixedThreadPool(100);
	
    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody RemoteFractal renderFractal(@RequestParam(value="pluginId", required=true) String pluginId, @RequestParam(value="xml", required=true) String xml) {
    	RemoteFractal fractal = new RemoteFractal();
    	try {
    		if (!pluginId.equals("Mandelbrot")) {
    			throw new RuntimeException("Unsupported plugin " + pluginId);
    		}
			FractalSession session = new FractalSession(pluginId);
			fractal.setUUID(UUID.randomUUID().toString());
			fractal.setTileSize(session.getTileSize());
			fractal.setJobsCount(session.getJobsCount());
			fractal.setJobs(session.getJobs());
			fractal.setSource(xml);
			persistFractal(fractal.getUUID(), fractal.getTimestamp(), pluginId, xml);
		} catch (Exception e) {
			logger.log(Level.WARNING, "Cannot render fractal", e);
			fractal.setError("Cannot render fractal: " + e.getMessage());
		}
        return fractal;
    }
	
	private DynamoDB connectToDB() {
		AWSCredentialsProvider provider = new SystemPropertiesCredentialsProvider();
		AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(provider);
		dbClient.setRegion(Region.getRegion(Regions.EU_WEST_1));
		DynamoDB dynamoDB = new DynamoDB(dbClient);
		return dynamoDB;
	}

	@RequestMapping(method=RequestMethod.GET)
    public DeferredResult<ResponseEntity<byte[]>> getFractal(@RequestParam(value="UUID", required=true) String UUID, @RequestParam(value="index", required=true) int index) {
		DeferredResult<ResponseEntity<byte[]>> deferredResult = new DeferredResult<>();
		try {
			RemoteFractal fractal = getFractal(UUID);
			if (fractal != null) {
				List<RemoteJob> jobs = fractal.getJobs();
				if (index >= 0 && index < jobs.size()) {
					RemoteJob job = jobs.get(index);
				    ProcessingTask task = new ProcessingTask(deferredResult, fractal, job);
				    synchronized (executor) {
				    	executor.execute(task);
					}
				} else {
					throw new RuntimeException("Job doesn't exist for index " + index);
				}
			} else {
				throw new RuntimeException("Fractal doesn't exist for UUID " + UUID);
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "Cannot render tile", e);
			deferredResult.setResult(null);
		}
        return deferredResult;
    }

	private void persistFractal(String uuid, Long timestamp, String pluginId, String xml) {
		DynamoDB dynamoDB = connectToDB();
		Table table = dynamoDB.getTable("Fractal");
		Item item = new Item().withPrimaryKey("uuid", uuid)
				.withString("pluginId", pluginId)
				.withLong("timestamp", timestamp)
				.withString("xml", xml);
		table.putItem(item);
	}

	private RemoteFractal getFractal(String UUID) throws UnsupportedEncodingException {
		DynamoDB dynamoDB = connectToDB();
		Table table = dynamoDB.getTable("Fractal");
		PrimaryKey pk = new PrimaryKey("uuid", UUID);
		Item item = table.getItem(pk);
		String xml = item.getString("xml");
    	RemoteFractal fractal = new RemoteFractal();
		FractalSession session = new FractalSession("Mandelbrot");
		fractal.setUUID(UUID);
		fractal.setTileSize(session.getTileSize());
		fractal.setJobsCount(session.getJobsCount());
		fractal.setJobs(session.getJobs());
		fractal.setSource(xml);
		return fractal;
	}

	private byte[] getImageAsPNG(RemoteJob job, IntBuffer pixels) {
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

	private class ProcessingTask implements Runnable {
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
				byte[] pngImageData = getImageAsPNG(job, pixels);
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
