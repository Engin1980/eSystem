package eng.eSystem.events;

public class EventRaiseException extends RuntimeException {
  private final Object listener;

  public EventRaiseException(Object listener, Throwable cause) {
    super ("Listener " + listener + " threw an exception during evaluation.", cause);
    this.listener = listener;
  }

  public Object getListener() {
    return listener;
  }
}
