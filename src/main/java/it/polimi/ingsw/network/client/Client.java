package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.network.messages.connectionMessages.Ping;
import it.polimi.ingsw.network.messages.serverMessages.GenericServerMessage;
import it.polimi.ingsw.observer.Observable;
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

public class Client extends Observable {
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

    }

    public void init() throws IOException {
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(ip, port));
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        clientConnected.set(true);
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
            e.printStackTrace();
        }
    }

    public void handleMessages(){
        while (clientConnected.get()){
            if(!messageQueue.isEmpty()){
                GenericServerMessage msg = messageQueue.poll();
                msg.show(view);
            }
        }
    }

    public void disconnect(){

    }
}