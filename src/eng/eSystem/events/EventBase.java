package eng.eSystem.events;

import eng.eSystem.collections.EList;
import eng.eSystem.collections.EMap;
import eng.eSystem.collections.IList;
import eng.eSystem.collections.IMap;
import eng.eSystem.exceptions.ApplicationException;

abstract class EventBase<TListener> {
  private static int nextId = 1;

  private static synchronized int getNextId() {
    int ret = nextId;
    nextId++;
    return ret;
  }

  private IMap<Integer, TListener> ids = new EMap<>();
  private IList<TListener> innerSync = new EList<>();
  private IList<TListener> innerAsync = new EList<>();
  private boolean isRaised = false;
  private IList<TListener> toAddSync = new EList<>();
  private IList<TListener> toRemSync = new EList<>();
  private IList<TListener> toAddAsync = new EList<>();
  private IList<TListener> toRemAsync = new EList<>();

  protected abstract void raiseListener(TListener listener, Object[] data);

  public boolean hasListeners() {
    return ids.isEmpty() == false;
  }

  public void remove(int id) {
    TListener lst = ids.get(id);
    if (innerSync.contains(lst))
      this.remove(lst);
    else if (innerAsync.contains(lst))
      this.removeAsync(lst);
    else
      throw new ApplicationException("Handler with id " + id + " not known.");
  }

  protected int add(TListener listener) {
//    int id = getNextId();
//    ids.set(id, listener);
//    if (isRaised)
//      toAddSync.add(listener);
//    else
//      innerSync.add(listener);
//    return id;
    return _add(listener, toAddSync, innerSync);
  }

  protected int addAsync(TListener listener) {
//    int id = getNextId();
//    ids.set(id, listener);
//    if (isRaised)
//      toAddAsync.add(listener);
//    else
//      innerAsync.add(listener);
//    return id;
    return _add(listener, toAddAsync, innerAsync);
  }

  private int _add(TListener listener, IList<TListener> toAddLst, IList<TListener> innerLst) {
    int id = getNextId();
    ids.set(id, listener);
    if (isRaised)
      toAddLst.add(listener);
    else
      innerLst.add(listener);
    return id;
  }

  protected void remove(TListener listener) {
    if (isRaised)
      toRemSync.add(listener);
    else {
      innerSync.remove(listener);
      ids.remove(q -> q.getValue().equals(listener));
    }
  }

  protected void removeAsync(TListener listener) {
    if (isRaised)
      toRemAsync.add(listener);
    else {
      innerAsync.remove(listener);
      ids.remove(q -> q.getValue().equals(listener));
    }
  }

  protected void raise(Object[] data) {
    isRaised = true;
    for (TListener listener : innerAsync) {
      raiseAsync(listener, data);
    }

    Throwable thrown = null;
    Object thrower = null;
    for (TListener listener : innerSync) {
      try {
        raiseListener(listener, data);
      } catch (Throwable t) {
        thrower = listener;
        thrown = t;
        break;
      }
    }
    isRaised = false;
    if (toAddSync.isEmpty() == false) {
      innerSync.addMany(toAddSync);
      toAddSync.clear();
    }
    if (toAddAsync.isEmpty() == false) {
      innerAsync.addMany(toAddAsync);
      toAddAsync.clear();
    }
    if (toRemSync.isEmpty() == false) {
      innerSync.addMany(toRemSync);
      ids.remove(q -> toRemSync.contains(q.getValue()));
      toRemSync.clear();
    }
    if (toRemAsync.isEmpty() == false) {
      innerAsync.addMany(toRemAsync);
      ids.remove(q -> toRemAsync.contains(q.getValue()));
      toRemAsync.clear();
    }
    if (thrown != null)
      throw new EventRaiseException(thrower, thrown);
  }

  private void raiseAsync(TListener listener, Object[] data) {
    Runnable r = () -> this.raiseListener(listener, data);
    Thread t = new Thread(r);
    t.start();
  }
}
