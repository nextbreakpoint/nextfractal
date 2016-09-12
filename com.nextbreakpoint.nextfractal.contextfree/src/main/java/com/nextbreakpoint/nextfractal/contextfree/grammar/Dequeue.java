package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.util.ArrayList;
import java.util.List;

import java.util.Iterator;

public class Dequeue {
    private List<CommandInfo> commands = new ArrayList<>();

    public void pushBack(CommandInfo commandInfo) {
        //TODO rivedere
        commands.add(0, commandInfo);
    }

    public CommandInfo back() {
        //TODO rivedere
        return commands.get(0);
    }

    public CommandInfo last() {
        //TODO rivedere
        return commands.get(commands.size() - 1);
    }

    public CommandIterator iterator() {
        return new CommandIterator(commands);
    }

    public void clear() {
        commands.clear();
    }

    public void add(CommandInfo commandInfo) {
        //TODO rivedere
        commands.add(commandInfo);
    }
}
