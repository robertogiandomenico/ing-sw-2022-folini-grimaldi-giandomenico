package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.connectionMessages.DisconnectionMessage;
import it.polimi.ingsw.network.messages.connectionMessages.Ping;
import it.polimi.ingsw.network.messages.serverMessages.GenericServerMessage;
import it.polimi.ingsw.view.ViewInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private final String ip;
    private final int port;
    private final ViewInterface view;
    private final int PING_TIME = 5000;
    private final Thread pingThread;
    private final AtomicBoolean clientConnected = new AtomicBoolean(false);
    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final Queue<GenericServerMessage> messageQueue = new LinkedList<>();
    private final Thread messageListener;
    private final Thread messageHandler;
    private String nickname;

    public Client(String ip, int port, ViewInterface view) {
        this.ip = ip;
        this.port = port;
        this.view = view;

        messageListener = new Thread(this::readMessages);
        messageHandler = new Thread(this::handleMessages);

        pingThread = new Thread(()->{
            while (clientConnected.get()){
                try {
                    Thread.sleep(PING_TIME);
                    sendMsgToServer(new Ping());
                } catch (InterruptedException ignored) {
                    break;
                }
            }
        });
    }

    public void sendMsgToServer(Serializable message){
        if(clientConnected.get()){
            try {
                outputStream.writeObject(message);
                outputStream.flush();
                outputStream.reset();
            } catch (IOException e) {
                disconnect(true);
            }
        }
    }

    public void init() throws IOException {
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(ip, port));
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
        clientConnected.set(true);
        pingThread.start();
        messageHandler.start();
        messageListener.start();
    }

    public void readMessages(){
        try {
            while (clientConnected.get()) {
                Object msg = inputStream.readObject();
                if(msg instanceof GenericServerMessage){
                    if (((GenericServerMessage) msg).getType() == MessageType.RESULT){
                        messageQueue.clear();
                        ((GenericServerMessage) msg).show(view);
                        disconnect(false);
                    }
                    messageQueue.add((GenericServerMessage) msg);
                } else if (msg instanceof DisconnectionMessage) {
                    messageQueue.clear();
                    ((DisconnectionMessage) msg).show(view);
                    disconnect(false);
                }
            }
        } catch (IOException | ClassNotFoundException e){
            disconnect(true);
        }
    }

    public void handleMessages(){
        while (clientConnected.get()){
            if(!messageQueue.isEmpty()){
                GenericServerMessage msg = messageQueue.poll();
                assert msg != null;
                msg.show(view);
            }
        }
    }

    public void disconnect(boolean error){
        if(messageListener.isAlive()) messageListener.interrupt();
        if(messageHandler.isAlive()) messageHandler.interrupt();

        try {
            inputStream.close();
        } catch (IOException ignored) {}

        try {
            outputStream.close();
        } catch (IOException ignored) {}

        try {
            clientSocket.close();
        } catch (IOException ignored) {}

        if(error) view.displayErrorAndExit("An error occurred during the communication with the server, you're being disconnected! See ya!");
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}