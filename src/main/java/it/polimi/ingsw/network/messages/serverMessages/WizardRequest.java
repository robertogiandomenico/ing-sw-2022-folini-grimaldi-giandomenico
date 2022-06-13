package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

import java.util.List;

/**
 * This class represents a message that the server sends to the client,
 * asking to select a wizard.
 */
public class WizardRequest extends GenericServerMessage {
    private final List<Wizard> availableWizards;

    /**
     * Class constructor specifying the wizards that are still available.
     *
     * @param availableWizards  the Wizard List of available ones.
     */
    public WizardRequest(List<Wizard> availableWizards) {
        super(MessageType.WIZARD_REQUEST);
        this.availableWizards = availableWizards;
    }

    /**
     * Shows the request (CLI or GUI).
     *
     * @param view              a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.askWizard(availableWizards);
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}