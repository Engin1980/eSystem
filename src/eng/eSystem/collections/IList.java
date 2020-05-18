package eng.eSystem.collections;

import eng.eSystem.functionalInterfaces.Selector;

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

  void clear();

  void insert(int index, T item);

  default void insert(int index, T[] items) {
    if (items == null) throw new IllegalArgumentException("Parameter 'items' cannot be null.");
    for (int i = 0; i < items.length; i++) {
      this.insert(index + i, items[i]);
    }
  }

  default void insert(int index, Iterable<? extends T> items) {
    if (items == null) throw new IllegalArgumentException("Parameter 'items' cannot be null.");
    int i = 0;
    for (T item : items) {
      this.insert(index + i, item);
      i++;
    }
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

  void removeAt(int index);

  default void retain(Predicate<T> predicate) {
    IList<T> tmp = this.where(predicate.negate());
    for (T t : tmp) {
      this.remove(t);
    }
  }

  void reverse();

  void set(int index, T item);

  /**
   * Filters the list according to the index-selector
   * @param indexSelector Selecting indices which should be kept in the collection.
   */
  void slice(Predicate<Integer> indexSelector);

  /**
   * Filters the list according to from-index to to-index.
   * @param fromIndex First index to be kept, included.
   * @param toIndex Last index to be kept, excluded.
   */
  default void slice(int fromIndex, int toIndex) {
    this.slice(q -> q >= fromIndex && q < toIndex);
  }

  /**
   * Filters the list according to the set of required indices.
   * @param indices Array of indices to be kept.
   */
  default void slice(int ... indices){
    ESet<Integer> set = new ESet<>();
    for (int index : indices) {
      set.add(index);
    }
    this.slice(set);
  }

  /**
   * Filters the list according to the set of required indices.
   * @param indicesSet Set of indices to be kept.
   */
  default void slice(ISet<Integer> indicesSet){
    this.slice(q -> indicesSet.contains(q));
  }

  <K extends Comparable<K>> void sort(Selector<T, K> selector);

  void sort(Comparator<T> comparator);

  void tryRemove(T item);

  default void tryRemove(Iterable<? extends T> items) {
    items.forEach(q -> this.tryRemove(q));
  }
}
