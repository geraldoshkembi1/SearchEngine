package com.company.datamanagment;

import com.company.commands.Commands;
import com.company.commands.IndexCommand;
import com.company.commands.QueryCommand;
import com.company.commands.Utils;
import com.company.exceptions.SearchException;

import java.time.temporal.ValueRange;
import java.util.*;

public class DataService {

    private static Map<Integer, List<String>> DATA = new HashMap<>();
    private static Set<String> FOUND = new HashSet<>();


    static {
        populate();
    }

    public static String runCommand(Commands command) {
        if (command instanceof QueryCommand) {
            FOUND.clear();
            QueryCommand queryCommand = (QueryCommand) command;
            String input = queryCommand.getInput();

            //Rasti (domate&patate)|qep
            if (input.contains(Utils.OPEN_BRACE) & input.contains(Utils.CLOSE_BRACE)) {

                //pjesa brenda kllapave pa ()
                String innerInput = input.substring(input.indexOf(Utils.OPEN_BRACE) + 1, input.indexOf(Utils.CLOSE_BRACE));
                boolean plot = checkSides(innerInput);

                //outerInerput ka akoma parameter
                String outerInput = input.substring(input.indexOf(Utils.CLOSE_BRACE) + 1, input.length());
                if (outerInput.contains(Utils.AND)) {
                    //eleminojm parametrin
                    String withoutP = outerInput.substring(outerInput.indexOf(Utils.AND) + 1, outerInput.length());
                    FOUND.addAll(findlocation(withoutP));
                    return "Query result " + String.join(",", FOUND);
                }

                if (outerInput.contains(Utils.OR)) {
                    if (plot) {
                        return "Query result " + String.join(",", FOUND);
                    } else {
                        String withoutP = outerInput.substring(outerInput.indexOf(Utils.OR) + 1, outerInput.length());
                        if (findlocation(withoutP) == null) {
                            return "Query result : error";

                        } else {
                            FOUND.addAll(findlocation(withoutP));
                            return "Query result " + String.join(",", FOUND);
                        }

                    }

                }

                //Rasti query patato&domato
            } else if (!input.contains(Utils.OPEN_BRACE) & !input.contains(Utils.CLOSE_BRACE) & (input.contains(Utils.OR) | input.contains(Utils.AND))) {

                checkSides(input);
                return "Query result " + String.join(",", FOUND);

                // query patato
            } else {
                var index = findlocation(input);
                FOUND.addAll(index);
                return (index == null) ? "Query error message" : "Query result " + String.join(",", FOUND);
            }
        }


        if (command instanceof IndexCommand) {
            IndexCommand indexCommand = (IndexCommand) command;
            DATA.put(((IndexCommand) command).getIndex(), ((IndexCommand) command).getArguments());
            return "Index of :" + indexCommand.getIndex();
        }
        throw new SearchException("error");
    }


    private static Set<String> findlocation(String component) {
        String f1 = "";
        Set<String> f2 = new HashSet<>();
        for (Map.Entry m : DATA.entrySet()) {

            List y = (List) m.getValue();
            if (y.contains(component)) {
                //f1 += m.getKey().toString();
                f2.add(m.getKey().toString());
            }

        }
        return f2.isEmpty() ? null : f2;
    }


    private static void populate() {
        DATA.put(0, List.of("soup", "tomato", "cream", "salt"));
        DATA.put(1, List.of("cake", "sugar", "eggs", "flour", "sugar", "cocoa", "cream", "butter"));
        DATA.put(2, List.of("soup", "fish", "potato", "salt", "pepper"));
    }

    public static Map getDATA() {
        return DATA;
    }


    public static boolean checkSides(String innerInput) {

        if (innerInput.contains(Utils.AND)) {

            Utils.leftSide = innerInput.substring(0, innerInput.indexOf(Utils.AND));
            if (findlocation(Utils.leftSide) != null) {
                FOUND.addAll(findlocation(Utils.leftSide));
            }


            Utils.rightSide = innerInput.substring(innerInput.indexOf(Utils.AND) + 1);
            if (findlocation(Utils.rightSide) != null) {
                FOUND.addAll(findlocation(Utils.rightSide));
            }

            if (findlocation(Utils.rightSide) != null & findlocation(Utils.leftSide) != null) {
                return true;
            } else return false;


        } else if (innerInput.contains(Utils.OR)) {

            Utils.leftSide = innerInput.substring(0, innerInput.indexOf(Utils.OR));
            if (findlocation(Utils.leftSide) != null) {
                FOUND.addAll(findlocation(Utils.leftSide));
                return true;
            }

            Utils.rightSide = innerInput.substring(innerInput.indexOf(Utils.OR) + 1, innerInput.length());
            if (findlocation(Utils.rightSide) != null) {
                FOUND.addAll(findlocation(Utils.rightSide));
                return true;
            }
            return false;
        }

        return false;
    }


}
