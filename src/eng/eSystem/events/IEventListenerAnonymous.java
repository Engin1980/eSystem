package eng.eSystem.events;

/**
 * Represents a listener listening to a {@linkplain EventAnonymous}. Instance of this class
 * must be registered with an instance of {@linkplain EventAnonymous} class to listen the event.
 * @param <TEventArgs> Class-type of event argument.
 * @see EventAnonymous
 */
public interface IEventListenerAnonymous<TEventArgs> {

  /**
   * This method is raised when an event occurs.
   * @param args Argument passed together with the event invocation.
   */
  void raise(TEventArgs args);
}
