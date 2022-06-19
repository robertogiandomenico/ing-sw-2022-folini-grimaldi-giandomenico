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

    @FXML
    private void initialize() {
        confirmButton.setDisable(true);
    }

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

            } catch (Exception ignored) {}
        }
    }


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


    private void disableAll() {
        confirmButton.setDisable(true);
        archipelagoBox.setDisable(true);
        diningRoomBox.setDisable(true);
    }


    /**
     * @param gui
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
