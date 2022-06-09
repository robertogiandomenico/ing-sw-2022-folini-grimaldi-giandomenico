package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.view.utilities.MatrixOperations;
import it.polimi.ingsw.view.utilities.lightclasses.LightArchi;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;
import it.polimi.ingsw.view.utilities.lightclasses.LightCharacter;
import it.polimi.ingsw.view.utilities.lightclasses.LightSchoolBoard;

import java.text.DecimalFormat;

/**
 * This class is used to properly display the board's features to the user.
 * Its methods are used in {@link CLI#printBoard(LightBoard) printBoard}.
 */
public class DisplayBoard {

    /**
     * Displays the school board (entrance, dining room, professor table,
     * tower section and, if expert mode, coin spots and supply).
     *
     * @param sB            a LightSchoolBoard to access all the information.
     * @param expertMode    a boolean whose value is:
     *                      <p>
     *                      -{@code true} if expert mode was chosen;
     *                      </p> <p>
     *                      -{@code false} otherwise.
     *                      </p>
     */
    public static void printSchoolBoard(LightSchoolBoard sB, boolean expertMode, boolean isCurrentPlayer) {
        int nicknameLength = sB.getPlayer().getNickname().length();
        CliColor color = isCurrentPlayer ? CliColor.CYAN : CliColor.RESET;

        System.out.print(CliColor.BOLD + sB.getPlayer().getNickname() + CliColor.RESET);

        //print blank schoolboard
        System.out.print("\033[1B");                //down 1 line
        for (int i = 0; i < nicknameLength; i++) {  //backward for -nicknameLength- lines
            System.out.print("\033[1D" + color);
        }
        System.out.print("╭───┬──────────────────┬───────╮" + "\033[1B" + "\033[32D");
        System.out.print("│   │ │           ║    │       │" + "\033[1B" + "\033[32D");
        System.out.print("│   │ │           ║    │       │" + "\033[1B" + "\033[32D");
        System.out.print("│   │ │           ║    │       │" + "\033[1B" + "\033[32D");
        System.out.print("│   │ │           ║    │       │" + "\033[1B" + "\033[32D");
        System.out.print("│   │ │           ║    │       │" + "\033[1B" + "\033[32D");
        System.out.print("╰───┴──────────────────┴───────╯" + CliColor.RESET);

        System.out.print("\033[32D" + "\033[5A");
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
        if (expertMode) {
            for (int i = 0; i < 5; i++) {
                System.out.print(CliColor.BOLDYELLOW + "\033[2C" + "©" + "\033[2C" + "©" + "\033[2C" + "©");
                System.out.print("\033[9D" + "\033[1B");
            }
            System.out.print("\033[5A");
        }

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
        System.out.print("\033[13C" + "\033[5A");

        //print professor table
        if (sB.getProfessorTable()[0]) {
            System.out.print(CliColor.GREEN + "■");
            System.out.print("\033[1B" + "\033[1D");
        } else System.out.print("\033[1B");
        if (sB.getProfessorTable()[1]) {
            System.out.print(CliColor.RED + "■");
            System.out.print("\033[1B" + "\033[1D");
        } else System.out.print("\033[1B");
        if (sB.getProfessorTable()[2]) {
            System.out.print(CliColor.BOLDYELLOW + "■");
            System.out.print("\033[1B" + "\033[1D");
        } else System.out.print("\033[1B");
        if (sB.getProfessorTable()[3]) {
            System.out.print(CliColor.PINK + "■");
            System.out.print("\033[1B" + "\033[1D");
        } else System.out.print("\033[1B");
        if (sB.getProfessorTable()[4]) {
            System.out.print(CliColor.BLUE + "■");
            System.out.print("\033[1B" + "\033[1D");
        } else System.out.print("\033[1B");

        System.out.print(CliColor.RESET);
        System.out.print("\033[5C" + "\033[4A");

        //print tower section
        int towerNameLength = sB.getPlayer().getTowerColor().name().length();
        System.out.print(sB.getPlayer().getTowerColor().name());
        for (int i = 0; i < towerNameLength; i++)
            System.out.print("\033[1D");
        System.out.print("\033[1B" + "\033[1C");
        System.out.print("♜×" + sB.getTowersLeft());

        System.out.print("\033[2B" + "\033[4D");

        //print money section
        if (expertMode)
            System.out.print("\uD83D\uDCB0×" + sB.getPlayer().getCoins());
        else
            System.out.print("\033[4C");

        if (sB.getPlayer().getCoins() < 10)
            System.out.print("\033[1B" + "\033[4C");
        else
            System.out.print("\033[1B" + "\033[3C");

    }

