package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.messages.clientMessages.WizardReply;
import it.polimi.ingsw.view.gui.GUI;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;

public class WizardSceneController implements SceneControllerInterface {

    @FXML
    private VBox articWizard;
    @FXML
    private VBox desertWizard;
    @FXML
    private VBox forestWizard;
    @FXML
    private VBox skyWizard;
    @FXML
    private Button goButton;
    @FXML
    private Button exitButton;
    private String chosenWizardID;
    List<Wizard> availableWizards;

    private static final PseudoClass focusedElement = PseudoClass.getPseudoClass("focused");
    private GUI gui;

    @FXML
    private void initialize() {
        disableAll();
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
                        //System.out.println("articWizard selected");
                        desertWizard.pseudoClassStateChanged(focusedElement, false);
                        forestWizard.pseudoClassStateChanged(focusedElement, false);
                        skyWizard.pseudoClassStateChanged(focusedElement, false);
                        break;

                    case "desertWizard":
                        //System.out.println("desertWizard selected");
                        articWizard.pseudoClassStateChanged(focusedElement, false);
                        forestWizard.pseudoClassStateChanged(focusedElement, false);
                        skyWizard.pseudoClassStateChanged(focusedElement, false);
                        break;

                    case "forestWizard":
                        //System.out.println("forestWizard selected");
                        articWizard.pseudoClassStateChanged(focusedElement, false);
                        desertWizard.pseudoClassStateChanged(focusedElement, false);
                        skyWizard.pseudoClassStateChanged(focusedElement, false);
                        break;

                    case "skyWizard":
                        //System.out.println("skyWizard selected");
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
    private void go() {
        try {
            if(!chosenWizardID.isEmpty()) {
                //System.out.println("Go! Selected wizard: " + chosenWizardID.toUpperCase());
                goButton.setDisable(true);
                gui.getClient().sendMsgToServer(new WizardReply(availableWizards.get(getWizardIndex())));
                disableAll();
            }
        } catch (Exception e) {
            gui.errorDialog("No wizard is selected. Try again.");
        }
    }

    @FXML
    private void exit() {
        gui.closeWindow(gui.getStage());
        System.exit(0);
    }

    public void setAvailableWizards(List<Wizard> availableWizards) {
        this.availableWizards = availableWizards;

        for (Wizard w : availableWizards) {
            switch (w.name().toUpperCase()) {
                case "ARTICWIZARD":
                    articWizard.setDisable(false);
                    break;
                case "SKYWIZARD":
                    skyWizard.setDisable(false);
                    break;
                case "DESERTWIZARD":
                    desertWizard.setDisable(false);
                    break;
                case "FORESTWIZARD":
                    forestWizard.setDisable(false);
                    break;
                default: break;
            }
        }
    }


    private int getWizardIndex() {
        switch (chosenWizardID.toUpperCase()) {
            case "ARTICWIZARD":
                return availableWizards.indexOf(Wizard.ARTICWIZARD);
            case "DESERTWIZARD":
                return availableWizards.indexOf(Wizard.DESERTWIZARD);
            case "FORESTWIZARD":
                return availableWizards.indexOf(Wizard.FORESTWIZARD);
            case "SKYWIZARD":
                return availableWizards.indexOf(Wizard.SKYWIZARD);
            default:
                break;
        }
        return -1;
    }

    private void disableAll() {
        goButton.setDisable(true);
        articWizard.setDisable(true);
        skyWizard.setDisable(true);
        desertWizard.setDisable(true);
        forestWizard.setDisable(true);
    }

    /**
     * Sets the GUI.
     *
     * @param gui                 a GUI.
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
