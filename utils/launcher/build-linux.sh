#/bin/sh
export BUILD_DIR=`pwd`/build

mkdir $BUILD_DIR/linux

cp linux/* $BUILD_DIR/linux

cd $BUILD_DIR/linux

make

readelf -d NextFractal | grep 'NEEDED'
