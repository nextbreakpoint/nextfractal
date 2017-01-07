#!/bin/sh
java -Xmx2g -Djava.util.logging.config.class=com.nextbreakpoint.nextfractal.runtime.LogConfig -Djava.library.path=resources -Dbrowser.location=$(pwd)/examples -jar resources/com.nextbreakpoint.nextfractal.runtime-2.0.0.jar
