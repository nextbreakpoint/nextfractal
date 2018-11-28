import com.nextbreakpoint.nextfractal.core.common.CoreFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.module.MandelbrotFactory;

module com.nextbreakpoint.nextfractal.mandelbrot {
    requires java.logging;
    requires java.desktop;
    requires jdk.compiler;
    requires commons.math3;
    requires antlr4.runtime;
    requires java.xml.bind;
    requires com.fasterxml.jackson.databind;
    requires jackson.annotations;
    requires com.nextbreakpoint.try4java;
    requires com.nextbreakpoint.nextfractal.core;
    exports com.nextbreakpoint.nextfractal.mandelbrot.module;
    exports com.nextbreakpoint.nextfractal.mandelbrot.grammar;
    exports com.nextbreakpoint.nextfractal.mandelbrot.compiler;
    exports com.nextbreakpoint.nextfractal.mandelbrot.renderer;
    exports com.nextbreakpoint.nextfractal.mandelbrot.interpreter;
    exports com.nextbreakpoint.nextfractal.mandelbrot.core;
    provides CoreFactory with MandelbrotFactory;
    opens com.nextbreakpoint.nextfractal.mandelbrot.module to com.fasterxml.jackson.databind;
}
