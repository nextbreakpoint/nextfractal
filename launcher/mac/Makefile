JAVA_VERSION?=11

JHOME:=$(shell /usr/libexec/java_home -v $(JAVA_VERSION))
SYSTEM:=darwin

CC = gcc
LD = ld

CPPFLAGS += -DJAVA_VERSION=$(JAVA_VERSION) -isysroot /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX10.14.sdk -arch x86_64
CXXFLAGS += -g
LDFLAGS +=
CXXFLAGS += -I$(JHOME)/include -I$(JHOME)/include/$(SYSTEM) -framework CoreFoundation
LDLIBS += -lstdc++

all: NextFractal

NextFractal:
	$(CC) $(CPPFLAGS) $(CXXFLAGS) $(LDFLAGS) -o NextFractal NextFractal.cpp $(LDLIBS)
	otool -L NextFractal

clean:
	rm -rf NextFractal *.dSYM
