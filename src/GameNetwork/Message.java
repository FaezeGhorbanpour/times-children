package GameNetwork;
import java.io.Serializable;


public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6249237918670996703L;
	private String message;
	private int sendersID;
	private int receiversID;
	public Message(String message, int sendersID, int receiversID){
		this.setSendersID(sendersID);
		this.setMessage(message);
		this.setReceiversID(receiversID);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getSendersID() {
		return sendersID;
	}
	public void setSendersID(int sendersID) {
		this.sendersID = sendersID;
	}
	public int getReceiversID() {
		return receiversID;
	}
	public void setReceiversID(int receiversID) {
		this.receiversID = receiversID;
	}
}
