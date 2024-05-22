package com.nextbreakpoint.nextfractal.runtime.javafx.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.nextbreakpoint.nextfractal.runtime.javafx.utils.Constants.PROJECT_EXTENSION;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmssSSS");

    public static File newProjectFile(File workspace) {
        if (!workspace.exists() || !workspace.canWrite()) {
            throw new IllegalStateException("Can't create file");
        }
        File file = new File(workspace, createFileName(PROJECT_EXTENSION));
        while (file.exists()) {
            file = new File(workspace, createFileName(PROJECT_EXTENSION));
        }
        return file;
    }

    public static String createFileName(String extension) {
        return FORMATTER.format(LocalDateTime.now(Clock.systemUTC())) + extension;
    }
}
