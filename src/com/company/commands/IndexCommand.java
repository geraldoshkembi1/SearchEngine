package com.company.commands;

import java.util.List;

public class IndexCommand implements Commands {

    private static final String COMMAND1 = "index";
    private Integer index;
    private List<String> arguments;

    public IndexCommand(Integer index, List<String> arguments) {
        this.index = index;
        this.arguments = arguments;
    }


    public List<String> getArguments() {
        return arguments;
    }

    public Integer getIndex() {
        return index;
    }
}
