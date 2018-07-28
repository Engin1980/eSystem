package eng.eSystem.collections;

import eng.eSystem.utilites.Selector;

import java.util.Comparator;
import java.util.function.Predicate;

public interface IList<T> extends IReadOnlyList<T> {
  void add(T item);


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

  void insert(int index, T item);

  void set(int index, T item);

  void removeAt(int index);

  void tryRemove(T item);

  default void tryRemove(Iterable<? extends T> items) {
    items.forEach(q -> this.tryRemove(q));
  }

  void remove(T item);

  default void remove(Iterable<? extends T> items) {
    for (T item : items) {
      this.remove(item);
    }
  }

  default void remove(Predicate<T> predicate) {
    IList<T> tmp = this.where(predicate);
    for (T t : tmp) {
      this.remove(t);
    }
  }

  default void retain(Predicate<T> predicate) {
    IList<T> tmp = this.where(predicate.negate());
    for (T t : tmp) {
      this.remove(t);
    }
  }

  void clear();

  void reverse();

  <K extends Comparable<K>> void sort(Selector<T, K> selector);

  void sort(Comparator<T> comparator);
}
