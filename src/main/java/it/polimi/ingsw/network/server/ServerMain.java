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
        Scanner scanner = new Scanner(System.in);

        System.out.print(CliColor.CLEAR_ALL);

        while (!valid){
            System.out.println("*************************");
            System.out.println("*    ERIANTYS SERVER    *");
            System.out.println("*************************\n");
            System.out.println("Please select a valid port between [" + MIN + ", " + MAX + "]");
            System.out.print("Insert 'd' for the default value (" + DEFAULT + "): ");
            if(notAnInt){
                System.out.println("\nERROR: Please insert only numbers or \"d\"");
                System.out.print("> ");
                notAnInt = false;
            }
            if(wrongPort){
                System.out.println("\nERROR: MIN_PORT = " + MIN + ", MAX_PORT = " + MAX);
                System.out.print("> ");
                wrongPort = false;
            }

            input = scanner.nextLine();

            if(input.equalsIgnoreCase("d")){
                port = DEFAULT;
                valid = true;
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
            if (!valid) {
                System.out.print(CliColor.CLEAR_ALL);
                System.out.flush();
            }
        }

        new Server(port).start();
    }
}
