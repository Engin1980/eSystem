package eng.eSystem.collections.exceptions;

public class EmptyCollectionException extends RuntimeException {

  public EmptyCollectionException() {
    super("Collection is empty.");
  }

  public EmptyCollectionException(String message) {
    super(message);
  }
}
