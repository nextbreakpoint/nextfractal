# NextFractal 2.1.4

Copyright 2015-2023 Andrea Medeghini


## NOTICE

NextFractal is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

NextFractal is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with NextFractal. If not, see http://www.gnu.org/licenses/.

NextFractal depends on several thirdparty libraries including FreeImage, FFmpeg, ANTLR, and other software from Apache Software Foundation.

NextFractal contains code derived from Xaos and ContextFree (C/C++ code ported to Java).


## DESCRIPTION

NextFractal is an application for creating fractals and other algorithmically generated images. Images are generated from instructions contained in a script. NextFractal provides tools for rendering fractals, creating time-based and event-based animations, organising images, and exporting images and animations.

NextFractal is currently able to interpret two scripting languages: Mandelbrot and CFDG. Mandelbrot is a domain specific language for creating images of the Mandelbrot set and its variants (the Julia and Fatou sets). CFDG is a context-free grammar for creating images by an iterative process.


## SYSTEM REQUIREMENTS

NextFractal has been tested on OS X 10.14, Windows 7, and Linux/Debian 9 and Linux/Fedora 30. We recommend a machine with at least 4-cores CPU and 4Gb RAM.
Windows users must install [Microsoft Visual C++ Redistributable for Visual Studio 2017](https://support.microsoft.com/en-gb/help/2977003/the-latest-supported-visual-c-downloads).

        NextFractal hasn't been recompiled yet for Apple Silicon processors, therefore the application runs with 
        degraded performance on the new Apple computers. The issue will be addressed in the next release.


## DOCUMENTATION

Please see the [Wiki](https://github.com/nextbreakpoint/nextfractal/wiki) for documentation. The wiki contains a tutorial for the scripting language, and examples of scripts.


## BUILD INSTRUCTIONS

Download the source code from GitHub and run the build script. The build script requires Apache Ant and Apache Maven. Use the correct target for your system to build the distribution package. Please note that Apple command-line development tools are required to build the distribution for MacOS.

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
