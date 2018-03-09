package eng.eSystem.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents simple event with argument.
 * @param <TSource> Class-type of the object invoking event
 * @param <TEventArgs> Class-type of argument passed when event is invoked.
 */
public class Event<TSource, TEventArgs>{

  private final TSource source;

  private List<IEventListener<TSource, TEventArgs>> inner = new ArrayList();

  /**
   * Creates new instance of event.
   * @param source Parent object which defines this event. Used as a sender of the event. Typically "this".
   */
  public Event(TSource source) {
    this.source = source;
  }

  /**
   * Registers a new listener ot the event.
   * @param listener An instance of listener if type {@linkplain IEventListener}.
   */
  public void add(IEventListener listener){
    inner.add(listener);
  }

  /**
   * Unregisters a listener of the event. If the listener has not been registered, nothing happens.
   * @param listener An instance of listener previously registered.
   */
  public void remove(IEventListener listener){
    if (inner.contains(listener))
      inner.remove(listener);
  }

  /**
   * This method invokes an event and notifies all listeners.
   * @param args Argument object sended to all listeners as a parameter during event invocation.
   */
  public void raise(TEventArgs args){
    for (IEventListener<TSource, TEventArgs> eventListener : inner) {
      eventListener.raise(this.source, args);
    }
  }
}

