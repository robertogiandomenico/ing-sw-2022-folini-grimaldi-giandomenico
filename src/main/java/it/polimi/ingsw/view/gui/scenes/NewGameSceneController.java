package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class NewGameSceneController {

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

    @FXML
    public void initialize() {
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
        } //FIXME: unable to read the toggle value, it returns null but it should return the actual value of the toggle

        validNPlayers = true;
    }

    @FXML
    public void exit() {
        System.out.println("Exit");
        System.exit(0);
    }
}
