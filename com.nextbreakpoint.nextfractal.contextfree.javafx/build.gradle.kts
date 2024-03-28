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
    api(project(":com.nextbreakpoint.nextfractal.contextfree"))
    api(project(":com.nextbreakpoint.nextfractal.core.javafx"))
    api(libs.org.openjfx.javafx.controls)
}

extraJavaModuleInfo {
//    module("rxjava-2.2.21.jar", "io.reactivex.rxjava2", "2.2.21") {
//        exports("io.reactivex")
//        exports("io.reactivex.annotations")
//        exports("io.reactivex.disposables")
//        exports("io.reactivex.exceptions")
//        exports("io.reactivex.flowables")
//        exports("io.reactivex.functions")
//        exports("io.reactivex.observables")
//        exports("io.reactivex.observers")
//        exports("io.reactivex.parallel")
//        exports("io.reactivex.plugins")
//        exports("io.reactivex.processors")
//        exports("io.reactivex.schedulers")
//        exports("io.reactivex.subjects")
//        exports("io.reactivex.subscribers")
//    }
//    module("rxjavafx-2.2.2.jar", "rxjavafx", "2.2.2") {
//        exports("io.reactivex.rxjavafx.observables")
//        exports("io.reactivex.rxjavafx.observers")
//        exports("io.reactivex.rxjavafx.schedulers")
//        exports("io.reactivex.rxjavafx.sources")
//        exports("io.reactivex.rxjavafx.subscriptions")
//        exports("io.reactivex.rxjavafx.transformers")
//    }
    module("antlr4-runtime.jar", "org.antlr.antlr4.runtime", "4.13.1") {
        exports("org.antlr.v4.runtime")
    }
    automaticModule("antlr4-4.13.1.jar", "antlr4")
    automaticModule("ST4-4.3.4.jar", "ST4")
    automaticModule("antlr-runtime-3.5.3.jar", "antlr.runtime")
    automaticModule("org.abego.treelayout.core-1.0.3.jar", "org.abego.treelayout.core")
}

description = "com.nextbreakpoint.nextfractal.contextfree.javafx"
