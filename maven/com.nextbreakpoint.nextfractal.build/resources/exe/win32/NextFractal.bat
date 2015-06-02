@echo off
cd NextFractal
start javaw -Xmx1024m -Djava.util.logging.config=com.nextbreakpoint.nextfractal.runtime.LogConfig -Dmandelbrot.browser.default=#[user.dir]/examples -jar com.nextbreakpoint.nextfractal.runtime-1.0.5.jar
