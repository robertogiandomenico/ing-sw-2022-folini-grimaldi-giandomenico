package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root,Color.BLACK);

        Image icon = new Image("/img/icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Eriantys");
        stage.setWidth(420);
        stage.setHeight(420);
        stage.setResizable(true);
        //stage.setX(50);
        //stage.setY(50);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Press 'q' to exit fullscreen");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));

        stage.setScene(scene);
        stage.show();
    }
}
