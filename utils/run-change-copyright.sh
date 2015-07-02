export ROOT=`pwd`/..

java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/maven/com.nextbreakpoint.nextfractal.core/src/main/java
java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/maven/com.nextbreakpoint.nextfractal.mandelbrot/src/main/java
java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/maven/com.nextbreakpoint.nextfractal.contextfree/src/main/java
java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/maven/com.nextbreakpoint.nextfractal.runtime/src/main/java
java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/maven/com.nextbreakpoint.nextfractal.server/src/main/java

java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/maven/com.nextbreakpoint.nextfractal.core/src/test/java
java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/maven/com.nextbreakpoint.nextfractal.mandelbrot/src/test/java
java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/maven/com.nextbreakpoint.nextfractal.contextfree/src/test/java
java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/maven/com.nextbreakpoint.nextfractal.runtime/src/test/java
java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/maven/com.nextbreakpoint.nextfractal.server/src/test/java

java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright-xaos.txt $ROOT/maven/com.nextbreakpoint.nextfractal.mandelbrot/src/main/java/com/nextbreakpoint/nextfractal/mandelbrot/renderer/xaos/XaosRenderer.java

