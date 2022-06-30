package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.serverMessages.PlayerNumberRequest;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

/**
 * This class represents a message that the client sends to the server, that
 * contains the chosen game mode.
 * Follows a {@link it.polimi.ingsw.network.messages.serverMessages.GameModeRequest GameModeRequest}.
 */
public class GameModeReply extends GenericClientMessage {
    private static final long serialVersionUID = 4394864072188395086L;
    private boolean gameMode; //true if expertMode, otherwise false

    /**
     * Class constructor.
     *
     * @param gameMode      a boolean whose value is:
     *                      <p>
     *                      -{@code true} if the chosen mode is expert;
     *                      </p> <p>
     *                      -{@code false} otherwise.
     *                      </p>
     */
    public GameModeReply(boolean gameMode) {
        super(MessageType.GAMEMODE_REPLY);
        this.gameMode = gameMode;
    }

    /**
     * Executes the specific action based on the message.
     *
     * @param server        the Server.
     * @param clientHandler the ClientHandler.
     */
    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        if(!(clientHandler.getClientHandlerPhase() == ClientHandlerPhases.WAITING_GAMEMODE)){
            return;
        }
        clientHandler.getController().setExpertMode(gameMode);
        clientHandler.setClientHandlerPhase(ClientHandlerPhases.WAITING_PLAYERNUMBER);
        clientHandler.sendMsgToClient(new PlayerNumberRequest());
    }
}