module com.nextbreakpoint.nextfractal.core {
    requires java.sql;
    requires java.desktop;
    requires java.logging;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.nextbreakpoint.try4java;
    exports com.nextbreakpoint.nextfractal.core.common;
    exports com.nextbreakpoint.nextfractal.core.encode;
    exports com.nextbreakpoint.nextfractal.core.export;
    exports com.nextbreakpoint.nextfractal.core.network;
    exports com.nextbreakpoint.nextfractal.core.render;
    uses com.nextbreakpoint.nextfractal.core.common.CoreFactory;
    uses com.nextbreakpoint.nextfractal.core.encode.Encoder;
    opens com.nextbreakpoint.nextfractal.core.common to com.fasterxml.jackson.databind;
}
