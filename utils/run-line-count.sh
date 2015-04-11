export ROOT=`pwd`/..

java -classpath FileUtility.jar net.sf.jame.fileutility.LineCount $ROOT/maven >report.txt
