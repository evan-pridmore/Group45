package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class LoginViewController {
	
	@FXML
    private TextField loginUsername;
	
	@FXML
    private TextField loginPassword;
	
	@FXML
	private Label loginErrorLabel;
		
	/**This method handles the On Action event attemptLogin from the GUI of loginView.
	 * 
	 * @param loginEvent The event of the 'login' button being pressed, provided by the GUI.
	 */
	@FXML
	public void attemptLogin(ActionEvent loginEvent) {
		loginErrorLabel.setText("");
		System.out.println(String.format("Attempted Login:%n--> Username: '%s'%n--> Password: '%s'", loginUsername.getText(), loginPassword.getText()));
		
		try {
			// Gets reference to a instance of User based on the username input through the loginUsername TextField.
			User currentUser = User.deserializeUser(loginUsername.getText());
			
			// Checks whether the password of this instance of User and compares whether its password equals the password 
			// entered in loginPassword. 
			if (currentUser.getPassword().equals(loginPassword.getText())) {
				System.out.println(String.format("Logged in user '%s'!", currentUser.getUsername()));
				loginErrorLabel.setText(String.format("Logged in user '%s'!", currentUser.getUsername()));
				
				// This login event is then passed onto Main to change the scene of the application to the CalendarView.
				// A reference to this user is forwarded to this application to provide access to the events/data
				// associated with that user.
				Main.initializeCalendarView(loginEvent, currentUser);
			
			// If the password is incorrect, the label is updated to reflect this.
			} else {
				System.out.println("Incorrect password!");
				loginErrorLabel.setText("Incorrect password!");
			}
			
		} catch (ClassNotFoundException | IOException e) {
			// This should be a custom exception?
			System.out.println(String.format("ERROR (attemptLogin): Cannot deserialize user '%s'. ", loginUsername.getText()));
			System.out.println(e.getMessage());
			
		} catch (UserDoesNotExistException udne) {
			System.out.println(String.format("ERROR (attemptLogin): User '%s' does not exist.", loginUsername.getText()));
			loginErrorLabel.setText(udne.getMessage());
		}
	} 
	
	/**This method handles the On Action event signUp from the GUI of loginView.
	 * Creates a new user based on the String entered in the loginUsername and loginPassword TextFields. 
	 * @throws InvalidPasswordException 
	 */
	@FXML
	public void signUp() {
		loginErrorLabel.setText("");
		System.out.println(String.format("Attempted Sign Up:%n--> Username: '%s'%n--> Password: '%s'", loginUsername.getText(), loginPassword.getText()));
		
		try {
			// A new instance of User is created with the information provided in the loginUsername and loginPassword 
			// TextFields.
			new User(loginUsername.getText(), loginPassword.getText());
			
		} catch (InvalidUsernameException iue) {
			System.out.println(String.format("ERROR (signUp): Cannot create new user with username '%s'.", loginUsername.getText()));
			System.out.println(iue.getMessage());
			loginErrorLabel.setText(iue.getMessage());
			
		} catch (InvalidPasswordException ipe) {
			System.out.println(String.format("ERROR (signUp): Cannot create new user with password '%s' and password '%s'.", loginPassword.getText()));
			System.out.println(ipe.getMessage());
			loginErrorLabel.setText(ipe.getMessage());
			
		} catch (UserAlreadyExistsException uaee) {
			System.out.println(String.format("ERROR (signUp): User with username '%s' already exists.", loginUsername.getText()));
			System.out.println(uaee.getMessage());
			loginErrorLabel.setText(uaee.getMessage());
		}
	}
	
}
	
	// Requires File ID module to access and compare stored login information? i.e., use a for-loop to scan through a list of stored usernames and correlating passwords?
	// Look into using HashMaps to create a key:value pair (username:password) pair, wrapped with a try/catch statement.
