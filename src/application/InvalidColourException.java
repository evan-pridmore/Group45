package application;

public class InvalidColourException extends Exception {

	public InvalidColourException() {
	}

	public InvalidColourException(String message) {
		super(message);
	}

	public InvalidColourException(Throwable cause) {
		super(cause);
	}

	public InvalidColourException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidColourException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
