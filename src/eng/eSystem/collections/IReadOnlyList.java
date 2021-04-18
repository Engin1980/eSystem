package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.collections.subinterfaces.IReadOnlyCollection;
import eng.eSystem.functionalInterfaces.Selector;
import eng.eSystem.validation.EAssert;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.Predicate;

public interface IReadOnlyList<T> extends ICollection<T>, IReadOnlyCollection<IReadOnlyList<T>, T, IList<T>> {

  /**
   * Returns item by index, or exception.
   * @param index index to get
   * @return Item at index, or exception.
   */
  T get(int index);

  /**
   * Return duplicate items by value
   * @param selector Selector to convert item to value checked for duplicity
   * @param <K> Value used as duplicity criterion
   * @return Set of duplicate items of this collection.
   */
  <K> ISet<T> getDuplicateItems(Selector<T, K> selector);

  /**
   * Try find item in this collection.
   * @param item Item to find
   * @return Index of item, or empty of {@link OptionalInt}
   */
  OptionalInt tryIndexOf(T item);

  /**
   * Try find item in this collection matching predicate
   * @param predicate Predicate to match
   * @return Index of item, or empty of {@link Optional}
   */
  OptionalInt tryIndexOf(Predicate<T> predicate);

  /**
   * Returns ordered collections
   * @param selector How ordering value for each item is obtained (must be {@link Comparable})
   * @param reverse If order should be reversed
   * @param <K> Type of ordering value
   * @return Ordered collection
   */
  <K extends Comparable<K>> IList<T> orderBy(Selector<T, K> selector, boolean reverse);

  /**
   * Transforms this collection into collection of other type
   * @param selector How every item is transformed into new type
   * @param <V> Result type of one item
   * @return Collection of transformed items
   */
  <V> IList<V> select(Selector<T, V> selector);

  /**
   * Returns list with reversed order
   * @return List with reversed order
   */
  IReadOnlyList<T> toReversed();

  /**
   * Returns list with random order
   * @return List with random order
   */
  IReadOnlyList<T> toShuffled();

  /**
   * Select every item from the current collection only once
   * @return Returns collection of the same type interface type as the current one, with every item only once.
   */
  default IList<T> distinct() {
    return distinct(q -> q);
  }

  /**
   * Returns items by indices range
   * @param fromIndex From index, inclusive
   * @param toIndex To index, exclusive
   * @return List with selected items
   */
  default IReadOnlyList<T> getMany(int fromIndex, int toIndex) {
    EAssert.isTrue(
            fromIndex >= 0,
            () -> new IllegalArgumentException("{fromIndex} must be greater or equal zero (is " + fromIndex + ")."));
    EAssert.isTrue(
            fromIndex < toIndex,
            () -> new IllegalArgumentException("{fromIndex} must be smaller than {toIndex} (" + fromIndex + " < " + toIndex + ")."));
    EAssert.isTrue(
            toIndex <= this.size(),
            () -> new IllegalArgumentException("{toIndex} must be smaller than {size} (" + fromIndex + " < " + this.size() + ")."));
    EList<T> ret = new EList<>();

    for (int i = fromIndex; i < toIndex; i++) {
      ret.add(this.get(i));
    }

    return ret;
  }

  @Override
  default T getRandom(Random rnd) {
    Common.ensureNotEmpty(this);

    int index = (int) (rnd.nextDouble() * this.size());
    T ret = this.get(index);
    return ret;
  }

  /**
   * Group list into map
   * @param keySelector How key from item is obtained
   * @param <K> Key item type
   * @return Map with items
   */
  default <K> IMap<K, eng.eSystem.collections.IList<T>> groupBy(Selector<T, K> keySelector) {
    EMap<K, eng.eSystem.collections.IList<T>> ret = new EMap<>();

    for (T item : this) {
      K key = keySelector.invoke(item);
      if (!ret.containsKey(key))
        ret.set(key, new eng.eSystem.collections.EList<>());
      ret.get(key).add(item);
    }

    return ret;
  }

  /**
   * Returns index of item in list, or exception
   * @param item Item to find
   * @return Index of item
   */
  default int indexOf(T item) {
    OptionalInt ret = tryIndexOf(item);
    if (ret.isEmpty())
      throw new ElementNotFoundException();
    else
      return ret.getAsInt();
  }

  /**
   * Returns index of first item in list,
   * @param predicate
   * @return
   */
  default int indexOf(Predicate<T> predicate) {
    OptionalInt ret = tryIndexOf(predicate);
    if (ret.isEmpty())
      throw new ElementNotFoundException();
    else
      return ret.getAsInt();
  }

  default <K extends Comparable<K>> IList<T> orderBy(Selector<T, K> selector) {
    return this.orderBy(selector, false);
  }

  default <K> IList<K> selectMany(Selector<T, IReadOnlyList<K>> selector) {
    EList<K> ret = new EList<>();
    this.forEach(q -> ret.addMany(selector.invoke(q)));
    return ret;
  }

  default Optional<T> tryGet(int index) {
    if (index < 0 || index >= this.size())
      return Optional.empty();
    else
      return Optional.of(this.get(index));
  }

  default <V> IList<V> whereItemClassIs(Class<? extends V> clazz, boolean includeInheritance) {
    IList<V> ret;
    if (includeInheritance)
      ret = this.where(q -> clazz.isAssignableFrom(q.getClass())).select(q -> (V) q);
    else
      ret = this.where(q -> q.getClass().equals(clazz)).select(q -> (V) q);
    return ret;
  }
}
