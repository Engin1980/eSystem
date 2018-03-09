package eng.eSystem.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents simple event without argument and source.
 */
public class EventAnonymousSimple{

  private List<IEventListenerAnonymousSimple> inner = new ArrayList();

  /**
   * Creates new instance of event.
   */
  public EventAnonymousSimple() {

  }

  /**
   * Registers a new listener ot the event.
   * @param listener An instance of listener if type {@linkplain IEventListenerAnonymousSimple}.
   */
  public void add(IEventListenerAnonymousSimple listener){
    inner.add(listener);
  }

  /**
   * Unregisters a listener of the event. If the listener has not been registered, nothing happens.
   */
  public void remove(IEventListenerAnonymousSimple listener){
    if (inner.contains(listener))
      inner.remove(listener);
  }

  /**
   * This method invokes an event and notifies all listeners.
   */
  public void raise(){
    for (IEventListenerAnonymousSimple eventListener : inner) {
      eventListener.raise();
    }
  }
}
