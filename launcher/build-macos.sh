#/bin/sh
export BUILD_DIR=`pwd`/build

mkdir -p $BUILD_DIR/mac

cp mac/* $BUILD_DIR/mac

cd $BUILD_DIR/mac

make
