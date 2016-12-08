JAVA_VERSION?=8

JHOME:=/usr/lib/jvm/java-8-oracle
SYSTEM:=linux

CPPFLAGS += -DJAVA_VERSION=$(JAVA_VERSION)
CXXFLAGS += -g
VM_DIR=$(JHOME)/jre/lib
LDFLAGS += -L$(VM_DIR)/amd64/server -Wl,-rpath,$(VM_DIR) -Wl,-rpath,$(VM_DIR)/amd64/server
CXXFLAGS += -I$(JHOME)/include -I$(JHOME)/include/$(SYSTEM)
LDLIBS += -ljvm

all: NextFractal

clean:
	rm -rf NextFractal *.dSYM