    /**
     * Displays an archipelago (with students and towers on it and, if present,
     * Mother Nature or No Entry Tiles).
     *
     * @param archipelago   a LightArchi to access all its information.
     * @param archiIndex    the index of the Archipelago.
     */
    public static void printArchipelago(LightArchi archipelago, int archiIndex) {
        DecimalFormat formatter = new DecimalFormat("00");

        //print blank archipelago
        if (archipelago.isMNPresent())
            System.out.print(CliColor.YELLOW);
        System.out.print("╒════════════════╕" + "\033[1B" + "\033[18D");
        System.out.print("│                │" + "\033[1B" + "\033[18D");
        System.out.print("│                │" + "\033[1B" + "\033[18D");
        System.out.print("│                │" + "\033[1B" + "\033[18D");
        System.out.print("╰────────────────╯" + CliColor.RESET);

        System.out.print("\033[4A" + "\033[10D");

        //print archiIndex
        if (archiIndex < 10)
            System.out.print(CliColor.BOLD + " " + archiIndex + " " + CliColor.RESET);
        else
            System.out.print("\033[D" + CliColor.BOLD + " " + archiIndex + " " + CliColor.RESET);

        System.out.print("\033[1B" + "\033[9D");

        //print students in archipelago
        System.out.print(CliColor.BGREEN + formatter.format(MatrixOperations.columnSum(archipelago.getIslands(), 0)));
        System.out.print("\033[1C");
        System.out.print(CliColor.BRED + formatter.format(MatrixOperations.columnSum(archipelago.getIslands(), 1)));
        System.out.print("\033[1C");
        System.out.print(CliColor.BYELLOW + formatter.format(MatrixOperations.columnSum(archipelago.getIslands(), 2)));
        System.out.print("\033[1C");
        System.out.print(CliColor.BPINK + formatter.format(MatrixOperations.columnSum(archipelago.getIslands(), 3)));
        System.out.print("\033[1C");
        System.out.print(CliColor.BBLUE + formatter.format(MatrixOperations.columnSum(archipelago.getIslands(), 4)));
        System.out.print(CliColor.RESET);

        System.out.print("\033[2B" + "\033[12D");

        //print no entry tile
        if (archipelago.isNoEntryTilePresent())
            System.out.print(CliColor.BOLDRED + "X" + CliColor.RESET);
        else
            System.out.print("\033[1C");

        System.out.print("\033[4C");

        //print mother nature
        if (archipelago.isMNPresent())
            System.out.print(CliColor.YELLOW + "M" + CliColor.RESET);
        else
            System.out.print("\033[1C");

        System.out.print("\033[4C");

        //print tower color
        if (archipelago.getTowerColor() == TowerColor.BLACK)
            System.out.print(CliColor.BBLACK + "B" + CliColor.RESET);
        else if (archipelago.getTowerColor() == TowerColor.GREY)
            System.out.print(CliColor.BGREY + "G" + CliColor.RESET);
        else if (archipelago.getTowerColor() == TowerColor.WHITE)
            System.out.print(CliColor.BWHITE + "W" + CliColor.RESET);
        else
            System.out.print("\033[1C");

        System.out.print("\033[1B");

        //print size of the archipelago
        if(archipelago.getSize() < 10)
            System.out.print("×" + archipelago.getSize());
        else {
            System.out.print("\033[1D");
            System.out.print("×" + archipelago.getSize());
        }

        System.out.print("\033[1C");
    }

    /**
     * Displays the cloud (with students on it).
     *
     * @param board         the LightBoard to access the Cloud.
     * @param cloudIndex    the index of the Cloud.
     */
    public static void printCloud(LightBoard board, int cloudIndex) {
        //print blank cloud
        if (board.getCloud(cloudIndex)[0] == null)    //check if the cloud is empty
            System.out.print(CliColor.BOLDBLACK);
        else
            System.out.print(CliColor.BOLDCYAN);
        System.out.print("╔═══════╗" + "\033[1B" + "\033[9D");
        System.out.print("║       ║" + "\033[1B" + "\033[9D");
        System.out.print("║       ║" + "\033[1B" + "\033[9D");
        System.out.print("╚═══════╝" + CliColor.RESET);

        System.out.print("\033[6D" + "\033[3A");

        //print cloudIndex
        System.out.print(CliColor.BOLD + " " + cloudIndex + " " + CliColor.RESET);

        System.out.print("\033[1B" + "\033[4D");

        //print students in cloud
        System.out.print(CliColor.BGREEN + numByColor(board.getCloud(cloudIndex), Color.GREEN));
        System.out.print("\033[1C");
        System.out.print(CliColor.BRED + numByColor(board.getCloud(cloudIndex), Color.RED));
        System.out.print("\033[1C");
        System.out.print(CliColor.BYELLOW + numByColor(board.getCloud(cloudIndex), Color.YELLOW));
        System.out.print("\033[1B" + "\033[4D");
        System.out.print(CliColor.BPINK + numByColor(board.getCloud(cloudIndex), Color.PINK));
        System.out.print("\033[1C");
        System.out.print(CliColor.BBLUE + numByColor(board.getCloud(cloudIndex), Color.BLUE) + CliColor.RESET);

        System.out.print("\033[3C" + "\033[1B");
    }

