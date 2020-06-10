package eng.eSystem.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents simple event with argument.
 *
 * @param <TSource>    Class-type of the object invoking event
 * @param <TEventArgs> Class-type of argument passed when event is invoked.
 */
public class Event<TSource, TEventArgs> extends EventBase<IEventListener<TSource, TEventArgs>> {

  private final TSource source;

  /**
   * Creates new instance of event.
   *
   * @param source Parent object which defines this event. Used as a sender of the event. Typically "this".
   */
  public Event(TSource source) {
    super();
    this.source = source;
  }

  /**
   * Registers a new listener ot the event.
   *
   * @param listener An instance of listener if type {@linkplain IEventListener}.
   */
  public int add(IEventListener<TSource, TEventArgs> listener) {
    return super.add(listener);
  }

  /* Registers a new listener ot the event.
   * @param listener An instance of listener if type {@linkplain IEventListener}.
   */
  public int addAsync(IEventListener<TSource, TEventArgs> listener) {
    return super.addAsync(listener);
  }

  /**
   * Unregisters a listener of the event. If the listener has not been registered, nothing happens.
   *
   * @param listener An instance of listener previously registered.
   */
  public void remove(IEventListener<TSource, TEventArgs> listener) {
    super.remove(listener);
  }

  /**
   * Unregisters a listener of the event. If the listener has not been registered, nothing happens.
   *
   * @param listener An instance of listener previously registered.
   */
  public void removeAsync(IEventListener<TSource, TEventArgs> listener) {
    super.removeAsync(listener);
  }

  @Override
  protected void raiseListener(IEventListener<TSource, TEventArgs> listener, Object[] data) {
    listener.raise(source, (TEventArgs) data[0]);
  }

    /**
   * This method invokes an event and notifies all listeners.
   *
   * @param args Argument object sent to all listeners as a parameter during an event invocation.
   */
  public void raise(TEventArgs args) {
    super.raise(new Object[]{args});
  }
}

