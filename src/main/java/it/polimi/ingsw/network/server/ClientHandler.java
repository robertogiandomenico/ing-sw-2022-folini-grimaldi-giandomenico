package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.actions.Action;
import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.GenericServerMessage;
import it.polimi.ingsw.network.messages.serverMessages.NicknameRequest;
import it.polimi.ingsw.network.messages.connectionMessages.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;

/**
 * This class is used to manage a client connected to the server.
 */
public class ClientHandler implements Runnable{
    private final int PING_TIME = 5000;
    private final Server server;
    private final Socket socket;
    private final Thread pingThread;
    private boolean activeClient;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String clientNickname;
    private Wizard chosenWizard;
    private Controller controller;
    private ClientHandlerPhases clientHandlerPhase;
    private Action currentAction;

    /**
     * Class constructor specifying server and socket, that initializes a
     * ping thread.
     *
     * @param server             a Server.
     * @param socket             a Socket.
     */
    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        pingThread = new Thread(()->{
            while (activeClient){
                try {
                    Thread.sleep(PING_TIME);
                    sendMsgToClient(new Ping());
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
    }

    @Override
    public void run() {
        try{
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            activeClient = true;

            pingThread.start();

            clientHandlerPhase = ClientHandlerPhases.WAITING_NICKNAME;
            sendMsgToClient(new NicknameRequest());

            while(activeClient){
                try{
                    Object object = inputStream.readObject();
                    if(!(object instanceof Ping)) {
                        GenericClientMessage msg = (GenericClientMessage) object;
                        msg.execute(server, this);
                    }
                } catch (ClassNotFoundException | SocketTimeoutException e) {
                    manageDisconnection();
                }

            }

        } catch (IOException e) {
            manageDisconnection();
        }
    }

    /**
     * Gets the nickname of the client.
     *
     * @return                   the nickname of the Client.
     */
    public String getClientNickname() {
        return clientNickname;
    }

    /**
     * Sets the nickname of the client.
     *
     * @param clientNickname     the nickname of the Client.
     */
    public void setClientNickname(String clientNickname) {
        this.clientNickname = clientNickname;
        server.validateNickname(this);
    }

    /**
     * Gets the chosen wizard.
     *
     * @return                   the chosen Wizard.
     */
    public Wizard getChosenWizard() {
        return chosenWizard;
    }

    /**
     * Sets the chosen wizard.
     *
     * @param chosenWizard       the chosen Wizard.
     */
    public void setChosenWizard(Wizard chosenWizard) {
        this.chosenWizard = chosenWizard;
    }

    /**
     * Sends a message to the client.
     *
     * @param msg                the message to be sent.
     */
    public void sendMsgToClient(Serializable msg){
        assert (msg instanceof GenericServerMessage) || (msg instanceof Ping);
        try {
            outputStream.writeObject(msg);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException e) {
            manageDisconnection();
        }
    }

    /**
     * Handles the disconnection of the client.
     */
    public void manageDisconnection() {
        if (activeClient){
            activeClient = false;
            Server.SERVER_LOGGER.log(Level.INFO, "DISCONNECTION: client " + socket.getInetAddress().getHostAddress() + " has disconnected");
            server.removeClient(this);
            disconnect();
        }

    }

    /**
     * Gets the client handler phase.
     *
     * @return                   the ClientHandlerPhase.
     */
    public ClientHandlerPhases getClientHandlerPhase() {
        return clientHandlerPhase;
    }

    /**
     * Sets the client handler phase.
     *
     * @param clientHandlerPhase the ClientHandlerPhase.
     */
    public void setClientHandlerPhase(ClientHandlerPhases clientHandlerPhase) {
        this.clientHandlerPhase = clientHandlerPhase;
    }

    /**
     * Gets the controller.
     *
     * @return                   the Controller.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller         the Controller to set.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Gets the current action.
     *
     * @return                   the current Action.
     */
    public Action getCurrentAction() {
        return currentAction;
    }

    /**
     * Sets the current action.
     *
     * @param currentAction      the Action to set as current.
     */
    public void setCurrentAction(Action currentAction) {
        this.currentAction = currentAction;
    }

    /**
     * Disconnects the server closing input and output stream and socket.
     */
    public void disconnect() {
        activeClient = false;
        try {
            inputStream.close();
        } catch (IOException ignored){}
        try {
            outputStream.close();
        } catch (IOException ignored){}
        try {
            socket.close();
        } catch (IOException ignored){}
    }

}
