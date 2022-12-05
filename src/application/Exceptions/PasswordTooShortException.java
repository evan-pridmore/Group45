package application.Exceptions;

public class PasswordTooShortException extends Exception {

	public PasswordTooShortException() {
	}

	public PasswordTooShortException(String message) {
		super(message);
	}

	public PasswordTooShortException(Throwable cause) {
		super(cause);
	}

	public PasswordTooShortException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordTooShortException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
