JAVA_VERSION?=11

JHOME:=/usr/lib/jvm/jdk-11
SYSTEM:=linux

CC = gcc-8
LD = ld-8

CPPFLAGS += -DJAVA_VERSION=$(JAVA_VERSION) -std=c++11
CXXFLAGS += -g
VM_DIR=$(JHOME)/jre/lib
LDFLAGS +=
CXXFLAGS += -I$(JHOME)/include -I$(JHOME)/include/$(SYSTEM)
LDLIBS += -lstdc++ -ldl

all: NextFractal

NextFractal:
	$(CC) $(CPPFLAGS) $(CXXFLAGS) $(LDFLAGS) -o NextFractal NextFractal.cpp $(LDLIBS) $(shell pkg-config --libs --cflags gtk+-2.0)
	readelf -d NextFractal | grep 'NEEDED'

clean:
	rm -rf NextFractal
