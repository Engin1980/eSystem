package eng.eSystem.exceptions;

public class EEnumValueUnsupportedException extends EApplicationException {

  public EEnumValueUnsupportedException(Object value) {
    super("Enum value {" + value + "} of type " + value.getClass().getName() + " is not supported.");
  }
}
