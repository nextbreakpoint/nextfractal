#!/bin/sh

JAVA_HOME=$(/usr/libexec/java_home)

$JAVA_HOME/bin/java -Xmx4g -Djava.util.logging.config.class=com.nextbreakpoint.nextfractal.runtime.logging.LogConfig -Djava.library.path=NextFractal.app/Contents/Resources -Dbrowser.location=$(pwd)/examples --module-path NextFractal.app/Contents/Resources --add-modules=ALL-MODULE-PATH --add-opens javafx.graphics/javafx.scene.text=richtextfx --add-opens javafx.graphics/com.sun.javafx.text=richtextfx --add-opens javafx.graphics/com.sun.javafx.geom=richtextfx --add-opens javafx.graphics/com.sun.javafx.scene.text=richtextfx com.nextbreakpoint.nextfractal.runtime.javafx.NextFractalApp
