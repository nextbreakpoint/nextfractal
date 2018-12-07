#!/bin/sh

JAVA_HOME=$(pwd)/../Resources/jdk

JAVA_HOME/bin/java \
    -Xmx4g \
    -Djava.util.logging.config.class=com.nextbreakpoint.nextfractal.runtime.logging.LogConfig \
    -Djava.library.path=$(pwd)/NextFractal.app/Content/Resources/libs \
    -Dbrowser.location=examples \
    --module-path $(pwd)/NextFractal.app/Content/Resources/jars \
    --add-modules=ALL-MODULE-PATH \
    com.nextbreakpoint.nextfractal.runtime.javafx.NextFractalApp
