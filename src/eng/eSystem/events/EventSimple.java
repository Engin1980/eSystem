package eng.eSystem.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents simple event without argument.
 * @param <TSource> A source - sender of the event.
 */
public class EventSimple<TSource>{

  private final TSource source;
  private List<IEventListenerSimple<TSource>> inner = new ArrayList();

  /**
   * Creates new instance of event.
   * @param source Parent object which defines this event. Used as a sender of the event. Typically "this
   */
  public EventSimple(TSource source) {
    this.source = source;
  }

  /**
   * Registers a new listener ot the event.
   * @param listener An instance of listener if type {@linkplain IEventListenerSimple}.
   */
  public void add(IEventListenerSimple listener){
    inner.add(listener);
  }

  /**
   * Unregisters a listener of the event. If the listener has not been registered, nothing happens.
   * @param listener An instance of listener previously registered.
   */
  public void remove(IEventListenerSimple listener){
    if (inner.contains(listener))
      inner.remove(listener);
  }

  /**
   * This method invokes an event and notifies all listeners.
   */
  public void raise(){
    for (IEventListenerSimple<TSource> eventListener : inner) {
      eventListener.raise(this.source);
    }
  }
}
