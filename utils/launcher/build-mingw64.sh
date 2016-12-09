#/bin/sh
export BUILD_DIR=`pwd`/build

mkdir $BUILD_DIR/win32

cp win32/* $BUILD_DIR/win32

cd $BUILD_DIR/win32

make

/usr/bin/x86_64-w64-mingw32-objdump -p NextFractal | grep 'DLL Name'
