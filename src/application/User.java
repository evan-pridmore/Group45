package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable {

	private String username;
	private String password;
	
	User(String usernameInput, String passwordInput) {
		
	}
	
	public String getUsername() {
		return new String(username);
	}
	
	public String getPassword() {
		return new String(password);
	}
	
	public void setUsername(String usernameInput) {
		if (usernameInput != null) {
			username = usernameInput;
		} else {
			System.out.println(String.format("ERROR (setUsername): Cannot set username to '%s'.", usernameInput));
			//throw custom exception (invalidUsernameInput)
		}
	}
	
	public void setPassword(String passwordInput) {
		if (passwordInput != null) {
			password = passwordInput;
		} else {
			System.out.println(String.format("ERROR (setPassword): Cannot set password to '%s'.", passwordInput));
			//throw custom exception (invalidUsernameInput)
		}
	}
	
	public static void serializeUser(User inputUser) throws IOException {
		try {
			FileOutputStream fileOut = new FileOutputStream("loginData" + inputUser + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(inputUser);
			out.close();
			fileOut.close();
			System.out.println("(User) Saved login data '%s', '%s' to 'loginData.ser'.");
			
		} catch (IOException ioe) {
			System.out.println("ERROR (serializeUser): Cannot save user data to loginData.ser");
		}
	}
	
	public static User deserializeUser(String inputUsername) throws ClassNotFoundException, IOException, UserDoesNotExistException {
		User outputUser = null;
		
		File loginData = new File("loginData" + inputUsername + ".ser");
				
		if (loginData.exists() && loginData.canRead()) {
			System.out.println(String.format("deserializeUser: data for username '%s' from file '%s'", inputUsername, loginData));
			try {
				FileInputStream fileIn = new FileInputStream(loginData);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				outputUser = (User) in.readObject();
				
				in.close();
				fileIn.close();
				
			} catch (IOException ioe) {
				System.out.println(String.format("ERROR (deserializeUser): Cannot read user data from '%s'", loginData));
				System.out.println(ioe.getMessage());
				
			} catch (ClassNotFoundException cnfe) {
				System.out.println(String.format("ERROR (deserializeUser): Cannot read user data from '%s'", loginData));
				System.out.println(cnfe.getMessage());
				
			}
		} else {
			throw new UserDoesNotExistException(String.format("ERROR (deserializeUser): file '%s' either does not exist or cannot be read.", loginData));
		}
		
		return outputUser;
	}
	
}
