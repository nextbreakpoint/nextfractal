JAVA_VERSION?=8

JHOME:=$(shell $(dirname $(dirname $(readlink -e /usr/bin/javac))))
SYSTEM:=$(shell uname -s)

CPPFLAGS += -DJAVA_VERSION=$(JAVA_VERSION)
CXXFLAGS += -g
VM_DIR=$(JHOME)/jre/lib
LDFLAGS += -L$(VM_DIR)/server -Wl,-rpath,$(VM_DIR) -Wl,-rpath,$(VM_DIR)/server
CXXFLAGS += -I$(JHOME)/include -I$(JHOME)/include/$(SYSTEM)
LDLIBS += -ljvm

all: NextFractal

clean:
	rm -rf NextFractal *.dSYM
