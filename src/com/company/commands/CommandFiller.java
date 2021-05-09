package com.company.commands;

import com.company.exceptions.SearchException;

import java.util.ArrayList;
import java.util.List;

public class CommandFiller {


    public static Commands fillCommand(String input) {
        ArrayList<String> arg = new ArrayList<>(List.of(input.split(" ")));
        if (arg.size() < 2) {
            throw new SearchException("Line is short");
        }
        String command = arg.get(0);

        switch (command) {
            case "index":
                return fillIndexCommand(arg);

            case "query":
                return fillQueryCommand(arg);

            default:
                throw new SearchException(command + " is not allowed");

        }


    }


    public static IndexCommand fillIndexCommand(List<String> arg) {
        Integer index = Integer.parseInt(arg.get(1));
        if (arg.size() < 3) {
            throw new SearchException("Index query should have at least one argument");
        }

        List<String> IndexArguments = arg.subList(2, arg.size());
        if (!Utils.validate(IndexArguments)) {
            throw new RuntimeException();
        }
        ;
        return new IndexCommand(index, IndexArguments);

    }

    public static QueryCommand fillQueryCommand(List<String> arg) {


        List<String> QueryArguments = arg.subList(1, arg.size());
        String QInput = String.join("", QueryArguments);

        return new QueryCommand(QInput);
    }


}
