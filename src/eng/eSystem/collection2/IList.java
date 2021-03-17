package eng.eSystem.collection2;

import eng.eSystem.collection2.subinterfaces.IEditableCollection;
import eng.eSystem.collections.ESet;
import eng.eSystem.collections.ISet;
import eng.eSystem.functionalInterfaces.Selector;
import eng.eSystem.validation.EAssert;

import java.util.Comparator;
import java.util.Random;
import java.util.function.Predicate;

public interface IList<T> extends IReadOnlyList<T>, IEditableCollection<T> {

  void insert(int index, T item);

  void removeAt(int index);

  void reverse();

  void shuffle(Random rnd);

  default void shuffle(){
    this.shuffle(Common.rnd);
  }

  void set(int index, T item);

  /**
   * Filters the list according to the index-selector
   *
   * @param indexSelector Selecting indices which should be kept in the collection.
   */
  void slice(Predicate<Integer> indexSelector);

  <K extends Comparable<K>> void sort(Selector<T, K> selector);

  void sort(Comparator<T> comparator);

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

  default IList<T> with(T... elements) {
    this.addMany(elements);
    return this;
  }

  default IList<T> with(Iterable<T> items) {
    this.addMany(items);
    return this;
  }
}
