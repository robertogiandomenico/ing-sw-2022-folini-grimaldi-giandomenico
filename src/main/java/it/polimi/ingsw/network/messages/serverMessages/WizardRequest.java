package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.List;

public class WizardRequest extends GenericServerMessage {
    private List<Wizard> availableWizards;

    public WizardRequest(List<Wizard> availableWizards) {
        super(MessageType.WIZARD_REQUEST);
        this.availableWizards = availableWizards;
    }

    @Override
    public void show() {

    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }

    public List<Wizard> getAvailableWizards() {
        return availableWizards;
    }
}