#/bin/sh
export BUILD_DIR=`pwd`/build

readlink -f /etc/alternatives/javac

gcc-8 --version

strings /usr/lib/x86_64-linux-gnu/libstdc++.so.6 | grep GLIBCXX

mkdir -p $BUILD_DIR/linux

cp linux/* $BUILD_DIR/linux

cd $BUILD_DIR/linux

make

./NextFractal
