import com.nextbreakpoint.nextfractal.mandelbrot.javafx.MandelbrotUIFactory;

module com.nextbreakpoint.nextfractal.mandelbrot.javafx {
    requires java.prefs;
    requires java.logging;
    requires javafx.controls;
    requires rxjavafx;
    requires io.reactivex.rxjava2;
    requires com.nextbreakpoint.try4java;
    requires com.nextbreakpoint.nextfractal.core;
    requires com.nextbreakpoint.nextfractal.mandelbrot;
    requires com.nextbreakpoint.nextfractal.core.javafx;
    provides com.nextbreakpoint.nextfractal.core.javafx.UIFactory with MandelbrotUIFactory;
}
