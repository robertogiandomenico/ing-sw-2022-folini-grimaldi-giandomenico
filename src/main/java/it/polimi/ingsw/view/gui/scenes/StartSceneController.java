package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;

public class StartSceneController {
    @FXML
    public void play () {
        System.out.println("play");
    }

    @FXML
    public void exit() {
        System.out.println("Exit");
        System.exit(0);
    }
}
