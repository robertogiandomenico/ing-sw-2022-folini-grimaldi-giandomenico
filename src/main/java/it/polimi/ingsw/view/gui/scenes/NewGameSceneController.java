package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messages.clientMessages.GameModeReply;
import it.polimi.ingsw.network.messages.clientMessages.PlayerNumberReply;
import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * This class represents the controller for the creation of a new game scene.
 */
public class NewGameSceneController implements SceneControllerInterface {
    @FXML
    private RadioButton easyMode;
    @FXML
    private RadioButton expertMode;
    @FXML
    private RadioButton n2Players;
    @FXML
    private RadioButton n3Players;
    @FXML
    private ToggleGroup gameMode;
    @FXML
    private ToggleGroup nPlayers;
    private GUI gui;

    /**
     * Initializes the scene and divides the buttons in Toggle groups.
     */
    @FXML
    private void initialize() {
        disableNPlayers();
        easyMode.setToggleGroup(gameMode);
        expertMode.setToggleGroup(gameMode);
        n2Players.setToggleGroup(nPlayers);
        n3Players.setToggleGroup(nPlayers);
    }

    /**
     * Enables different fields as the user proceeds with the creation of the game
     * and sends the information to the server.
     */
    @FXML
    private void next() {
        if (isGameModeEnabled() && !isNPlayersEnabled()) {
            gui.getClient().sendMsgToServer(new GameModeReply(expertMode.isSelected()));
            disableGameMode();
            enableNPlayers();
            return;
        }

        if (!isGameModeEnabled() && isNPlayersEnabled()) {
            gui.getClient().sendMsgToServer(new PlayerNumberReply(getPlayerNumber()));
            disableNPlayers();
        }
    }

    /**
     * Closes the window.
     */
    @FXML
    private void exit() {
        gui.closeWindow(gui.getStage());
    }

    /**
     * States whether the buttons for the selection of the game mode are
     * enabled or not.
     *
     * @return          a boolean whose value is:
     *                  <p>
     *                  -{@code true} if the player has to choose the game mode;
     *                  </p> <p>
     *                  -{@code false} otherwise.
     *                  </p>
     */
    private boolean isGameModeEnabled() {
        return !easyMode.isDisable() || !expertMode.isDisable();
    }

    /**
     * States whether the buttons for the selection of the number of players
     * are enabled or not.
     *
     * @return          a boolean whose value is:
     *                  <p>
     *                  -{@code true} if the player has to choose the number of players;
     *                  </p> <p>
     *                  -{@code false} otherwise.
     *                  </p>
     */
    private boolean isNPlayersEnabled() {
        return !n2Players.isDisable() || !n3Players.isDisable();
    }

    /**
     * Disables the buttons for the selection of the game mode.
     */
    private void disableGameMode() {
        easyMode.setDisable(true);
        expertMode.setDisable(true);
    }

    /**
     * Enables the buttons for the selection of the number of players.
     */
    private void enableNPlayers() {
        n2Players.setDisable(false);
        n3Players.setDisable(false);
        n2Players.requestFocus();
    }

    /**
     * Disables the buttons for the selection of the number of players.
     */
    private void disableNPlayers() {
        n2Players.setDisable(true);
        n3Players.setDisable(true);
    }

    /**
     * Gets the selected number of players for the game.
     *
     * @return          the selected number of players.
     */
    private int getPlayerNumber() {
        return Integer.parseInt(((RadioButton)nPlayers.getSelectedToggle()).getText());
    }

    /**
     * Sets the GUI.
     *
     * @param gui       a GUI.
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
