package it.polimi.ingsw.network.server;

import it.polimi.ingsw.view.cli.CliColor;

import java.util.Scanner;

public class ServerMain {
    private static final int MIN = 1024;
    private static final int MAX = 65535;
    private static final int DEFAULT = 2807;

    public static void main(String[] args) {
        int port = DEFAULT;
        String input;
        boolean valid = false;
        boolean notAnInt = false;
        boolean wrongPort = false;

        System.out.print(CliColor.CLEAR_ALL);

        while (!valid){
            System.out.println("*************************");
            System.out.println("*    ERIANTYS SERVER    *");
            System.out.println("*************************");
            if(notAnInt){
                System.err.println("ERROR: Please insert only numbers");
                notAnInt = false;
            }
            if(wrongPort){
                System.err.println("ERROR: MIN_PORT = " + MIN + ", MAX_PORT = " + MAX);
                wrongPort = false;
            }
            System.out.println("Please select a valid port between [" + MIN + ", " + MAX + "]");
            System.out.print("Insert 'd' for the default value [" + DEFAULT + "]: ");
            firstAttempt = false;
            input = scanner.nextLine();

            if(input.equalsIgnoreCase("d") || input.equals("")){
                port = DEFAULT;
                validInput = true;
            } else {
                try {
                    port = Integer.parseInt(input);
                    if(MIN <= port && port <= MAX){
                        valid = true;
                    } else {
                        wrongPort = true;
                    }
                } catch (NumberFormatException e){
                    notAnInt = true;
                }
            }
            if (!validInput) {
                System.out.print(CliColor.CLEAR_ALL);
                System.out.flush();
            }
        }

        new Server(port).start();
    }
}
  