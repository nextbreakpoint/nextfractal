# NextFractal 2.1.4

Copyright 2015-2023 Andrea Medeghini

## NOTICE

NextFractal is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

NextFractal is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with NextFractal. If not, see http://www.gnu.org/licenses/.

NextFractal depends on several thirdparty libraries including FreeImage, FFmpeg, ANTLR, and other software from Apache Software Foundation.

NextFractal contains code ported to Java from C/C++ code of open source projects Xaos and ContextFree.


## DESCRIPTION

NextFractal is an application for creating fractals and other algorithmically generated images. Images are generated from a script and some parameters. NextFractal provides tools for rendering fractals, creating time-based and event-based animations, organising images, and exporting images and animations.

NextFractal is currently able to interpret two scripting languages: Mandelbrot and CFDG. Mandelbrot is a domain specific language for creating images of the Mandelbrot’s set (and its variants, such as Julia and Fatou sets). CFDG is a context-free grammar for creating images by an iterative process.


## SYSTEM REQUIREMENTS

NextFractal has been tested on OS X 10.14, Windows 7, and Linux/Debian 9 and Linux/Fedora 30.

Windows users must install [Microsoft Visual C++ Redistributable for Visual Studio 2017](https://support.microsoft.com/en-gb/help/2977003/the-latest-supported-visual-c-downloads).

Linux users may have problems if their system doesn't have all the required libraries. Run the embedded java command (jdk/bin/java) to find out which libraries are missing.


## DOCUMENTATION

Please see [Wiki](https://github.com/nextbreakpoint/nextfractal/wiki) for documentation.


## BUILD INSTRUCTIONS

Checkout or download the source code from GitHub and run the build script. The build script requires Apache Ant and Apache Maven. Use the correct target for your system to build the distribution package. Apple command-line development tools are required to build the distribution for MacOS.

Get the code from https://github.com/nextbreakpoint/nextfractal:

    git clone https://github.com/nextbreakpoint/nextfractal.git

Use target setup to configure the required tools:

    ant setup

Use target build-macos to build the distribution for OS X:

    ant build-mac

Use target build-windows to build the distribution for Windows (any Windows 64bit):

    ant build-windows

Use target build-debian to build the distribution for Debian (or any other Debian-like Linux):

    ant build-debian

Use target build-fedora to build the distribution for Fedora (or any other Fedora-like Linux):

    ant build-fedora
