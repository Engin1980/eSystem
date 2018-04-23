package eng.eSystem.exceptions;

public class EXmlRuntimeException extends EApplicationException {
  public EXmlRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public EXmlRuntimeException(String message) {
    super(message);
  }
}
