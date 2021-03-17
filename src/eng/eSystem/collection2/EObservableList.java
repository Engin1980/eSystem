package eng.eSystem.collection2;

import eng.eSystem.collections.EList;
import eng.eSystem.collections.IList;
import eng.eSystem.events.Event;

public class EObservableList<T> extends eng.eSystem.collections.EList<T> {

  public Event<EObservableList<T>, T> insertEvent = new Event<>(this);
  public Event<EObservableList<T>, T> removeEvent = new Event<>(this);

  @Override
  public void add(T item) {
    super.add(item);
    insertEvent.raise(item);
  }

  @Override
  public void clear() {
    IList<T> tmp = new EList<>(this);
    super.clear();
    tmp.forEach(q -> removeEvent.raise(q));
  }

  public Event<EObservableList<T>, T> onInsert() {
    return insertEvent;
  }

  public Event<EObservableList<T>, T> onRemove() {
    return removeEvent;
  }

  @Override
  public void insert(int index, T item) {
    super.insert(index, item);
    insertEvent.raise(item);
  }

  @Override
  public void remove(T item) {
    super.remove(item);
    removeEvent.raise(item);
  }

  @Override
  public void removeAt(int index) {
    T oldItem = this.get(index);
    super.removeAt(index);
    removeEvent.raise(oldItem);
  }

  @Override
  public void set(int index, T item) {
    T oldItem = this.get(index);
    super.set(index, item);
    removeEvent.raise(oldItem);
    insertEvent.raise(item);

  }

  @Override
  public void tryRemove(T item) {
    if (this.contains(item)) {
      this.remove(item);
    }
  }
}
