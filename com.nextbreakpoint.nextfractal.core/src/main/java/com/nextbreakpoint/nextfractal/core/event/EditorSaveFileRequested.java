package com.nextbreakpoint.nextfractal.core.event;

import lombok.Builder;

import java.io.File;

@Builder
public record EditorSaveFileRequested(File file) {}
