package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

public class BoardData extends GenericServerMessage {

    LightBoard lightBoard;

    public BoardData(LightBoard lightBoard) {
        super(MessageType.BOARD_DATA);
        this.lightBoard = lightBoard;
    }

    @Override
    public void show(ViewInterface view) {
        view.printBoard(lightBoard);
    }
}
