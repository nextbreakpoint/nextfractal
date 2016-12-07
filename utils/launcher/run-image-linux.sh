#/bin/sh

mkdir -p `pwd`/build && docker run --name nextfractal-linux --rm -i --volume=`pwd`/build:/build nextfractal-linux
