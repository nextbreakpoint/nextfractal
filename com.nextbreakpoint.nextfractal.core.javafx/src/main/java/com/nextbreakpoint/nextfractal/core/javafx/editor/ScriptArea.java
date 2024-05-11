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
package com.nextbreakpoint.nextfractal.core.javafx.editor;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.common.ParserResult;
import com.nextbreakpoint.nextfractal.core.common.ParserStrategy;
import com.nextbreakpoint.nextfractal.core.common.Session;
import com.nextbreakpoint.nextfractal.core.common.SourceError;
import com.nextbreakpoint.nextfractal.core.editor.GenericStyleSpans;
import com.nextbreakpoint.nextfractal.core.editor.GenericStyleSpansBuilder;
import com.nextbreakpoint.nextfractal.core.event.EditorReportChanged;
import com.nextbreakpoint.nextfractal.core.event.SessionDataChanged;
import com.nextbreakpoint.nextfractal.core.event.SessionDataLoaded;
import com.nextbreakpoint.nextfractal.core.event.SessionReportChanged;
import com.nextbreakpoint.nextfractal.core.event.SessionTerminated;
import com.nextbreakpoint.nextfractal.core.javafx.BooleanObservableValue;
import com.nextbreakpoint.nextfractal.core.javafx.PlatformEventBus;
import javafx.application.Platform;
import lombok.extern.java.Log;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.time.Duration;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import static com.nextbreakpoint.nextfractal.core.javafx.UIPlugins.tryFindFactory;

@Log
public class ScriptArea extends CodeArea {
    private final PlatformEventBus eventBus;

    private final BooleanObservableValue internalSource = new BooleanObservableValue();
    private final ExecutorService executor;

    private Session session;
    private ParserStrategy parserStrategy;

    public ScriptArea(PlatformEventBus eventBus) {
        this.eventBus = eventBus;

        setDisable(true);

        executor = Executors.newSingleThreadExecutor();
//        Cleaner.create().register(this, executor::shutdown);

        internalSource.setValue(false);

        setParagraphGraphicFactory(LineNumberFactory.get(this));

        plainTextChanges()
                .suppressible()
                .suspendedWhen(internalSource)
                .successionEnds(Duration.ofMillis(500))
                .supplyCompletionStage(() -> computeEvent(getText(), true))
                .awaitLatest()
                .map(org.reactfx.util.Try::get)
                .subscribe(this::notifyEvent);

        eventBus.subscribe(SessionDataLoaded.class.getSimpleName(), event -> handleSessionDataLoaded((SessionDataLoaded) event));

        eventBus.subscribe(SessionDataChanged.class.getSimpleName(), event -> handleSessionDataChanged((SessionDataChanged) event));

        eventBus.subscribe(EditorReportChanged.class.getSimpleName(), event -> handleEditorReportChanged((EditorReportChanged) event));

        eventBus.subscribe(SessionTerminated.class.getSimpleName(), event -> executor.shutdown());
    }

    private CompletionStage<EditorReportChanged> computeEvent(String text, boolean appendToHistory) {
        return parserStrategy.compute(executor, createModifiedSession(text)).thenApply(result -> createEditorReportChangedEvent(result, appendToHistory));
    }

    private Session createModifiedSession(String text) {
        // strategy, session, and text must be sampled at the same time to ensure they are consistent
        return parserStrategy.createSession(session.getMetadata(), text);
    }

    private void notifyEvent(EditorReportChanged event) {
        // we need to ignore the event if session has changed between creation and notification
        if (Objects.equals(event.session().getMetadata(), session.getMetadata())) {
            eventBus.postEvent(event);
        }
    }

    private void handleSessionDataChanged(SessionDataChanged event) {
        updateSession(event.session());
    }

    private void handleSessionDataLoaded(SessionDataLoaded event) {
        updateSession(event.session());

        internalSource.setValue(true);
        replaceText(0, getLength(), event.session().getScript());
        internalSource.setValue(false);

        computeEvent(event.session().getScript(), false)
            .whenComplete((newEvent, throwable) -> {
                if (throwable == null) {
                    Platform.runLater(() -> notifyEvent(newEvent));
                } else {
                    log.log(Level.WARNING, "Can't parse source code", throwable);
                }
            });
    }

    private void updateSession(Session session) {
        if (parserStrategy == null || !this.session.getPluginId().equals(session.getPluginId())) {
            parserStrategy = createParserStrategy(session).orElse(null);
            setDisable(false);
        }

        this.session = session;
    }

    public static Try<ParserStrategy, Exception> createParserStrategy(Session session) {
        return tryFindFactory(session.getPluginId())
                .map(plugin -> Objects.requireNonNull(plugin.createParserStrategy()));
    }

    private void handleEditorReportChanged(EditorReportChanged event) {
        updateTextStyles(event.result());
        notifySessionReportChanged(event);
    }

    private void notifySessionReportChanged(EditorReportChanged event) {
        eventBus.postEvent(SessionReportChanged.builder().session(event.session()).continuous(event.continuous()).appendToHistory(event.appendToHistory()).result(event.result()).build());
    }

    private EditorReportChanged createEditorReportChangedEvent(ParserResult result, boolean appendToHistory) {
        return EditorReportChanged.builder()
                .result(result)
                .session(result.session())
                .continuous(false)
                .appendToHistory(appendToHistory)
                .build();
    }

    private void updateTextStyles(ParserResult result) {
        setStyleSpans(0, convertStyleSpans(result.highlighting()));
        final List<SourceError> errors = result.errors();
        if (!errors.isEmpty()) {
            errors.sort(Comparator.comparing(SourceError::getIndex));
            for (SourceError error : errors) {
                if (log.isLoggable(Level.FINE)) {
                    log.fine(error.toString());
                }
                if (error.getType() != SourceError.ErrorType.RUNTIME) {
                    final int lineBegin = (int) error.getIndex();
                    final int lineEnd = lineBegin + 1;
                    try {
                        if (lineBegin < getLength() && lineEnd <= getLength()) {
                            final GenericStyleSpansBuilder<Collection<String>> builder = new GenericStyleSpansBuilder<>();
                            builder.add(List.of("error"), lineEnd - lineBegin);
                            final GenericStyleSpans<Collection<String>> spans = builder.create();
                            setStyleSpans(lineBegin, convertStyleSpans(spans));
                        } else {
                            if (log.isLoggable(Level.WARNING)) {
                                log.log(Level.WARNING, "begin " + lineBegin + ", length " + (lineEnd - lineBegin));
                            }
                        }
                    } catch (Exception e) {
                        if (log.isLoggable(Level.WARNING)) {
                            log.log(Level.WARNING, "Something went wrong", e);
                        }
                    }
                }
            }
        }
    }

    private static StyleSpans<Collection<String>> convertStyleSpans(GenericStyleSpans<Collection<String>> spans) {
        return spans.styleSpans()
                .stream()
                .collect(() -> new StyleSpansBuilder<Collection<String>>(), (b, s) -> b.add(s.styles(), s.index()), (a, b) -> {
                })
                .create();
    }
}
