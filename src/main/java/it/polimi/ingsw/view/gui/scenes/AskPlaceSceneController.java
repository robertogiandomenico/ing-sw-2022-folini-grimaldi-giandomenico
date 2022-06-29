package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messages.clientMessages.PlaceReply;
import it.polimi.ingsw.view.gui.GUI;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class represents the controller for the scene in which the user is
 * asked to select a place.
 */
public class AskPlaceSceneController implements SceneControllerInterface {
    @FXML
    private VBox diningRoomBox;
    @FXML
    private VBox archipelagoBox;
    @FXML
    private Button confirmButton;
    private GUI gui;
    private String chosenPlaceID;

    private static final PseudoClass focusedElement = PseudoClass.getPseudoClass("focused");

    /**
     * Initializes the scene.
     */
    @FXML
    private void initialize() {
        confirmButton.setDisable(true);
    }

    /**
     * Puts the focus on the clicked place if it wasn't already focused.
     *
     * @param event     a MouseEvent representing a click.
     */
    @FXML
    private void onPlaceClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            Node selectedPlaceNode = event.getPickResult().getIntersectedNode().getParent();
            chosenPlaceID = selectedPlaceNode.getId();

            if (selectedPlaceNode.getPseudoClassStates().contains(focusedElement)) {
                selectedPlaceNode.pseudoClassStateChanged(focusedElement, false);
                confirmButton.setDisable(true);
            } else if (chosenPlaceID != null){
                selectedPlaceNode.pseudoClassStateChanged(focusedElement, true);
                confirmButton.setDisable(false);
            }

            try {
                if (chosenPlaceID.equals("archipelagoBox"))
                    diningRoomBox.pseudoClassStateChanged(focusedElement, false);
                else if (chosenPlaceID.equals("diningRoomBox"))
                    archipelagoBox.pseudoClassStateChanged(focusedElement, false);

            } catch (NullPointerException ignored) {}
        }
    }


    /**
     * Confirms the choice and
     * <p>
     * -sends a {@link PlaceReply} to the server if the chosen place is the dining room;
     * </p> <p>
     * -allows the user to select an archipelago otherwise.
     * </p>
     */
    @FXML
    private void confirm() {
        try {
            if (chosenPlaceID.equals("diningRoomBox")) {
                gui.getClient().sendMsgToServer(new PlaceReply("DININGROOM"));
                disableAll();
                ((Stage) confirmButton.getScene().getWindow()).close();
            } else {
                ((Stage) confirmButton.getScene().getWindow()).close();
                gui.enableArchiBox();
            }

        } catch (Exception e) {
            gui.warningDialog("No place is selected. Try again.");
        }
    }


    /**
     * Disables all the buttons and boxes.
     */
    private void disableAll() {
        confirmButton.setDisable(true);
        archipelagoBox.setDisable(true);
        diningRoomBox.setDisable(true);
    }


    /**
     * Sets the GUI.
     *
     * @param gui       a GUI.
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
