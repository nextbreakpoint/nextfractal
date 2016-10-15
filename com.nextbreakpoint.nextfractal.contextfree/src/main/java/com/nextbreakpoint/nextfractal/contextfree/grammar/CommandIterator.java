package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.util.List;

public class CommandIterator {
    private List<CommandInfo> commands;
    private int current;

    public CommandIterator(List<CommandInfo> commands) {
        this.commands = commands;
    }

    public CommandInfo current() {
        if (current < commands.size()) {
            return commands.get(current);
        }
        return null;
    }

    public boolean next() {
        if (current < commands.size()) {
            current += 1;
            return true;
        }
        return false;
    }
}
