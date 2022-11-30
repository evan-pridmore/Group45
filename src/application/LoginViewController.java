package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import application.Exceptions.InvalidPasswordException;
import application.Exceptions.InvalidUsernameException;
import application.Exceptions.NullEventEndPointException;
import application.Exceptions.UserAlreadyExistsException;
import application.Exceptions.UserDoesNotExistException;

/**A controller that manages {@link LoginView.fxml} and is associated GUI components (e.g., buttons, labels, textfields, etc.)
 * 
 * Extends {@link ApplicationController} which provides a range of static variables.
 * 
 * This should ONLY manage the associated GUI, and should NOT initialize any other stages, scenes, windows, views, etc.
 * (to switch windows or create new stages, add an initialize method in {@link ApplicationController}
 * 
 * @author evan-pridmore
 */
public class LoginViewController extends ApplicationController {
	
	@FXML
    private TextField loginUsername;
	
	@FXML
    private TextField loginPassword;
	
	@FXML
	private Label loginErrorLabel;
	
	/**This method handles the On Action event attemptLogin from the GUI of loginView.
	 * 
	 * @param loginEvent The event of the 'login' button being pressed, provided by the GUI.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws NullEventEndPointException 
	 */
	@FXML
	public void attemptLogin() {
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
					
				setCurrentUser(currentUser);
				initializeCalendarView();
				
			// If the password is incorrect, the label is updated to reflect this.
			} else {
				System.out.println("Incorrect password!");
				loginErrorLabel.setText(String.format("ERROR: Incorrect password entered for user '%s'.", loginUsername.getText()));
			}
			
		} catch (UserDoesNotExistException udne) {
			System.out.println(String.format("ERROR (attemptLogin): User '%s' does not exist.", loginUsername.getText()));
			loginErrorLabel.setText(String.format("ERROR: Could not find an existing user with the username '%s'", loginUsername.getText()));
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
			loginErrorLabel.setText(String.format("ERROR: Invalid username entered."));
			
		} catch (InvalidPasswordException ipe) {
			System.out.println(String.format("ERROR (signUp): Cannot create new user with password '%s' and password '%s'.", loginPassword.getText()));
			System.out.println(ipe.getMessage());
			loginErrorLabel.setText(String.format("ERROR: Invalid password entered."));
			
		} catch (UserAlreadyExistsException uaee) {
			System.out.println(String.format("ERROR (signUp): User with username '%s' already exists.", loginUsername.getText()));
			System.out.println(uaee.getMessage());
			loginErrorLabel.setText(String.format("ERROR: User with username '%s' already exists.", loginUsername.getText()));
		}
	}
	
}
