import java.util.*;
import java.lang.*;

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
	 * Initialises the backend by reading messages and parsing them.
	 */
	public BackEnd() {
		// read messages
		List<String> rawMsg = enc.read();
		
		System.out.println(rawMsg);
	}
	
	/**
	 * Returns a list of messages for the given user. If the user does not have
	 * any messages, NULL is returned.
	 * 
	 * @param username String username.
	 * @return A list of messages, or NULL if there are no messages.
	 */
	public List<String> getMessage(String username) {
		return null;
	}
	
	/**
	 * Adds a message for the given user.
	 * 
	 * @param username
	 * @param msg
	 */
	public void addMessage(String username, String msg) {
		
	}
	
	/**
	 * Gets all users with messages.
	 * 
	 * @return A list of all users.
	 */
	public List<String> getUsers() {
		return null;
	}
}
