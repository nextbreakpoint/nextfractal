export ROOT=`pwd`/..

java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright.txt $ROOT/maven

java -classpath FileUtility.jar net.sf.jame.fileutility.ChangeCopyright copyright-xaos.txt $ROOT/maven/com.nextbreakpoint.nextfractal.mandelbrot/src/main/java/com/nextbreakpoint/nextfractal/mandelbrot/renderer/xaos/XaosRenderer.java

