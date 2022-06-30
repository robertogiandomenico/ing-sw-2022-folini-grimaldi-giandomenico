package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

/**
 * This class represents a message that the server sends to the client, to
 * show the updated board.
 */
public class BoardData extends GenericServerMessage {
    private static final long serialVersionUID = 8164023771647411142L;
    private final LightBoard lightBoard;

    /**
     * Class constructor.
     *
     * @param lightBoard    a LightBoard.
     */
    public BoardData(LightBoard lightBoard) {
        super(MessageType.BOARD_DATA);
        this.lightBoard = lightBoard;
    }

    /**
     * Shows the board (CLI or GUI).
     *
     * @param view      a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.printBoard(lightBoard);
    }
}
