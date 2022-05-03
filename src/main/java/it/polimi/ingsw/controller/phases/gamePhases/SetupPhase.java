package it.polimi.ingsw.controller.phases.gamePhases;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.server.ClientHandler;

public class SetupPhase implements GamePhase {
    private Controller controller;

    @Override
    public void execute(Controller controller) {
        this.controller = controller;
        addPlayers();
    }

    private void addPlayers() {
        for (ClientHandler connection : controller.getHandlers()){
            Player p = new Player(connection.getClientNickname(),
                                  controller.getGame().getAvailableTowers().get(0),
                                  connection.getChosenWizard());

            controller.getGame().addNewPlayer(p);
        }
    }

    @Override
    public String toString() {
        return "SetupPhase";
    }
}
