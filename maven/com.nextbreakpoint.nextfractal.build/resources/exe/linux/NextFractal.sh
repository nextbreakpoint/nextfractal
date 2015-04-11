#!/bin/sh
cd NextFractal
java -Xmx1024m -Djava.util.logging.config=com.nextbreakpoint.nextfractal.runtime.LogConfig -jar com.nextbreakpoint.nextfractal.runtime-1.0.0.jar
