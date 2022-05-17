package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

import java.util.List;

public class CloudRequest extends GenericServerMessage {
    private List<Integer> indexesAvailableClouds;

    public CloudRequest(List<Integer> indexesAvailableClouds) {
        super(MessageType.CLOUD_REQUEST);
        this.indexesAvailableClouds = indexesAvailableClouds;
    }

    @Override
    public void show(ViewInterface view) {

    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }

    public List<Integer> getIndexesAvailableClouds() {
        return indexesAvailableClouds;
    }
}