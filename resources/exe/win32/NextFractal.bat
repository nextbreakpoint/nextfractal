@echo off
start javaw -Xmx1g -Djava.util.logging.config=com.nextbreakpoint.nextfractal.runtime.LogConfig -Djava.library.path=resources -Dmandelbrot.browser.default=%~dp0/examples -jar resources/com.nextbreakpoint.nextfractal.runtime-1.3.0.jar
