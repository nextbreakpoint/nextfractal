/*
 * NextFractal 2.0.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.compiler;

import com.nextbreakpoint.nextfractal.contextfree.compiler.CompilerReport.Type;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDG;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDGDriver;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDGLexer;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDGLogger;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDGParser;
import com.nextbreakpoint.nextfractal.contextfree.grammar.exceptions.CFDGException;
import com.nextbreakpoint.nextfractal.core.Error;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterpreterReportCompiler {
	private static final Logger logger = Logger.getLogger(InterpreterReportCompiler.class.getName());
	private static final String INCLUDE_LOCATION = "include.location";

	public CompilerReport generateReport(String source) throws IOException {
		List<Error> errors = new ArrayList<>();
		CFDG cfdg = parse(source, errors);
		return new CompilerReport(cfdg, Type.INTERPRETER, source, errors);
	}
	
	private CFDG parse(String source, List<Error> errors) throws IOException {
		try {
			ANTLRInputStream is = new ANTLRInputStream(new StringReader(source));
			CFDGLexer lexer = new CFDGLexer(is);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			CFDGParser parser = new CFDGParser(tokens);
			parser.setDriver(new CFDGDriver());
			CFDGLogger logger = new CFDGLogger();
			parser.getDriver().setLogger(logger);
			parser.setErrorHandler(new CompilerErrorStrategy(errors));
			parser.getDriver().setCurrentPath(getIncludeDir());
			if (parser.choose() != null) {
				errors.addAll(logger.getErrors());
				return parser.getDriver().getCFDG();
			}
		} catch (CFDGException e) {
			CompilerError error = new CompilerError(Error.ErrorType.SCRIPT_COMPILER, e.getLocation().getLine(), e.getLocation().getCharPositionInLine(), e.getLocation().getStartIndex(), e.getLocation().getStopIndex() - e.getLocation().getStartIndex(), e.getMessage());
			logger.log(Level.FINE, error.toString(), e);
			errors.add(error);
		} catch (Exception e) {
			CompilerError error = new CompilerError(Error.ErrorType.SCRIPT_COMPILER, 0L, 0L, 0L, 0L, e.getMessage());
			logger.log(Level.FINE, error.toString(), e);
			errors.add(error);
		}
		return null;
	}

	private String getIncludeDir() {
		String defaultBrowserDir = System.getProperty(INCLUDE_LOCATION, "[user.home]");
		String userHome = System.getProperty("user.home");
		String userDir = System.getProperty("user.dir");
		String currentDir = new File(".").getAbsoluteFile().getParent();
		defaultBrowserDir = defaultBrowserDir.replace("[current.path]", currentDir);
		defaultBrowserDir = defaultBrowserDir.replace("[user.home]", userHome);
		defaultBrowserDir = defaultBrowserDir.replace("[user.dir]", userDir);
		logger.info("includeDir = " + defaultBrowserDir);
		return defaultBrowserDir;
	}
}
