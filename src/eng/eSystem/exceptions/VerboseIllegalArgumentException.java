package eng.eSystem.exceptions;

public class VerboseIllegalArgumentException extends IllegalArgumentException {

  public VerboseIllegalArgumentException(String argumentName, Object value, String description){
    super("Argument {" + argumentName + "} is not valid. " + description + " Argument value: " + value);
  }
}
