package eng.eSystem.exceptions;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

/**
 * Deprecated, use {@link UnexpectedValueException}
 * @see UnexpectedValueException
 */
@Deprecated
public class SwitchCaseNotFoundException extends ApplicationException {
  public SwitchCaseNotFoundException(Object value){
    super(sf("Switch case for option '%s' not found.", value));
  }
}
