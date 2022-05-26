package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.utilities.IPvalidator;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ConnectionSceneController implements SceneControllerInterface {

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
    boolean validPort;
    private GUI gui;

    @FXML
    private void connect() throws IOException {
        ipAddress = ipAddressField.getText();
        port = portField.getText();

        gui.setClient(new Client(ipAddress, Integer.parseInt(port), gui));
        gui.getClient().init();

        System.out.println("Connection...");
    }

    @FXML
    private void ipCheckProperty(KeyEvent e) {
        ipAddress = ipAddressField.getText();
        port = portField.getText();
        validPort = port.matches("^\\d{4,5}$") && Integer.parseInt(port)>=MIN_PORT && Integer.parseInt(port)<=MAX_PORT;
        connectButton.setDisable( !validPort || !IPvalidator.validateIP(ipAddress) );

        if (e.getCode().toString().equals("ENTER") && !connectButton.isDisable()) {
            portField.requestFocus();
        }
    }

    @FXML
    private void portCheckProperty(KeyEvent e) {
        port = portField.getText();
        validPort = port.matches("^\\d{4,5}$") && Integer.parseInt(port)>=MIN_PORT && Integer.parseInt(port)<=MAX_PORT;
        connectButton.setDisable( !validPort || !IPvalidator.validateIP(ipAddress) );

        if (e.getCode().toString().equals("ENTER") && !connectButton.isDisable()) {
            connectButton.requestFocus();
        }
    }

    @FXML
    private void exit() {
        System.out.println("Exit");
        System.exit(0);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getPort() {
        return port;
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

}