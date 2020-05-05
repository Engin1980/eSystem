package eng.eSystem;

public class TryResult<T> {
  private final T value;
  private final Exception exception;

  public TryResult(T value) {
    this.value = value;
    this.exception = null;
  }

  public TryResult(Exception exception) {
    this.exception = exception;
    this.value = null;
  }

  public Exception getException() {
    return exception;
  }

  public T getValue() {
    return value;
  }
}
