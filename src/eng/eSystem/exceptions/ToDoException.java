package eng.eSystem.exceptions;

public class ToDoException extends RuntimeException {
  public ToDoException() {
    this("Not implemented yet.");
  }

  public ToDoException(String message) {
    super("TODO: " + message);
  }
}
