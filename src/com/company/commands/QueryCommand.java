package com.company.commands;

public class QueryCommand implements Commands {
    private static final String COMMAND1 = "query";
    private String input;

    public QueryCommand(String input) {
        this.input = input;
    }


    public String getInput() {
        return input;
    }
}
