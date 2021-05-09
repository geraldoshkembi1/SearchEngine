package com.company.commands;

import com.company.exceptions.SearchException;

import java.util.List;
import java.util.Set;

public class Utils {

    public static final String OPEN_BRACE = "(";
    public static final String CLOSE_BRACE = ")";
    public static final String OR = "|";
    public static final String AND = "&";
    public static final Set<String> ALLOWED_OPERATORS = Set.of(OPEN_BRACE, CLOSE_BRACE, OR, AND);
    public static String leftSide;
    public static String rightSide;
    public static String secondPart;


    public static boolean validate(List<String> arg) {
        String controller = String.join("", arg);
        if (controller.matches("[a-zA-Z]+")) {
            return true;
        } else {
            throw new SearchException("Index arguments should not have special characters");
        }

    }
}
