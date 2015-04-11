export ROOT=`pwd`/..

classespath=''
classespath=$classespath:$ROOT/maven/com.nextbreakpoint.nextfractal.core/target/classes
classespath=$classespath:$ROOT/maven/com.nextbreakpoint.nextfractal.contextfree/target/classes
classespath=$classespath:$ROOT/maven/com.nextbreakpoint.nextfractal.mandelbrot/target/classes
classespath=$classespath:$ROOT/maven/com.nextbreakpoint.nextfractal.runtime/target/classes

sourcespath=''
sourcespath=$sourcespath:$ROOT/maven/com.nextbreakpoint.nextfractal.core/src/main/java
sourcespath=$sourcespath:$ROOT/maven/com.nextbreakpoint.nextfractal.contextfree/src/main/java
sourcespath=$sourcespath:$ROOT/maven/com.nextbreakpoint.nextfractal.mandelbrot/src/main/java
sourcespath=$sourcespath:$ROOT/maven/com.nextbreakpoint.nextfractal.runtime/src/main/java

opts='-J-Xmx512M -author -version -use -d javadocs -subpackages com.nextbreakpoint.nextfractal'

javadoc -classpath $classespath -sourcepath $sourcespath $opts 2>generate-javadocs.log
