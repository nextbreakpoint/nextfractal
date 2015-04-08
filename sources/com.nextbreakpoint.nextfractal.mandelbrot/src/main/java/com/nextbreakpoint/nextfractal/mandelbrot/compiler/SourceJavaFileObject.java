package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class SourceJavaFileObject extends SimpleJavaFileObject {
    private final String code;

    public SourceJavaFileObject(String name, String code) {
        super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}