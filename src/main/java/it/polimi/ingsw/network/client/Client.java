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

/**
 * This class is used to manage the connection and communication of a client
 * with the server.
 */
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
    private boolean movingMN = false;
    private boolean movingStud = false;
    private boolean choosingChar = false;

    /**
     * Class constructor. Sets server IP and port and builds the threads
     * needed for messages (listener, handler and ping).
     *
     * @param ip        the IP address.
     * @param port      the port.
     * @param view      a ViewInterface.
     */
    public Client(String ip, int port, ViewInterface view) {
        this.ip = ip;
        this.port = port;
        this.view = view;

        messageListener = new Thread(this::readMessages);
        messageHandler = new Thread(this::handleMessages);

        pingThread = new Thread(() -> {
            while (clientConnected.get()) {
                try {
                    Thread.sleep(PING_TIME);
                    sendMsgToServer(new Ping());
                } catch (InterruptedException ignored) {
                    disconnect(true);
                }
            }
        });
    }

    /**
     * Sends a message to the server.
     *
     * @param message       the message to be sent.
     */
    public void sendMsgToServer(Serializable message) {
        if (clientConnected.get()) {
            try {
                outputStream.writeObject(message);
                outputStream.flush();
                outputStream.reset();
            } catch (IOException e) {
                disconnect(true);
            }
        }
    }

    /**
     * Initializes client socket and objects, starts the threads needed for
     * messages handling throughout the game.
     *
     * @throws IOException  if an error occurs during the connection.
     */
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

    /**
     * Reads the received messages and adds them to the message queue.
     * Handles RESULT and DISCONNECTION messages differently, displaying a message
     * to the user and then disconnecting.
     */
    public void readMessages() {
        try {
            while (clientConnected.get()) {
                Object msg = inputStream.readObject();
                if(msg instanceof GenericServerMessage){
                    if (((GenericServerMessage) msg).getType() == MessageType.RESULT) {
                        messageQueue.clear();
                        ((GenericServerMessage) msg).show(view);
                        disconnect(false);
                    }
                    messageQueue.add((GenericServerMessage) msg);

                    if (((GenericServerMessage) msg).getType() == MessageType.MNSTEPS_REQUEST) {
                        movingMN = true;
                        choosingChar = false;
                        movingStud = false;
                    } else if (((GenericServerMessage) msg).getType() == MessageType.CHARACTER_REQUEST) {
                        movingMN = false;
                        choosingChar = true;
                        movingStud = false;
                    }
                    else if (((GenericServerMessage) msg).getType() == MessageType.PLACE_REQUEST) {
                        movingMN = false;
                        choosingChar = false;
                        movingStud = true;
                    }

                } else if (msg instanceof DisconnectionMessage) {
                    messageQueue.clear();
                    ((DisconnectionMessage) msg).show(view);
                    disconnect(false);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            disconnect(true);
        }
    }

    /**
     * Handles the received messages removing them from the queue and showing them.
     */
    public void handleMessages() {
        while (clientConnected.get()) {
            if (!messageQueue.isEmpty()) {
                GenericServerMessage msg = messageQueue.poll();
                assert msg != null;
                msg.show(view);
            }
        }
    }

    /**
     * Handles the disconnection of the client.
     *
     * @param error         a boolean whose value is:
     *                      <p>
     *                      -{@code true} if an error occurred;
     *                      </p> <p>
     *                      -{@code false} otherwise.
     *                      </p>
     */
    public void disconnect(boolean error) {
        if (clientConnected.get()) {
            clientConnected.set(false);
            if (messageListener.isAlive()) messageListener.interrupt();
            if (messageHandler.isAlive()) messageHandler.interrupt();

            try {
                inputStream.close();
            } catch (IOException ignored) {}

            try {
                outputStream.close();
            } catch (IOException ignored) {}

            try {
                clientSocket.close();
            } catch (IOException ignored) {}

            if (error) view.displayErrorAndExit("An error occurred during the communication with the server, you're being disconnected! See ya!");
        }
    }

    /**
     * Sets the nickname.
     *
     * @param nickname      the chosen nickname.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns the nickname.
     *
     * @return              this Client's nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the boolean value movingMN after sending the
     * {@link it.polimi.ingsw.network.messages.clientMessages.MNStepsReply MNSteps Reply}.
     *
     * @param movingMN      a boolean whose value is:
     *                      <p>
     *                      -{@code false} if the sent reply is a MNStepsReply;
     *                      </p> <p>
     *                      -{@code true} otherwise.
     *                      </p>
     */
    public void setMovingMN(boolean movingMN) {
        this.movingMN = movingMN;
    }

    /**
     * Returns a boolean that states whether the sent message is a
     * {@link it.polimi.ingsw.network.messages.serverMessages.MNStepsRequest MNSteps Request}
     * or not.
     *
     * @return              a boolean whose value is:
     *                      <p>
     *                      -{@code true} if the sent message is a MNStepsRequest;
     *                      </p> <p>
     *                      -{@code false} otherwise.
     *                      </p>
     */
    public boolean isMovingMN() {
        return movingMN;
    }

    /**
     * Sets the boolean value movingStud after sending the
     * {@link it.polimi.ingsw.network.messages.clientMessages.PlaceReply Place Reply}.
     *
     * @param movingStud    a boolean whose value is:
     *                      <p>
     *                      -{@code false} if the sent reply is a PlaceReply;
     *                      </p> <p>
     *                      -{@code true} otherwise.
     *                      </p>
     */
    public void setMovingStud(boolean movingStud) {
        this.movingStud = movingStud;
    }

    /**
     * Returns a boolean that states whether the sent message is a
     * {@link it.polimi.ingsw.network.messages.serverMessages.PlaceRequest Place Request}
     * or not.
     *
     * @return              a boolean whose value is:
     *                      <p>
     *                      -{@code true} if the sent message is a PlaceRequest;
     *                      </p> <p>
     *                      -{@code false} otherwise.
     *                      </p>
     */
    public boolean isMovingStud() {
        return movingStud;
    }

    /**
     * Sets the boolean value choosingChar after sending the
     * {@link it.polimi.ingsw.network.messages.clientMessages.CharacterReply Character Reply}.
     *
     * @param choosingChar  a boolean whose value is:
     *                      <p>
     *                      -{@code false} if it the sent message is a CharacterReply;
     *                      </p> <p>
     *                      -{@code true} otherwise.
     *                      </p>
     */
    public void setChoosingChar(boolean choosingChar) {
        this.choosingChar = choosingChar;
    }

    /**
     * Returns a boolean that states whether the sent message is a
     * {@link it.polimi.ingsw.network.messages.serverMessages.CharacterRequest Character Request}
     * or not.
     *
     * @return              a boolean whose value is:
     *                      <p>
     *                      -{@code true} if it the sent message is a CharacterRequest;
     *                      </p> <p>
     *                      -{@code false} otherwise.
     *                      </p>
     */
    public boolean isChoosingChar() {
        return choosingChar;
    }
}