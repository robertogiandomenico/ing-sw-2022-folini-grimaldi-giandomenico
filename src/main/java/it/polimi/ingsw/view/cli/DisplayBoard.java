package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.view.utilities.lightclasses.LightArchi;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;
import it.polimi.ingsw.view.utilities.lightclasses.LightCharacter;
import it.polimi.ingsw.view.utilities.lightclasses.LightSchoolBoard;

import java.text.DecimalFormat;

public class DisplayBoard {

    public static void printSchoolBoard(LightSchoolBoard sB, boolean expertMode) {
        int nicknameLength = sB.getPlayer().getNickname().length();

        System.out.print(CliColor.BOLD + sB.getPlayer().getNickname() + CliColor.RESET);

        //print blank schoolboard
        System.out.print("\033[1B");          //down 1 line
        for (int i = 0; i < nicknameLength; i++) {  //backward for -nicknameLength- lines
            System.out.print("\033[1D");
        }
        System.out.print("╭───┬──────────────────┬───────╮" + "\033[1B" + "\033[32D");
        System.out.print("│   │ │           ║    │       │" + "\033[1B" + "\033[32D");
        System.out.print("│   │ │           ║    │       │" + "\033[1B" + "\033[32D");
        System.out.print("│   │ │           ║    │       │" + "\033[1B" + "\033[32D");
        System.out.print("│   │ │           ║    │       │" + "\033[1B" + "\033[32D");
        System.out.print("│   │ │           ║    │       │" + "\033[1B" + "\033[32D");
        System.out.print("╰───┴──────────────────┴───────╯");

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
        System.out.println("\033[13C" + "\033[5A");

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
        int towerNameLength = sB.getPlayer().getTowerColor().getClass().getName().length();
        System.out.print(sB.getPlayer().getTowerColor().getClass().getName());
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
            System.out.println("\033[1B" + "\033[4C");
        else
            System.out.println("\033[1B" + "\033[3C");

    }

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

        System.out.print("\033[1B" + "\033[6D");

        //print students in archipelago
        System.out.print(CliColor.BGREEN + formatter.format(Integer.toString( arraySum(archipelago.getIslands()[0]) )));
        System.out.print("\033[1C");
        System.out.print(CliColor.BRED + formatter.format(Integer.toString( arraySum(archipelago.getIslands()[1]) )));
        System.out.print("\033[1C");
        System.out.print(CliColor.BYELLOW + formatter.format(Integer.toString( arraySum(archipelago.getIslands()[2]) )));
        System.out.print("\033[1C");
        System.out.print(CliColor.BPINK + formatter.format(Integer.toString( arraySum(archipelago.getIslands()[3]) )));
        System.out.print("\033[1C");
        System.out.print(CliColor.BBLUE + formatter.format(Integer.toString( arraySum(archipelago.getIslands()[4])) ));
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

    public static void printCloud(LightBoard board, int cloudIndex) {
        //print blank cloud
        if (board.getCloud(cloudIndex)[0] == null)    //check is the cloud is empty
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

    public static void printCoinsSupply(LightBoard board) {
        //go at these coordinates
        System.out.print("\033[16;68H");

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
        System.out.print("\uD83D\uDCB0×" + board.getCoinsSupply());

        if (board.getCoinsSupply() < 10)
            System.out.print("\033[1B" + "\033[7C");
        else
            System.out.print("\033[1B" + "\033[6C");
    }

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

        System.out.print("\033[60D" + "\033[24C");

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
                                    CliColor.RESET);
                break;
            default:
                System.out.print("\033[11C");
        }

        System.out.print("\033[2C" + "\033[1B");
    }

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

    private static String numByColor(Student[] studentsArray, Color color) {
        int number = 0;

        for (Student s : studentsArray) {
            if (s.getColor() == color)
                number++;
        }
        return Integer.toString(number);
    }

    private static int arraySum(int[] array) {
        int sum = 0;
        for (int i : array)
            sum += i;

        return sum;
    }

}
