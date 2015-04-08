package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class ClassJavaFileObject extends SimpleJavaFileObject {
	private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    public ClassJavaFileObject(String name) {
        super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension), Kind.CLASS);
    }

	@Override
	public InputStream openInputStream() throws IOException {
		return new ByteArrayInputStream(baos.toByteArray());
	}

	@Override
	public OutputStream openOutputStream() throws IOException {
		baos.reset();
		return baos;
	}
}