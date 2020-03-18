/*
 * NextFractal 2.1.2-rc2
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2020 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import com.nextbreakpoint.nextfractal.core.common.SourceError;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.IntervalSet;

import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompilerErrorStrategy extends DefaultErrorStrategy {
	private static final Logger logger = Logger.getLogger(CompilerErrorStrategy.class.getName());
	private List<SourceError> errors;
	
	public CompilerErrorStrategy(List<SourceError> errors) {
		this.errors = errors;
	}

	@Override
	public void reportError(Parser recognizer, RecognitionException e) {
		String message = generateErrorMessage("Parse failed", recognizer);
		CompilerSourceError error = new CompilerSourceError(SourceError.ErrorType.SCRIPT_COMPILER, e.getOffendingToken().getLine(), e.getOffendingToken().getCharPositionInLine(), e.getOffendingToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
		logger.log(Level.FINE, error.toString(), e);
		errors.add(error);
	}

	@Override
	protected void reportInputMismatch(Parser recognizer, InputMismatchException e) {
		String message = generateErrorMessage("Input mismatch", recognizer);
		CompilerSourceError error = new CompilerSourceError(SourceError.ErrorType.SCRIPT_COMPILER, e.getOffendingToken().getLine(), e.getOffendingToken().getCharPositionInLine(), e.getOffendingToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
		logger.log(Level.FINE, error.toString(), e);
		errors.add(error);
	}

	@Override
	protected void reportFailedPredicate(Parser recognizer, FailedPredicateException e) {
		String message = generateErrorMessage("Failed predicate", recognizer);
		CompilerSourceError error = new CompilerSourceError(SourceError.ErrorType.SCRIPT_COMPILER, e.getOffendingToken().getLine(), e.getOffendingToken().getCharPositionInLine(), e.getOffendingToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
		logger.log(Level.FINE, error.toString(), e);
		errors.add(error);
	}

	@Override
	protected void reportUnwantedToken(Parser recognizer) {
		String message = generateErrorMessage("Unwanted token", recognizer);
		CompilerSourceError error = new CompilerSourceError(SourceError.ErrorType.SCRIPT_COMPILER, recognizer.getCurrentToken().getLine(), recognizer.getCurrentToken().getCharPositionInLine(), recognizer.getCurrentToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
		logger.log(Level.FINE, error.toString());
		errors.add(error);
	}

	@Override
	protected void reportMissingToken(Parser recognizer) {
		String message = generateErrorMessage("Missing token", recognizer);
		CompilerSourceError error = new CompilerSourceError(SourceError.ErrorType.SCRIPT_COMPILER, recognizer.getCurrentToken().getLine(), recognizer.getCurrentToken().getCharPositionInLine(), recognizer.getCurrentToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
		logger.log(Level.FINE, error.toString());
		errors.add(error);
	}

	private String generateErrorMessage(String message, Parser recognizer) {
		StringBuilder builder = new StringBuilder();
		builder.append(message);
		IntervalSet tokens = recognizer.getExpectedTokens();
		boolean first = true;
		for (Entry<String, Integer> entry : recognizer.getTokenTypeMap().entrySet()) {
			if (tokens.contains(entry.getValue())) {
				if (first) {
					first = false;
					if (message.length() > 0 && !message.endsWith(".")) {
						builder.append(". ");
					}
					builder.append("Expected tokens: ");
				} else {
					builder.append(", ");
				}
				builder.append(entry.getKey());
			}
		}
		return builder.toString();
	}
}
