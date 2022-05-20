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
    private void Create() {
        System.out.println("Creating...");
    }

    public void Back() {
        System.out.println("Going Back...");
        System.exit(0);
    }
}
