package eng.eSystem.exceptions;

public class DeprecatedException extends RuntimeException {

  public DeprecatedException(String message) {
    super("This member is deprecated. " + message);
  }

  public DeprecatedException() {
  }
}
