package eng.eSystem.exceptions;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class NulArgumentException extends IllegalArgumentException {

  public NulArgumentException(String argumentName) {
    super(sf("Argument '%s' is null.", argumentName));
  }

  public NulArgumentException(String argumentName, Throwable cause) {
    super(sf("Argument '%s' is null.", argumentName), cause);
  }
}
