package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import application.Exceptions.EventOutsideTimeUnitException;
import application.Exceptions.InvalidPasswordException;
import application.Exceptions.InvalidUsernameException;
import application.Exceptions.NullEventEndPointException;
import application.Exceptions.UserAlreadyExistsException;
import application.Exceptions.UserDoesNotExistException;
import application.TimeUnits.Event;
import application.TimeUnits.InstantEvent;
import application.TimeUnits.TimedEvent;
import application.TimeUnits.Week;

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
	
	private ArrayList<Week> userEvents = new ArrayList<Week>();
	
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
			userEvents = new ArrayList<Week>(); 
			System.out.println(String.format("User Constructor (String, String): User created with username '%s' and password '%s'.", usernameInput, passwordInput));
			
			// Once instance variables username and password are assigned, the user data is immediately saved.
			serializeUser(this);
			
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
		if (usernameInput != null && !(usernameInput.trim().isEmpty())) {
			username = new String(usernameInput);
			
		} else {
			// If the username is invalid, an InvalidUsernameException is thrown.
			throw new InvalidUsernameException(String.format("ERROR (setUsername): Cannot set username to '%s'.", usernameInput));
			}
	}
	
	public void setPassword(String passwordInput) throws InvalidPasswordException {
		// Checks if the provided password is null (Additional password restrictions can be added here).
		if (passwordInput != null && !(passwordInput.trim().isEmpty())) {
			password = new String(passwordInput);
			
		} else {
			// If the password is invalid, an InvalidPasswordException is thrown.
			throw new InvalidPasswordException(String.format("ERROR (setPassword): Cannot set password to '%s'.", passwordInput));
		}
	}
	
	public ArrayList<Week> getEvents() {
		return userEvents;
	}
	
	/**
	 * Adds and event to the user's userEvents ArrayList ordered based on start date.
	 * @param newEvent The event to add to the user.
	 * @throws EventOutsideTimeUnitException 
	 * @throws NullEventEndPointException 
	 */
	public void addEvent(Event newEvent) throws NullEventEndPointException, EventOutsideTimeUnitException {
		if (userEvents.size() > 0) {
			if (newEvent instanceof InstantEvent) {
				for (Week week : userEvents) {
					if (week.contains(newEvent)) {
						week.addEvent(newEvent);
						return;
					}
				}
				Week newWeek = new Week(newEvent.getStart());
				newWeek.addEvent(newEvent);
				userEvents.add(newWeek);
				return;
				}
			else if (newEvent instanceof TimedEvent) {
				for (Week week : userEvents) {
					if (newEvent.containedIn(week)) {
						week.addEvent(newEvent);
						return;
					}
					else if (newEvent.startsIn(week) || newEvent.endsIn(week)) {
						TimedEvent firstPart = new TimedEvent(newEvent.getStart(), week.getEnd(), newEvent.getName(), newEvent.getColour());
						TimedEvent secondPart = new TimedEvent(new Date(week.getEnd().getTime() + 1), newEvent.getEnd(),newEvent.getName(), newEvent.getColour());
						addEvent(firstPart);
						addEvent(secondPart);
						return;
					}
				}
	
				Week newWeek = new Week(newEvent.getStart());
				userEvents.add(newWeek);
				if (newEvent.containedIn(newWeek)) {
					newWeek.addEvent(newEvent);
					return;
				}
				else {
					TimedEvent firstPart = new TimedEvent(newEvent.getStart(), newWeek.getEnd(), newEvent.getName(), newEvent.getColour());
					TimedEvent secondPart = new TimedEvent(new Date(newWeek.getEnd().getTime() + 1), newEvent.getEnd(),newEvent.getName(), newEvent.getColour());
					addEvent(firstPart);
					addEvent(secondPart);
					return;
				}		
			}
		}
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
			System.out.println(String.format("ERROR (useralreadyExists): User login data with username '%s' already exists!", inputUsername));
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
	public static void serializeUser(User inputUser) {
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
	public static User deserializeUser(String inputUsername) throws UserDoesNotExistException {
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
