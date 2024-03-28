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
    api(project(":com.nextbreakpoint.nextfractal.core"))
    api(libs.com.nextbreakpoint.com.nextbreakpoint.try4java)
    api(libs.org.openjfx.javafx.controls)
}

extraJavaModuleInfo {
}

description = "com.nextbreakpoint.nextfractal.core.javafx"
