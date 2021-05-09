package com.company;

import com.company.commands.CommandFiller;
import com.company.commands.Commands;
import com.company.commands.IndexCommand;
import com.company.datamanagment.DataService;
import com.company.exceptions.SearchException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here

        Scanner in = new Scanner(System.in);

        while (true) {

            System.out.println("Enter index or query or exit if u want to exit");
            String input = in.nextLine();

            if (input.equals("exit")) {
                break;
            }

            try {
                Commands commands = CommandFiller.fillCommand(input);
                System.out.println(DataService.runCommand(commands));
            } catch (SearchException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException exception) {
                System.out.println("Index command first argument should be an integer!");
            }


            System.out.println(DataService.getDATA());
        }
    }


}
