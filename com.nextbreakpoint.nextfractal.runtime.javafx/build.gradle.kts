plugins {
    id("common")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("extra-java-module-info")
    antlr
}

javafx {
    version = "21"
    modules("javafx.controls")
}

dependencies {
    api(project(":com.nextbreakpoint.nextfractal.runtime"))
    api(project(":com.nextbreakpoint.nextfractal.core.javafx"))
    implementation(project(":com.nextbreakpoint.nextfractal.mandelbrot.javafx"))
    implementation(project(":com.nextbreakpoint.nextfractal.contextfree.javafx"))
    api(libs.org.openjfx.javafx.controls)
    antlr("org.antlr:antlr4:4.13.1")
}

extraJavaModuleInfo {
    module("commons-math3-3.6.1.jar", "commons.math3", "3.6.1") {
        exports("org.apache.commons.math3")
        exports("org.apache.commons.math3.special")
        exports("org.apache.commons.math3.util")
    }
    module("antlr4-runtime.jar", "org.antlr.antlr4.runtime", "4.13.1") {
        exports("org.antlr.v4.runtime")
    }
    automaticModule("antlr4-4.13.1.jar", "antlr4")
    automaticModule("ST4-4.3.4.jar", "ST4")
    automaticModule("antlr-runtime-3.5.3.jar", "antlr.runtime")
    automaticModule("org.abego.treelayout.core-1.0.3.jar", "org.abego.treelayout.core")
}

description = "com.nextbreakpoint.nextfractal.runtime.javafx"

