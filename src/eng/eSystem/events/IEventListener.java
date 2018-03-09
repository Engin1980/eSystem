package eng.eSystem.events;

/**
 * Represents a listener listening to a {@linkplain Event}. Instance of this class
 * must be registered with an instance of {@linkplain Event} class to listen the event.
 * @param <TSender> Class-type of event sender.
 * @param <TEventArgs> Class-type of event argument.
 * @see Event
 */
public interface IEventListener<TSender, TEventArgs> {

  /**
   * This method is raised when an event occurs.
   * @param sender Object sending the event.
   * @param args Argument passed together with the event invocation.
   */
  void raise(TSender sender, TEventArgs args);
}
