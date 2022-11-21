package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class LoginViewController {
	
	@FXML
    private TextField loginUsername;
	
	@FXML
    private TextField loginPassword;
	
	@FXML
	void attemptLogin() {
		System.out.println(String.format("Attempted Login:%n--> Username: '%s'%n--> Password: '%s'", loginUsername.getText(), loginPassword.getText()));
		// Requires File ID module to access and compare stored login information? i.e., use a for-loop to scan through a list of stored usernames and correlating passwords?
		// Look into using HashMaps to create a key:value pair (username:password) pair, wrapped with a try/catch statement.
	}
}
