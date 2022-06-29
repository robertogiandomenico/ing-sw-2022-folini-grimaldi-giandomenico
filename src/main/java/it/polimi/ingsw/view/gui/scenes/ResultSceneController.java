package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * This class represents the controller for the scene in which the results of
 * the game are showed.
 */
public class ResultSceneController implements SceneControllerInterface {
    private GUI gui;
    @FXML
    private Label resultLabel;
    @FXML
    private Label subtitleLabel;

    /**
     * Sets the GUI.
     *
     * @param gui               a GUI.
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    /**
     * Sets the results of the game based on who is the winner.
     *
     * @param result            a String that states whether the Player won or lost the game.
     */
    public void setWinner(String result) {
        resultLabel.setText(result);
    }

    /**
     * Sets a subtitle for the scene to inform who won and under which condition.
     *
     * @param subtitle          the subtitle String.
     */
    public void setSubtitle(String subtitle) {
        subtitleLabel.setText(subtitle);
    }
}
