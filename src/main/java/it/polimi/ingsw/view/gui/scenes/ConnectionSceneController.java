package it.polimi.ingsw.view.gui.scenes;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ConnectionSceneController {

    @FXML
    private TextField ipAddress;
    @FXML
    private TextField port;
    @FXML
    private AnchorPane connect;
    @FXML
    private AnchorPane exit;

    @FXML
    public void connect() {
        BooleanBinding booleanBinding = ipAddress.textProperty().isEmpty().or(port.textProperty().isEmpty());
        if (ipAddress.getCharacters().toString().isEmpty() || port.getCharacters().toString().isEmpty()) {
            System.out.println("Fields are empty, insert values");
            connect.disableProperty().bind(booleanBinding);
        } else {
            System.out.println("Connection...");
        }
    }

    public void exit() {
        System.out.println("Exit");
        System.exit(0);
    }
}
