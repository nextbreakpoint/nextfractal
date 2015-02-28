package com.nextbreakpoint.nextfractal.mandelbrot;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXB;

public class MandelbrotDataStore {
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

	public MandelbrotData loadFromStream(InputStream is) throws Exception {
		try {
			return JAXB.unmarshal(is, MandelbrotData.class);
		} catch (Exception e) {
			throw new Exception("Cannot load data from stream");
		}
	}

	public void saveToStream(OutputStream os, MandelbrotData data) throws Exception {
		try {
			JAXB.marshal(data, os);
		} catch (Exception e) {
			throw new Exception("Cannot save data to stream");
		}
	}
}
