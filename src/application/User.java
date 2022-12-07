package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.temporal.ChronoField;
import java.util.ArrayList;

import application.Exceptions.EventOutsideTimeUnitException;
import application.Exceptions.InvalidPasswordException;
import application.Exceptions.InvalidUsernameException;
import application.Exceptions.NullEventEndPointException;
import application.Exceptions.PasswordTooLongException;
import application.Exceptions.PasswordTooShortException;
import application.Exceptions.UserAlreadyExistsException;
import application.Exceptions.UserDoesNotExistException;
import application.Exceptions.UsernameTooLongException;
import application.Exceptions.UsernameTooShortException;
import application.TimeUnits.Day;
import application.TimeUnits.Event;
import application.TimeUnits.TimedEvent;
import application.TimeUnits.Week;

/**A class that creates objects which represent user profiles. The data stored in this class can be saved through the 
 * {@link #serializeUser(User)} and {@link #deserializeUser(String)} methods.
 * 
 * <p> Implements {@link Serializable}.
 * 
 * @author evan-pridmore
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
	 * @param usernameInput The provided username.
	 * @param passwordInput The provided password.
	 * 
	 * @throws UserAlreadyExistsException Thrown if a user has already been created under the provided user. 
	 * @throws InvalidUsernameException Thrown if the username provided is null or otherwise invalid.
	 * @throws InvalidPasswordException Thrown if the username provided is null or otherwise invalid.
	 * @throws UsernameTooShortException Thrown if the username provided is less than 2 characters.
	 * @throws PasswordTooShortException Thrown if the password provided is less than 4 characters.
	 * @throws UsernameTooLongException  Thrown if the username provided is greater than 15 characters.
	 * @throws PasswordTooLongException  Thrown if the passowrd provided is greater than 15 characters.
	 */
	User(String usernameInput, String passwordInput) throws InvalidUsernameException, UserAlreadyExistsException, InvalidPasswordException, PasswordTooShortException, UsernameTooLongException, UsernameTooShortException, PasswordTooLongException {
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
	
	/**Sets the username of a new user, if the input meets the following criteria:
	 * <br><tab>--> Is not null or contains only white space.
	 * <br><tab>--> Has a length greater than or equal to 2
	 * <br><tab>--> Has a length less than or equal to 15.
	 * 
	 * @param usernameInput
	 * @throws InvalidUsernameException
	 * @throws UsernameTooLongException
	 * @throws UsernameTooShortException
	 */
	public void setUsername(String usernameInput) throws InvalidUsernameException, UsernameTooLongException, UsernameTooShortException {
		// Checks if the provided username is null (Additional username restrictions can be added here).
		if ((usernameInput != null && !(usernameInput.trim().isEmpty())) && usernameInput.length() >= 2 && usernameInput.length() <= 15) {
			username = new String(usernameInput);
			
		} else if (usernameInput.length() < 2) {
			// If the password is less than 2 characters (i.e., too short), an UsernameTooShortException is thrown.
			throw new UsernameTooShortException(String.format("ERROR (setUsername): Username '%s' is too short.", usernameInput));
		
		} else if (usernameInput.length() > 15) {
			// If the username is greater than 15 characters (i.e., too long), a UsernameTooLongException is thrown.
			throw new UsernameTooLongException(String.format("ERROR (setUsername): Username '%s' is too long.", usernameInput));
			
		} else {
			// If the username is invalid, an InvalidUsernameException is thrown.
			throw new InvalidUsernameException(String.format("ERROR (setUsername): Cannot set username to '%s'.", usernameInput));
			}
	}
	
	/**Sets the password of a new user, if the input meets the following criteria:
	 * <br><tab>--> Is not null or contains only white space.
	 * <br><tab>--> Has a length greater than or equal to 4.
	 * <br><tab>--> Has a length less than or equal to 15.
	 * 
	 * @param passwordInput
	 * @throws InvalidPasswordException
	 * @throws PasswordTooShortException
	 * @throws PasswordTooLongException
	 */
	public void setPassword(String passwordInput) throws InvalidPasswordException, PasswordTooShortException, PasswordTooLongException {
		// Checks if the provided password is null (Additional password restrictions can be added here).
		if ((passwordInput != null && !(passwordInput.trim().isEmpty())) && passwordInput.length() >= 4 && passwordInput.length() <= 15) {
			password = new String(passwordInput);
			
		} else if (passwordInput.length() < 4) {
			// If the password is less than 4 characters (i.e., too short), an PasswordTooShortException is thrown.
			throw new PasswordTooShortException(String.format("ERROR (setPassword): Password '%s' is too short.", passwordInput));
			
		} else if (passwordInput.length() > 15) {
			// If the password is greater than 15 characters (i.e., too long), an PasswordTooLongException is thrown.
			throw new PasswordTooLongException(String.format("ERROR (setPassword): Password '%s' is too long.", passwordInput));
			
		} else {
			// If the password is invalid, an InvalidPasswordException is thrown.
			throw new InvalidPasswordException(String.format("ERROR (setPassword): Cannot set password to '%s'.", passwordInput));
		}
	}
	
	public ArrayList<Week> getEvents() {
		return userEvents;
	}
	
	/** A method used to display <b>all</b> events stored in a user in the console.
	 * 
	 * <p><u>Only used for diagnostic purposes in the console.</u>
	 */
	public void dumpEvents() {
		System.out.println(String.format("%ndumpEvents: Printing all events for user %s...", this.getUsername()));
    	for (Week w : this.getEvents()) {
    		System.out.println("--> Week number " + w.getStart().get(ChronoField.ALIGNED_WEEK_OF_YEAR) + " exists.");
    		for (int dayIndex = 1 ; dayIndex < 8; dayIndex ++) {
    			System.out.println("	--> '" + dayIndex + "' has '" + w.getDay(dayIndex).getEvents().size() + "' events.");
    			for (Event e : w.getDay(dayIndex).getEvents()) {
    				System.out.println("		--> Event with name '" + e.getName() + "' exists. (" + e.toString() + ")");
    			}
    		}
    	}
	}
	
	/**
	 * Adds and event to the user's userEvents ArrayList ordered based on start date.
	 * @param newEvent The event to add to the user.
	 * @throws EventOutsideTimeUnitException 
	 * @throws NullEventEndPointException
	 * @return Always true not for external use;
	 */
	public boolean addEvent(Event newEvent) throws NullEventEndPointException, EventOutsideTimeUnitException {

		if (userEvents.size() > 0) {
			for (Week week : userEvents) {
				if (newEvent.containedIn(week)) {
					week.addEvent(newEvent);
					return true;
				}
				else if (newEvent.startsIn(week)) {
					TimedEvent firstPart = new TimedEvent(newEvent.getStart(), week.getEnd(), newEvent.getName(), newEvent.getColour());
					TimedEvent secondPart = new TimedEvent(week.getEnd().plusNanos(1000000000), newEvent.getEnd(), newEvent.getName(), newEvent.getColour());
					addEvent(firstPart);
					addEvent(secondPart);
					return true;
					
				}
			}
		}
		System.out.println("Week not found in user, creating new week.");
		Week newWeek = new Week(newEvent.getStart());
		userEvents.add(newWeek);
		addEvent(newEvent);
		return true;
	}
	
	public void removeEvent(Event eventToDelete) {
		int weekToRemove = -1;
		for (Week week : userEvents) {
			int filledDays = 0;
			for (Day day : week.getDays()) {
				if (day.getEvents().size() > 0) {
					filledDays += 1;
					int eventToRemove = -1;
					for (Event event : day.getEvents()) {
						if (event.equals(eventToDelete))
							eventToRemove = day.getEvents().indexOf(event);
					}
					if (eventToRemove >= 0)
						day.getEvents().remove(eventToRemove);
				}
			}
			if (filledDays == 0)
				userEvents.indexOf(week);
		}
		if (weekToRemove >= 0)
			userEvents.remove(weekToRemove);
	}
	
	
	/**Checks if the provided username already has a user save file created.
	 * 
	 * @param inputUsername The username provided to verify that it exists or not.
	 * @return False if the username has NOT already been created as a user save file. 
	 * @return True if the username HAS already been created as a user save file. 
	 */
	public static boolean userAlreadyExists(String inputUsername) {
		File userDataFile = new File("User Data/" + inputUsername + ".ser");
		
		if (userDataFile.exists()) {
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
		File userDataDir = new File("User Data");
		File userDataFile = new File("User Data/" + inputUser.getUsername() + ".ser");
		
		if (!userDataDir.exists()) {
			System.out.println("The directory " + userDataDir.getAbsolutePath() + " does NOT exist. Creating directory...");
			userDataDir.mkdir();
		} else {
			System.out.println("The directory " + userDataDir.getAbsolutePath() + " exists.");

		}
		
		try {
			// Not mentioned in class material, but a FileOutputStream is necessary to write/create these user data files.
			FileOutputStream fileOut = new FileOutputStream(userDataFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(inputUser);
			
			out.close();
			fileOut.close();
			
			System.out.println(String.format("serializeUser: Successfully saved user data '%s', '%s' to '%s'.", inputUser.getUsername(), inputUser.getPassword(), userDataFile));
			
		} catch (IOException ioe) {
			System.out.println(String.format("ERROR serializeUser: Failed to save user data '%s', '%s' to '%s'.", inputUser.getUsername(), inputUser.getPassword(), userDataFile));
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
		File userDataFile = new File("User Data/" + inputUsername + ".ser");
		
		// Is this if-statement necessary? Or is it simpler to rely on throwing an IOException? 
		if (userDataFile.exists() && userDataFile.canRead()) {
			System.out.println(String.format("deserializeUser: data for username '%s' from file '%s' exists and can be read.", inputUsername, userDataFile));
			
			try {
				// Not mentioned in class material, but a FileInputStrea, is necessary to write/create these user data files.
				FileInputStream fileIn = new FileInputStream(userDataFile);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				outputUser = (User) in.readObject();
				
				in.close();
				fileIn.close();
				
				System.out.println(String.format("deserializeUser: Successfully read data for username '%s' from file '%s'.", inputUsername, userDataFile));
				
			} catch (IOException ioe) {
				System.out.println(String.format("ERROR deserializeUser: Failed to read data for username '%s' from file '%s'.", inputUsername, userDataFile));
				System.out.println(ioe.getMessage());
				
			} catch (ClassNotFoundException cnfe) {
				System.out.println(String.format("ERROR deserializeUser: Failed to read data from user object '%s'", userDataFile));
				System.out.println(cnfe.getMessage());
			}
			
		} else {
			throw new UserDoesNotExistException(String.format("ERROR deserializeUser: file '%s' either does not exist or cannot be read.", userDataFile));
		}
		
		return outputUser;
	}
}
