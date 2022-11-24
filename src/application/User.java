package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**A class that creates objects which represent user profiles. The data stored in this class can be saved through the 
 * {@link #serializeUser(User)} and {@link #deserializeUser(String)} methods.
 * 
 * @author evanpridmore
 *
 */
public class User implements Serializable {

	// Add ArrayList for saved events?
	private static final long serialVersionUID = 5095466992353474477L;
	private String username;
	private String password;
	
	private ArrayList<Event> userEvents;
	
	/**A constructor that creates a new instance of User and assigns values to the instance variables username and
	 * password.
	 * 
	 * @param usernameInput
	 * @param passwordInput
	 * @throws InvalidUsernameException Thrown if the username provided is null or otherwise invalid.
	 * @throws UserAlreadyExistsException Thrown if a user has already been created under the provided user. 
	 * @throws InvalidPasswordException 
	 */
	User(String usernameInput, String passwordInput) throws InvalidUsernameException, UserAlreadyExistsException, InvalidPasswordException {
		if (!userAlreadyExists(usernameInput)) {
			setUsername(usernameInput);
			setPassword(passwordInput);
			System.out.println(String.format("User Constructor (String, String): User created with username '%s' and password '%s'.", usernameInput, passwordInput));
			
			try {
			// Once instance variables username and password are assigned, the user data is immediately saved.
			serializeUser(this);
			
			} catch (IOException ioe) {
				System.out.println(String.format("ERROR (User Constructor (String, String)): Cannot serialize user with username '%s' and password '%s'", getUsername(), getPassword()));
				System.out.println(ioe.getMessage());
			}
			
		} else 
			// If a user already exists under the provided username, a UserAlreadyExistsException is thrown. 
			throw new UserAlreadyExistsException(String.format("ERROR (User Constructor (String, String)): User already exists."));
	}
	
	public String getUsername() {
		return new String(username);
	}
	
	public String getPassword() {
		return new String(password);
	}
	
	public void setUsername(String usernameInput) throws InvalidUsernameException {
		// Checks if the provided username is null (Additional username restrictions can be added here).
		if (usernameInput != null) {
			username = new String(usernameInput);
			
		} else {
			// If the username is invalid, an InvalidUsernameException is thrown.
			System.out.println(String.format(String.format("ERROR (setUsername): Cannot set username to '%s'.", usernameInput)));
			throw new InvalidUsernameException(String.format("ERROR (setUsername): Cannot set username to '%s'.", usernameInput));
			}
	}
	
	public void setPassword(String passwordInput) throws InvalidPasswordException {
		// Checks if the provided password is null (Additional password restrictions can be added here).
		if (passwordInput != null) {
			password = new String(passwordInput);
			
		} else {
			// If the password is invalid, an InvalidPasswordException is thrown.
			System.out.println(String.format("ERROR (setPassword): Cannot set password to '%s'.", passwordInput));
			throw new InvalidPasswordException(String.format("ERROR (setPassword): Cannot set password to '%s'.", passwordInput));
		}
	}
	
	public ArrayList<Event> getEvents() {
		return new ArrayList<Event>(userEvents);
	}
	
	/**Checks if the provided username already has a user save file created.
	 * 
	 * @param inputUsername The username provided to verify that it exists or not.
	 * @return False if the username has NOT already been created as a user save file. 
	 * @return True if the username HAS already been created as a user save file. 
	 */
	public static boolean userAlreadyExists(String inputUsername) {
		File loginDataFile = new File("loginData" + inputUsername + ".ser");

		if (loginDataFile.exists()) {
			System.out.println(String.format("useralreadyExists: User login data with username '%s' already exists!", inputUsername));
			return true;
		}
		
		return false;
	}
	
	/**Creates a '.ser' file in the workspace directory, which allows the data stored in a User instance to be stored.
	 * This allows for persistent User data that can be used after the application has been closed and reopened.
	 * 
	 * @param inputUser The instance of User to be serialized (stored as a user data file).
	 * @throws IOException Occurs if there is an error saving a user data file.
	 */
	public static void serializeUser(User inputUser) throws IOException {
		// An instance of File is created to reference the desired user save file data, unique to the user.
		File loginDataFile = new File("loginData" + inputUser.getUsername() + ".ser");
		
		try {
			// Not mentioned in class material, but a FileOutputStream is necessary to write/create these user data files.
			FileOutputStream fileOut = new FileOutputStream(loginDataFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(inputUser);
			
			out.close();
			fileOut.close();
			
			System.out.println(String.format("serializeUser: Successfully saved login data '%s', '%s' to '%s'.", inputUser.getUsername(), inputUser.getPassword(), loginDataFile));
			
		} catch (IOException ioe) {
			System.out.println(String.format("ERROR serializeUser: Failed to save login data '%s', '%s' to '%s'.", inputUser.getUsername(), inputUser.getPassword(), loginDataFile));
		}
	}
	
	/**Reads '.ser' files in the workspace directory based off of a provided username. This returns an instance of user
	 * with the user data saved in these .ser files.
	 * 
	 * @param inputUsername
	 * @return Returns a user created from the read .ser file data.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws UserDoesNotExistException
	 */
	public static User deserializeUser(String inputUsername) throws ClassNotFoundException, IOException, UserDoesNotExistException {
		User outputUser = null;
		
		// An instance of File is created to reference the desired user save file data, unique to the user.
		File loginDataFile = new File("loginData" + inputUsername + ".ser");
		
		// Is this if-statement necessary? Or is it simpler to rely on throwing an IOException? 
		if (loginDataFile.exists() && loginDataFile.canRead()) {
			System.out.println(String.format("deserializeUser: data for username '%s' from file '%s' exists and can be read.", inputUsername, loginDataFile));
			
			try {
				// Not mentioned in class material, but a FileInputStrea, is necessary to write/create these user data files.
				FileInputStream fileIn = new FileInputStream(loginDataFile);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				outputUser = (User) in.readObject();
				
				in.close();
				fileIn.close();
				
				System.out.println(String.format("deserializeUser: Successfully read data for username '%s' from file '%s'.", inputUsername, loginDataFile));
				
			} catch (IOException ioe) {
				System.out.println(String.format("ERROR deserializeUser: Failed to read data for username '%s' from file '%s'.", inputUsername, loginDataFile));
				System.out.println(ioe.getMessage());
				
			} catch (ClassNotFoundException cnfe) {
				System.out.println(String.format("ERROR deserializeUser: Failed to read data from user object '%s'", loginDataFile));
				System.out.println(cnfe.getMessage());
			}
			
		} else {
			throw new UserDoesNotExistException(String.format("ERROR deserializeUser: file '%s' either does not exist or cannot be read.", loginDataFile));
		}
		
		return outputUser;
	}
	
}
