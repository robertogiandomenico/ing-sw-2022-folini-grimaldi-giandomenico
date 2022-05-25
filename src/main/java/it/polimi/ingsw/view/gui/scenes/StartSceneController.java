package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class StartSceneController {
    @FXML
    private void play(MouseEvent onClick) throws IOException {
        System.out.println("play");
        SceneController.switchScene(onClick, "ConnectionScene.fxml");
    }

    @FXML
    private void onCreditsClick() {
        try {
            Desktop.getDesktop().browse(new URL("https://github.com/robertogiandomenico/ing-sw-2022-folini-grimaldi-giandomenico").toURI());
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error in opening the URL.");
        }
    }

    @FXML
    private void onRulesClick() {
        try {
            Desktop.getDesktop().browse(new URL("https://craniointernational.com/2021/wp-content/uploads/2021/06/Eriantys_rules_small.pdf").toURI());
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error in opening the URL.");
        }
    }

    @FXML
    private void exit() {
        System.out.println("Exit");
        System.exit(0);
    }
}
