#!/bin/sh

java -Xmx3g -Djava.util.logging.config.class=com.nextbreakpoint.nextfractal.runtime.LogConfig -Djava.library.path=resources -Dbrowser.location=$(pwd)/examples -jar resources/com.nextbreakpoint.nextfractal.runtime.javafx-2.0.2.jar
