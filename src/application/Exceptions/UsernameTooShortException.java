package application.Exceptions;

public class UsernameTooShortException extends Exception {

	public UsernameTooShortException() {
	}

	public UsernameTooShortException(String message) {
		super(message);
	}

	public UsernameTooShortException(Throwable cause) {
		super(cause);
	}

	public UsernameTooShortException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameTooShortException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
