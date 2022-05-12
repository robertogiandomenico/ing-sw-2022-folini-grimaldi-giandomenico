package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.IntegerReader;

import java.util.List;
import java.util.Scanner;

public class CLI implements ViewInterface {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void askServerInfo() {
    /*  final String DEFAULT_PORT = "2807";
        final String DEFAULT_ADDRESS = "localhost";
        final String MIN_PORT = "1024";
        final String MAX_PORT = "65535";
        Map<String, String> serverInfo = new HashMap<>();
        boolean validInput;

        System.out.println("Game connection setup. Follow the instructions.");

        do {
            System.out.print("Enter the server address [" + DEFAULT_ADDRESS + "]: ");
            String address = scanner.nextLine();

            if (address.equals("")) {
                serverInfo.put("address", DEFAULT_ADDRESS);
                validInput = true;
            } else if (ClientController.isValidIpAddress(address)) {
                serverInfo.put("address", address);
                validInput = true;
            } else {
                System.out.println("Invalid address!");
                clearCLI();
                validInput = false;
            }
        } while (!validInput);

        do {
            System.out.print("Enter the server port [" + DEFAULT_PORT + "]: ");
            String port = scanner.nextLine();

            if (port.equals("")) {
                serverInfo.put("port", DEFAULT_PORT);
                validInput = true;
            } else {
                if (ClientController.isValidPort(port)) {
                    serverInfo.put("port", port);
                    validInput = true;
                } else {
                    System.out.println("Invalid port!");
                    validInput = false;
                }
            }
        } while (!validInput);   */

        //TODO: notifyObserver(obs -> obs.onUpdateServerInfo(serverInfo));
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
    public void askAssistant(List<Assistant> availableAssistants, List<Assistant> discardedAssistants) {

        if (!discardedAssistants.isEmpty()) {
            System.out.println("These are the assistant cards already chosen by the other player(s): ");
            for (Assistant a : discardedAssistants) {
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
        System.out.print("Enter the index of your next " + CliColor.BOLD + "action" + CliColor.RESET + ": ");

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
        int i = 0;

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
        System.out.print("Which " + CliColor.BOLD + "student" + CliColor.RESET + " would you like to move?" +
                         "Type the right color index: ");

        int colorIndex = IntegerReader.readInput();
        while (colorIndex<0 || colorIndex>availableColors.size()) {
            System.out.println(CliColor.RESET_LINE);
            System.out.print("Invalid color index. Try again: ");
            colorIndex = IntegerReader.readInput();
        }

        //TODO: notifyObserver(obs -> obs.onUpdateColor( availableColors.get(colorIndex) ));
    }

    @Override
    public void askPlace(int maxArchis) {
        System.out.println("Would you like to move the student in the " + CliColor.BOLD + "DINING ROOM" + CliColor.RESET +
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
    public void askCharacter(Board board) {
        boolean affordable;
        GameCharacter selectedCharacter;
        int archiIndex = -1;
        int studentNumber = 0;
        Color[] studColors = null;

        System.out.print("Enter the index of the " + CliColor.BOLD + "character" + CliColor.RESET + " you would like to choose: ");

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

        //switch case of all characters to ask the proper values
        switch (selectedCharacter.getName()) {
            case "Centaur":
                System.out.print("Enter the index of the " + CliColor.BOLD + "island" + CliColor.RESET + " to cancel its towers influence: ");
                    /*archiIndex = askArchipelago(maxArchis);
                     should I give to askCharacter(..) ALL the parameters from which I can choose?
                     */
        }
        //TODO: switch case of all characters to ask the proper values

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
        System.out.println("Enter the index of the " + CliColor.BBLUE + "cloud" + CliColor.RESET + " you would like to choose: ");

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
    public void printBoard(Board board) {
        for (int i = 0; i < board.getArchipelagos().size(); i++) {
            DisplayBoard.printArchipelago(board.getArchipelago(i), i);

            if (i == ((board.getArchipelagos().size())/2)-1)
                System.out.print("\n\n");
            else if (i == (board.getArchipelagos().size()-1))
                System.out.print("\n\n\n");
            else
                System.out.print("\033[4A" + "\033[2C");
        }

        for (int i = 0; i < board.getClouds().length; i++) {
            DisplayBoard.printCloud(board.getClouds()[i], i);

            if (i != (board.getClouds().length)-1)
                System.out.print("\033[3A" + "\033[2C");
            else
                System.out.print("\n\n\n");
        }

        for (int i = 0; i < board.getSelectedCharacters().length; i++) {
            DisplayBoard.printCharacter(board.getSelectedCharacters()[i], i);

            if (i != (board.getSelectedCharacters().length)-1)
                System.out.print("\n");
            else
                System.out.print("\n\n\n");
        }

        for (int i = 0; i < board.getPlayerBoards().length; i++) {
            DisplayBoard.printSchoolBoard(board.getPlayerBoards()[i]);

            if (i != (board.getPlayerBoards().length)-1)
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
}
