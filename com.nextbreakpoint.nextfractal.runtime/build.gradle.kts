plugins {
    id("common")
    id("extra-java-module-info")
}

dependencies {
    api(project(":com.nextbreakpoint.nextfractal.core"))
    implementation(project(":com.nextbreakpoint.nextfractal.mandelbrot"))
    implementation(project(":com.nextbreakpoint.nextfractal.contextfree"))
    implementation(libs.com.nextbreakpoint.com.nextbreakpoint.ffmpeg4java)
    implementation(libs.com.nextbreakpoint.com.nextbreakpoint.freeimage4java)
    api(libs.com.nextbreakpoint.com.nextbreakpoint.try4java)
}

extraJavaModuleInfo {
    module("antlr4-runtime.jar", "antlr4-runtime", "4.13.1") {
        exports("org.antlr.v4.runtime")
    }
    automaticModule("antlr4-4.13.1.jar", "antlr4")
    automaticModule("ST4-4.3.4.jar", "ST4")
    automaticModule("antlr-runtime-3.5.3.jar", "antlr-runtime")
    automaticModule("org.abego.treelayout.core-1.0.3.jar", "org.abego.treelayout.core")
}

description = "com.nextbreakpoint.nextfractal.runtime"
