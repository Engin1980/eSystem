package eng.eSystem.collections;

import java.util.function.Predicate;

public interface ISet<T> extends IReadOnlySet<T> {

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

  void clear();

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

  void remove(T item);

  default void retain(Predicate<T> predicate) {
    ISet<T> tmp = this.where(predicate.negate());
    for (T t : tmp) {
      this.remove(tmp);
    }
  }
}
