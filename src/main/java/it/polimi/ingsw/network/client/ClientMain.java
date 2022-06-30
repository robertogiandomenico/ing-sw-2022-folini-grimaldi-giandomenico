package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.GUI;

/**
 * This class is used to launch the game client side.
 */
public class ClientMain {
    private static final String CLI_ARGUMENT = "-cli";

    /**
     * Main method. Launches the game in {@link CLI} mode if -cli
     * argument is specified, {@link GUI} mode if there are no arguments.
     *
     * @param args      the command line arguments.
     */
    public static void main(String[] args) {
        if (args.length == 0)
            GUI.main(args);
            //Application.launch(GUI.class, args);
        else if (args.length > 1)
            System.out.println("Too many arguments, insert " + CLI_ARGUMENT + " start in CLI mode.");
        else {
            if (CLI_ARGUMENT.equals(args[0]))
                CLI.main(args);
            else
                System.out.println("Command not found, insert " + CLI_ARGUMENT + " start in CLI mode.");
        }
    }

}