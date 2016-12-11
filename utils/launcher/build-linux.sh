#/bin/sh
export BUILD_DIR=`pwd`/build

find /usr/include/qt4 -name "qmessagebox*" -print

mkdir -p $BUILD_DIR/linux

cp linux/* $BUILD_DIR/linux

cd $BUILD_DIR/linux

make

