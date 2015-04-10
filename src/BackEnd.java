import java.util.*;

/**
 * Handles the messages and users, read from an encoder.
 * 
 * @author tristan
 */
public class BackEnd {
	/**
	 * Handles encoding/decoding of messages.
	 */
	Encoder enc = new Encoder("messages.bin");
	
	/**
	 * Stores lists of messages for a given username.
	 */
	HashMap<String, List<String>> messages;
	
	/**
	 * Initialises the backend by reading messages and parsing them.
	 */
	public BackEnd() {
		// initialise stuff
		this.messages = new HashMap<String, List<String>>();
		
		// read messages
		List<String> rawMsg = this.enc.read();
		
		// parse them
		if(rawMsg != null) {
			for(String msg : rawMsg) {
				String[] components = msg.split("\\t");
				
				if(this.messages.containsKey(components[0])) {
					this.messages.get(components[0]).add(components[1]);
				} else {
					ArrayList<String> a = new ArrayList<String>();
					a.add(components[1]);
					
					this.messages.put(components[0], a);
				}
			}
		}
	}
	
	/**
	 * Returns a list of messages for the given user. If the user does not have
	 * any messages, NULL is returned.
	 * 
	 * @param username String username.
	 * @return A list of messages, or NULL if there are no messages.
	 */
	public List<String> getMessages(String username) {
		return this.messages.get(username);
	}
	
	/**
	 * Adds a message for the given user.
	 * 
	 * @param username
	 * @param msg
	 */
	public void addMessage(String username, String msg) {
		// any messages for this user?
		if(this.messages.containsKey(username)) {
			this.messages.get(username).add(msg);
		} else {
			ArrayList<String> a = new ArrayList<String>();
			a.add(msg);
			this.messages.put(username, a);
		}
		
		// write
		String s = convertToString();
		this.enc.write(s);
	}
	
	/**
	 * Converts the internal kerjigger to a list of strings.
	 */
	private String convertToString() {
		String s = "";
		
		// iterate over each message
		for (String user : this.getUsers()) {
			// Get all messages by this user
			for (String message : this.getMessages(user)) {
				String kerjigger = user + "\t" + message;
				s += kerjigger + "\n";
			}
		}
		
		return s;
	}
	
	/**
	 * Gets all users with messages.
	 * 
	 * @return A list of all users.
	 */
	public List<String> getUsers() {
		Set<String> keys = this.messages.keySet();
		ArrayList<String> list = new ArrayList<String>(keys.size());
		
		for (String string : keys) {
			list.add(string);
		}
		
		return list;
	}
}
