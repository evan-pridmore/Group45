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
	
	User(String usernameInput, String passwordInput) throws InvalidUsernameException, UserAlreadyExistsException {
		if (!userAlreadyExists(usernameInput)) {
			setUsername(usernameInput);
			setPassword(passwordInput);
			System.out.println(String.format("User Constructor (String, String): User created with username '%s' and password '%s'.", usernameInput, passwordInput));
			// Add exception handling (invalidUsernameException & invalidPasswordException?)

			try {
			serializeUser(this);
			
			} catch (IOException ioe) {
				System.out.println(String.format("ERROR (User Constructor (String, String)): Cannot serialize user with username '%s' and password '%s'", getUsername(), getPassword()));
				System.out.println(ioe.getMessage());
			}
		} else 
			throw new UserAlreadyExistsException(String.format("ERROR (User Constructor (String, String)): User already exists."));
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
			//throw custom exception (invalidUsernameException?)
		}
	}
	
	public void setPassword(String passwordInput) {
		if (passwordInput != null) {
			password = passwordInput;
		} else {
			System.out.println(String.format("ERROR (setPassword): Cannot set password to '%s'.", passwordInput));
			//throw custom exception (invalidPasswordException?)
		}
	}
	
	public static boolean userAlreadyExists(String inputUsername) {
		File loginDataFile = new File("loginData" + inputUsername + ".ser");

		if (loginDataFile.exists()) {
			System.out.println(String.format("alreadyExists: User login data with username '%s' already exists!", inputUsername));
			return true;
		}
		return false;
	}
	
	public static void serializeUser(User inputUser) throws IOException {
		File loginDataFile = new File("loginData" + inputUser.getUsername() + ".ser");
		
		try {
			FileOutputStream fileOut = new FileOutputStream(loginDataFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(inputUser);
			out.close();
			fileOut.close();
			System.out.println("(User) Saved login data '%s', '%s' to '" + loginDataFile + "'.");
			
		} catch (IOException ioe) {
			System.out.println("ERROR (serializeUser): Cannot save user data to ''" + loginDataFile + "'.");
		}
	}
	
	public static User deserializeUser(String inputUsername) throws ClassNotFoundException, IOException, UserDoesNotExistException {
		User outputUser = null;
		
		File loginDataFile = new File("loginData" + inputUsername + ".ser");
				
		if (loginDataFile.exists() && loginDataFile.canRead()) {
			System.out.println(String.format("deserializeUser: data for username '%s' from file '%s'", inputUsername, loginDataFile));
			try {
				FileInputStream fileIn = new FileInputStream(loginDataFile);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				outputUser = (User) in.readObject();
				
				in.close();
				fileIn.close();
				
			} catch (IOException ioe) {
				System.out.println(String.format("ERROR (deserializeUser): Cannot read user data from '%s'", loginDataFile));
				System.out.println(ioe.getMessage());
				
			} catch (ClassNotFoundException cnfe) {
				System.out.println(String.format("ERROR (deserializeUser): Cannot read user data from '%s'", loginDataFile));
				System.out.println(cnfe.getMessage());
				
			}
		} else {
			throw new UserDoesNotExistException(String.format("ERROR (deserializeUser): file '%s' either does not exist or cannot be read.", loginDataFile));
		}
		
		return outputUser;
	}
	
}
