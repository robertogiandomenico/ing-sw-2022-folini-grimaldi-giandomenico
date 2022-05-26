package it.polimi.ingsw.view.gui.scenes;

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

        if (expertMode.isSelected())
            expertModeBool = true;
        if (n3Players.isSelected())
            num3OfPlayers = true;


        if (!validGameMode) {
            System.out.println("Sending ExpertMode = " + expertModeBool);
            validGameMode = true;
        } //FIXME: unable to read the toggle value, it returns null but it should return the actual value of the toggle

        easyMode.setDisable(true);
        expertMode.setDisable(true);
        n2Players.setDisable(false);
        n3Players.setDisable(false);

        if (validNPlayers) {
            System.out.println("Sending nPlayers of 3 = " + num3OfPlayers);
            System.out.println("Creating...");
            n2Players.setDisable(true);
            n3Players.setDisable(true);
        } //FIXME: unable to read the toggle value, it returns null but it should return the actual value of the toggle

        validNPlayers = true;
    }

    @FXML
    private void exit() {
        System.out.println("Exit");
        System.exit(0);
    }

    /**
     * @param gui
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
