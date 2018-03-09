package eng.eSystem.events;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents simple event with argument without source.
 * @param <TEventArgs> Class-type of argument passed when event is invoked.
 */
public class EventAnonymous<TEventArgs>{

  private List<IEventListenerAnonymous<TEventArgs>> inner = new ArrayList();

  /**
   * Creates new instance of event.
   */
  public EventAnonymous() {

  }

  /**
   * Registers a new liIEventListenerAnonymousevent.
   * @param listener An instance of listener if type {@linkplain IEventListenerAnonymous}.
   */
  public void add(IEventListenerAnonymous listener){
    inner.add(listener);
  }

  /**
   * Unregisters a listener of the event. If the listener has not been registered, nothing happens.
   * @param listener An instance of listener previously registered.
   */
  public void remove(IEventListenerAnonymous listener){
    if (inner.contains(listener))
      inner.remove(listener);
  }

  /**
   * This method invokes an event and notifies all listeners.
   * @param args Argument object sended to all listeners as a parameter during event invocation.
   */
  public void raise(TEventArgs args){
    for (IEventListenerAnonymous<TEventArgs> eventListener : inner) {
      eventListener.raise(args);
    }
  }
}

