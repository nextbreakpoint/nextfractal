#/bin/sh
export BUILD_DIR=`pwd`/build

mkdir $BUILD_DIR/win32

cp win32/* $BUILD_DIR/win32

cd $BUILD_DIR/win32

make
