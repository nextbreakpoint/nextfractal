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
package com.nextbreakpoint.nextfractal.mandelbrot.javafx;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.Error;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.javafx.BooleanObservableValue;
import com.nextbreakpoint.nextfractal.core.utils.Block;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotMetadata;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotSession;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerSourceException;
import javafx.concurrent.Task;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorPane extends BorderPane {
    private static final Logger logger = Logger.getLogger(EditorPane.class.getName());

    private final BooleanObservableValue internalSource;
    private final ExecutorService textExecutor;
    private final Pattern highlightingPattern;
    private final CodeArea codeArea;

    private MandelbrotSession session;

    public EditorPane(EventBus eventBus) {
        codeArea = new CodeArea();
        codeArea.getStyleClass().add("mandelbrot");

        internalSource = new BooleanObservableValue();
        internalSource.setValue(true);

        ScrollPane codePane = new ScrollPane();
        codePane.setContent(codeArea);
        codePane.setFitToWidth(true);
        codePane.setFitToHeight(true);

        highlightingPattern = createHighlightingPattern();

        setCenter(codePane);

        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        codeArea.plainTextChanges().suppressible().suppressWhen(internalSource).successionEnds(Duration.ofMillis(500)).supplyTask(this::computeTaskAsync)
            .awaitLatest().map(org.reactfx.util.Try::get).map(this::applyTaskResult).subscribe(result -> notifyTaskResult(eventBus, result));

        codeArea.setOnDragDropped(e -> e.getDragboard().getFiles().stream().findFirst()
            .ifPresent(file -> eventBus.postEvent("editor-load-file", file)));

        codeArea.setOnDragOver(e -> Optional.of(e).filter(q -> q.getGestureSource() != codeArea
            && q.getDragboard().hasFiles()).ifPresent(q -> q.acceptTransferModes(TransferMode.COPY_OR_MOVE)));

        textExecutor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("Mandelbrot Editor", true, Thread.MIN_PRIORITY));

        eventBus.subscribe("session-data-changed", event -> session = (MandelbrotSession) event[0]);

        eventBus.subscribe("session-data-loaded", event -> {
            MandelbrotSession session = (MandelbrotSession) event[0];
            updateSource(session.getScript()).ifPresent(result -> {
                eventBus.postEvent("session-report-changed", result.report, event[0], event[1], event[2]);
//                eventBus.postEvent("session-data-changed", event);
//                MandelbrotSession newSession = (MandelbrotSession) event[0];
//                Boolean continuous = (Boolean) event[1];
//                Boolean appendHistory = (Boolean) event[2];
//                if (!continuous && appendHistory) {
//                    eventBus.postEvent("history-add-session", newSession);
//                }
            });
        });

        eventBus.subscribe("editor-report-changed", event -> {
            eventBus.postEvent("session-report-changed", event);
            notifySourceIfRequired(eventBus, (CompilerReport)event[0]);
        });

//        eventBus.subscribe("editor-source-changed", event -> {
//            MandelbrotSession newSession = new MandelbrotSession((String) event[0], (MandelbrotMetadata) session.getMetadata());
//            eventBus.postEvent("session-data-changed", newSession, false, true);
//        });

        eventBus.subscribe("editor-action", event -> {
            if (session != null && event[0].equals("reload")) eventBus.postEvent("session-data-loaded", session, false, false);
        });

        eventBus.subscribe("session-terminated", event -> dispose());
    }

    private Try<TaskResult, Exception> updateSource(String source) {
        internalSource.setValue(true);
        codeArea.replaceText("");
        codeArea.replaceText(source);
        Try<TaskResult, Exception> result = Try.of(() -> generateReport(source))
            .map(report -> new TaskResult(source, report, computeHighlighting(source))).map(this::updateTextStyles);
        internalSource.setValue(false);
        return result;
    }

    private class TaskResult {
        private String source;
        private CompilerReport report;
        private StyleSpans<Collection<String>> highlighting;

        public TaskResult(String source, CompilerReport report, StyleSpans<Collection<String>> highlighting) {
            this.source = source;
            this.report = report;
            this.highlighting = highlighting;
        }
    }

    private Task<Optional<TaskResult>> computeTaskAsync() {
//        logger.info("Compute task..." + internalSource.getValue());
        String text = codeArea.getText();
        Task<Optional<TaskResult>> task = new Task<Optional<TaskResult>>() {
            @Override
            protected Optional<TaskResult> call() throws Exception {
                return Try.of(() -> new TaskResult(text, generateReport(text), computeHighlighting(text)))
                    .onFailure(e -> logger.log(Level.WARNING, "Cannot parse source", e)).value();
            }
        };
        textExecutor.execute(task);
        return task;
    }

    private CompilerReport generateReport(String text) throws Exception {
        return new Compiler().compileReport(text);
    }

    private StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = highlightingPattern.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass = matcher
                .group("KEYWORD") != null ? "keyword" : matcher
                .group("FUNCTION") != null ? "function" : matcher
                .group("PAREN") != null ? "paren" : matcher
                .group("BRACE") != null ? "brace" : matcher
                .group("OPERATOR") != null ? "operator" : matcher
                .group("PATHOP") != null ? "pathop" : null;
            assert styleClass != null;
            spansBuilder.add(Collections.singleton("code"), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.singleton("code"), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private Pattern createHighlightingPattern() {
        String[] KEYWORDS = new String[] {
            "fractal", "orbit", "color", "begin", "loop", "end", "rule", "trap", "palette", "if", "else", "stop", "init"
        };

        String[] FUNCTIONS = new String[] {
            "re", "im", "mod", "pha", "log", "exp", "sqrt", "mod2", "abs", "ceil", "floor", "pow", "hypot", "atan2", "min", "max", "cos", "sin", "tan", "asin", "acos", "atan", "time", "square", "saw", "ramp", "pulse"
        };

        String[] PATHOP = new String[] {
            "MOVETO", "MOVEREL", "LINETO", "LINEREL", "ARCTO", "ARCREL", "QUADTO", "QUADREL", "CURVETO", "CURVEREL", "CLOSE"
        };

        String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
        String FUNCTION_PATTERN = "\\b(" + String.join("|", FUNCTIONS) + ")\\b";
        String PATHOP_PATTERN = "\\b(" + String.join("|", PATHOP) + ")\\b";
        String PAREN_PATTERN = "\\(|\\)";
        String BRACE_PATTERN = "\\{|\\}";
        String OPERATOR_PATTERN = "\\*|\\+|-|/|\\^|<|>|\\||&|=|#|;|\\[|\\]";

        return Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
            + "|(?<FUNCTION>" + FUNCTION_PATTERN + ")"
            + "|(?<PAREN>" + PAREN_PATTERN + ")"
            + "|(?<BRACE>" + BRACE_PATTERN + ")"
            + "|(?<OPERATOR>" + OPERATOR_PATTERN + ")"
            + "|(?<PATHOP>" + PATHOP_PATTERN + ")"
        );
    }

    private Try<TaskResult, Exception> applyTaskResult(Optional<TaskResult> result) {
        return Try.of(() -> result.orElse(null)).flatMap(task -> compileOrbitAndColor(task)).map(task -> updateTextStyles(task));
    }

    private void notifyTaskResult(EventBus eventBus, Try<TaskResult, Exception> result) {
        result.map(task -> task.report).ifPresent(report -> eventBus.postEvent("editor-report-changed", report, new MandelbrotSession(report.getSource(), (MandelbrotMetadata) session.getMetadata()), false, !report.getSource().equals(session.getScript())));
    }

    private Try<TaskResult, Exception> compileOrbitAndColor(TaskResult task) {
        return Block.create(CompilerReport.class).andThen(this::compileOrbit).andThen(this::compileColor)
            .tryExecute(task.report).onFailure(e -> processCompilerErrors(task.report, e)).map(x -> task);
    }

    private void compileOrbit(CompilerReport report) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, CompilerSourceException {
        Optional.of(new Compiler().compileOrbit(report)).filter(builder -> builder.getErrors().size() == 0).ifPresent(builder -> Try.of(() -> builder.build()).execute());
    }

    private void compileColor(CompilerReport report) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, CompilerSourceException {
        Optional.of(new Compiler().compileColor(report)).filter(builder -> builder.getErrors().size() == 0).ifPresent(builder -> Try.of(() -> builder.build()).execute());
    }

    private void notifySourceIfRequired(EventBus eventBus, CompilerReport result) {
        Optional.of(result).filter(report -> report.getErrors().size() == 0).ifPresent(report -> eventBus.postEvent("editor-source-changed", result.getSource()));
    }

    private void processCompilerErrors(CompilerReport report, Exception e) {
        if (e instanceof CompilerSourceException) {
            report.getErrors().addAll(((CompilerSourceException)e).getErrors());
        } else {
            logger.log(Level.WARNING, "Cannot compile fractal", e);
        }
    }

    private TaskResult updateTextStyles(TaskResult task) {
        codeArea.setStyleSpans(0, task.highlighting);
        List<Error> errors = task.report.getErrors();
        if (errors.size() > 0) {
            Collections.sort(errors, (o1, o2) -> o2.getIndex() < o1.getIndex() ? -1 : 1);
            for (Error error : errors) {
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine(error.toString());
                }
                if (error.getType() == Error.ErrorType.SCRIPT_COMPILER) {
                    int lineEnd = (int)error.getIndex() + 1;
                    int lineBegin = (int)error.getIndex();
                    StyleSpansBuilder<Collection<String>> builder = new StyleSpansBuilder<>();
                    builder.add(Collections.singleton("error"), lineEnd - lineBegin);
                    try {
                        if (lineBegin < codeArea.getLength()) {
                            codeArea.setStyleSpans(lineBegin, builder.create());
                        } else {
                            if (logger.isLoggable(Level.WARNING)) {
                                logger.log(Level.WARNING, "begin " + lineBegin + ", length " + (lineEnd - lineBegin));
                            }
                        }
                    } catch (Exception e) {
                        if (logger.isLoggable(Level.WARNING)) {
                            logger.log(Level.WARNING, "begin " + lineBegin + ", length " + (lineEnd - lineBegin));
                            logger.log(Level.WARNING, "Something is wrong", e);
                        }
                    }
                }
            }
        }
        return task;
    }

    @Override
    protected void finalize() throws Throwable {
        dispose();
        super.finalize();
    }

    public void dispose() {
        List<ExecutorService> executors = Arrays.asList(textExecutor);
        executors.forEach(executor -> executor.shutdownNow());
        executors.forEach(executor -> await(executor));
    }

    private void await(ExecutorService executor) {
        Try.of(() -> executor.awaitTermination(5000, TimeUnit.MILLISECONDS)).onFailure(e -> logger.warning("Await termination timeout")).execute();
    }
}
