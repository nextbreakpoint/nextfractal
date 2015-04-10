export ROOT=/Volumes/MAC/NextFractal

java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/sources/com.nextbreakpoint.nextfractal.core
java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/sources/com.nextbreakpoint.nextfractal.contextfree
java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/sources/com.nextbreakpoint.nextfractal.mandelbrot
java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/sources/com.nextbreakpoint.nextfractal.runtime

java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright3.txt $ROOT/sources/com.nextbreakpoint.nextfractal.mandelbrot/src/main/java/com/nextbreakpoint/nextfractal/mandelbrot/renderer/xaos/XaosRenderer.java

