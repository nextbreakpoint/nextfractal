/*
 * NextFractal 1.1.2
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
package com.nextbreakpoint.nextfractal.server.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Test;

public class TestFractal {
	private static final String URL = "http://localhost:8080/fractal";

	@Test
	@Ignore
	public void renderFractal() {
//		try {
//			String xml = getResource("/mandelbrot.m");
//			RestTemplate restTemplate = new RestTemplate();
//			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//			map.add("xml", xml);
//			RemoteFractal fractal = restTemplate.postForObject(URL, map, RemoteFractal.class);
//			System.out.println(fractal.getUUID());
//			System.out.println(fractal.getSource());
//			System.out.println(fractal.getJobsCount());
//			byte[] pngImageData = restTemplate.getForObject(URL + "?UUID=" + fractal.getUUID() + "&index=0", byte[].class);
//			System.out.println(pngImageData.length);
//		} catch (IOException e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
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
