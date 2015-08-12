#!/bin/sh
cd NextFractal
java -Xmx1024m -Djava.util.logging.config=com.nextbreakpoint.nextfractal.runtime.LogConfig -Djava.library.path=. -jar com.nextbreakpoint.nextfractal.runtime-1.1.5.jar
