plugins {
    id("common")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("extra-java-module-info")
}

javafx {
    version = "21"
    modules("javafx.controls")
}

dependencies {
    api(project(":com.nextbreakpoint.nextfractal.mandelbrot"))
    api(project(":com.nextbreakpoint.nextfractal.core.javafx"))
    api(libs.org.openjfx.javafx.controls)
}

extraJavaModuleInfo {
    module("antlr4-runtime.jar", "org.antlr.antlr4.runtime", "4.13.1") {
        exports("org.antlr.v4.runtime")
    }
    automaticModule("antlr4-4.13.1.jar", "antlr4")
    automaticModule("ST4-4.3.4.jar", "ST4")
    automaticModule("antlr-runtime-3.5.3.jar", "antlr.runtime")
    automaticModule("org.abego.treelayout.core-1.0.3.jar", "org.abego.treelayout.core")
}

description = "com.nextbreakpoint.nextfractal.mandelbrot.javafx"

