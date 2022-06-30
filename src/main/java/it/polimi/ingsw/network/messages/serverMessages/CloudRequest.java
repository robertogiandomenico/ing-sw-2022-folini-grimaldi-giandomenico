package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

import java.util.List;

/**
 * This class represents a message that the server sends to the client,
 * asking to select a cloud.
 */
public class CloudRequest extends GenericServerMessage {
    private static final long serialVersionUID = 2526525956650403074L;
    private final List<Integer> indexesAvailableClouds;

    /**
     * Class constructor.
     *
     * @param indexesAvailableClouds    an Integer List containing the indexes of
     *                                  available Clouds.
     */
    public CloudRequest(List<Integer> indexesAvailableClouds) {
        super(MessageType.CLOUD_REQUEST);
        this.indexesAvailableClouds = indexesAvailableClouds;
    }

    /**
     * Shows the request (CLI or GUI).
     *
     * @param view                      a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.askCloud(indexesAvailableClouds);
    }
}