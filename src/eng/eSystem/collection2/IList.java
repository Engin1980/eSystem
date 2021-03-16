package eng.eSystem.collection2;

import eng.eSystem.collection2.subinterfaces.IShared;
import eng.eSystem.collections.ESet;
import eng.eSystem.collections.ISet;
import eng.eSystem.events.EventAnonymous;
import eng.eSystem.functionalInterfaces.Selector;
import eng.eSystem.validation.EAssert;

import java.util.Comparator;
import java.util.function.Predicate;

public interface IList<T> extends IReadOnlyList<T>, IShared<IList<T>, T> {
  void add(T item);

  default void addMany(Iterable<? extends T> items) {
    EAssert.Argument.isNotNull(items, "items");

    for (T item : items) {
      this.add(item);
    }
  }

  default void addMany(T[] items) {
    EAssert.Argument.isNotNull(items, "items");

    for (T item : items) {
      this.add(item);
    }
  }

  void clear();

  void insert(int index, T item);

  default void insertMany(int index, T[] items) {
    EAssert.Argument.isNotNull(items, "items");
    Common.ensureIndexRange(this, index);

    for (int i = 0; i < items.length; i++) {
      this.insert(index + i, items[i]);
    }
  }

  default void insertMany(int index, Iterable<? extends T> items) {
    EAssert.Argument.isNotNull(items, "items");
    Common.ensureIndexRange(this, index);

    int i = 0;
    for (T item : items) {
      this.insert(index + i, item);
      i++;
    }
  }

  void remove(T item);

  default void removeMany(Iterable<? extends T> items) {
    EAssert.Argument.isNotNull(items, "items");

    for (T item : items) {
      this.remove(item);
    }
  }

  default void remove(Predicate<T> predicate) {
    IList<T> tmp = this.where(predicate);
    this.removeMany(tmp);
  }

  void removeAt(int index);

  default void retain(Predicate<T> predicate) {
    IList<T> tmp = this.where(predicate.negate());
    this.removeMany(tmp);
  }

  void reverse();

  void shuffle();

  void set(int index, T item);

  /**
   * Filters the list according to the index-selector
   *
   * @param indexSelector Selecting indices which should be kept in the collection.
   */
  void slice(Predicate<Integer> indexSelector);

  /**
   * Filters the list according to from-index to to-index.
   *
   * @param fromIndex First index to be kept, included.
   * @param toIndex   Last index to be kept, excluded.
   */
  default void slice(int fromIndex, int toIndex) {
    this.slice(q -> q >= fromIndex && q < toIndex);
  }

  /**
   * Filters the list according to the set of required indices.
   *
   * @param indices Array of indices to be kept.
   */
  default void slice(int... indices) {
    ESet<Integer> set = new ESet<>();
    for (int index : indices) {
      set.add(index);
    }
    this.slice(set);
  }

  /**
   * Filters the list according to the set of required indices.
   *
   * @param indicesSet Set of indices to be kept.
   */
  default void slice(ISet<Integer> indicesSet) {
    this.slice(q -> indicesSet.contains(q));
  }

  <K extends Comparable<K>> void sort(Selector<T, K> selector);

  void sort(Comparator<T> comparator);

  void tryRemove(T item);

  default void tryRemoveMany(Iterable<? extends T> items) {
    EAssert.Argument.isNotNull(items, "items");

    items.forEach(q -> this.tryRemove(q));
  }
}
