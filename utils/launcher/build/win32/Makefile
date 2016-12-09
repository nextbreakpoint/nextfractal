JAVA_VERSION?=8

JHOME:=/usr/lib/jvm/java-8-oracle
SYSTEM:=linux

CC = /usr/bin/x86_64-w64-mingw32-gcc
LD = /usr/bin/x86_64-w64-mingw32-ld

CPPFLAGS += -DJAVA_VERSION=$(JAVA_VERSION)
CXXFLAGS += -g 
VM_DIR=$(JHOME)/jre/lib
LDFLAGS += -L/usr/x86_64-w64-mingw32/lib -Wl,-static
CXXFLAGS += -I/usr/x86_64-w64-mingw32/include -I$(JHOME)/include -I$(JHOME)/include/$(SYSTEM)
LDLIBS += -lstdc++

all: NextFractal

NextFractal: 
	$(CC) $(CPPFLAGS) $(CXXFLAGS) $(LDFLAGS) -o NextFractal NextFractal.cpp $(LDLIBS) 
	/usr/bin/x86_64-w64-mingw32-objdump -p NextFractal | grep 'DLL Name'

clean:
	rm -rf NextFractal