    /**
     * Displays the coin supply on the board (if expert mode).
     *
     * @param coinSupply    the quantity of coins in the supply.
     */
    public static void printCoinsSupply(int coinSupply) {
        //go at these coordinates
        System.out.print("\033[14;68H");

        //print blank box
        System.out.print("┌──────────────┐" + "\033[16D" + "\033[1B");
        System.out.print("│              │" + "\033[16D" + "\033[1B");
        System.out.print("│              │" + "\033[16D" + "\033[1B");
        System.out.print("└──────────────┘");

        System.out.print("\033[14D" + "\033[2A");

        //print text
        System.out.print("COINS SUPPLY");

        System.out.print("\033[1B" + "\033[9D");

        //print coins supply
        System.out.print("\uD83D\uDCB0×" + coinSupply);

        if (coinSupply < 10)
            System.out.print("\033[1B" + "\033[7C");
        else
            System.out.print("\033[1B" + "\033[6C");
    }

    /**
     * Displays the character (name, cost and, if present, students or No Entry
     * Tiles on it).
     *
     * @param character     the LightCharacter.
     * @param charIndex     the index of the character.
     */
    public static void printCharacter(LightCharacter character, int charIndex) {
        //print blank box
        System.out.print("╓───╥─────────────────╥───────────────────────╖" + "\033[1B" + "\033[47D");
        System.out.print("║   ║                 ║                       ║" + "\033[1B" + "\033[47D");
        System.out.print("╙───╨─────────────────╨───────────────────────╜");

        System.out.print("\033[1A" + "\033[45D");

        //print charIndex
        System.out.print(charIndex);

        System.out.print("\033[3C");

        //print name
        System.out.print(character.getName());
        int nameLength = character.getName().length();
        for (int i = 0; i < nameLength; i++)
            System.out.print("\033[1D");

        System.out.print("\033[18C");

        //print cost
        System.out.print(CliColor.BOLDYELLOW + "C" + CliColor.RESET + ":" + character.getCost());

        System.out.print("\033[2C");

        //print no entry tile, only if it's grannygrass
        if (character.getName().equals("GrannyGrass"))
            System.out.print(CliColor.BOLDRED + "X" + CliColor.RESET + ":" + character.getNoEntryTiles());
        else
            System.out.print("\033[3C");

        System.out.print("\033[2C");

        //print students, only if available
        switch (character.getName()) {
            case("Monk"):
            case("Jester"):
            case("SpoiledPrincess"):
                System.out.print("S:" +
                        CliColor.BGREEN  + numByColor(character.getStudents(), Color.GREEN)  +
                        "\033[1C" + CliColor.BRED    + numByColor(character.getStudents(), Color.RED)    +
                        "\033[1C" + CliColor.BYELLOW + numByColor(character.getStudents(), Color.YELLOW) +
                        "\033[1C" + CliColor.BPINK   + numByColor(character.getStudents(), Color.PINK)   +
                        "\033[1C" + CliColor.BBLUE   + numByColor(character.getStudents(), Color.BLUE)   +
                        "\033[1C" + CliColor.RESET);
                break;
            default:
                System.out.print("\033[12C");
        }

        System.out.print("\033[2C" + "\033[1B");
    }

    /**
     * Displays the students table in a school board.
     * Called in {@link CLI#printBoard printBoard} for each color.
     *
     * @param sB            the LightSchoolBoard.
     * @param tableIndex    the index of the dining room.
     */
    private static void printStudentsTable(LightSchoolBoard sB, int tableIndex) {
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

    /**
     * Returns the number of students of the given color in the given array.
     * Utility for {@link CLI} methods.
     *
     * @param studentsArray a Student Array.
     * @param color         a Color.
     * @return              the String representing the number of Students of that Color.
     */
    private static String numByColor(Student[] studentsArray, Color color) {
        int number = 0;

        for (Student s : studentsArray) {
            if (s != null && s.getColor() == color)
                number++;
        }
        return Integer.toString(number);
    }

}