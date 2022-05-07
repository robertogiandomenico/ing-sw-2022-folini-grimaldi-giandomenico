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
        boolean firstAttempt = true;
        boolean validInput = false;

        Scanner scanner = new Scanner(System.in);


        System.out.print(CliColor.CLEAR_ALL);

        while (!validInput){
            System.out.println("\t E R I A N T Y S  |  S E R V E R \n");

            if(!firstAttempt)
                System.err.println("ERROR: MIN PORT = " + MIN + ", MAX PORT = " + MAX + ". Try Again.");

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
                    if(port >= MIN && port <= MAX)
                        validInput = true;

                } catch (NumberFormatException e){
                    System.err.println("Please insert only numbers.");
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
