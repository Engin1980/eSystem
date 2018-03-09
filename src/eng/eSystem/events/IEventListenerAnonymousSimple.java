package eng.eSystem.events;

/**
 * Represents a listener listening to a {@linkplain EventAnonymousSimple}. Instance of this class
 * must be registered with an instance of {@linkplain EventAnonymousSimple} class to listen the event.
 * @see EventAnonymousSimple
 */
public interface IEventListenerAnonymousSimple {

  /**
   * This method is raised when an event occurs.
   */
  void raise();
}
