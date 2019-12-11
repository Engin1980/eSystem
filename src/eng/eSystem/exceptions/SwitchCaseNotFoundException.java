package eng.eSystem.exceptions;

import eng.eSystem.collections.*;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class SwitchCaseNotFoundException extends EApplicationException {
  public SwitchCaseNotFoundException(Object value){
    super(sf("Switch case for option '%s' not found.", value));
  }
}
