package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class WizardReply extends GenericClientMessage {
    private Wizard wizard;

    public WizardReply(Wizard wizard) {
        super(MessageType.WIZARD_REPLY);
        this.wizard = wizard;
    }

    public Wizard getWizard() {
        return wizard;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        clientHandler.getController().receiveMessage(this);
    }
}
