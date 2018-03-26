package eng.eSystem.collections;

import java.util.function.Predicate;

public interface ISet<T> extends  IReadOnlySet<T> {

  void add(T item);

  void add(Iterable<? extends T> items);

  void add(T[] items);

  void remove(T item);
  void remove(Iterable<? extends T> items);
  void remove(Predicate<T> predicate);
  void retain(Predicate<T> predicate);
  void clear();

}
