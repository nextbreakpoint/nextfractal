# NextFractal 2.1.0

Copyright 2015-2018 Andrea Medeghini

NextFractal is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

NextFractal is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with NextFractal. If not, see http://www.gnu.org/licenses/.


## NOTICE

NextFractal depends on several thirdparty libraries including FreeImage, FFmpeg, ANTLR, and other software from Apache Software Foundation.

NextFractal contains code ported to Java from C/C++ code of open source projects Xaos and ContextFree.


## DESCRIPTION

NextFractal is an application for creating Fractals and other algorithmically generated images.
Images are generated processing a script and some user provided parameters depending on the selected grammar.

NextFractal is currently able to interpret M scripts and CFDG scripts. The M scripts are based on a domain specific language for creating Mandelbrot, Julia and Fatou Sets. The CFDG scripts are based on a context-free grammar for creating geometric shapes using an iterative process.

NextFractal provides also tools for exploring Mandelbrot, Julia and Fatou Sets, browsing images, creating time based and events based animations, and rendering image and video files. NextFractal exports image as PNG files, and videos as AVI or Quicktime files.


## SYSTEM REQUIREMENTS

NextFractal has been tested on OS X 10.14, Windows 7, and Linux/Debian 9 and Linux/Fedora 28.

Windows users must install [Microsoft Visual C++ Redistributable for Visual Studio 2017](https://support.microsoft.com/en-gb/help/2977003/the-latest-supported-visual-c-downloads).

Linux users may have problems if their system doesn't have all the required libraries. Run the embedded java command (jdk/bin/java) to find out which libraries are missing.


## DOCUMENTATION

Please see documentation of grammars and tutorials on https://nextbreakpoint.com/nextfractal.html.


## BUILD INSTRUCTIONS

Checkout or download the source code from GitHub and run the build script. The build script requires Apache Ant and Apache Maven. Use the correct target for your system to build the distribution package. Apple command-line development tools are required to build the distribution for MacOS.

Get the code from https://github.com/nextbreakpoint/nextfractal:

    git clone https://github.com/nextbreakpoint/nextfractal.git

Use target build-macos to build the distribution for OS X:

    ant build-mac

Use target build-windows to build the distribution for Windows (any Windows 64bit):

    ant build-windows

Use target build-debian to build the distribution for Debian (or any other Debian-like Linux):

    ant build-debian

Use target build-fedora to build the distribution for Fedora (or any other Fedora-like Linux):

    ant build-fedora
