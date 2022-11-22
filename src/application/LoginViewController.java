package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class LoginViewController {
	
	@FXML
    private TextField loginUsername;
	
	@FXML
    private TextField loginPassword;
	
	@FXML
	private Label loginErrorLabel;
		
	@FXML
	public void attemptLogin() throws IOException {
		loginErrorLabel.setText("");
		System.out.println(String.format("Attempted Login:%n--> Username: '%s'%n--> Password: '%s'", loginUsername.getText(), loginPassword.getText()));
		
		try {
			User currentUser = User.deserializeUser(loginUsername.getText());
			
			System.out.println(String.format("Logged in user '%s'!", currentUser.getUsername()));
			loginErrorLabel.setText(String.format("Logged in user '%s'!", currentUser.getUsername()));

		} catch (ClassNotFoundException | IOException e) {
			// This should be a custom exception
			System.out.println(String.format("ERROR (attemptLogin): Cannot deserialize user '%s'. ", loginUsername.getText()));
			System.out.println(e.getMessage());
			
		} catch (UserDoesNotExistException udne) {
			System.out.println(String.format("ERROR (attemptLogin): User '%s' does not exist.", loginUsername.getText()));
			loginErrorLabel.setText(udne.getMessage());
		}
		
	} 
	
	@FXML
	public void signUp() throws IOException {
		loginErrorLabel.setText("");
		System.out.println(String.format("Attempted Sign Up:%n--> Username: '%s'%n--> Password: '%s'", loginUsername.getText(), loginPassword.getText()));
		
		try {
			new User(loginUsername.getText(), loginPassword.getText());
			
		} catch (InvalidUsernameException iue) {
			System.out.println(String.format("ERROR (signUp): Cannot create new user with username '%s' and password '%s'.", loginUsername.getText(), loginPassword.getText()));
			System.out.println(iue.getMessage());
			loginErrorLabel.setText(iue.getMessage());
		} catch (UserAlreadyExistsException uaee) {
			System.out.println(String.format("ERROR (signUp): User with username '%s' already exists.", loginUsername.getText()));
			System.out.println(uaee.getMessage());
			loginErrorLabel.setText(uaee.getMessage());
		}
	}
	
}
	
	// Requires File ID module to access and compare stored login information? i.e., use a for-loop to scan through a list of stored usernames and correlating passwords?
	// Look into using HashMaps to create a key:value pair (username:password) pair, wrapped with a try/catch statement.
