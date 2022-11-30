package application.Exceptions;

public class EventOutsideTimeUnitException extends Exception {

	public EventOutsideTimeUnitException() {
	}

	public EventOutsideTimeUnitException(String message) {
		super(message);
	}

	public EventOutsideTimeUnitException(Throwable cause) {
		super(cause);
	}

	public EventOutsideTimeUnitException(String message, Throwable cause) {
		super(message, cause);
	}

	public EventOutsideTimeUnitException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
