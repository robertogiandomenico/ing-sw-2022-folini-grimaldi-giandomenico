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

/**
 * This class represents the controller for the choice of the wizard scene.
 */
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

    /**
     * Initializes the wizards VBoxes.
     */
    @FXML
    private void initialize() {
        disableAll();
    }

    /**
     * Puts the focus on a specific wizard card according to the mouse event.
     *
     * @param event             a MouseEvent.
     */
    @FXML
    private void handleOnMouseClicked(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            Node selectedWizardNode = event.getPickResult().getIntersectedNode().getParent();
            chosenWizardID = selectedWizardNode.getId();


            if (selectedWizardNode.getPseudoClassStates().contains(focusedElement)) {
                selectedWizardNode.pseudoClassStateChanged(focusedElement, false);
                goButton.setDisable(true);
            } else if (chosenWizardID != null){
                selectedWizardNode.pseudoClassStateChanged(focusedElement, true);
                goButton.setDisable(false);
            }

            try {
                switch (chosenWizardID) {
                    case "articWizard":
                        desertWizard.pseudoClassStateChanged(focusedElement, false);
                        forestWizard.pseudoClassStateChanged(focusedElement, false);
                        skyWizard.pseudoClassStateChanged(focusedElement, false);
                        break;

                    case "desertWizard":
                        articWizard.pseudoClassStateChanged(focusedElement, false);
                        forestWizard.pseudoClassStateChanged(focusedElement, false);
                        skyWizard.pseudoClassStateChanged(focusedElement, false);
                        break;

                    case "forestWizard":
                        articWizard.pseudoClassStateChanged(focusedElement, false);
                        desertWizard.pseudoClassStateChanged(focusedElement, false);
                        skyWizard.pseudoClassStateChanged(focusedElement, false);
                        break;

                    case "skyWizard":
                        articWizard.pseudoClassStateChanged(focusedElement, false);
                        desertWizard.pseudoClassStateChanged(focusedElement, false);
                        forestWizard.pseudoClassStateChanged(focusedElement, false);
                        break;

                    default:
                        break;
                }
            } catch (NullPointerException ignored) {}
        }
    }

    /**
     * Goes to the next scene sending the user choice to the server.
     */
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
            gui.errorDialog("No wizard is selected. Try again.", false);
        }
    }

    /**
     * Closes the window.
     */
    @FXML
    private void exit() {
        gui.closeWindow(gui.getStage());
    }

    /**
     * Sets the available wizards.
     *
     * @param availableWizards  a Wizard List of the available ones.
     */
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


    /**
     * Gets the index of the chosen wizard.
     *
     * @return                  the index corresponding to the chosen Wizard.
     */
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
     * @param gui               a GUI.
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
