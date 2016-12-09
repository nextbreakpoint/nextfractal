#/bin/sh
export BUILD_DIR=`pwd`/build

mkdir -p $BUILD_DIR/linux

cp linux/* $BUILD_DIR/linux

cd $BUILD_DIR/linux

make

