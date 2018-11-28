module com.nextbreakpoint.nextfractal.core.javafx {
    requires java.prefs;
    requires java.logging;
    requires javafx.controls;
    requires com.nextbreakpoint.try4java;
    requires com.nextbreakpoint.nextfractal.core;
    exports com.nextbreakpoint.nextfractal.core.javafx;
    exports com.nextbreakpoint.nextfractal.core.javafx.render;
    uses com.nextbreakpoint.nextfractal.core.javafx.UIFactory;
}
