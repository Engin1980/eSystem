package eng.eSystem.collection.subinterfaces;

import eng.eSystem.collection.ESet;
import eng.eSystem.collection.ICollection;
import eng.eSystem.collection.ISet;
import eng.eSystem.functionalInterfaces.Predicate;
import eng.eSystem.validation.EAssert;

public interface IEditableCollection<T> extends ICollection<T> {
  void add(T item);

  void clear();

  void remove(T item);

  void tryRemove(T item);

  default void addMany(Iterable<? extends T> items) {
    for (T item : items) {
      this.add(item);
    }
  }

  default void addMany(T[] items) {
    for (T item : items) {
      this.add(item);
    }
  }

  default void remove(Predicate<T> predicate) {
    ISet<T> tmp = new ESet<>();
    for (T t : tmp) {
      if (predicate.invoke(t)) tmp.add(t);
    }
    for (T t : tmp) {
      this.remove(t);
    }
  }

  default void removeMany(Iterable<? extends T> items) {
    EAssert.Argument.isNotNull(items, "items");

    for (T item : items) {
      this.remove(item);
    }
  }

  default void retain(Predicate<T> predicate) {
    this.remove(predicate.negate());
  }

  default void tryRemoveMany(Iterable<? extends T> items) {
    items.forEach(q -> this.tryRemove(q));
  }
}
