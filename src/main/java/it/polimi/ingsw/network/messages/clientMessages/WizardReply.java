package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

/**
 * This class represents a message that the client sends to the server, that
 * contains the chosen wizard.
 * Follows a {@link it.polimi.ingsw.network.messages.serverMessages.WizardRequest WizardRequest}.
 */
public class WizardReply extends GenericClientMessage {
    private static final long serialVersionUID = 1878414349073370676L;
    private Wizard wizard;

    /**
     * Class constructor.
     *
     * @param wizard    the chosen Wizard.
     */
    public WizardReply(Wizard wizard) {
        super(MessageType.WIZARD_REPLY);
        this.wizard = wizard;
    }

    /**
     * Returns the wizard.
     *
     * @return          the chosen Wizard.
     */
    public Wizard getWizard() {
        return wizard;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        clientHandler.getController().receiveMessage(this);
    }
}
