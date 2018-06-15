package eng.eSystem.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents simple event without argument and source.
 */
public class EventAnonymousSimple extends EventBase<IEventListenerAnonymousSimple>{

  /**
   * Registers a new listener ot the event.
   *
   * @param listener An instance of listener if type {@linkplain IEventListener}.
   */
  public int add(IEventListenerAnonymousSimple listener) {
    return super.add(listener);
  }

  /* Registers a new listener ot the event.
   * @param listener An instance of listener if type {@linkplain IEventListener}.
   */
  public int addAsync(IEventListenerAnonymousSimple listener) {
    return super.addAsync(listener);
  }

  /**
   * Unregisters a listener of the event. If the listener has not been registered, nothing happens.
   *
   * @param listener An instance of listener previously registered.
   */
  public void remove(IEventListenerAnonymousSimple listener) {
    super.remove(listener);
  }

  /**
   * Unregisters a listener of the event. If the listener has not been registered, nothing happens.
   *
   * @param listener An instance of listener previously registered.
   */
  public void removeAsync(IEventListenerAnonymousSimple listener) {
    super.removeAsync(listener);
  }

  @Override
  protected void raiseListener(IEventListenerAnonymousSimple listener, Object[] data) {
    listener.raise();
  }

  /**
   * This method invokes an event and notifies all listeners.
   *
   */
  public void raise() {
    super.raise(null);
  }
}
