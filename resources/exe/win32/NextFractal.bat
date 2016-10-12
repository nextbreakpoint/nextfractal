@echo off
cd NextFractal
start javaw -Xmx1024m -Djava.util.logging.config=com.nextbreakpoint.nextfractal.runtime.LogConfig -Djava.library.path=. -Dmandelbrot.browser.default=../examples -jar com.nextbreakpoint.nextfractal.runtime-1.3.0.jar
