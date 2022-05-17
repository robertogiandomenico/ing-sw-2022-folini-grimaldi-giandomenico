package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.clientMessages.*;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.IntegerReader;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CLI implements ViewInterface {
    private final Scanner scanner = new Scanner(System.in);
    private Client client;

    public static void main(String[] args) {
        new CLI().start();
    }

    private void start() {
        System.out.print(CliColor.CLEAR_ALL);
        System.out.println("  ✹    .      .     * ███████╗ ██████╗  ██╗  █████╗  ███╗   ██╗ ████████╗ ██╗   ██╗ ███████╗. 　　 　　　　　. 　\n" +
                           "         ✦     *      ██╔════╝ ██╔══██╗ ██║ ██╔══██╗ ████╗  ██║ ╚══██╔══╝ ╚██╗ ██╔╝ ██╔════╝ ⋆     ˚  *      .\n" +
                           "    .    *    .    ✹  █████╗   ██████╔╝ ██║ ███████║ ██╔██╗ ██║    ██║     ╚████╔╝  ███████╗   ·　   ✦     *  \n" +
                           "  .    *              ██╔══╝   ██╔══██╗ ██║ ██╔══██║ ██║╚██╗██║    ██║      ╚██╔╝   ╚════██║　 ✹ .   ·  　   ✧\n" +
                           "      .     ✦       * ███████╗ ██║  ██║ ██║ ██║  ██║ ██║ ╚████║    ██║       ██║    ███████║ ✦ 　 　       ·　 \n" +
                           "   ✹          .      .╚══════╝ ╚═╝  ╚═╝ ╚═╝ ╚═╝  ╚═╝ ╚═╝  ╚═══╝    ╚═╝       ╚═╝    ╚══════╝  　　 *　　✹　　　 ˚\n");
        boolean socketError = true;
        while (socketError){
            try {
                client = askServerInfo();
                client.init();
                socketError = false;
            } catch (IOException ignored){}
        }
    }

    @Override
    public Client askServerInfo() {
      final int DEFAULT_PORT = 2807;
        final String DEFAULT_ADDRESS = "127.0.0.1";
        final int MIN_PORT = 1024;
        final int MAX_PORT = 65535;
        int port = DEFAULT_PORT;
        String ip = DEFAULT_ADDRESS;
        boolean validInput = false;
        boolean firstTry = true;
        boolean notAnInt = false;
        boolean wrongPort = false;

        do {
            if(!firstTry){
                System.out.println(CliColor.RED + "ERROR: Invalid address! (remember the syntax xxx.xxx.xxx.xxx)" + CliColor.RESET);
            }
            System.out.println("Please enter the server address");
            System.out.print("Insert 'd' for the default value (" + DEFAULT_ADDRESS + "): ");
            String address = scanner.nextLine();

            if (address.equals("d") || address.equalsIgnoreCase("localhost") || address.equals(DEFAULT_ADDRESS)) {
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
        String zeroTo255 = "([01]?\\d{1,2}|2[0-4]\\d|25[0-5])";
        String IP_REGEX = "^(" + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + ")$";
        return address.matches(IP_REGEX);
    }

    @Override
    public void askNickname() {
        System.out.print(CliColor.CLEAR_ALL);
        System.out.print("Enter your nickname: ");
        String nickname = scanner.nextLine();

        client.sendMsgToServer(new NicknameReply(nickname));
    }

    @Override
    public void askGameName() {
        System.out.print(CliColor.CLEAR_ALL);
        System.out.print("Enter the game name: ");
        String gameName = scanner.nextLine();

        client.sendMsgToServer(new GameNameReply(gameName));
    }


    @Override
    public void askGameMode() {
        System.out.print(CliColor.CLEAR_ALL);
        System.out.println("Game difficulty:\n0 - Easy mode\n1 - Expert mode");
        System.out.print("Enter the game mode you would like to play: ");

        //int gameMode = IntegerReader.readInput();
        String input = scanner.nextLine();
        int gameMode;
        try {
            gameMode = Integer.parseInt(input);
            if (gameMode!=0 && gameMode!=1) {
                askGameMode();
            } else {
                client.sendMsgToServer(new GameModeReply(gameMode == 1));
            }
        } catch (NumberFormatException e){
            askGameMode();
        }
    }

    @Override
    public void askPlayerNumber() {
        System.out.print(CliColor.CLEAR_ALL);
        System.out.print("How many players are going to play? [2/3]: ");

        //int playerNumber = IntegerReader.readInput();
        String input = scanner.nextLine();
        int playerNumber;
        try {
            playerNumber = Integer.parseInt(input);
            if (playerNumber!=2 && playerNumber!=3) {
                askPlayerNumber();
            } else {
                client.sendMsgToServer(new PlayerNumberReply(playerNumber));
            }
        } catch (NumberFormatException e){
            askPlayerNumber();
        }
    }

    @Override
    public void askWizard(List<Wizard> availableWizards) {
        System.out.print(CliColor.CLEAR_ALL);
        for (int i = 0; i < availableWizards.size(); i++) {
            System.out.print("[ " + (i+1) + " | " + availableWizards.get(i).name() + "] \t");
        }
        System.out.print("\nEnter the index of the " + CliColor.BOLDPINK + "wizard" + CliColor.RESET + " you would like to choose: ");

        String input = scanner.nextLine();
        int wizardIndex;
        try {
            wizardIndex = Integer.parseInt(input);
            if (wizardIndex < 1 || wizardIndex > availableWizards.size()) {
                askWizard(availableWizards);
            } else {
                client.sendMsgToServer(new WizardReply(availableWizards.get(wizardIndex-1)));
            }
        } catch (NumberFormatException e){
            askWizard(availableWizards);
        }
    }

    @Override
    public void askAssistant(List<Assistant> availableAssistants, List<Assistant> discardedAssistants) {
        System.out.print(CliColor.CLEAR_ALL);

        if (!discardedAssistants.isEmpty()) {
            System.out.println("The " + CliColor.RED + "red cards" + CliColor.RESET +" are the assistant cards already chosen by the other player(s) so you can not play them in this round!");
            System.out.println("Thus the white ones are your available assistant cards that you can choose between: \n");
        } else {
            System.out.println("You are the first player of this round!"+
                    "\nThus you can choose any assistant card you want from your hand: \n");
        }

        CliColor color;
        for (int i = 0; i < availableAssistants.size(); i++) {
            color = discardedAssistants.contains(availableAssistants.get(i)) ? CliColor.RED : CliColor.WHITE;
            System.out.println(color + "[ " + (i+1) + " | " + availableAssistants.get(i).name() + "  W:" + availableAssistants.get(i).getWeight() + " M:" + availableAssistants.get(i).getMaxMNSteps() +" ]" + CliColor.RESET);
        }
        System.out.print("\nEnter the index of the " + CliColor.BOLDYELLOW + "assistant" + CliColor.RESET + " you would like to choose: ");


        String input = scanner.nextLine();
        int assistantIndex;
        try {
            assistantIndex = Integer.parseInt(input);
            if (assistantIndex <= 0 || assistantIndex > availableAssistants.size() || discardedAssistants.contains(availableAssistants.get(assistantIndex-1))) {
                askAssistant(availableAssistants, discardedAssistants);
            } else {
                client.sendMsgToServer(new ChooseAssistantReply(availableAssistants.get(assistantIndex-1)));
            }
        } catch (NumberFormatException e){
            askAssistant(availableAssistants, discardedAssistants);
        }
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
