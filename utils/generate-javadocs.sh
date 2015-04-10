export ROOT=/Volumes/MAC/NextFractal

classespath=''
classespath=$classespath:$ROOT/sources/com.nextbreakpoint.nextfractal.core/target/classes
classespath=$classespath:$ROOT/sources/com.nextbreakpoint.nextfractal.contextfree/target/classes
classespath=$classespath:$ROOT/sources/com.nextbreakpoint.nextfractal.mandelbrot/target/classes
classespath=$classespath:$ROOT/sources/com.nextbreakpoint.nextfractal.runtime/target/classes
classespath=$classespath:$ROOT/main/net.sf.ffmpeg4java/ffmpeg4java.jar
classespath=$classespath:$ROOT/main/net.sf.freeimage4java/freeimage4java.jar

sourcespath=''
sourcespath=$sourcespath:$ROOT/sources/com.nextbreakpoint.nextfractal.core/src/main/java
sourcespath=$sourcespath:$ROOT/sources/com.nextbreakpoint.nextfractal.contextfree/src/main/java
sourcespath=$sourcespath:$ROOT/sources/com.nextbreakpoint.nextfractal.mandelbrot/src/main/java
sourcespath=$sourcespath:$ROOT/sources/com.nextbreakpoint.nextfractal.runtime/src/main/java

opts='-J-Xmx512M -author -version -use -d javadocs -subpackages com.nextbreakpoint.nextfractal'

javadoc -classpath $classespath -sourcepath $sourcespath $opts 2>generate-javadocs.log
