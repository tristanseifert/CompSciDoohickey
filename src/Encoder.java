import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.*;

import java.util.*;
import java.io.*;

/**
 * Handles reading and writing of things to an encrypted file.
 * 
 * @author tristan
 */
public class Encoder {
	private File f;
	
	/**
	 * Key for the AES encryption
	 */
	private String _k = "aD1uX2NfqeYJ1yHN";
	Key aes_key;
	
	/**
	 * Creates an encoder that can read/write encoded text to/from the specified
	 * file. The file contains only encrypted text.
	 * 
	 * @param filename
	 */
	public Encoder(String filename) {
		f = new File(filename);
		
		// allocate the key value
		this.aes_key = new SecretKeySpec(this._k.getBytes(), "AES");
	}
	
	/**
	 * Writes an encoded version of the plaintext to the encoder's file.
	 * 
	 * @param plaintext
	 */
	public void write(String plaintext) {
		// encrypt using AES256
		Cipher c1 = null;
		
		try {
			c1 = Cipher.getInstance("AES");
			c1.init(Cipher.ENCRYPT_MODE, this.aes_key);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.err.println("Invalid Key !!!");
			e.printStackTrace();
		}

		// encrypt the passed in string
		byte[] encVal = {};
		
		try {
			encVal = c1.doFinal(plaintext.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		String encryptedValue = Base64.getEncoder().encodeToString(encVal);
		
		// write the string to the file
		FileWriter fw;
		try {
			fw = new FileWriter(this.f);
			fw.write(encryptedValue);
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a list of unencoded strings of text that are contained in this
	 * encoder's file.
	 * 
	 * @return A list of strings.
	 */
	public List<String> read() {
		// check if the file exists
		if(!this.f.exists()) {
			return null;
		}
		
		// read file
		FileReader dank = null;
		try {
			dank = new FileReader(this.f);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(dank); 
		
		String s, str = "";
		
		try {
			while((s = br.readLine()) != null) { 
				str += s;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		// close reader
		try {
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// decode base64
		byte[] encrypted = Base64.getDecoder().decode(str);
		
		// set up cipher for AES256 decryption
		Cipher c1 = null;
		
		try {
			c1 = Cipher.getInstance("AES");
			c1.init(Cipher.DECRYPT_MODE, this.aes_key);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.err.println("Invalid Key !!!");
			e.printStackTrace();
		}

		// decrypt
		byte[] encVal = {};
		
		try {
			encVal = c1.doFinal(encrypted);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		// parse it
		String file = new String(encVal);
		
		return splitStringToList(file);
	}
	
	/**
	 * Converts a string of text into a list of strings, splitting by each
	 * string by the newline character.
	 */
	private List<String> splitStringToList(String s) {
		return Arrays.asList(s.split("\\n"));
	}
}
