package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.GenericServerMessage;
import it.polimi.ingsw.network.messages.serverMessages.NicknameRequest;
import it.polimi.ingsw.network.messages.serverMessages.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        pingThread = new Thread(()->{
            while (activeClient){
                try {
                    Thread.sleep(PING_TIME);
                    sendMsgToClient(new Ping());
                } catch (InterruptedException ignored) {
                    break;
                }
            }
        });
    }

    @Override
    public void run() {
        try{
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            activeClient = true;

            pingThread.start();

            clientHandlerPhase = ClientHandlerPhases.WAITING_NICKNAME;
            sendMsgToClient(new NicknameRequest());

            while(activeClient){
                try{
                    GenericClientMessage msg = (GenericClientMessage) inputStream.readObject();
                    msg.execute(server, this);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientNickname() {
        return clientNickname;
    }

    public void setClientNickname(String clientNickname) {
        this.clientNickname = clientNickname;
    }

    public Wizard getChosenWizard() {
        return chosenWizard;
    }

    public void setChosenWizard(Wizard chosenWizard) {
        this.chosenWizard = chosenWizard;
    }

    public void sendMsgToClient(GenericServerMessage msg){
        try {
            outputStream.writeObject(msg);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException e) {
            manageDisconnection();
        }
    }

    public void manageDisconnection() {

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
}
