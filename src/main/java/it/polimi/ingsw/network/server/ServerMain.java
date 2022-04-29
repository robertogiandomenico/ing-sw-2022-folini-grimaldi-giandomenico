package it.polimi.ingsw.network.server;

import java.util.Scanner;

public class ServerMain {
    private static final int MIN = 1024;
    private static final int MAX = 65535;
    private static final int DEFAULT = 2807;
    private static final String CLEAR = "\033[H\033[2J";

    public static void main(String[] args) {
        int port = DEFAULT;
        String input;
        boolean valid = false;

        Scanner sc = new Scanner(System.in);


        System.out.print(CLEAR);

        while (!valid){
            System.out.println("*************************");
            System.out.println("*    ERIANTYS SERVER    *");
            System.out.println("*************************");
            System.out.println("Please select a valid port between [" + MIN + ", " + MAX + "]");
            System.out.print("Insert 'd' for the default value (" + DEFAULT + "): ");
            input = sc.nextLine();
            if(input.equalsIgnoreCase("d")){
                port = DEFAULT;
                valid = true;
            } else {
                try {
                    port = Integer.parseInt(input);
                    if(MIN <= port && port <= MAX){
                        valid = true;
                    } else {
                        System.out.println("ERROR: MIN_PORT = " + MIN + ", MAX_PORT = " + MAX);
                    }
                } catch (NumberFormatException e){
                    System.out.println("Please insert only numbers");
                }
            }
            if (!valid) {
                System.out.print(CLEAR);
                System.out.flush();
            }
        }

        new Server(port).start();
    }
}
