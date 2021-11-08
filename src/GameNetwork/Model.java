package GameNetwork;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Model {
	private int broadcastID;
	private int myID;
	private HashMap<Integer, ObjectOutputStream> outputs;
	private Queue<Message> toShowMessagesQueue = new LinkedList<>();
	private Queue<String> toShowStringsQueue = new LinkedList<>();

	public void offerString(String str) {
		toShowStringsQueue.offer(str);
	}

	public void offerMessage(Message message) {
		toShowMessagesQueue.offer(message);
	}

	public int getMyID() {
		return myID;
	}

	public HashMap<Integer, ObjectOutputStream> getOutputs() {
		return outputs;
	}

	public int getBroadcastID() {
		return broadcastID;
	}

	public void setBroadcastID(int boradcastID) {
		this.broadcastID = boradcastID;
	}

	public void setMyID(int myID) {
		this.myID = myID;
	}

	public void setOutputs(HashMap<Integer, ObjectOutputStream> outputs) {
		this.outputs = outputs;
	}
	public abstract void start();
	public abstract void createNewInput(ObjectInputStream newInput);

	public String pollString() {
		return toShowStringsQueue.poll();
	}

	public Message pollMessage() {
		return toShowMessagesQueue.poll();
	}
}
