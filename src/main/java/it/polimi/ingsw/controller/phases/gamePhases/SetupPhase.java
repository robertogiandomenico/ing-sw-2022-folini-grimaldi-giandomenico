package it.polimi.ingsw.controller.phases.gamePhases;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.clientMessages.WizardReply;
import it.polimi.ingsw.network.messages.serverMessages.TextMessage;
import it.polimi.ingsw.network.messages.serverMessages.WizardRequest;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the setup phase of the game.
 */
public class SetupPhase implements GamePhase {
    private Controller controller;
    private List<Wizard> availableWizards;

    private int playerIndex = 0;

    /**
     * Executes the phase.
     *
     * @param controller a Controller.
     */
    @Override
    public void execute(Controller controller) {
        this.controller = controller;

        availableWizards = new ArrayList<>(controller.getGame().getAvailableWizards());

        for(ClientHandler c : controller.getHandlers()){
            if(!c.equals(controller.getHandlers().get(playerIndex))){
                c.sendMsgToClient(new TextMessage("Waiting for your turn to start"));
            }
        }

        controller.getHandlers().get(playerIndex).sendMsgToClient(new WizardRequest(availableWizards));
    }

    /**
     * Adds new players to the game.
     */
    private void addPlayers() {
        for (ClientHandler connection : controller.getHandlers()){
            Player p = new Player(connection.getClientNickname(),
                    controller.getGame().getAvailableTowers().get(0),
                    connection.getChosenWizard());

            controller.getGame().addNewPlayer(p);
        }
    }

    /**
     * Handles a received message.
     *
     * @param msg        the received GenericClientMessage.
     */
    @Override
    public void receiveMessage(GenericClientMessage msg) {
        if(msg == null || msg.getType() != MessageType.WIZARD_REPLY){
            return;
        }
        Wizard wizard = ((WizardReply) msg).getWizard();
        controller.getHandlers().get(playerIndex).setChosenWizard(wizard);
        controller.getHandlers().get(playerIndex).setClientHandlerPhase(ClientHandlerPhases.WAITING_PHASE_CHANGE);
        availableWizards.remove(wizard);
        if (controller.getHandlers().stream().filter(c -> c.getClientHandlerPhase() == ClientHandlerPhases.WAITING_PHASE_CHANGE).count() == controller.getHandlers().size()){
            addPlayers();
            controller.getGame().initializeBoard();
            controller.getGame().setCurrentPlayer(controller.getGame().getPlayerByNickname(controller.getHandlers().get(0).getClientNickname()));
            controller.setGamePhase(new PlanningPhase());
        } else {
            playerIndex++;
            controller.getHandlers().get(playerIndex).sendMsgToClient(new WizardRequest(availableWizards));
        }
    }

    @Override
    public String toString() {
        return "SetupPhase";
    }

}
