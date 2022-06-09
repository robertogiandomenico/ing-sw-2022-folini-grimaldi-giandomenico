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
    private boolean validGameMode = false;
    private boolean validNPlayers = false;
    private boolean expertModeBool = false;
    private boolean num3OfPlayers = false;
    private GUI gui;

    @FXML
    private void initialize() {
        n2Players.setDisable(true);
        n3Players.setDisable(true);
    }

    @FXML
    private void next() {
        if(isGameModeEnabled() && !isNPlayersEnabled()) {
            gui.getClient().sendMsgToServer(new GameModeReply(expertMode.isSelected()));

            disableGameMode();
            enableNPlayers();
            return;
        }

        if(!isGameModeEnabled() && isNPlayersEnabled()) {
            gui.getClient().sendMsgToServer(new PlayerNumberReply(getPlayerNumber()));
        }
        //FIXME: unable to read the toggle value, it returns null but it should return the actual value of the toggle
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

    private int getPlayerNumber() {
        if (n2Players.isSelected())
            return 2;
        else
            return 3;
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
