module com.nextbreakpoint.nextfractal.runtime {
    requires java.logging;
    requires com.nextbreakpoint.nextfractal.core;
    requires com.nextbreakpoint.nextfractal.mandelbrot;
    requires com.nextbreakpoint.nextfractal.contextfree;
    requires com.nextbreakpoint.try4java;
    requires com.nextbreakpoint.ffmpeg4java;
    requires com.nextbreakpoint.freeimage4java;
    exports com.nextbreakpoint.nextfractal.runtime.logging;
    exports com.nextbreakpoint.nextfractal.runtime.encode;
    exports com.nextbreakpoint.nextfractal.runtime.export;
}
