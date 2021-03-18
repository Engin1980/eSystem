package eng.eSystem.collections;

import eng.eSystem.events.Event;

public class EObservableList<T> extends EList<T> {

  public Event<EObservableList<T>, T> onInsertEvent = new Event<>(this);
  public Event<EObservableList<T>, T> onRemoveEvent = new Event<>(this);

  public Event<EObservableList<T>, T> getOnInsertEvent() {
    return onInsertEvent;
  }

  public Event<EObservableList<T>, T> getOnRemoveEvent() {
    return onRemoveEvent;
  }

  @Override
  public void add(T item) {
    super.add(item);
    onInsertEvent.raise(item);
  }

  @Override
  public void insert(int index, T item) {
    super.insert(index, item);
    onInsertEvent.raise(item);
  }

  @Override
  public void set(int index, T item) {
    T oldItem = this.get(index);
    super.set(index, item);
    onRemoveEvent.raise(oldItem);
    onInsertEvent.raise(item);

  }

  @Override
  public void removeAt(int index) {
    T oldItem = this.get(index);
    super.removeAt(index);
    onRemoveEvent.raise(oldItem);
  }

  @Override
  public void tryRemove(T item) {
    if (this.contains(item)){
      this.remove(item);
    }
  }

  @Override
  public void remove(T item) {
    super.remove(item);
    onRemoveEvent.raise(item);
  }

  @Override
  public void clear() {
    IList<T> tmp = new EList<>(this);
    super.clear();
    tmp.forEach(q-> onRemoveEvent.raise(q));
  }
}
