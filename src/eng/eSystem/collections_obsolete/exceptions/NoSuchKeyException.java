package eng.eSystem.collections.exceptions;

public class NoSuchKeyException extends RuntimeException {
  public NoSuchKeyException(Object key) {
    super("Key " + key + " not found.");
  }
}
