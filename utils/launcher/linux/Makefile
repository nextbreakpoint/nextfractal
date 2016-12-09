JAVA_VERSION?=8

JHOME:=/usr/lib/jvm/java-8-oracle
SYSTEM:=linux

CC = gcc
LD = ld

CPPFLAGS += -DJAVA_VERSION=$(JAVA_VERSION)
CXXFLAGS += -g
VM_DIR=$(JHOME)/jre/lib
LDFLAGS += -L$(VM_DIR)/amd64/server
CXXFLAGS += -I$(JHOME)/include -I$(JHOME)/include/$(SYSTEM)
LDLIBS += -lstdc++ -ljvm

all: NextFractal

NextFractal: 
	$(CC) $(CPPFLAGS) $(CXXFLAGS) $(LDFLAGS) -o NextFractal NextFractal.cpp $(LDLIBS) 
	readelf -d NextFractal | grep 'NEEDED'

clean:
	rm -rf NextFractal
