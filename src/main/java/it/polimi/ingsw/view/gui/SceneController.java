package it.polimi.ingsw.view.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private static Scene scene;
    private static Parent root;
    private static Stage window;
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
    public static void switchScene(MouseEvent event, String fxmlFile) throws IOException {
        root = FXMLLoader.load(SceneController.class.getResource("/fxml/" + fxmlFile));
        scene = new Scene(root);

        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        //window.show();
    }

    /**
     * Returns the scene currently active.
     *
     * @return              the current Scene.
     */
    public Scene getCurrentScene(){
        return scene;
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