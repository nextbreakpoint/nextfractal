#/bin/sh
export BUILD_DIR=`pwd`/build

readlink -f /etc/alternatives/javac

gcc-4.9 --version

strings /usr/lib/x86_64-linux-gnu/libstdc++.so.6 | grep GLIBCXX

find /usr/include/qt4 -name "qmessagebox*" -print

mkdir -p $BUILD_DIR/linux

cp linux/* $BUILD_DIR/linux

cd $BUILD_DIR/linux

make

./NextFractal
