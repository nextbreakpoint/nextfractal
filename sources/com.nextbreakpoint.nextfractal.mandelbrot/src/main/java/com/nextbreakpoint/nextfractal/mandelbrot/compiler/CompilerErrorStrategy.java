package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.IntervalSet;

public class CompilerErrorStrategy extends DefaultErrorStrategy {
	private static final Logger logger = Logger.getLogger(CompilerErrorStrategy.class.getName());
	private List<CompilerError> errors;
	
	public CompilerErrorStrategy(List<CompilerError> errors) {
		this.errors = errors;
	}

	@Override
	public void reportError(Parser recognizer, RecognitionException e) {
		String message = generateErrorMessage("Parse failed", recognizer);
		CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, e.getOffendingToken().getLine(), e.getOffendingToken().getCharPositionInLine(), e.getOffendingToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
		logger.log(Level.FINE, error.toString(), e);
		errors.add(error);
	}

	@Override
	protected void reportInputMismatch(Parser recognizer, InputMismatchException e) {
		String message = generateErrorMessage("Input mismatch", recognizer);
		CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, e.getOffendingToken().getLine(), e.getOffendingToken().getCharPositionInLine(), e.getOffendingToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
		logger.log(Level.FINE, error.toString(), e);
		errors.add(error);
	}

	@Override
	protected void reportFailedPredicate(Parser recognizer, FailedPredicateException e) {
		String message = generateErrorMessage("Failed predicate", recognizer);
		CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, e.getOffendingToken().getLine(), e.getOffendingToken().getCharPositionInLine(), e.getOffendingToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
		logger.log(Level.FINE, error.toString(), e);
		errors.add(error);
	}

	@Override
	protected void reportUnwantedToken(Parser recognizer) {
		String message = generateErrorMessage("Unwanted token", recognizer);
		CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, recognizer.getCurrentToken().getLine(), recognizer.getCurrentToken().getCharPositionInLine(), recognizer.getCurrentToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
		logger.log(Level.FINE, error.toString());
		errors.add(error);
	}

	@Override
	protected void reportMissingToken(Parser recognizer) {
		String message = generateErrorMessage("Missing token", recognizer);
		CompilerError error = new CompilerError(CompilerError.ErrorType.M_COMPILER, recognizer.getCurrentToken().getLine(), recognizer.getCurrentToken().getCharPositionInLine(), recognizer.getCurrentToken().getStartIndex(), recognizer.getCurrentToken().getStopIndex() - recognizer.getCurrentToken().getStartIndex(), message);
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