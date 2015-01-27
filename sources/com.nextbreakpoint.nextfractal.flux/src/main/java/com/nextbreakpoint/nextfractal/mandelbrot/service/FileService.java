package com.nextbreakpoint.nextfractal.mandelbrot.service;

import java.io.File;

import javax.xml.bind.JAXB;

import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;

public class FileService {
	public MandelbrotData loadFromFile(File path) throws Exception {
		try {
			return JAXB.unmarshal(path, MandelbrotData.class);
		} catch (Exception e) {
			throw new Exception("Cannot load data from file " + path.getAbsolutePath());
		}
	}

	public void saveToFile(File path, MandelbrotData data) throws Exception {
		try {
			JAXB.marshal(data, path);
		} catch (Exception e) {
			throw new Exception("Cannot save data to file " + path.getAbsolutePath());
		}
	}
}
