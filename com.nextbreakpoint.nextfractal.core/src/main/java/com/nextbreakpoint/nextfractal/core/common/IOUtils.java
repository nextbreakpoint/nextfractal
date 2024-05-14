package com.nextbreakpoint.nextfractal.core.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
    private IOUtils() {}

    public static void copyBytes(InputStream is, OutputStream os) throws IOException {
        byte[] data = new byte[4096];
        int length;
        while ((length = is.read(data)) > 0) {
            os.write(data, 0, length);
        }
    }
}
