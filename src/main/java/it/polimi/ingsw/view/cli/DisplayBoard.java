package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.GrannyGrassEffect;
import it.polimi.ingsw.model.effects.MonkEffect;

public class DisplayBoard {

    public static void printSchoolBoard(SchoolBoard sB) {
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
        for (int i = 0; i < 5; i++) {
            System.out.print(CliColor.BOLDYELLOW + "\033[2C" + "©" + "\033[2C" + "©" + "\033[2C" + "©");
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
        System.out.println("\033[13C" + "\033[5A");

        //print professor table
        if (sB.isProfessorPresent(0)) {
            System.out.print(CliColor.GREEN + "■");
            System.out.print("\033[1B" + "\033[1D");
        } else System.out.print("\033[1B");
        if (sB.isProfessorPresent(1)) {
            System.out.print(CliColor.RED + "■");
            System.out.print("\033[1B" + "\033[1D");
        } else System.out.print("\033[1B");
        if (sB.isProfessorPresent(2)) {
            System.out.print(CliColor.BOLDYELLOW + "■");
            System.out.print("\033[1B" + "\033[1D");
        } else System.out.print("\033[1B");
        if (sB.isProfessorPresent(3)) {
            System.out.print(CliColor.PINK + "■");
            System.out.print("\033[1B" + "\033[1D");
        } else System.out.print("\033[1B");
        if (sB.isProfessorPresent(4)) {
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
        System.out.print("\uD83D\uDCB0×" + sB.getPlayer().getCoins());

        if (sB.getPlayer().getCoins() < 10)
            System.out.println("\033[1B" + "\033[4C");
        else
            System.out.println("\033[1B" + "\033[3C");

    }

    public static void printArchipelago(Archipelago archipelago, int archiIndex) {
        //print blank archipelago
        if (archipelago.isMNPresent())
            System.out.print(CliColor.YELLOW);
        System.out.print("╒═══════════╕" + "\033[1B" + "\033[13D");
        System.out.print("│           │" + "\033[1B" + "\033[13D");
        System.out.print("│           │" + "\033[1B" + "\033[13D");
        System.out.print("│           │" + "\033[1B" + "\033[13D");
        System.out.print("╰───────────╯" + CliColor.RESET);

        System.out.print("\033[4A" + "\033[8D");

        //print archiIndex
        if (archiIndex < 10)
            System.out.print(CliColor.BOLD + " " + archiIndex + " " + CliColor.RESET);
        else
            System.out.print("\033[D" + CliColor.BOLD + " " + archiIndex + " " + CliColor.RESET);

        System.out.print("\033[1B" + "\033[6D");

        //print students in archipelago
        System.out.print(CliColor.BGREEN + Integer.toString(archipelago.getTotalStudents(Color.GREEN)));
        System.out.print("\033[1C");
        System.out.print(CliColor.BRED + Integer.toString(archipelago.getTotalStudents(Color.RED)));
        System.out.print("\033[1C");
        System.out.print(CliColor.BYELLOW + Integer.toString(archipelago.getTotalStudents(Color.YELLOW)));
        System.out.print("\033[1C");
        System.out.print(CliColor.BPINK + Integer.toString(archipelago.getTotalStudents(Color.PINK)));
        System.out.print("\033[1C");
        System.out.print(CliColor.BBLUE + Integer.toString(archipelago.getTotalStudents(Color.BLUE)) + CliColor.RESET);

        System.out.print("\033[2B" + "\033[9D");

        //print no entry tile
        if (archipelago.isNoEntryTilePresent())
            System.out.print(CliColor.BOLDRED + "X" + CliColor.RESET);
        else
            System.out.print("\033[1C");

        System.out.print("\033[3C");

        //print mother nature
        if (archipelago.isMNPresent())
            System.out.print(CliColor.YELLOW + "M" + CliColor.RESET);
        else
            System.out.print("\033[1C");

        System.out.print("\033[3C");

        //print tower color
        if (archipelago.getTowerColor() == TowerColor.BLACK)
            System.out.print(CliColor.BBLACK + "B" + CliColor.RESET);
        else if (archipelago.getTowerColor() == TowerColor.GREY)
            System.out.print(CliColor.BGREY + "G" + CliColor.RESET);
        else if (archipelago.getTowerColor() == TowerColor.WHITE)
            System.out.print(CliColor.BWHITE + "W" + CliColor.RESET);
        else
            System.out.print("\033[1C");

        System.out.print("\033[1B" + "\033[1D");

        //print size of the archipelago
        System.out.print("×" + archipelago.getIslands().size());

        System.out.print("\033[1C");
    }

    public static void printCloud(Cloud cloud, int cloudIndex) {
        //print blank cloud
        if (cloud.isEmpty())
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
        System.out.print(CliColor.BGREEN + numByColor(cloud.get(), Color.GREEN));
        System.out.print("\033[1C");
        System.out.print(CliColor.BRED + numByColor(cloud.get(), Color.RED));
        System.out.print("\033[1C");
        System.out.print(CliColor.BYELLOW + numByColor(cloud.get(), Color.YELLOW));
        System.out.print("\033[1B" + "\033[4D");
        System.out.print(CliColor.BPINK + numByColor(cloud.get(), Color.PINK));
        System.out.print("\033[1C");
        System.out.print(CliColor.BBLUE + numByColor(cloud.get(), Color.BLUE) + CliColor.RESET);

        System.out.print("\033[3C" + "\033[1B");
    }

    public static void printCharacter(GameCharacter character, int charIndex) {
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
            System.out.print(CliColor.BOLDRED + "X" + CliColor.RESET + ":" + ((GrannyGrassEffect)character.getEffect()).getNoEntryTiles());
        else
            System.out.print("\033[3C");

        System.out.print("\033[2C");

        //print students, only if available
        switch (character.getName()) {
            case("Monk"):
            case("Jester"):
            case("SpoiledPrincess"):
                System.out.print("S:" +
                                 CliColor.BGREEN + numByColor(((MonkEffect)character.getEffect()).getStudents(), Color.GREEN) +
                     "\033[1C" + CliColor.BRED + numByColor(((MonkEffect)character.getEffect()).getStudents(), Color.RED) +
                     "\033[1C" + CliColor.BYELLOW + numByColor(((MonkEffect)character.getEffect()).getStudents(), Color.YELLOW) +
                     "\033[1C" + CliColor.BPINK + numByColor(((MonkEffect)character.getEffect()).getStudents(), Color.PINK) +
                     "\033[1C" + CliColor.BBLUE + numByColor(((MonkEffect)character.getEffect()).getStudents(), Color.BLUE) +
                                 CliColor.RESET);
                break;
            default:
                System.out.print("\033[11C");
        }

        System.out.print("\033[2C" + "\033[1B");
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
