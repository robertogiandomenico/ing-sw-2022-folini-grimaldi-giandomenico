package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.ViewInterface;

import java.util.List;
import java.util.Scanner;

public class CLI implements ViewInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final String STRING_STANDARD = "^[a-zA-Z0-9_\\s]{1,15}$";

    @Override
    public void askServerInfo() {

    }

    @Override
    public void askNickname() {
        System.out.print("Enter your nickname: ");

        try {
            String nickname = scanner.nextLine();
            while (!nickname.matches(STRING_STANDARD)) {
                System.out.println(CliColor.RESET_LINE);
                System.out.println("Invalid nickname. Try again: ");
                nickname = scanner.nextLine();
            }
            //TODO: notifyObserver(obs -> obs.onUpdateNickname(nickname));
        } catch (Exception e) {
            System.out.println("An error occured while reading the nickname.");
        }
    }

    @Override
    public void askGameName() {
        System.out.print("Enter the game name: ");

        try {
            String gameName = scanner.nextLine();
            while (!gameName.matches(STRING_STANDARD)) {
                System.out.println(CliColor.RESET_LINE);
                System.out.println("Invalid game name. Try again: ");
                gameName = scanner.nextLine();
            }
            //TODO: notifyObserver(obs -> obs.onUpdateGameName(gameName));
        } catch (Exception e) {
            System.out.println("An error occured while reading the game name.");
        }
    }


    @Override
    public void askGameMode() {
        System.out.println("Game difficulty:\n0 - Easy mode\n1 - Expert mode");
        System.out.print("Enter the game mode you would like to play: ");

        try {
            int gameMode = scanner.nextInt();
            while (gameMode != 0 && gameMode !=1) {
                System.out.println(CliColor.RESET_LINE);
                System.out.print("Invalid game mode. Try again: ");
                gameMode = scanner.nextInt();
            }
            //TODO: notifyObserver(obs -> obs.onUpdateGameMode(gameMode));
        } catch (Exception e) {
            System.out.println("An error occured while reading the game mode.");
        }
    }

    @Override
    public void askPlayerNumber() {
        int playerNumber;

        System.out.print("How many players are going to play? [2/3]: ");

        try {
            playerNumber = scanner.nextInt();
            while (playerNumber != 2 && playerNumber !=3) {
                System.out.println(CliColor.RESET_LINE);
                System.out.print("Match can only be started with 2 or 3 players. Try again: ");
                playerNumber = scanner.nextInt();
            }
            //TODO: notifyObserver(obs -> obs.onUpdatePlayerNumber(playerNumber));
        } catch (Exception e) {
            System.out.println("An error occured while reading the player number.");
        }
    }

    @Override
    public void askWizard(List<Wizard> availableWizards) {
        for (int i = 0; i < availableWizards.size(); i++) {
            System.out.print("[ " + i + " | " + availableWizards.get(i).name() + "] \t");
        }
        System.out.print("\nEnter the index of the " + CliColor.BOLDPINK + "wizard" + CliColor.RESET + " you would like to choose: ");

        try {
            int wizardIndex = scanner.nextInt();
            while (wizardIndex<0 || wizardIndex>availableWizards.size()) {
                System.out.println(CliColor.RESET_LINE);
                System.out.print("Invalid wizard index. Try again: ");
                wizardIndex = scanner.nextInt();
            }
            //TODO: notifyObserver(obs -> obs.onUpdateWizard(wizardIndex));
        } catch (Exception e) {
            System.out.println("An error occured while reading the index of the wizard.");
        }
    }

    @Override
    public void askAssistant(List<Assistant> availableAssistants, List<Assistant> discardedAssistants) {
        System.out.println("These are the assistant cards already chosen by the other player(s): ");
        for (Assistant a : discardedAssistants) {
            System.out.println("[ " + a.name() + "  W:" + a.getWeight() + " M:" + a.getMaxMNSteps() +" ]");
        }

        System.out.println("\nThus these are your available assistant cards that you can choose:");
        for (int i = 0; i < availableAssistants.size(); i++) {
            System.out.print("[ " + i + " | " + availableAssistants.get(i).name() + "  W:" + availableAssistants.get(i).getWeight() + " M:" + availableAssistants.get(i).getMaxMNSteps() +" ] \t");
        }
        System.out.print("Enter the index of the " + CliColor.BOLD + "assistant" + CliColor.RESET + " you would like to choose: ");

        try {
            int assistantIndex = scanner.nextInt();
            while (assistantIndex<0 || assistantIndex>availableAssistants.size()) {
                System.out.println(CliColor.RESET_LINE);
                System.out.print("Invalid assistant index. Try again: ");
                assistantIndex = scanner.nextInt();
            }

            //TODO: notifyObserver(obs -> obs.onUpdateWizard(wizardIndex));
        } catch (Exception e) {
            System.out.println("An error occured while reading the index of the wizard.");
        }
    }

    @Override
    public void askAction(List<ActionType> possibleActions) {

        for (int i = 0; i < possibleActions.size(); i++) {
            System.out.println("[" + i + "| " + possibleActions.get(i).getAction());
        }
        System.out.print("Enter the index of your next " + CliColor.BOLD + "action" + CliColor.RESET + ": ");

        try {
            int actionIndex = scanner.nextInt();
            while (actionIndex<0 || actionIndex>possibleActions.size()) {
                System.out.println(CliColor.RESET_LINE);
                System.out.print("Invalid action index. Try again: ");
                actionIndex = scanner.nextInt();
            }

            //TODO: notifyObserver(obs -> obs.onUpdateAction(actionIndex));
        } catch (Exception e) {
            System.out.println("An error occured while reading the index of the action.");
        }

    }

    @Override
    public void askStudent(List<Color> availableColors) {
        System.out.println(CliColor.GREEN  + "0 - GREEN\n" +
                           CliColor.RED    + "1 - RED\n" +
                           CliColor.YELLOW + "2 - YELLOW\n" +
                           CliColor.PINK   + "3 - PINK\n" +
                           CliColor.BLUE   + "4 - BLUE\n" + CliColor.RESET);

        System.out.print("Which " + CliColor.BOLD + "student" + CliColor.RESET + " would you like to move? Type the right index: ");

        try {
            int colorIndex = scanner.nextInt();
            while (colorIndex<0 || colorIndex>4) {
                System.out.println(CliColor.RESET_LINE);
                System.out.print("Invalid color index. Try again: ");
                colorIndex = scanner.nextInt();
            }

            //TODO: notifyObserver(obs -> obs.onUpdateStudent(colorIndex));
        } catch (Exception e) {
            System.out.println("An error occured while reading the index of the color.");
        }

    }

    @Override
    public void askPlace(int maxArchis) {
        System.out.println("Would you like to move the student in the " + CliColor.BOLD + "DINING ROOM" + CliColor.RESET +
                           "? [y/n]: ");

        try {
            String diningRoom = scanner.nextLine();

            while (!diningRoom.equalsIgnoreCase("y") && !diningRoom.equalsIgnoreCase("n")) {
                System.out.println(CliColor.RESET_LINE);
                System.out.print("Invalid input. Try again [y/n]: ");
                diningRoom = scanner.nextLine();
            }

            if (diningRoom.equalsIgnoreCase("n")) {
                System.out.print("Enter the index of the " + CliColor.BOLDGREEN + "island" + CliColor.RESET + " you would like to place the student on : ");

                try {
                    int archiIndex = scanner.nextInt();
                    while (archiIndex<0 || archiIndex>maxArchis) {
                        System.out.println(CliColor.RESET_LINE);
                        System.out.print("Invalid island index. Try again: ");
                        archiIndex = scanner.nextInt();
                    }

                } catch (Exception e) {
                    System.out.println("An error occured while reading the index of the island.");
                }
            }

            //TODO: notifyObserver(obs -> obs.onUpdatePlace(place));
        } catch (Exception e) {
            System.out.println("An error occured while reading the index of the character.");
        }

    }

    @Override
    public void askCharacter(List<GameCharacter> availableCharacters) {
        System.out.print("Enter the index of the " + CliColor.BOLDYELLOW + "character" + CliColor.RESET + " you would like to choose: ");

        try {
            int characterIndex = scanner.nextInt();
            while (characterIndex<0 || characterIndex>3) {
                System.out.println(CliColor.RESET_LINE);
                System.out.print("Invalid character index. Try again: ");
                characterIndex = scanner.nextInt();
            }

            //TODO: notifyObserver(obs -> obs.onUpdateCharacter(characterIndex));
        } catch (Exception e) {
            System.out.println("An error occured while reading the index of the character.");
        }
    }

    @Override
    public void askMNSteps(int maxMNSteps) {
        System.out.println("Enter the number of steps " + CliColor.BBLUE + "Mother Nature" + CliColor.RESET + " will do: ");

        try {
            int mnSteps = scanner.nextInt();
            while (mnSteps==0 || mnSteps>maxMNSteps) {
                System.out.println(CliColor.RESET_LINE);
                System.out.print("Invalid input. Mother Nature can do from 1 up to maximum " + maxMNSteps + " steps. Try again: ");
                mnSteps = scanner.nextInt();
            }
            //TODO: notifyObserver(obs -> obs.onUpdateMNSteps(mnSteps));
        } catch (Exception e) {
            System.out.println("An error occured while reading the number of steps.");
        }
    }

    @Override
    public void askCloud(List<Integer> availableClouds) {
        System.out.println("Enter the index of the " + CliColor.BBLUE + "cloud" + CliColor.RESET + " you would like to choose: ");

        try {
            int cloudIndex = scanner.nextInt();
            while (cloudIndex<0 || cloudIndex>availableClouds.size()) {
                System.out.println(CliColor.RESET_LINE);
                System.out.print("Invalid cloud index. Try again: ");
                cloudIndex = scanner.nextInt();
            }
            //TODO: notifyObserver(obs -> obs.onUpdateCloud(cloudIndex));
        } catch (Exception e) {
            System.out.println("An error occured while reading the index of the cloud.");
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
    public void displayBoard(Board board) {

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
