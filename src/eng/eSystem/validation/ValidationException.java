package eng.eSystem.validation;

@Deprecated() //use AssertException instead
public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}
