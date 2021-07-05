package eng.eSystem.exceptions;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

/**
 * Invoked when argument is null.
 */
public class NullArgumentException extends IllegalArgumentException {

  public NullArgumentException(String argumentName) {
    super(sf("Argument '%s' is null.", argumentName));
  }

  public NullArgumentException(String argumentName, Throwable cause) {
    super(sf("Argument '%s' is null.", argumentName), cause);
  }
}
