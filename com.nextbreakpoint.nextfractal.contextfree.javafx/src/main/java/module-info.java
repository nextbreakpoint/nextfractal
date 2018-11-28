import com.nextbreakpoint.nextfractal.contextfree.javafx.ContextFreeUIFactory;

module com.nextbreakpoint.nextfractal.contextfree.javafx {
    requires java.logging;
    requires reactfx;
    requires richtextfx;
    requires javafx.controls;
    requires com.nextbreakpoint.try4java;
    requires com.nextbreakpoint.nextfractal.core;
    requires com.nextbreakpoint.nextfractal.contextfree;
    requires com.nextbreakpoint.nextfractal.core.javafx;
    provides com.nextbreakpoint.nextfractal.core.javafx.UIFactory with ContextFreeUIFactory;
}
