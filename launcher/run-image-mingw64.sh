#/bin/sh

mkdir -p `pwd`/build && docker run --name nextfractal-mingw64 --rm -i --volume=`pwd`/build:/build nextfractal-mingw64