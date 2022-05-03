package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.messages.MessageType;

public class WizardReply extends GenericClientMessage {
    private Wizard wizard;

    public WizardReply(Wizard wizard) {
        super(MessageType.WIZARD_REPLY);
        this.wizard = wizard;
    }

    public Wizard getWizard() {
        return wizard;
    }
}
