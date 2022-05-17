package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.ClientController;
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
    private String ip;
    private int port;
    private ViewInterface view;
    private final int PING_TIME = 5000;
    private Thread pingThread;
    private AtomicBoolean clientConnected = new AtomicBoolean(false);
    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final Queue<GenericServerMessage> messageQueue = new LinkedList();
    private Thread messageListener;
    private Thread messageHandler;
    private ClientController clientController;

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
            } catch (IOException e) {
                disconnect();
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
                    messageQueue.add((GenericServerMessage) msg);
                }
            }
        } catch (IOException | ClassNotFoundException e){
            disconnect();
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

    public void disconnect(){
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

        view.displayErrorAndExit("An error occurred during the communication with the server, you're being disconnected! See ya!");
    }
}