package it.polimi.ingsw.view.utilities;

import it.polimi.ingsw.view.cli.CliColor;

import java.util.InputMismatchException;
import java.util.Scanner;

public class IntegerReader {
    public static int readInput(Scanner scanner) {
        int in = -1;

        while (true) {
            try {
                in = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.print("\033[1A" + CliColor.RESET_LINE);
                System.err.print("Numeric format requested, try again: ");
                //this instruction is needed to clear the buffer and be ready to read the next input
                scanner.next();
            }
        }
        return in;
    }
}
