#!/bin/sh
java -Xmx1g -Djava.util.logging.config=com.nextbreakpoint.nextfractal.runtime.LogConfig -Djava.library.path=NextFractal.app/Contents/Resources -Dbrowser.location=$(pwd)/examples -jar NextFractal.app/Contents/Resources/com.nextbreakpoint.nextfractal.runtime-1.3.0.jar
