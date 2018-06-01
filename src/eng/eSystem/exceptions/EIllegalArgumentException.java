package eng.eSystem.exceptions;

public class EIllegalArgumentException extends IllegalArgumentException {

  public EIllegalArgumentException(String argumentName, Object value, String description){
    super("Argument {" + argumentName + "} is not valid. " + description + " Argument value: " + value);
  }
}
