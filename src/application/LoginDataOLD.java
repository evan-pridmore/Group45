package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Properties;

public class LoginDataOLD {
	
	private Hashtable<String, String> loginData = new Hashtable<String, String>();
	
	public Hashtable<String, String> getLoginData() {
		return loginData;
	}
	
	public void updateLoginData() throws IOException {
		try {
			// Creates BufferedWriter
			PrintWriter pWriter = new PrintWriter (new BufferedWriter(new FileWriter("loginData.txt")));
			
			// Converts loginData (Hashtable) to String type?
			
			// Iterate through entries of loginData via a for-each loop to convert and write each entry as a String to 
			// loginData.txt.
			// pWriter.write(loginData);
			
			
			pWriter.close();
		} catch (IOException ioe) {
			System.out.println(String.format("ERROR (updateLoginData): Cannot read/access file 'loginData.txt'."));
			System.out.println(ioe.getLocalizedMessage());
			ioe.printStackTrace();
		}
		
	}
	
	public void addLoginData(String addUsername, String addPassword) {
			if (addUsername != null && addPassword != null) {
				loginData.put(addUsername, addPassword);
				
			} else if (addUsername == null) {
				System.out.println(String.format("ERROR (addLoginData): Invalid username '%s'", addUsername));
				//Add custom exception?
				
			} else if (addPassword == null) {
				System.out.println(String.format("ERROR (addLoginData): Invalid username '%s'", addUsername));
				//Add custom exception?
			}
	}
	
}
