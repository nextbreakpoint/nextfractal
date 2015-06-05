package com.nextbreakpoint.nextfractal.server.test;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.nextbreakpoint.nextfractal.server.RemoteFractal;

public class TestFractal {
	private static final String URL = "http://localhost:8080/fractal";

	@Test
	public void renderFractal() {
		try {
			String xml = getResource("mandelbrot.m");
			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("xml", xml);
			RemoteFractal fractal = restTemplate.postForObject(URL, map, RemoteFractal.class);
			System.out.println(fractal.getUUID());
			System.out.println(fractal.getSource());
			System.out.println(fractal.getJobsCount());
			byte[] pngImageData = restTemplate.getForObject(URL + "?UUID=" + fractal.getUUID() + "&index=0", byte[].class);
			System.out.println(pngImageData.length);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	protected String getResource(String name) throws IOException {
		InputStream is = getClass().getResourceAsStream(name);
		if (is != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int length = 0;
			while ((length = is.read(buffer)) > 0) {
				baos.write(buffer, 0, length);
			}
			return baos.toString();
		}
		return "";
	}
}
