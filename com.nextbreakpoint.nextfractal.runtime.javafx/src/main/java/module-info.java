module com.nextbreakpoint.nextfractal.runtime.javafx {
    requires java.logging;
    requires jdk.compiler;
    requires java.desktop;
    requires javafx.controls;
    requires controlsfx;
    requires com.nextbreakpoint.try4java;
    requires com.nextbreakpoint.nextfractal.core;
    requires com.nextbreakpoint.nextfractal.runtime;
    requires com.nextbreakpoint.nextfractal.core.javafx;
    requires com.nextbreakpoint.nextfractal.mandelbrot.javafx;
    requires com.nextbreakpoint.nextfractal.contextfree.javafx;
    exports com.nextbreakpoint.nextfractal.runtime.javafx;
}
