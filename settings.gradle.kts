pluginManagement {
    includeBuild("build-logic")
}

rootProject.name = "com.nextbreakpoint.nextfractal"

include(":com.nextbreakpoint.nextfractal.core")
include(":com.nextbreakpoint.nextfractal.mandelbrot")
include(":com.nextbreakpoint.nextfractal.contextfree")
include(":com.nextbreakpoint.nextfractal.runtime")
include(":com.nextbreakpoint.nextfractal.core.javafx")
include(":com.nextbreakpoint.nextfractal.mandelbrot.javafx")
include(":com.nextbreakpoint.nextfractal.contextfree.javafx")
include(":com.nextbreakpoint.nextfractal.runtime.javafx")
