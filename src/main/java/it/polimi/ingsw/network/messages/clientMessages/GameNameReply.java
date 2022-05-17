package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.serverMessages.GameModeRequest;
import it.polimi.ingsw.network.messages.serverMessages.GameNameRequest;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class GameNameReply extends GenericClientMessage {
    private String gameName;
    public GameNameReply(String gameName) {
        super(MessageType.GAMENAME_REPLY);
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        if(!(clientHandler.getClientHandlerPhase() == ClientHandlerPhases.WAITING_GAMENAME)){
            return;
        }
        String REGEX = "^([a-zA-Z]+\\w{2,10})$";
        if(gameName == null || !gameName.matches(REGEX)){
            clientHandler.sendMsgToClient(new GameNameRequest());
        }

        if(!alreadyExists(gameName, server)){
            //if a game with that name doesn't already exist
            Controller controller = new Controller(gameName);
            server.getLobbies().put(controller, -1);
            clientHandler.setClientHandlerPhase(ClientHandlerPhases.WAITING_GAMEMODE);
            server.addToLobby(gameName, clientHandler);
            clientHandler.sendMsgToClient(new GameModeRequest());
        } else {
            if(!server.getLobbies().keySet().stream().filter(c -> c.getGameName().equalsIgnoreCase(gameName)).findFirst().get().isGameStarted()){
                //if a game with that name exists, and it hasn't started yet
                server.addToLobby(gameName, clientHandler);
            } else {
                //if a game with that name exists, but it has already started, server asks the client for a game name
                clientHandler.sendMsgToClient(new GameNameRequest());
            }
        }
    }

    private boolean alreadyExists(String gameName, Server server) {
        return server.getLobbies().keySet().stream().map(Controller::getGameName).anyMatch(n -> n.equals(gameName));
    }
}