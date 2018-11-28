JAVA_VERSION?=11

JHOME:=/usr/lib/jvm/java-11-oracle
SYSTEM:=linux

CC = gcc-4.9
LD = ld-4.9

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
