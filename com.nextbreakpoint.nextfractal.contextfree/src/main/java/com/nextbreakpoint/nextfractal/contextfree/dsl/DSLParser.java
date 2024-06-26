/*
 * NextFractal 2.2.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2024 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.contextfree.dsl;

import com.nextbreakpoint.nextfractal.contextfree.core.ParserException;
import com.nextbreakpoint.nextfractal.contextfree.dsl.grammar.*;
import com.nextbreakpoint.nextfractal.contextfree.dsl.grammar.exceptions.CFDGException;
import com.nextbreakpoint.nextfractal.core.common.SourceError;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DSLParser {
	private static final Logger logger = Logger.getLogger(DSLParser.class.getName());
	private static final String INCLUDE_LOCATION = "include.location";

	public DSLParser() {
	}
	
	public DSLParserResult parse(String source) throws ParserException {
		List<SourceError> errors = new ArrayList<>();
		CFDG cfdg = parse(source, errors);
		return new DSLParserResult(cfdg, DSLParserResult.Type.INTERPRETER, source, errors);
	}

	private CFDG parse(String source, List<SourceError> errors) throws ParserException {
		try {
			CharStream is = CharStreams.fromReader(new StringReader(source));
			CFDGLexer lexer = new CFDGLexer(is);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			CFDGParser parser = new CFDGParser(tokens);
			parser.setDriver(new CFDGDriver());
			CFDGLogger logger = new CFDGLogger();
			parser.getDriver().setLogger(logger);
			parser.setErrorHandler(new ErrorStrategy(errors));
			parser.getDriver().setCurrentPath(getIncludeDir());
			if (parser.choose() != null) {
				errors.addAll(logger.getErrors());
				return parser.getDriver().getCFDG();
			}
		} catch (CFDGException e) {
			SourceError.ErrorType type = SourceError.ErrorType.SCRIPT_COMPILER;
			long line = e.getLocation().getLine();
			long charPositionInLine = e.getLocation().getCharPositionInLine();
			long index = e.getLocation().getStartIndex();
			long length = e.getLocation().getStopIndex() - e.getLocation().getStartIndex();
			String message = e.getMessage();
			SourceError error = new SourceError(type, line, charPositionInLine, index, length, message);
			logger.log(Level.FINE, error.toString(), e);
			errors.add(error);
			throw new ParserException("Can't parse source", errors);
		} catch (Exception e) {
			SourceError.ErrorType type = SourceError.ErrorType.SCRIPT_COMPILER;
			String message = e.getMessage();
			SourceError error = new SourceError(type, 0L, 0L, 0L, 0L, message);
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
