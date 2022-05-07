package it.polimi.ingsw;

import it.polimi.ingsw.view.cli.CliColor;

import java.util.Scanner;

public class Eriantys {

    public static void main(String[] args) {
        System.out.println("███████╗ ██████╗  ██╗  █████╗  ███╗   ██╗ ████████╗ ██╗   ██╗ ███████╗\n" +
                           "██╔════╝ ██╔══██╗ ██║ ██╔══██╗ ████╗  ██║ ╚══██╔══╝ ╚██╗ ██╔╝ ██╔════╝\n" +
                           "█████╗   ██████╔╝ ██║ ███████║ ██╔██╗ ██║    ██║     ╚████╔╝  ███████╗\n" +
                           "██╔══╝   ██╔══██╗ ██║ ██╔══██║ ██║╚██╗██║    ██║      ╚██╔╝   ╚════██║\n" +
                           "███████╗ ██║  ██║ ██║ ██║  ██║ ██║ ╚████║    ██║       ██║    ███████║\n" +
                           "╚══════╝ ╚═╝  ╚═╝ ╚═╝ ╚═╝  ╚═╝ ╚═╝  ╚═══╝    ╚═╝       ╚═╝    ╚══════╝\n");

        System.out.println("Hi! Welcome to Eriantys!\nWhat do you want to launch?");
        System.out.println("0. SERVER\n" +
                           "1. CLIENT with CLI INTERFACE\n" +
                           "2. CLIENT with GUI INTERFACE\n");

        System.out.print("Type the number of the desired option: ");
        int input = readInput();

        while (input!=0 && input!=1 && input!=2) {
            System.out.println((CliColor.RESET_LINE));
            System.out.println("Invalid argument, please insert one of the available option again: ");
            input = readInput();
        }

        switch (input) {
            case 0:
                /*FIXME: ServerMain serverMain = new ServerMain();
                         serverMain.main(null);*/
                break;
            case 1:
                //FIXME: CLI.main(null);
                break;
            case 2:
                System.out.println("GUI has been selected. Opening...");
                //FIXME: GUI.main(null);
                break;
            default: //main should never reach this point
        }
    }

    private static int readInput() {
        Scanner scanner = new Scanner(System.in);
        int in = 0;

        try {
            in = scanner.nextInt();
        } catch (NumberFormatException e) {
            System.err.println("Numeric format requested, application will now close...");
            System.exit(-1);
        }

        return in;
    }
}