@echo off

start javaw -Xmx4g -Djava.util.logging.config.class=com.nextbreakpoint.nextfractal.runtime.logging.LogConfig -Djava.library.path=resources -Dbrowser.location=%~dp0/examples --module-path resources --add-modules=ALL-MODULE-PATH --add-opens javafx.graphics/javafx.scene.text=richtextfx --add-opens javafx.graphics/com.sun.javafx.text=richtextfx --add-opens javafx.graphics/com.sun.javafx.geom=richtextfx --add-opens javafx.graphics/com.sun.javafx.scene.text=richtextfx com.nextbreakpoint.nextfractal.runtime.javafx.NextFractalApp
