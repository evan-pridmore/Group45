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
			User.deserializeUser(loginUsername.getText());
		} catch (ClassNotFoundException | IOException e) {
			// This should be a custom exception
			System.out.println(String.format("ERROR (attemptLogin): Cannot deserialize user '%s'. ", loginUsername.getText()));
			System.out.println(e.getMessage());
			
		} catch (UserDoesNotExistException udne) {
			System.out.println(String.format("ERROR (attemptLogin): User '%s' does not exist.", loginUsername.getText()));
			loginErrorLabel.setText(udne.getMessage());
		}
	} 
	
}
	
	// Requires File ID module to access and compare stored login information? i.e., use a for-loop to scan through a list of stored usernames and correlating passwords?
	// Look into using HashMaps to create a key:value pair (username:password) pair, wrapped with a try/catch statement.
