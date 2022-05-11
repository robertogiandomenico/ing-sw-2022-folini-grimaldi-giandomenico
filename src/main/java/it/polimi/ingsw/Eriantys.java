package it.polimi.ingsw;

import it.polimi.ingsw.view.cli.CliColor;
import it.polimi.ingsw.view.utilities.IntegerReader;

public class Eriantys {

    public static void main(String[] args) {
        System.out.println("  ✹    .      .     * ███████╗ ██████╗  ██╗  █████╗  ███╗   ██╗ ████████╗ ██╗   ██╗ ███████╗. 　　 　　　　　. 　\n" +
                           "         ✦     *      ██╔════╝ ██╔══██╗ ██║ ██╔══██╗ ████╗  ██║ ╚══██╔══╝ ╚██╗ ██╔╝ ██╔════╝ ⋆     ˚  *      .\n" +
                           "    .    *    .    ✹  █████╗   ██████╔╝ ██║ ███████║ ██╔██╗ ██║    ██║     ╚████╔╝  ███████╗   ·　   ✦     *  \n" +
                           "  .    *              ██╔══╝   ██╔══██╗ ██║ ██╔══██║ ██║╚██╗██║    ██║      ╚██╔╝   ╚════██║　 ✹ .   ·  　   ✧\n" +
                           "      .     ✦       * ███████╗ ██║  ██║ ██║ ██║  ██║ ██║ ╚████║    ██║       ██║    ███████║ ✦ 　 　       ·　 \n" +
                           "   ✹          .      .╚══════╝ ╚═╝  ╚═╝ ╚═╝ ╚═╝  ╚═╝ ╚═╝  ╚═══╝    ╚═╝       ╚═╝    ╚══════╝  　　 *　　✹　　　 ˚\n");

        System.out.println("Hi! Welcome to Eriantys!\nWhat do you want to launch?");
        System.out.println("1. SERVER\n" +
                           "2. CLIENT with CLI INTERFACE\n" +
                           "3. CLIENT with GUI INTERFACE\n" +
                           "\n0. Exit\n");

        System.out.print("Type the number of the desired option: ");
        int input = IntegerReader.readInput();

        while (input<0 || input>3) {
            System.out.println((CliColor.RESET_LINE));
            System.out.println("Invalid argument, please insert one of the available option again: ");
            input = IntegerReader.readInput();
        }

        switch (input) {
            case 1:
                /*FIXME: ServerMain serverMain = new ServerMain();
                         serverMain.main(null);*/
                break;
            case 2:
                //FIXME: CLI.main(null);
                break;
            case 3:
                System.out.println("GUI has been selected. Opening...");
                //FIXME: GUI.main(null);
                break;
            case 0:
                System.out.println("Application will now close.");
                System.exit(-1);
                break;

            default: //main should never reach this point
        }
    }

}