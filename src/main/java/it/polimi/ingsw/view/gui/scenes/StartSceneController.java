package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class StartSceneController implements SceneControllerInterface {

    @FXML
    private ImageView audioButton;
    private boolean muted = false;
    private GUI gui;


    @FXML
    private void play() {
        gui.askServerInfo();
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
    private void onLogoClick() {
        try {
            Desktop.getDesktop().browse(new URL("https://www.craniocreations.it/prodotto/eriantys").toURI());
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error in opening the URL.");
        }
    }

    @FXML
    private void onAudioClick() {
        if (muted) {
            audioButton.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/img/audio/audio_on.png")));
            gui.getMediaPlayer().play();
            muted = false;
        } else {
            audioButton.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/img/audio/audio_off.png")));
            gui.getMediaPlayer().stop();
            muted = true;
        }
    }

    @FXML
    private void exit() {
        Alert alertDialog = new Alert(Alert.AlertType.CONFIRMATION);
        alertDialog.setTitle("Exiting");
        alertDialog.setHeaderText("You're about to exit");
        alertDialog.setContentText("Do you want to close the game?");

        if (alertDialog.showAndWait().get() == ButtonType.OK) {
            System.out.println("Exit confirmed.");
            System.exit(0);
        }
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
