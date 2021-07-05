package eng.eSystem.exceptions;

/**
 * Deprecated. Use {@link UnexpectedValueException}
 * @see UnexpectedValueException
 */
@Deprecated
public class EnumValueUnsupportedException extends ApplicationException {

  public EnumValueUnsupportedException(Object value) {
    super("Unexpected");
    throw new DeprecatedException("Use 'UnexpectedValueException' instead.");
//    super("Enum value {" + value + "} of type " + value.getClass().getName() + " is not supported.");
  }
}
