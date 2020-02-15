import com.nextbreakpoint.nextfractal.core.common.CoreFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.module.MandelbrotFactory;

module com.nextbreakpoint.nextfractal.mandelbrot {
    requires java.sql;
    requires java.logging;
    requires java.desktop;
    requires java.compiler;
    requires commons.math3;
    requires org.antlr.antlr4.runtime;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
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
