#!/bin/sh
java -Xmx1g -Djava.util.logging.config=com.nextbreakpoint.nextfractal.runtime.LogConfig -Djava.library.path=resources -Dmandelbrot.browser.default=$(pwd)/examples -jar resources/com.nextbreakpoint.nextfractal.runtime-1.3.0.jar
