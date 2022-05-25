package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.view.utilities.IPvalidator;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class ConnectionSceneController {

    @FXML
    private TextField ipAddressField;
    @FXML
    private TextField portField;
    @FXML
    private AnchorPane connectButton;
    @FXML
    private AnchorPane exitButton;
    private String ipAddress;
    private String port;
    final int MIN_PORT = 1024;
    final int MAX_PORT = 65535;
    boolean portTyped = false;

    @FXML
    private void initialize() {
        portField.setDisable(true);
    }

    @FXML
    private void connect() {
        System.out.println("Connection...");
        portField.setDisable(false);
        ipAddressField.setDisable(true);

        if (!portTyped) {
            portTyped = true;
        } else {
            portField.setDisable(true);
            connectButton.setDisable(true);
        }
    }

    @FXML
    private void ipCheckProperty(KeyEvent e) {
        ipAddress = ipAddressField.getText();
        connectButton.setDisable(!IPvalidator.validateIP(ipAddress));

        if (e.getCode().toString().equals("ENTER") && !connectButton.isDisable()) {
            connect(); //should be    registerIP();
        }
    }

    @FXML
    private void portCheckProperty(KeyEvent e) {
        port = portField.getText();
        boolean isValid = port.matches("^\\d{4,5}$") && Integer.parseInt(port)>=MIN_PORT && Integer.parseInt(port)<=MAX_PORT;
        connectButton.setDisable( !isValid );

        if (e.getCode().toString().equals("ENTER") && !connectButton.isDisable()) {
            connect(); //should be    registerPort();
        }
    }

    public void exit() {
        System.out.println("Exit");
        System.exit(0);
    }
}
