package application;

public class User {
	private String username;
	private String password;
	
	User(){}
	
	User(String usernameInput, String passwordInput){
		setUsername(usernameInput);
		setPassword(passwordInput);
	}
	
	public String getUsername() {
		return new String(username);
	}
	
	public String getPassword() {
		return new String(password);
	}
	
	public void setUsername(String input) {
		if (input != null)
			username = new String(input);
	}
	
	public void setPassword(String input) {
		if (input != null)
			password = new String(input);
	}
}
