package com.nextbreakpoint.nextfractal.core.event;

import lombok.Builder;

import java.io.File;
import java.util.List;

@Builder
public record EditorDeleteFilesRequested(List<File> files) {}
