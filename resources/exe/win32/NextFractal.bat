@echo off
start javaw -Xmx1g -Djava.util.logging.config=com.nextbreakpoint.nextfractal.runtime.LogConfig -Djava.library.path=resources -Dbrowser.location=%~dp0/examples -jar resources/com.nextbreakpoint.nextfractal.runtime-2.0.0.jar
