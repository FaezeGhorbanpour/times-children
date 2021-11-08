package GameNetwork;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Client extends Model {
    private int serversID;
    private String serversIP;
    private int serversPort;
    private Socket newSocket;

    public Client(int port, String serversIP) {
        setOutputs(new HashMap<>());
        this.serversIP = serversIP;
        serversPort = port;
    }

    public Socket getNewSocket() {
        return newSocket;
    }

    public void start() {
        try {
            newSocket = new Socket(serversIP, serversPort);
            ObjectOutputStream output = new ObjectOutputStream(
                    newSocket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(
                    newSocket.getInputStream());
            offerString("connected");
            serversID = input.readInt();
            HashMap<Integer, ObjectOutputStream> outputs = getOutputs();
            outputs.put(serversID, output);
            setOutputs(outputs);
            setMyID(input.readInt());
            setBroadcastID(input.readInt());
            offerString("your ID is: " + getMyID());
            offerString("servers ID is: " + serversID);
            createNewInput(input);
        } catch (IOException e) {

        }

    }

    @Override
    public void createNewInput(ObjectInputStream newInput) {
        Thread newThread = new Thread(new Runnable() {

            @Override
            public void run() {
                Object readedObject;
                Message readedMessage;
                while (true) {
                    try {
                        readedObject = newInput.readObject();
                        readedMessage = (Message) readedObject;
                        if (readedMessage.getSendersID() != getMyID())
                            offerMessage(readedMessage);
                    } catch (Exception e) {

                    }
                }
            }

        });
        newThread.start();
    }

    public int getServersID() {
        return serversID;
    }
}
