package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.util.ArrayList;
import java.util.List;

public class Dequeue {
    private List<CommandInfo> commands = new ArrayList<>();

    public void pushBack(CommandInfo commandInfo) {
        //TODO rivedere
        commands.add(commandInfo);
    }

    public CommandInfo back() {
        return commands.get(commands.size() - 1);
    }

    public CommandIterator iterator() {
        return new CommandIterator(commands);
    }

    public void clear() {
        commands.clear();
    }
}
