package eng.eSystem.exceptions;

public class ERuntimeException extends RuntimeException {

  public ERuntimeException(String message) {
    super(message);
  }

  public ERuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public ERuntimeException(Throwable cause) {
    super(cause);
  }

}
