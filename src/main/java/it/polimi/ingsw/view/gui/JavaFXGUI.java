package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class JavaFXGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/BoardScene.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/css/setup.css");

        //  Font.loadFont(getClass().getResourceAsStream("/fonts/Dumbledor.ttf"), 14);
            Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto.ttf"), 13);
            Font.loadFont(getClass().getResourceAsStream("/fonts/Metamorphous.ttf"), 13);

            Image icon = new Image("/img/icon.png");
            stage.getIcons().add(icon);
            stage.setTitle("Eriantys");
            stage.setResizable(true);
            stage.setMinHeight(630);
            stage.setMinWidth(610);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
