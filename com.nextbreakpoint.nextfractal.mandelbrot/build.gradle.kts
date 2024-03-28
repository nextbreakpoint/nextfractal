plugins {
    id("common")
    id("extra-java-module-info")
    antlr
}

dependencies {
    api(project(":com.nextbreakpoint.nextfractal.core"))
    api(libs.com.nextbreakpoint.com.nextbreakpoint.try4java)
    implementation(libs.com.fasterxml.jackson.core.jackson.databind)
    implementation(libs.com.fasterxml.jackson.core.jackson.annotations)
    api(libs.org.antlr.antlr4.runtime)
    implementation(libs.org.apache.commons.commons.math3)
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

tasks.generateGrammarSource {
    maxHeapSize = "64m"
    arguments = arguments + listOf("-visitor", "-long-messages")
}

tasks.sourcesJar {
    dependsOn("generateGrammarSource")
}

tasks.compileJava {
    options.javaModuleVersion = provider({ version as String })
}

//sourceSets {
//    main {
//        java {
//            srcDirs("${project.buildDir}/generated-src/antlr/main")
//        }
//    }
//}

description = "com.nextbreakpoint.nextfractal.mandelbrot"

