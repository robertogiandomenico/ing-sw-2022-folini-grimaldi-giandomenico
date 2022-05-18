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
        System.out.println("\n\t" + CliColor.BWHITE + " E R I A N T Y S  |  S E R V E R " + CliColor.RESET + "\n\n");

        while (!valid) {

            if (notAnInt) {
                System.out.println(CliColor.RED + "ERROR: Please insert numbers only or \"d\"" + CliColor.RESET);
                notAnInt = false;
            }
            if (wrongPort) {
                System.out.println(CliColor.RED + "ERROR: MIN PORT = " + MIN + ", MAX PORT = " + MAX + CliColor.RESET);
                wrongPort = false;
            }

            System.out.println("Please select a valid port between [" + MIN + ", " + MAX + "]");
            System.out.print("Insert 'd' for the default value (" + DEFAULT + "): ");

            input = scanner.nextLine();

            if (input.equalsIgnoreCase("d")) {
                port = DEFAULT;
                valid = true;
                System.out.print("\033[3A" + CliColor.RESET_LINE + "\033[3B");
                System.out.println(CliColor.BOLDGREEN + "Port accepted successfully!" + CliColor.RESET);
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
                System.out.print("\033[3A" + CliColor.RESET_DOWN);
            }
        }

        new Server(port).start();
    }
}
