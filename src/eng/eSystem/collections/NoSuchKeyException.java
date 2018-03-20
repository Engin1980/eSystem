package eng.eSystem.collections;

public class NoSuchKeyException extends RuntimeException {
  public NoSuchKeyException(Object key) {
    super("Key " + key + " not found.");
  }
}
