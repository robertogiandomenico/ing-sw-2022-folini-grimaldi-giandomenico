package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messages.clientMessages.GameModeReply;
import it.polimi.ingsw.network.messages.clientMessages.PlayerNumberReply;
import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

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

    @FXML
    private void initialize() {
        disableNPlayers();
        easyMode.setToggleGroup(gameMode);
        expertMode.setToggleGroup(gameMode);
        n2Players.setToggleGroup(nPlayers);
        n3Players.setToggleGroup(nPlayers);
    }

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

    @FXML
    private void exit() {
        gui.closeWindow(gui.getStage());
        System.exit(0);
    }

    private boolean isGameModeEnabled() {
        return !easyMode.isDisable() || !expertMode.isDisable();
    }

    private boolean isNPlayersEnabled() {
        return !n2Players.isDisable() || !n3Players.isDisable();
    }

    private void disableGameMode() {
        easyMode.setDisable(true);
        expertMode.setDisable(true);
    }

    private void enableNPlayers() {
        n2Players.setDisable(false);
        n3Players.setDisable(false);
    }

    private void disableNPlayers() {
        n2Players.setDisable(true);
        n3Players.setDisable(true);
    }

    private int getPlayerNumber() {
        return Integer.parseInt(((RadioButton)nPlayers.getSelectedToggle()).getText());
    }

    /**
     * Sets the GUI.
     *
     * @param gui                 a GUI.
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
