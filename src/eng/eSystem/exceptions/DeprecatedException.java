package eng.eSystem.exceptions;

/**
 * Throw when calling deprecated method or method in deprecated class.
 */
public class DeprecatedException extends RuntimeException {

  public DeprecatedException(String message) {
    super("This member is deprecated. " + message);
  }

  public DeprecatedException() {
  }
}
