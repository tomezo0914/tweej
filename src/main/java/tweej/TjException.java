package tweej;

public class TjException extends Exception {

	/** Default serial version uid. */
	private static final long serialVersionUID = 1L;

	public TjException(String errorCode) {
		this(errorCode, null);
	}
	
	public TjException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
