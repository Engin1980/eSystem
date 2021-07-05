package eng.eSystem.exceptions;

/**
 * Thrown when some unexpected value is achieved - typically in default branch of case/switch.
 */
public class UnexpectedValueException extends RuntimeException {

  public UnexpectedValueException(Object value) {
    super("Enum value {" + value + "} of type " + value.getClass().getName() + " is not supported.");
  }
}
