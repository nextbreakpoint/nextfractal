plugins {
    id("common")
    id("extra-java-module-info")
}

dependencies {
    api(libs.com.nextbreakpoint.com.nextbreakpoint.try4java)
    implementation(libs.com.fasterxml.jackson.core.jackson.core)
    implementation(libs.com.fasterxml.jackson.core.jackson.databind)
    implementation(libs.com.fasterxml.jackson.core.jackson.annotations)
}

extraJavaModuleInfo {
}

description = "com.nextbreakpoint.nextfractal.core"

