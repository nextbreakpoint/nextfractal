package com.nextbreakpoint.nextfractal.contextfree.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeSession;
import com.nextbreakpoint.nextfractal.contextfree.compiler.Compiler;
import com.nextbreakpoint.nextfractal.contextfree.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.contextfree.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.contextfree.compiler.CompilerSourceException;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.utils.Block;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import javafx.concurrent.Task;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

import java.time.Duration;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorPane extends BorderPane {
    private static final Logger logger = Logger.getLogger(EditorPane.class.getName());

    private final ExecutorService textExecutor;
    private final Pattern highlightingPattern;
    private final CodeArea codeArea;

    private String source = "";

    public EditorPane(EventBus eventBus) {
        codeArea = new CodeArea();
        codeArea.getStyleClass().add("contextfree");

        ScrollPane codePane = new ScrollPane();
        codePane.setContent(codeArea);
        codePane.setFitToWidth(true);
        codePane.setFitToHeight(true);

        highlightingPattern = createHighlightingPattern();

        setCenter(codePane);

        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        codeArea.plainTextChanges().successionEnds(Duration.ofMillis(500)).supplyTask(this::computeTaskAsync)
                .awaitLatest().map(org.reactfx.util.Try::get).subscribe(result -> applyTaskResult(eventBus, result));

//        codeArea.setOnDragDropped(e -> e.getDragboard().getFiles().stream().findFirst().ifPresent(file -> loadDataFromFile(file)));

        codeArea.setOnDragOver(e -> Optional.of(e).filter(q -> q.getGestureSource() != codeArea
                && q.getDragboard().hasFiles()).ifPresent(q -> q.acceptTransferModes(TransferMode.COPY_OR_MOVE)));

        textExecutor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("Editor", true, Thread.MIN_PRIORITY));

        eventBus.subscribe("editor-source-loaded", event -> updateSource((((ContextFreeSession)event)).getScript()));

		eventBus.subscribe("editor-report-changed", event -> {
			eventBus.postEvent("session-report-changed", event);
		});

		eventBus.subscribe("editor-source-changed", event -> {
            source = (String)event;
			eventBus.postEvent("session-source-changed", event);
		});

        eventBus.subscribe("editor-action", event -> {
            if (event.equals("reload")) updateSource(source);
        });
    }

    private void updateSource(String source) {
        codeArea.replaceText("");
        codeArea.replaceText(source);
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
        String text = codeArea.getText();
        Task<Optional<TaskResult>> task = new Task<Optional<TaskResult>>() {
            @Override
            protected Optional<TaskResult> call() throws Exception {
                return Try.of(() -> new TaskResult(text, generateReport(text), computeHighlighting(text))).onFailure(e -> logger.log(Level.WARNING, "Cannot parse source", e)).value();
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
            "startshape", "background", "include", "import", "tile", "rule", "path", "shape", "loop", "finally", "if", "switch", "case", "CF_INFINITY", "\u221E", "LET"
        };

        String[] FUNCTIONS = new String[] {
            "time", "timescale", "x", "y", "z", "rotate", "r", "size", "s", "skew", "flip", "f", "hue", "h", "saturation", "sat", "brightness", "b", "alpha", "a", "x1", "x2", "y1", "y2", "rx", "ry", "width", "transform", "trans", "param", "p", "clone"
        };

        String[] PATHOP = new String[] {
            "CIRCLE", "SQUARE", "TRIANGLE", "STROKE", "FILL", "MOVETO", "LINETO", "ARCTO", "CURVETO", "MOVEREL", "LINEREL", "ARCREL", "CURVEREL", "CLOSEPOLY"
        };

        String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
        String FUNCTION_PATTERN = "\\b(" + String.join("|", FUNCTIONS) + ")\\b";
        String PATHOP_PATTERN = "\\b(" + String.join("|", PATHOP) + ")\\b";
        String PAREN_PATTERN = "\\(|\\)|\\[|\\]";
        String BRACE_PATTERN = "\\{|\\}";
        String OPERATOR_PATTERN = "\\.\\.|\\u2026|\\+/-|\\u00b1";

        return Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
            + "|(?<FUNCTION>" + FUNCTION_PATTERN + ")"
            + "|(?<PAREN>" + PAREN_PATTERN + ")"
            + "|(?<BRACE>" + BRACE_PATTERN + ")"
            + "|(?<OPERATOR>" + OPERATOR_PATTERN + ")"
            + "|(?<PATHOP>" + PATHOP_PATTERN + ")"
        );
    }

    private void applyTaskResult(EventBus eventBus, Optional<TaskResult> result) {
        result.ifPresent(value -> Block.create(TaskResult.class)
            .andThen(task -> compileReport(task.report))
            .andThen(task -> eventBus.postEvent("editor-report-changed", task.report))
            .andThen(task -> updateSourceIfRequired(eventBus, task))
            .andThen(task -> codeArea.setStyleSpans(0, task.highlighting))
            .andThen(task -> displayErrors(task.report))
            .tryExecute(value)
            .ifFailure(e -> logger.log(Level.WARNING, "Cannot process source", e)));
    }

    private void updateSourceIfRequired(EventBus eventBus, TaskResult task) {
        Optional.of(task.report).filter(report -> report.getErrors().size() == 0).ifPresent(report -> eventBus.postEvent("editor-source-changed", task.source));
    }

    private void compileReport(CompilerReport report) {
        Block.create(CompilerReport.class).tryExecute(report).ifFailure(e -> processCompilerErrors(report, e));
    }

    private void processCompilerErrors(CompilerReport report, Exception e) {
        if (e instanceof CompilerSourceException) {
            report.getErrors().addAll(((CompilerSourceException)e).getErrors());
        } else {
            logger.log(Level.WARNING, "Cannot compile fractal", e);
        }
    }

    private void displayErrors(CompilerReport report) {
        List<CompilerError> errors = report.getErrors();
        if (errors.size() > 0) {
            Collections.sort(errors, (o1, o2) -> o2.getIndex() < o1.getIndex() ? -1 : 1);
            for (CompilerError error : errors) {
                logger.info(error.toString());
                if (error.getType() == CompilerError.ErrorType.CFDG_COMPILER) {
                    int lineEnd = (int)error.getIndex() + 1;
                    int lineBegin = (int)error.getIndex();
                    StyleSpansBuilder<Collection<String>> builder = new StyleSpansBuilder<>();
                    builder.add(Collections.singleton("error"), lineEnd - lineBegin);
                    try {
                        if (lineBegin < codeArea.getLength()) {
                            codeArea.setStyleSpans(lineBegin, builder.create());
                        } else {
                            logger.info("begin " + lineBegin + ", length " + (lineEnd - lineBegin));
                        }
                    } catch (Exception e) {
                        logger.info("begin " + lineBegin + ", length " + (lineEnd - lineBegin));
                        logger.log(Level.WARNING, "Something is wrong", e);
                    }
                }
            }
        }
    }
}
