package it.polimi.ingsw.view.gui.scenes;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class WizardSceneController {

    @FXML
    private VBox articWizard;
    @FXML
    private VBox desertWizard;
    @FXML
    private VBox forestWizard;
    @FXML
    private VBox skyWizard;
    @FXML
    private AnchorPane goButton;
    @FXML
    private AnchorPane exitButton;
    String chosenWizardID;

    private static final PseudoClass focusedElement = PseudoClass.getPseudoClass("focused");

    @FXML
    private void initialize() {
        goButton.setDisable(true);
    }

    @FXML
    private void handleOnMouseClicked(MouseEvent event) {

        Node selectedWizardNode = event.getPickResult().getIntersectedNode().getParent();

        if (event.getButton().equals(MouseButton.PRIMARY)) {

            if (selectedWizardNode.getPseudoClassStates().contains(focusedElement)) {
                selectedWizardNode.pseudoClassStateChanged(focusedElement, false);
                goButton.setDisable(true);
            } else {
                selectedWizardNode.pseudoClassStateChanged(focusedElement, true);
                goButton.setDisable(false);
            }

            chosenWizardID = event.getPickResult().getIntersectedNode().getParent().getId();

            try {
                switch (chosenWizardID) {
                    case "articWizard":
                        System.out.println("articWizard selected");
                        desertWizard.pseudoClassStateChanged(focusedElement, false);
                        forestWizard.pseudoClassStateChanged(focusedElement, false);
                        skyWizard.pseudoClassStateChanged(focusedElement, false);
                        break;

                    case "desertWizard":
                        System.out.println("desertWizard selected");
                        articWizard.pseudoClassStateChanged(focusedElement, false);
                        forestWizard.pseudoClassStateChanged(focusedElement, false);
                        skyWizard.pseudoClassStateChanged(focusedElement, false);
                        break;

                    case "forestWizard":
                        System.out.println("forestWizard selected");
                        articWizard.pseudoClassStateChanged(focusedElement, false);
                        desertWizard.pseudoClassStateChanged(focusedElement, false);
                        skyWizard.pseudoClassStateChanged(focusedElement, false);
                        break;

                    case "skyWizard":
                        System.out.println("skyWizard selected");
                        articWizard.pseudoClassStateChanged(focusedElement, false);
                        desertWizard.pseudoClassStateChanged(focusedElement, false);
                        forestWizard.pseudoClassStateChanged(focusedElement, false);
                        break;

                    default:
                        break;
                }
            } catch (Exception ignored) {}
        }
    }


    @FXML
    public void go() {
        try {
            if(!chosenWizardID.isEmpty())
                System.out.println("Go! Selected wizard: " + chosenWizardID.toUpperCase());
        } catch (Exception e) {
            System.out.println("No wizard is selected");
        }
    }

    @FXML
    public void exit() {
        System.out.println("Exit");
        System.exit(0);
    }
}
