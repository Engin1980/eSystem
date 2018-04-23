package eng.eSystem.collections;

import eng.eSystem.utilites.Selector;

import java.util.function.Predicate;

public interface IList<T> extends IReadOnlyList<T> {
  void add(T item);

  void add(Iterable<? extends T> items);

  void add(T[] items);

  void insert(int index, T item);

  void set(int index, T item);

  void removeAt(int index);
  void remove(T item);
  void remove(Iterable<? extends T> items);
  void remove(Predicate<T> predicate);
  void retain(Predicate<T> predicate);
  void clear();

  <K extends Comparable<K>> void sort(Selector<T, K> selector);
}
