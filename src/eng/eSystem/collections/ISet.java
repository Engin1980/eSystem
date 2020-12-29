package eng.eSystem.collections;

import eng.eSystem.functionalInterfaces.Selector;

import java.util.function.Predicate;

public interface ISet<T> extends IReadOnlySet<T> {

  void add(T item);

  void clear();

  void remove(T item);

  void tryRemove(T item);

  default void add(Iterable<? extends T> items) {
    for (T item : items) {
      this.add(item);
    }
  }

  default void add(T[] items) {
    for (T item : items) {
      this.add(item);
    }
  }

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

  default void remove(Iterable<? extends T> items) {
    for (T item : items) {
      this.remove(item);
    }
  }

  default void remove(Predicate<T> predicate) {
    ISet<T> tmp = this.where(predicate);
    for (T t : tmp) {
      this.remove(tmp);
    }
  }

  default void retain(Predicate<T> predicate) {
    ISet<T> tmp = this.where(predicate.negate());
    for (T t : tmp) {
      this.remove(tmp);
    }
  }

  default <V> IMap<T, V> toMap(Selector<T, V> valueSelector) {
    IMap<T, V> ret = this.toMap(q -> q, valueSelector);
    return ret;
  }

  default void tryRemoveMany(Iterable<? extends T> items) {
    items.forEach(q -> this.tryRemove(q));
  }
}
