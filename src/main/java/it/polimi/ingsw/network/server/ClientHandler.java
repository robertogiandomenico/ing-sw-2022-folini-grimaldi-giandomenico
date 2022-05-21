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
import java.util.logging.Level;

public class ClientHandler implements Runnable{
    private final int PING_TIME = 5000;

    private final Server server;
    private final Socket socket;
    private Thread pingThread;
    private boolean activeClient;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String clientNickname;
    private Wizard chosenWizard;

    private Controller controller;

    private ClientHandlerPhases clientHandlerPhase;

    private Action currentAction;

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
                } catch (ClassNotFoundException e) {
                    manageDisconnection();
                }

            }

        } catch (IOException e) {
            manageDisconnection();
        }
    }

    public String getClientNickname() {
        return clientNickname;
    }

    public void setClientNickname(String clientNickname) {
        this.clientNickname = clientNickname;
        server.validateNickname(this);
    }

    public Wizard getChosenWizard() {
        return chosenWizard;
    }

    public void setChosenWizard(Wizard chosenWizard) {
        this.chosenWizard = chosenWizard;
    }

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

    public void manageDisconnection() {
        activeClient = false;
        Server.SERVER_LOGGER.log(Level.INFO, "DISCONNECTION: client " + socket.getInetAddress().getHostAddress() + " has disconnected");
        server.removeClient(this);
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

    public ClientHandlerPhases getClientHandlerPhase() {
        return clientHandlerPhase;
    }

    public void setClientHandlerPhase(ClientHandlerPhases clientHandlerPhase) {
        this.clientHandlerPhase = clientHandlerPhase;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(Action currentAction) {
        this.currentAction = currentAction;
    }
}
