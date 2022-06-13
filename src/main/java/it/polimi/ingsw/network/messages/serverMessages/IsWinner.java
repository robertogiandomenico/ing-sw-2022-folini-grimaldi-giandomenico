package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

/**
 * This class represents a message that the server sends to the client, to
 * declare who won the game.
 */
public class IsWinner extends GenericServerMessage {
    private final String winner;
    private final String condition;
    private final LightBoard lightBoard;

    /**
     * Class constructor.
     *
     * @param winner        the nickname of the winner.
     * @param condition     the winning condition.
     * @param lightBoard    the LightBoard.
     */
    public IsWinner(String winner, String condition, LightBoard lightBoard) {
        super(MessageType.RESULT);
        this.winner = winner;
        this.condition = condition;
        this.lightBoard = lightBoard;
    }

    /**
     * Shows the message (CLI or GUI).
     *
     * @param view          a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.printBoard(lightBoard);
        view.displayEndgameResult(winner, condition);
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}
