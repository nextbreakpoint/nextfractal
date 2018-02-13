#!/bin/sh

java -Xmx3g -Djava.util.logging.config.class=com.nextbreakpoint.nextfractal.runtime.LogConfig -Djava.library.path=NextFractal.app/Contents/Resources -Dbrowser.location=$(pwd)/examples -jar NextFractal.app/Contents/Resources/com.nextbreakpoint.nextfractal.runtime.javafx-2.0.3.jar
