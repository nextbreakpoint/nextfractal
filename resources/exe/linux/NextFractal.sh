#!/bin/sh

JAVA_HOME=$(pwd)/resources/jdk

$JAVA_HOME/bin/java -Xmx4g -Djava.util.logging.config.class=com.nextbreakpoint.nextfractal.runtime.logging.LogConfig -Djava.library.path=resources/libs -Dbrowser.location=$(pwd)/examples --module-path resources/jars --add-modules=ALL-MODULE-PATH com.nextbreakpoint.nextfractal.runtime.javafx.NextFractalApp
