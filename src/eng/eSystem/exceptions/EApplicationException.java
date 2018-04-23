package eng.eSystem.exceptions;

public class EApplicationException extends ERuntimeException {

  private final static String PREFIX = "Application exception. ";

  public EApplicationException(String message) {
    super(PREFIX + message);
  }

  public EApplicationException(String message, Throwable cause) {
    super(PREFIX + message, cause);
  }

  public EApplicationException(Throwable cause) {
    super(PREFIX, cause);
  }
}