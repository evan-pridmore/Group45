package application.Exceptions;

public class PasswordTooLongException extends Exception {

	public PasswordTooLongException() {
	}

	public PasswordTooLongException(String message) {
		super(message);
	}

	public PasswordTooLongException(Throwable cause) {
		super(cause);
	}

	public PasswordTooLongException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordTooLongException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
