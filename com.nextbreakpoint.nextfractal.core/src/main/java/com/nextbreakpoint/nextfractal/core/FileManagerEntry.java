package com.nextbreakpoint.nextfractal.core;

public class FileManagerEntry {
    private final String name;
    private final byte[] data;

    public FileManagerEntry(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }
}
