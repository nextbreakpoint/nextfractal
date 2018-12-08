@echo off

set JAVA_HOME=%~dp0/resources/jdk

start %JAVA_HOME/bin/javaw -Xmx4g -Djava.util.logging.config.class=com.nextbreakpoint.nextfractal.runtime.logging.LogConfig -Djava.library.path=%~dp0/resources/libs -Dbrowser.location=%~dp0/examples --module-path %~dp0/resources/jars --add-modules=ALL-MODULE-PATH com.nextbreakpoint.nextfractal.runtime.javafx.NextFractalApp
