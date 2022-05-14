package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.IntegerReader;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;
import it.polimi.ingsw.view.utilities.lightclasses.LightCharacter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLI implements ViewInterface {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public Client askServerInfo() {
      final int DEFAULT_PORT = 2807;
        final String DEFAULT_ADDRESS = "localhost";
        final int MIN_PORT = 1024;
        final int MAX_PORT = 65535;
        int port = DEFAULT_PORT;
        String ip = DEFAULT_ADDRESS;
        boolean validInput = false;
        boolean firstTry = true;
        boolean notAnInt = false;
        boolean wrongPort = false;

        do {
            if(firstTry){
                System.out.println("ERROR: Invalid address! (remember the syntax xxx.xxx.xxx.xxx)");
            }
            System.out.println("Please enter the server address");
            System.out.print("Insert 'd' for the default value (" + DEFAULT_ADDRESS + "): ");
            String address = scanner.nextLine();

            if (address.equals("d") || address.equalsIgnoreCase("localhost")) {
                validInput = true;
            } else if (validateIP(address)) {
                ip = address;
                validInput = true;
            } else {
                System.out.println("Invalid address!");
                clearCLI();
                firstTry = false;
            }
        } while (!validInput);

        validInput = false;

        while (!validInput){
            System.out.println("Select a valid port between [" + MIN_PORT + ", " + MAX_PORT + "]");
            System.out.print("Insert 'd' for the default value (" + DEFAULT_PORT + "): ");
            if(notAnInt){
                notAnInt = false;
                System.out.println("\nERROR: Please insert only numbers or \"d\"");
                System.out.print("> ");
            }
            if(wrongPort){
                wrongPort = false;
                System.out.println("\nERROR: MIN_PORT = " + MIN_PORT + ", MAX_PORT = " + MAX_PORT);
                System.out.print("> ");
            }

            String input = scanner.nextLine();

            if(input.equalsIgnoreCase("d")){
                validInput = true;
            } else {
                try {
                    port = Integer.parseInt(input);
                    if(MIN_PORT <= port && port <= MAX_PORT){
                        validInput = true;
                    } else {
                        wrongPort = true;
                    }
                } catch (NumberFormatException e){
                    notAnInt = true;
                }
            }
            if (!validInput) {
                System.out.print(CliColor.CLEAR_ALL);
                System.out.flush();
            }
        }
        return new Client(ip, port, this);
    }

    private boolean validateIP(String address) {
        String zeroTo255 = "(\\d{1, 2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])";
        String IP_REGEX = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;
        return address.matches(IP_REGEX);
    }

    @Override
    public void askNickname() {
        System.out.print("Enter your nickname: ");
        String nickname = scanner.nextLine();

        //TODO: notifyObserver(obs -> obs.onUpdateNickname(nickname));
    }

    @Override
    public void askGameName() {
        System.out.print("Enter the game name: ");
        String gameName = scanner.nextLine();

        //TODO: notifyObserver(obs -> obs.onUpdateGameName(gameName));
    }


    @Override
    public void askGameMode() {
        System.out.println("Game difficulty:\n0 - Easy mode\n1 - Expert mode");
        System.out.print("Enter the game mode you would like to play: ");

        int gameMode = IntegerReader.readInput();
        while (gameMode!=0 && gameMode!=1) {
            System.out.println(CliColor.RESET_LINE);
            System.out.print("Invalid game mode. Try again: ");
            gameMode = IntegerReader.readInput();
        }

        //TODO: notifyObserver(obs -> obs.onUpdateGameMode(gameMode));
    }

    @Override
    public void askPlayerNumber() {
        System.out.print("How many players are going to play? [2/3]: ");

        int playerNumber = IntegerReader.readInput();
        while (playerNumber!=2 && playerNumber!=3) {
            System.out.println(CliColor.RESET_LINE);
            System.out.print("Match can only be started with 2 or 3 players. Try again: ");
            playerNumber = IntegerReader.readInput();
        }

        //TODO: notifyObserver(obs -> obs.onUpdatePlayerNumber(Integer.parseInt(playerNumber)));
    }

    @Override
    public void askWizard(List<Wizard> availableWizards) {
        for (int i = 0; i < availableWizards.size(); i++) {
            System.out.print("[ " + i + " | " + availableWizards.get(i).name() + "] \t");
        }
        System.out.print("\nEnter the index of the " + CliColor.BOLDPINK + "wizard" + CliColor.RESET + " you would like to choose: ");

        int wizardIndex = IntegerReader.readInput();
        while (wizardIndex<0 || wizardIndex>availableWizards.size()) {
            System.out.println(CliColor.RESET_LINE);
            System.out.print("Invalid wizard index. Try again: ");
            wizardIndex = scanner.nextInt();
        }

        //TODO: notifyObserver(obs -> obs.onUpdateWizard( availableWizard.get(wizardIndex) ));
    }

    @Override
    public void askAssistant(List<Assistant> availableAssistants, List<Assistant> chosenAssistants) {

        if (!chosenAssistants.isEmpty()) {
            System.out.println("These are the assistant cards already chosen by the other player(s): ");
            for (Assistant a : chosenAssistants) {
                System.out.println("[ " + a.name() + "  W:" + a.getWeight() + " M:" + a.getMaxMNSteps() + " ]");
            }
        }

        System.out.println("\nThus these are your available assistant cards that you can choose:");
        for (int i = 0; i < availableAssistants.size(); i++) {
            System.out.print("[ " + i + " | " + availableAssistants.get(i).name() + "  W:" + availableAssistants.get(i).getWeight() + " M:" + availableAssistants.get(i).getMaxMNSteps() +" ] \t");
        }
        System.out.print("Enter the index of the " + CliColor.BOLDYELLOW + "assistant" + CliColor.RESET + " you would like to choose: ");

        int assistantIndex = IntegerReader.readInput();
        while (assistantIndex<0 || assistantIndex>availableAssistants.size()) {
            System.out.println(CliColor.RESET_LINE);
            System.out.print("Invalid assistant index. Try again: ");
            assistantIndex = IntegerReader.readInput();
        }

        //TODO: notifyObserver(obs -> obs.onUpdateAssistant( availableAssistant.get(AssistantIndex) ));
    }

    @Override
    public void askAction(List<ActionType> possibleActions) {

        for (int i = 0; i < possibleActions.size(); i++) {
            System.out.println("[" + i + "| " + possibleActions.get(i).getAction());
        }
        System.out.print("Enter the index of your next " + CliColor.BOLDCYAN + "action" + CliColor.RESET + ": ");

        int actionIndex = IntegerReader.readInput();
        while (actionIndex<0 || actionIndex>possibleActions.size()) {
            System.out.println(CliColor.RESET_LINE);
            System.out.print("Invalid action index. Try again: ");
            actionIndex = IntegerReader.readInput();
        }

        //TODO: notifyObserver(obs -> obs.onUpdateAction( possibleActions.get(actionIndex) ));
    }

    @Override
    public void askStudent(List<Color> availableColors) {
        Color studColor;
        System.out.println("Select the color of the student you would like to move.");
        studColor = askColor(availableColors);

        //TODO: notifyObserver(obs -> obs.onUpdateColor( availableColors.get(colorIndex) ));
    }

    @Override
    public void askPlace(int maxArchis) {
        System.out.println("Would you like to move the student in the " + CliColor.BOLDWHITE + "DINING ROOM" + CliColor.RESET +
                           "? [y/n]: ");

        String diningRoom = scanner.nextLine();

        while (!diningRoom.equalsIgnoreCase("y") && !diningRoom.equalsIgnoreCase("n")) {
            System.out.println(CliColor.RESET_LINE);
            System.out.print("Invalid input. Try again [y/n]: ");
            diningRoom = scanner.nextLine();
        }

        if (diningRoom.equalsIgnoreCase("n")) {
            System.out.print("Enter the index of the " + CliColor.BOLDGREEN + "island" + CliColor.RESET + " you would like to place the student on : ");
            int archiIndex = askArchipelago(maxArchis);
        }

        //TODO: notifyObserver(obs -> obs.onUpdatePlace(place, archiIndex));
    }

    @Override
    public int askArchipelago(int maxArchis) {
        int archiIndex;

        archiIndex = IntegerReader.readInput();
        while (archiIndex<0 || archiIndex>maxArchis) {
            System.out.println(CliColor.RESET_LINE);
            System.out.print("Invalid island index. Try again: ");
            archiIndex = IntegerReader.readInput();
        }
        return archiIndex;
    }

    @Override
    public void askCharacter(LightBoard board) {
        boolean affordable;
        LightCharacter selectedCharacter;
        int archiIndex = -1;
        int studentNumber = 0;
        Color[] studColors = null;

        System.out.print("Enter the index of the " + CliColor.BOLDYELLOW + "character" + CliColor.RESET + " you would like to choose: ");

        int characterIndex = IntegerReader.readInput();

        do {
            while (characterIndex < 0 || characterIndex > 2) {
                System.out.println(CliColor.RESET_LINE);
                System.out.print("Invalid character index. Try again: ");
                characterIndex = IntegerReader.readInput();
            }

            if (board.getSelectedCharacters()[characterIndex].getCost() > board.getCurrentPlayerSchoolBoard().getPlayer().getCoins()) {
                affordable = false;
                System.out.println(CliColor.RESET_LINE);
                System.out.print("Cannot choose this character card since you do not have enough coins. Try again: ");
                characterIndex = IntegerReader.readInput();
            } else {
                affordable = true;
            }

        } while (!affordable);

        selectedCharacter = board.getSelectedCharacters()[characterIndex];
        int maxArchis = board.getArchipelagos().size();

        //switch case of all characters to ask the proper values
        switch (selectedCharacter.getName()) {
            case "Monk":
                System.out.println(CliColor.BOLDYELLOW + "Monk effect" + CliColor.RESET + "activated!");

                studentNumber = 1;
                studColors = new Color[studentNumber*2];
                System.out.println("Select the student you would like to take from the character card.");
                studColors[studentNumber-1] = askColor(getColorsByStudents(selectedCharacter.getStudents()));

                System.out.print("\nEnter the index of the " + CliColor.YELLOW + "island" + CliColor.RESET + " where you would like to move the student on: ");
                archiIndex = askArchipelago(maxArchis);
                break;

            case "Farmer":
                System.out.println(CliColor.BOLDYELLOW + "Farmer effect" + CliColor.RESET + " activated for your whole turn!");
                System.out.println("You take control of any professor if there's a tie between players' students in the dining rooms.");
                break;

            case "Herald":
                System.out.println(CliColor.BOLDYELLOW + "Herald effect" + CliColor.RESET + " activated!");
                System.out.print("\nEnter the index of the " + CliColor.YELLOW + "island" + CliColor.RESET + " you would like to be resolved: ");
                archiIndex = askArchipelago(maxArchis);
                break;

            case "MagicMailman":
                System.out.println(CliColor.BOLDYELLOW + "Magic Mailman effect" + CliColor.RESET + " activated!");
                System.out.println("You may now move " + CliColor.YELLOW + "mother nature" + CliColor.RESET + " up to 2 additional islands.");
                break;

            case "GrannyGrass":
                System.out.println(CliColor.BOLDYELLOW + "Granny Grass effect" + CliColor.RESET + " activated!");
                System.out.print("\nEnter the index of the " + CliColor.YELLOW + "island" + CliColor.RESET + " where you would like to put the " + CliColor.RED + "No Entry Tile" + CliColor.RESET + " on: ");
                archiIndex = askArchipelago(maxArchis);
                break;

            case "Centaur":
                System.out.println(CliColor.BOLDYELLOW + "Centaur effect" + CliColor.RESET + " activated!");
                System.out.print("Enter the index of the " + CliColor.YELLOW + "island" + CliColor.RESET + " to cancel its " + CliColor.BOLD + "towers" + CliColor.RESET + " influence: ");
                archiIndex = askArchipelago(maxArchis);
                break;

            case "Jester":
                System.out.println(CliColor.BOLDYELLOW + "Jester effect" + CliColor.RESET + " activated!");

                System.out.print("Enter the number of " + CliColor.BOLD + "students" + CliColor.RESET + " you would like to swap from the character card [up to 3]: ");
                studentNumber = IntegerReader.readInput();
                while (studentNumber<0 || studentNumber>3) {
                    System.out.print("Invalid student number. You can take up to 3 students. Try again: ");
                    studentNumber = IntegerReader.readInput();
                }

                studColors = new Color[studentNumber*2];
                System.out.println("Select the student(s) you would like to take from the character card.");
                for (int i = 0; i < studentNumber; i++) {
                    System.out.println((studentNumber-i) + " student(s) left.");
                    studColors[i] = askColor(getColorsByStudents(selectedCharacter.getStudents()));
                    System.out.print("\n");
                }

                System.out.println("\nSelect now the student(s) you would like to swap from your entrance.");
                for (int i = 0; i < studentNumber; i++) {
                    System.out.println((studentNumber - i) + " student(s) left.");
                    studColors[studentNumber+i] = askColor(getColorsByStudents(board.getCurrentPlayerSchoolBoard().getEntrance()));
                    System.out.print("\n");
                }

                break;

            case "Knight":
                System.out.println(CliColor.BOLDYELLOW + "Knight effect" + CliColor.RESET + " activated!");
                System.out.println("You now have 2 more points of influence on islands during this turn.");
                break;

            case "MushroomMan":
                System.out.println(CliColor.BOLDYELLOW + "Mushroom Man effect" + CliColor.RESET + " activated!");
                studentNumber = 1;
                studColors = new Color[studentNumber*2];
                System.out.println("Select a color. During this turn, that color add no influence.");
                studColors[studentNumber-1] = askColor(new ArrayList<>(Arrays.asList(Color.values())));
                break;

            case "Mistrel":
                System.out.println(CliColor.BOLDYELLOW + "Minstrel effect" + CliColor.RESET + " activated!");

                System.out.print("Enter the number of " + CliColor.BOLD + "students" + CliColor.RESET + " you would like to swap from the character card [up to 2]: ");
                studentNumber = IntegerReader.readInput();
                while (studentNumber<0 || studentNumber>2) {
                    System.out.print("Invalid student number. You can take up to 2 students. Try again: ");
                    studentNumber = IntegerReader.readInput();
                }

                studColors = new Color[studentNumber*2];
                System.out.println("Select the student(s) you would like to take from the character card.");
                for (int i = 0; i < studentNumber; i++) {
                    System.out.println((studentNumber-i) + " student(s) left.");
                    studColors[i] = askColor(getColorsByStudents(selectedCharacter.getStudents()));
                    System.out.print("\n");
                }

                System.out.println("\nSelect now the student(s) you would like to swap from your entrance.");
                for (int i = 0; i < studentNumber; i++) {
                    System.out.println((studentNumber - i) + " student(s) left.");
                    studColors[studentNumber+i] = askColor(getColorsByDR(board.getCurrentPlayerSchoolBoard().getDiningRoom()));
                    System.out.print("\n");
                }

                break;

            case "SpoiledPrincess":
                System.out.println(CliColor.BOLDYELLOW + "Spoiled Princess effect" + CliColor.RESET + "activated!");
                System.out.println("Select the student you would like to take from the character card and place in your dining room.");
                studentNumber = 1;
                studColors = new Color[studentNumber*2];
                studColors[studentNumber-1] = askColor(getColorsByStudents(selectedCharacter.getStudents()));
                break;

            case "Thief":
                System.out.println(CliColor.BOLDYELLOW + "Thief effect" + CliColor.RESET + "activated!");
                System.out.println("Select a color. Every player, including yourself, will return 3 students of that color from the dining room to the bag.");
                studentNumber = 1;
                studColors = new Color[studentNumber*2];
                studColors[studentNumber-1] = askColor(new ArrayList<>(Arrays.asList(Color.values())));
                break;

            default:
                break;
        }

        //TODO: notifyObserver(obs -> obs.onUpdateCharacter( selectedCharacter, archiIndex, studentNumber, studColors ));
    }

    @Override
    public void askMNSteps(int maxMNSteps) {
        System.out.println("Enter the number of steps " + CliColor.BBLUE + "Mother Nature" + CliColor.RESET + " will do: ");

        int mnSteps = IntegerReader.readInput();
        while (mnSteps == 0 || mnSteps > maxMNSteps) {
            System.out.println(CliColor.RESET_LINE);
            System.out.print("Invalid input. Mother Nature can do from 1 up to maximum " + maxMNSteps + " steps. Try again: ");
            mnSteps = IntegerReader.readInput();
        }

        //TODO: notifyObserver(obs -> obs.onUpdateMNSteps(mnSteps));
    }

    @Override
    public void askCloud(List<Integer> availableClouds) {
        System.out.println("Enter the index of the " + CliColor.BOLDCYAN + "cloud" + CliColor.RESET + " you would like to choose: ");

        int cloudIndex = IntegerReader.readInput();
        while (cloudIndex < 0 || cloudIndex > availableClouds.size()) {
            System.out.println(CliColor.RESET_LINE);
            System.out.print("Invalid cloud index. Try again: ");
            cloudIndex = IntegerReader.readInput();
        }

        //TODO: notifyObserver(obs -> obs.onUpdateCloud(cloudIndex));
    }

    @Override
    public void displayLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname) {
        clearCLI();

        if (nicknameAccepted && connectionSuccessful) {
            System.out.println("Hi, " + nickname + "! You are now connected to the server.");
        } else if (connectionSuccessful) {
            askNickname();
        } else if (nicknameAccepted) {
            System.out.println("Maximum number of players reached. Connection refused.\nEXIT");
            System.exit(1);
        } else {
            displayErrorAndExit("Could not contact server.");
        }
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayDisconnectionMessage(String disconnectedNickname, String message) {
        //FIXME:    inputThread.interrupt();
        System.out.println(disconnectedNickname + message);
        System.exit(-1);
    }

    @Override
    public void displayErrorAndExit(String message) {
        //FIXME: inputThread.interrupt();

        System.out.println("\nERROR: " + message);
        System.out.println("EXIT.");
        System.exit(1);
    }

    @Override
    public void printBoard(LightBoard board) {
        // to resize the console window     length:48  width:125
        System.out.print("\033[8;48;125t");

        //print all the archipelagos clockwise
        for (int i = 0; i < board.getArchipelagos().size(); i++) {
            DisplayBoard.printArchipelago(board.getArchipelagos().get(i), i);

            if (i < (board.getArchipelagos().size()/2 - 1))
                System.out.print("\033[4A" + "\033[1C");

            if (i == (board.getArchipelagos().size()/2 - 1))
                System.out.print("\033[2B" + "\033[32D");

            if (i > (board.getArchipelagos().size()/2 - 1)  &&  i != (board.getArchipelagos().size() - 2))
                System.out.print("\033[32D" + "\033[1D" + "\033[32D" + "\033[4A");

            if (i == (board.getArchipelagos().size() - 2))
                System.out.print("\033[2B" + CliColor.RESET_LINE);
        }

        //print all the clouds
        for (int i = 0; i < board.getCloudsNumber(); i++) {
            DisplayBoard.printCloud(board, i);

            if (i < (board.getCloudsNumber())-1)
                System.out.print("\033[3A" + "\033[2C");
        }

        if (board.getSelectedCharacters() != null) {  //if it's expert mode
            //print coins supply
            DisplayBoard.printCoinsSupply(board);

            System.out.print("\033[2B" + CliColor.RESET_LINE);

            //print all the characters
            for (int i = 0; i < board.getSelectedCharacters().length; i++) {
                DisplayBoard.printCharacter(board.getSelectedCharacters()[i], i);

                if (i < (board.getSelectedCharacters().length) - 1)
                    System.out.print("\n");
                else
                    System.out.print("\n\n");
            }
        }

        //print all the schoolboards
        for (int i = 0; i < board.getSchoolBoards().size(); i++) {
            DisplayBoard.printSchoolBoard(board.getSchoolBoards().get(i), board.getSelectedCharacters() != null);

            if (i < (board.getSchoolBoards().size() -1 ))
                System.out.print("\033[7A" + "\033[4C");
            else
                System.out.print("\n\n");
        }
    }

    @Override
    public void displayEndgameResult(String winner) {
        System.out.println("Match ended.");
        System.out.println(CliColor.BOLD + winner + CliColor.RESET + " WINS!");

        System.exit(0);
    }

    public void clearCLI() {
        System.out.println(CliColor.CLEAR_ALL);
        System.out.flush();
    }

    public List<Color> getColorsByStudents(Student[] studentsArray) {
        List<Color> availableColors = new ArrayList<>();

        for (Student s : studentsArray) {
            if (!availableColors.contains(s.getColor()))
                availableColors.add(s.getColor());
        }

        return availableColors;
    }

    public List<Color> getColorsByDR(int[] drArray) {
        List<Color> availableColors = new ArrayList<>();

        for (int i = 0; i < drArray.length; i++) {
            if (drArray[i] > 0) {
                switch (i) {
                    case 0:
                        availableColors.add(Color.GREEN);
                        break;
                    case 1:
                        availableColors.add(Color.RED);
                        break;
                    case 2:
                        availableColors.add(Color.YELLOW);
                        break;
                    case 3:
                        availableColors.add(Color.PINK);
                        break;
                    case 4:
                        availableColors.add(Color.BLUE);
                        break;
                    default: break; //not reachable
                }
            }
        }

        return availableColors;
    }

    public Color askColor(List<Color> availableColors) {
        int i = 0;
        Color selectedColor;

        if (availableColors.contains(Color.GREEN)) {
            System.out.println(CliColor.GREEN + "[" + i + " - GREEN]");
            i++;
        }
        if (availableColors.contains(Color.RED)) {
            System.out.println(CliColor.GREEN + "[" + i + " - RED]");
            i++;
        }
        if (availableColors.contains(Color.YELLOW)) {
            System.out.println(CliColor.YELLOW + "[" + i + " - YELLOW]");
            i++;
        }
        if (availableColors.contains(Color.PINK)) {
            System.out.println(CliColor.PINK + "[" + i + " - PINK]");
            i++;
        }
        if (availableColors.contains(Color.BLUE)) {
            System.out.println(CliColor.BLUE + "[" + i + " - BLUE]");
        }

        System.out.println(CliColor.RESET + "\n");
        System.out.print("Which " + CliColor.BOLDWHITE + "color" + CliColor.RESET + " would you like to select?" +
                "Type the right color index: ");

        int colorIndex = IntegerReader.readInput();
        while (colorIndex < 0 || colorIndex > availableColors.size()) {
            System.out.println(CliColor.RESET_LINE);
            System.out.print("Invalid color index. Try again: ");
            colorIndex = IntegerReader.readInput();
        }

        return availableColors.get(colorIndex);
    }

}
