package application.Exceptions;

public class NullEventEndPointException extends Exception {

	public NullEventEndPointException() {
	}

	public NullEventEndPointException(String message) {
		super(message);
	}

	public NullEventEndPointException(Throwable cause) {
		super(cause);
	}

	public NullEventEndPointException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullEventEndPointException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
