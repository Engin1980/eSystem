package eng.eSystem.events;

/**
 * Represents a listener listening to a {@linkplain EventSimple}. Instance of this class
 * must be registered with an instance of {@linkplain EventSimple} class to listen the event.
 * @param <TSender> Class-type of event sender.
 * @see EventSimple
 */
public interface IEventListenerSimple<TSender> {

  /**
   * This method is raised when an event occurs.
   * @param sender Object sending the event.
   */
  void raise(TSender sender);
}
