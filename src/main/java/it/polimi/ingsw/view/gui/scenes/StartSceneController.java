package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class StartSceneController {
    @FXML
    public void play () {
        System.out.println("play");
    }

    @FXML
    private void onCreditsClick() {
        try {
            Desktop.getDesktop().browse(new URL("https://github.com/robertogiandomenico/ing-sw-2022-folini-grimaldi-giandomenico").toURI());
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error in opening the URL");
        }
    }

    @FXML
    public void exit() {
        System.out.println("Exit");
        System.exit(0);
    }
}
