package GameNetwork;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server extends Model {
    private ServerSocket serverSocket;
    private int newID;
    private Socket newSocket;
    public Server(int port) {
        setBroadcastID(-1);
        setMyID(0);
        newID = 1;
        setOutputs(new HashMap<>());
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Socket getNewSocket() {
        return newSocket;
    }

    @Override
    public void start() {
        int a = 2;
        while (a==2) {

            try {
                System.out.println("begining to accept in server");

                newSocket = serverSocket.accept();
                System.out.println("accepted");
                ObjectOutputStream newOutput = new ObjectOutputStream(
                        newSocket.getOutputStream());
                ObjectInputStream newInput = new ObjectInputStream(
                        newSocket.getInputStream());
                offerString("connected");
                offerString("new clients ID is:" + newID);
                HashMap<Integer, ObjectOutputStream> outputs = getOutputs();
                outputs.put(newID, newOutput);
                setOutputs(outputs);
                newOutput.writeInt(getMyID());
                newOutput.writeInt(newID);
                newOutput.writeInt(getBroadcastID());
                newOutput.flush();
                newID++;
                createNewInput(newInput);
                a=3;
            } catch (IOException e) {
                e.printStackTrace();
            }
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

                        if (readedMessage.getReceiversID() == getMyID()) {
                            offerMessage(readedMessage);
                        } else if (readedMessage.getReceiversID() == getBroadcastID()) {
                            for (ObjectOutputStream output : getOutputs().values()) {
                                output.writeObject(readedMessage);
                                output.flush();
                            }
                            offerMessage(readedMessage);
                        } else {
                            ObjectOutputStream toSend = getOutputs()
                                    .get(readedMessage.getReceiversID());
                            toSend.writeObject(readedMessage);
                            toSend.flush();
                        }
                    } catch (Exception e) {

                    }
                }
            }

        });
        newThread.start();
    }

}
