import com.nextbreakpoint.nextfractal.contextfree.module.ContextFreeFactory;
import com.nextbreakpoint.nextfractal.core.common.CoreFactory;

module com.nextbreakpoint.nextfractal.contextfree {
    requires java.sql;
    requires java.logging;
    requires java.desktop;
    requires commons.math3;
    requires org.antlr.antlr4.runtime;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.nextbreakpoint.nextfractal.core;
    requires com.nextbreakpoint.try4java;
    exports com.nextbreakpoint.nextfractal.contextfree.module;
    exports com.nextbreakpoint.nextfractal.contextfree.core;
    exports com.nextbreakpoint.nextfractal.contextfree.grammar;
    exports com.nextbreakpoint.nextfractal.contextfree.renderer;
    exports com.nextbreakpoint.nextfractal.contextfree.compiler;
    provides CoreFactory with ContextFreeFactory;
    opens com.nextbreakpoint.nextfractal.contextfree.module to com.fasterxml.jackson.databind;
}
