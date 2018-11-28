#/bin/sh
export BUILD_DIR=`pwd`/build

mkdir -p $BUILD_DIR/windows

cp windows/* $BUILD_DIR/windows

cd $BUILD_DIR/windows

make
