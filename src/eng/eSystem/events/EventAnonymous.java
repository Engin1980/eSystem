package eng.eSystem.events;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents simple event with argument without source.
 * @param <TEventArgs> Class-type of argument passed when event is invoked.
 */
public class EventAnonymous<TEventArgs> extends EventBase<IEventListenerAnonymous<TEventArgs>>{

  /**
   * Registers a new listener ot the event.
   *
   * @param listener An instance of listener if type {@linkplain IEventListener}.
   */
  public int add(IEventListenerAnonymous<TEventArgs> listener) {
    return super.add(listener);
  }

  /* Registers a new listener ot the event.
   * @param listener An instance of listener if type {@linkplain IEventListener}.
   */
  public int addAsync(IEventListenerAnonymous<TEventArgs> listener) {
    return super.addAsync(listener);
  }

  /**
   * Unregisters a listener of the event. If the listener has not been registered, nothing happens.
   *
   * @param listener An instance of listener previously registered.
   */
  public void remove(IEventListenerAnonymous<TEventArgs> listener) {
    super.remove(listener);
  }

  /**
   * Unregisters a listener of the event. If the listener has not been registered, nothing happens.
   *
   * @param listener An instance of listener previously registered.
   */
  public void removeAsync(IEventListenerAnonymous<TEventArgs> listener) {
    super.removeAsync(listener);
  }

  @Override
  protected void raiseListener(IEventListenerAnonymous<TEventArgs> listener, Object[] data) {
    listener.raise((TEventArgs) data[0]);
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

