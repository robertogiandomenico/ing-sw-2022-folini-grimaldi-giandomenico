package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.messages.serverMessages.GenericServerMessage;

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

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        pingThread = new Thread(()->{
            while (activeClient){
                try {
                    Thread.sleep(PING_TIME);
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
}
