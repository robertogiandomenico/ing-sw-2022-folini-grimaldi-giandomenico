package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

import java.util.List;

public class WizardRequest extends GenericServerMessage {
    private final List<Wizard> availableWizards;

    public WizardRequest(List<Wizard> availableWizards) {
        super(MessageType.WIZARD_REQUEST);
        this.availableWizards = availableWizards;
    }

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