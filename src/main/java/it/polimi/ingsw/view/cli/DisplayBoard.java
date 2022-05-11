package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.*;

public class DisplayBoard {

    public static void printSchoolBoard(SchoolBoard sB) {
        int nicknameLength = sB.getPlayer().getNickname().length();

        System.out.print(CliColor.BOLD + sB.getPlayer().getNickname() + CliColor.RESET);

        //print blank schoolboard
        System.out.print("\033[1B");          //down 1 line
        for (int i = 0; i < nicknameLength; i++) {  //backward for -nicknameLength- lines
            System.out.print("\033[1D");
        }
        System.out.print("┏━━━┳━━━━━━━━━━━━━━━━━┳━━━━━━━┓" + "\033[1B" + "\033[31D");
        System.out.print("┃   ┃ │          ║    ┃       ┃" + "\033[1B" + "\033[31D");
        System.out.print("┃   ┃ │          ║    ┃       ┃" + "\033[1B" + "\033[31D");
        System.out.print("┃   ┃ │          ║    ┃       ┃" + "\033[1B" + "\033[31D");
        System.out.print("┃   ┃ │          ║    ┃       ┃" + "\033[1B" + "\033[31D");
        System.out.print("┃   ┃ │          ║    ┃       ┃" + "\033[1B" + "\033[31D");
        System.out.print("┗━━━┻━━━━━━━━━━━━━━━━━┻━━━━━━━┛");

        System.out.print("\033[31D" + "\033[5A");
        System.out.print("\033[2C");

        //print all the entrance
        System.out.print(CliColor.BGREEN + numByColor(sB.getEntrance(), Color.GREEN));
        System.out.print("\033[1B" + "\033[1D");
        System.out.print(CliColor.BRED + numByColor(sB.getEntrance(), Color.RED));
        System.out.print("\033[1B" + "\033[1D");
        System.out.print(CliColor.BYELLOW + numByColor(sB.getEntrance(), Color.YELLOW));
        System.out.print("\033[1B" + "\033[1D");
        System.out.print(CliColor.BPINK + numByColor(sB.getEntrance(), Color.PINK));
        System.out.print("\033[1B" + "\033[1D");
        System.out.print(CliColor.BBLUE + numByColor(sB.getEntrance(), Color.BLUE) + CliColor.RESET);

        System.out.print("\033[4A" + "\033[4C");

        //print all the coin spots
        for (int i = 0; i < 5; i++) {
            System.out.print(CliColor.BOLDYELLOW + "\033[2C" + "C" + "\033[2C" + "C" + "\033[2C" + "C");
            System.out.print("\033[9D" + "\033[1B");
        }

        System.out.print("\033[5A");

        //print all the students tables
        System.out.print(CliColor.GREEN);
        printStudentsTable(sB, 0);
        System.out.print(CliColor.RED);
        printStudentsTable(sB, 1);
        System.out.print(CliColor.YELLOW);
        printStudentsTable(sB, 2);
        System.out.print(CliColor.PINK);
        printStudentsTable(sB, 3);
        System.out.print(CliColor.BLUE);
        printStudentsTable(sB, 4);

        System.out.print(CliColor.RESET);
        System.out.println("\033[11C" + "\033[5A");

        //print professor table
        if (sB.isProfessorPresent(0)) {
            System.out.print(CliColor.GREEN + " ■");
            System.out.print("\033[1B" + "\033[2D");
        } else System.out.print("\033[1B");
        if (sB.isProfessorPresent(1)) {
            System.out.print(CliColor.RED + " ■");
            System.out.print("\033[1B" + "\033[2D");
        } else System.out.print("\033[1B");
        if (sB.isProfessorPresent(2)) {
            System.out.print(CliColor.YELLOW + " ■");
            System.out.print("\033[1B" + "\033[2D");
        } else System.out.print("\033[1B");
        if (sB.isProfessorPresent(3)) {
            System.out.print(CliColor.PINK + " ■");
            System.out.print("\033[1B" + "\033[2D");
        } else System.out.print("\033[1B");
        if (sB.isProfessorPresent(4)) {
            System.out.print(CliColor.BLUE + " ■");
            System.out.print("\033[1B" + "\033[2D");
        } else System.out.print("\033[1B");

        System.out.print(CliColor.RESET);
        System.out.print("\033[6C" + "\033[4A");

        //print tower section
        int towerNameLength = sB.getPlayer().getTowerColor().getClass().getName().length();
        System.out.print(sB.getPlayer().getTowerColor().getClass().getName());
        for (int i = 0; i < towerNameLength; i++)
            System.out.print("\033[1B");
        System.out.print("T x" + sB.getTowersLeft());

        System.out.print("\033[2B" + "\033[4D");

        //print money section
        System.out.print("$ x" + sB.getPlayer().getCoins());

        if (sB.getPlayer().getCoins() < 10)
            System.out.println("\033[1B" + "\033[3C");
        else
            System.out.println("\033[1B" + "\033[2C");

    }

    public static void printArchipelago(Archipelago archipelago) {

    }

    public static void printCloud(Cloud cloud) {

    }

    public static void printCharacter(GameCharacter availableCharacters) {

    }

    private static void printStudentsTable(SchoolBoard sB, int tableIndex) {
        //print students
        for (int i = 0; i < sB.getDiningRoom()[tableIndex]; i++) {
            System.out.print("•");
        }

        //go to the new table print position
        System.out.print("\033[1B");
        for (int i = 0; i < sB.getDiningRoom()[tableIndex]; i++) {
            System.out.print("\033[1D");
        }
    }

    private static String numByColor(Student[] entrance, Color color) {
        int number = 0;

        for (Student s : entrance) {
            if (s.getColor() == color)
                number++;
        }
        return Integer.toString(number);
    }

}
