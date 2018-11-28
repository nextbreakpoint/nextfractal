JAVA_VERSION?=11

JHOME:=/usr/lib/jvm/java-11-oracle
SYSTEM:=linux

CC = /usr/bin/x86_64-w64-mingw32-gcc
LD = /usr/bin/x86_64-w64-mingw32-ld
RC = /usr/bin/x86_64-w64-mingw32-windres

CPPFLAGS += -DJAVA_VERSION=$(JAVA_VERSION) -std=c++11
CXXFLAGS += -g
VM_DIR=$(JHOME)/jre/lib
LDFLAGS += -L/usr/x86_64-w64-mingw32/lib -Wl,-static
CXXFLAGS += -I/usr/x86_64-w64-mingw32/include -I$(JHOME)/include -I$(JHOME)/include/$(SYSTEM)
LDLIBS += -lstdc++

all: NextFractal

NextFractal:
	$(RC) NextFractal.rc resources.o
	$(CC) $(CPPFLAGS) $(CXXFLAGS) $(LDFLAGS) -o NextFractal.exe NextFractal.cpp resources.o $(LDLIBS)
	/usr/bin/x86_64-w64-mingw32-objdump -p NextFractal.exe | grep 'DLL Name'

clean:
	rm -rf NextFractal
