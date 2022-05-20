package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

public class NewGameController {

    @FXML
    private RadioButton easyMode;
    @FXML
    private RadioButton expertMode;
    @FXML
    private RadioButton n2Players;
    @FXML
    private RadioButton n3Players;


    @FXML
    private void next() {
        System.out.println("Creating...");
    }

    @FXML
    public void exit() {
        System.out.println("Exit");
        System.exit(0);
    }
}
