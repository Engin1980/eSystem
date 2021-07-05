package eng.eSystem.exceptions;

/**
 * Invoked in functions/code which is not implemented yet, but will be implemented in the future.
 */
public class ToDoException extends RuntimeException {
  public ToDoException() {
    this("Not implemented yet.");
  }

  public ToDoException(String message) {
    super("Not implemented yet. " + message);
  }
}
