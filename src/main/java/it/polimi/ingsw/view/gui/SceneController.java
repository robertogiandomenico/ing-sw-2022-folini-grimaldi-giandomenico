package it.polimi.ingsw.view.gui;

import javafx.scene.Scene;

public class SceneController {
    private Scene currentScene;
    private SceneController currentController;

    /**
     * Displays an error in a popup.
     */
    public void displayError() {
        //TODO: implement displayError method
    }

    /**
     * Displays who won the game.
     */
    public void displayWinner() {
        //TODO: implement displayWinner method
    }

    /**
     * Switches between scenes.
     * Takes different parameters based on the specific situation.
     */
    public void switchScene() {
        //TODO: implement switchScene method
    }

    /**
     * Returns the scene currently active.
     *
     * @return              the current Scene.
     */
    public Scene getCurrentScene(){
        return currentScene;
    }

    /**
     * Returns the controller corresponding to the currently active scene.
     *
     * @return              the current SceneController.
     */
    public SceneController getCurrentController(){
        return currentController;
    }

}