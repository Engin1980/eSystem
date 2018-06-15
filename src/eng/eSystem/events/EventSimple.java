package eng.eSystem.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents simple event without argument.
 * @param <TSource> A source - sender of the event.
 */
public class EventSimple<TSource> extends  EventBase<IEventListenerSimple<TSource>>{

  private final TSource source;

  /**
   * Creates new instance of event.
   *
   * @param source Parent object which defines this event. Used as a sender of the event. Typically "this".
   */
  public EventSimple(TSource source) {
    super();
    this.source = source;
  }

  /**
   * Registers a new listener ot the event.
   *
   * @param listener An instance of listener if type {@linkplain IEventListener}.
   */
  public int add(IEventListenerSimple<TSource> listener) {
    return super.add(listener);
  }

  /* Registers a new listener ot the event.
   * @param listener An instance of listener if type {@linkplain IEventListener}.
   */
  public int addAsync(IEventListenerSimple<TSource> listener) {
    return super.addAsync(listener);
  }

  /**
   * Unregisters a listener of the event. If the listener has not been registered, nothing happens.
   *
   * @param listener An instance of listener previously registered.
   */
  public void remove(IEventListenerSimple<TSource> listener) {
    super.remove(listener);
  }

  /**
   * Unregisters a listener of the event. If the listener has not been registered, nothing happens.
   *
   * @param listener An instance of listener previously registered.
   */
  public void removeAsync(IEventListenerSimple<TSource> listener) {
    super.removeAsync(listener);
  }

  @Override
  protected void raiseListener(IEventListenerSimple<TSource> listener, Object[] data) {
    listener.raise(source);
  }

  /**
   * This method invokes an event and notifies all listeners.
   *
   */
  public void raise() {
    super.raise(null);
  }
}
