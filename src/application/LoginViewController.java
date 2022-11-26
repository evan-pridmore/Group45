package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import application.Exceptions.InvalidPasswordException;
import application.Exceptions.InvalidUsernameException;
import application.Exceptions.NullEventEndPointException;
import application.Exceptions.UserAlreadyExistsException;
import application.Exceptions.UserDoesNotExistException;
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
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws NullEventEndPointException 
	 */
	@FXML
	public void attemptLogin(ActionEvent loginEvent) throws FileNotFoundException, IOException, NullEventEndPointException {
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
				
				Stage loginStage = (Stage)((Node)loginEvent.getSource()).getScene().getWindow();
	 			loginStage.close();
	      
	 			FXMLLoader loader = new FXMLLoader();
	 			Parent root = loader.load(new FileInputStream("src/application/FXML/CalendarView.fxml"));
	 			
	 			Stage applicationStage = new Stage(); 
	 			Scene scene = new Scene(root);
	 			applicationStage.setScene(scene);
	 			applicationStage.setTitle(currentUser.getUsername() + "'s Calendar");
	 			
	 			CalendarViewController controller = (CalendarViewController) loader.getController();
	 			controller.initializeCalendarView(applicationStage, loginEvent, currentUser);
	 			
			
			// If the password is incorrect, the label is updated to reflect this.
			} else {
				System.out.println("Incorrect password!");
				loginErrorLabel.setText("Incorrect password!");
			}
			
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
