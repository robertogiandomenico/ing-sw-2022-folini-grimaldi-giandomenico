package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.cli.CLI;

public class ClientMain {
    private static final String CLI_ARGUMENT = "-cli";

    public static void main(String[] args) {
        //if (args.length == 0)
        //Application.launch(GUI.class, args);
        if (args.length > 1)
            System.out.println("Too many arguments, insert " + CLI_ARGUMENT + " start in CLI mode.");
        else {
            if (CLI_ARGUMENT.equals(args[0]))
                CLI.main(args);
            else
                System.out.println("Command not found, insert " + CLI_ARGUMENT + " start in CLI mode.");
        }
    }
}