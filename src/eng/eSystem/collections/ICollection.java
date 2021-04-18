package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.functionalInterfaces.Selector;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Represents generic object able to contain multiple items.
 * @param <T> Type of item in this object.
 */
public interface ICollection<T> extends Iterable<T> {
  /**
   * Returns True if item is in the collection.
   * Equality is based on JavaCollectionFramework equality in lists/sets, that is <code>equals(...)</code> function.
   *
   * @param item Item to find
   * @return True if item is in collection, false otherwise.
   */
  boolean contains(T item);

  /**
   * Returns number of elements in the collection.
   *
   * @return Number of elements
   */
  int size();

  /**
   * Returns random element. If collection is empty, throws an exception.
   * @param rnd Instance of Random class
   * @return Random element from collection
   */
  T getRandom(Random rnd);

  /**
   * Converts collection to {@link IList}.
   *
   * @return {@link IList} instance with same elements.
   */
  IList<T> toList();

  /**
   * Adds collection items to {@link java.util.List}.
   *
   * @param target Target list in which elements will be added.
   */
  void toJavaList(java.util.List<T> target);


  /**
   * Converts collection to {@link ISet}.
   *
   * @return {@link ISet} instance with same elements.
   */
  ISet<T> toSet();

  /**
   * Adds collection items to {@link java.util.Set}.
   *
   * @param target Target set in which elements will be added.
   */
  void toJavaSet(java.util.Set<T> target);

  /**
   * Aggregates all the elements of this collection into a single value.
   *
   * @param selector               How value representing one item is selected.
   * @param aggregator             How value of item is aggregated with previous aggregating values.
   * @param initialAggregatorValue What is the initial aggregation value.
   * @param <V>                    Result value type
   * @return Result of the aggregation
   */
  default <V> V aggregate(Selector<T, V> selector, BiFunction<V, V, V> aggregator, V initialAggregatorValue) {
    V ret = initialAggregatorValue;
    for (T t : this) {
      V v = selector.invoke(t);
      ret = aggregator.apply(ret, v);
    }
    return ret;
  }

  /**
   * Return number of elements fulfilling {@link eng.eSystem.functionalInterfaces.Predicate}.
   *
   * @param predicate Predicate to fulfil
   * @return Number of elements accepted by predicate
   */
  default int count(Predicate<T> predicate) {
    int ret = 0;
    for (T t : this) {
      if (predicate.test(t)) ret++;
    }
    return ret;
  }

  /**
   * Returns number of elements. Equal to {@link #size()}.
   *
   * @return Number of elements in this collection
   * @see #size()
   */
  default int count() {
    return this.size();
  }

  /**
   * Returns the first element fulfilling the predicate, {@link ElementNotFoundException} otherwise.
   *
   * @param predicate Predicate to fulfil.
   * @return The first found element fulfilling predicate, or exception.
   */
  default T getFirst(Predicate<T> predicate) {
    Optional<T> ret = tryGetFirst(predicate);
    if (ret.isEmpty())
      throw new ElementNotFoundException("fulfilling predicate");
    else
      return ret.get();
  }

  /**
   * Returns the first element, {@link ElementNotFoundException} otherwise.
   *
   * @return The first found element fulfilling predicate, or exception.
   */
  default T getFirst() {
    Optional<T> ret = tryGetFirst();
    if (ret.isEmpty())
      throw new ElementNotFoundException("Collection is empty.");
    return ret.get();
  }

  /**
   * Returns the last element fulfilling the predicate, {@link ElementNotFoundException} otherwise.
   *
   * @param predicate Predicate to fulfil.
   * @return The last found element fulfilling predicate, or exception.
   */
  default T getLast(Predicate<T> predicate) {
    Optional<T> ret = tryGetLast(predicate);
    if (ret.isEmpty())
      throw new ElementNotFoundException("fulfilling predicate");
    else
      return ret.get();
  }

  /**
   * Returns the last element, {@link ElementNotFoundException} otherwise.
   *
   * @return The last found element fulfilling predicate, or exception.
   */
  default T getLast() {
    Optional<T> ret = tryGetLast();
    if (ret.isEmpty())
      throw new ElementNotFoundException("Collection is empty.");
    else
      return ret.get();
  }

  /**
   * Get the maximal/highest element with respect to the criterion.
   *
   * @param criteriaSelector Criterion selecting compared value for each element; result must be {@link Comparable}.
   * @param <V>              Type of criterion to be compared
   * @return The maximal element.
   * @see #getMaximal(Comparator)
   */
  default <V extends Comparable<V>> Optional<T> getMaximal(Selector<T, V> criteriaSelector) {
    return this.getMaximal(Comparator.comparing(criteriaSelector::invoke));
  }

  /**
   * Get the maximal/highest element with respect to the comparator.
   *
   * @param comparator Defines how to compare two collection elements.
   * @return The maximal element.
   */
  default Optional<T> getMaximal(java.util.Comparator<T> comparator) {
    if (this.isEmpty()) return Optional.empty();

    T ret = null;
    for (T t : this) {
      if (ret == null) {
        ret = t;
      } else {
        if (comparator.compare(ret, t) > 0)
          ret = t;
      }
    }
    return ret == null ? Optional.empty() : Optional.of(ret);
  }

  /**
   * Get the minimal/lowest element with respect to the criterion.
   *
   * @param criteriaSelector Criterion selecting compared value for each element; result must be {@link Comparable}.
   * @param <V>              Type of criterion to be compared
   * @return The minimal/lowest element.
   * @see #getMinimal(Comparator)
   */
  default <V extends Comparable<V>> Optional<T> getMinimal(Selector<T, V> criteriaSelector) {
    return this.getMinimal(Comparator.comparing(criteriaSelector::invoke));
  }

  /**
   * Get the minimal/lowest element with respect to the comparator.
   *
   * @param comparator Defines how to compare two collection elements.
   * @return The minimal/lowest element.
   */
  default Optional<T> getMinimal(java.util.Comparator<T> comparator) {
    if (this.isEmpty()) return Optional.empty();

    T ret = null;
    for (T t : this) {
      if (ret == null) {
        ret = t;
      } else {
        if (comparator.compare(ret, t) < 0)
          ret = t;
      }
    }
    return ret == null ? Optional.empty() : Optional.of(ret);
  }

  /**
   * Returns random element. If collection is empty, throws an exception.
   * @return Random element from collection
   */
  default T getRandom() {
    return this.getRandom(Common.rnd);
  }

  /**
   * Returns random element by weight. If collection is empty, throws an exception.
   * @param weightSelector Selector to select the weight for collection element
   * @return Random element from collection
   */
  default T getRandomByWeights(Selector<T, Double> weightSelector) {
    return getRandomByWeights(weightSelector, Common.rnd);
  }

  /**
   * Returns random element by weight. If collection is empty, throws an exception.
   * @param weightSelector Selector to select the weight for collection element
   * @param rnd Instance of Random class
   * @return Random element from collection
   */
  default T getRandomByWeights(Selector<T, Double> weightSelector, Random rnd) {
    Common.ensureNotEmpty(this);

    T ret = null;
    double sum = this.sumDouble(q -> weightSelector.invoke(q));
    double val = rnd.nextDouble() * sum;
    for (T t : this) {
      ret = t;
      val -= weightSelector.invoke(t);
      if (val < 0) break;
    }
    return ret;
  }

  /**
   * Checks if all items in this collections match an predicate.
   * @param predicate Predicate to match.
   * @return True if all elements passes the predicate, false otherwise.
   */
  default boolean isAll(Predicate<T> predicate) {
    for (T t : this) {
      if (predicate.test(t) == false)
        return false;
    }
    return true;
  }

  /**
   * Checks if at least one item in this collections matches an predicate.
   * @param predicate Predicate to match.
   * @return True if at leas one element passes the predicate, false otherwise.
   */
  default boolean isAny(Predicate<T> predicate) {
    for (T t : this) {
      if (predicate.test(t))
        return true;
    }
    return false;
  }

  /**
   * Checks if this collection is empty.
   * @return True if this collection is empty, false otherwise.
   */
  default boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Checks if any item in this collections matches an predicate.
   * @param predicate Predicate to match.
   * @return True if any element passes the predicate, false otherwise.
   */
  default boolean isNone(Predicate<T> predicate) {
    return !isAny(predicate);
  }

  /**
   * Returns maximal value selected from items in this collection.
   * @param selector How to select representative value from collection's item, must be {@link Comparable}.
   * @param <V> Type if result
   * @return Maximal item, or empty {@link Optional}
   */
  default <V extends Comparable<V>> Optional<V> max(Selector<T, V> selector) {
    IList<V> tmp = selectNonNull(selector);
    if (tmp.isEmpty())
      return Optional.empty();

    V ret = null;
    for (V v : tmp) {
      if (ret == null)
        ret = v;
      else if (v != null && v.compareTo(ret) > 0)
        ret = v;
    }
    return ret == null ? Optional.empty() : Optional.of(ret);
  }

  /**
   * Returns maximal double value selected from items in this collection.
   * @param selector How to select double value from collection's item.
   * @return Maximal value, or empty {@link Optional} for empty collection.
   */
  default OptionalDouble maxDouble(Selector<T, Double> selector) {
    Optional<Double> ret = max(selector);
    return ret.isEmpty() ? OptionalDouble.empty() : OptionalDouble.of(ret.get());
  }

  /**
   * Returns maximal int value selected from items in this collection.
   * @param selector How to select int value from collection's item.
   * @return Maximal value, or empty {@link Optional} for empty collection.
   */
  default OptionalInt maxInt(Selector<T, Integer> selector) {
    Optional<Integer> ret = max(selector);
    return ret.isEmpty() ? OptionalInt.empty() : OptionalInt.of(ret.get());
  }

  /**
   * Returns average value selected from items in this collection.
   * @param selector How to select value representing one item
   * @return Mean over all selected value, or empty {@link Optional}.
   */
  default OptionalDouble mean(Selector<T, Double> selector) {
    OptionalDouble ret;
    if (this.isEmpty())
      ret = OptionalDouble.empty();
    else
      ret = OptionalDouble.of(this.sumDouble(selector) / this.size());
    return ret;
  }

  /**
   * Returns minimal value selected from items in this collection.
   * @param selector How to select representative value from collection's item, must be {@link Comparable}.
   * @param <V> Type if result
   * @return Minimal item, or empty {@link Optional}
   */
  default <V extends Comparable<V>> Optional<V> min(Selector<T, V> selector) {
    IList<V> tmp = selectNonNull(selector);
    if (tmp.isEmpty())
      return Optional.empty();

    V ret = null;
    for (V v : tmp) {
      if (ret == null)
        ret = v;
      else if (v != null && v.compareTo(ret) < 0)
        ret = v;
    }
    return ret == null ? Optional.empty() : Optional.of(ret);
  }

  /**
   * Returns minimal double value selected from items in this collection.
   * @param selector How to select double value from collection's item.
   * @return Minimal value, or empty {@link Optional} for empty collection.
   */
  default OptionalDouble minDouble(Selector<T, Double> selector) {
    Optional<Double> ret = min(selector);
    return ret.isEmpty() ? OptionalDouble.empty() : OptionalDouble.of(ret.get());
  }

  /**
   * Returns minimal int value selected from items in this collection.
   * @param selector How to select int value from collection's item.
   * @return Minimal value, or empty {@link Optional} for empty collection.
   */
  default OptionalInt minInt(Selector<T, Integer> selector) {
    Optional<Integer> ret = min(selector);
    return ret.isEmpty() ? OptionalInt.empty() : OptionalInt.of(ret.get());
  }

  /**
   * Returns sum of double values selected from items in this collection.
   * @param selector How to select double value from collection's item.
   * @return Sum of selected values
   */
  default double sumDouble(Selector<T, Double> selector) {
    double ret = 0;
    for (T t : this) {
      ret += selector.invoke(t);
    }
    return ret;
  }

  /**
   * Returns sum of int values selected from items in this collection.
   * @param selector How to select int value from collection's item.
   * @return Sum of selected values
   */
  default int sumInt(Selector<T, Integer> selector) {
    int ret = 0;
    for (T t : this) {
      ret += selector.invoke(t);
    }
    return ret;
  }

  /**
   * Returns sum of long values selected from items in this collection.
   * @param selector How to select long value from collection's item.
   * @return Sum of selected values
   */
  default long sumLong(Selector<T, Long> selector) {
    long ret = 0;
    for (T t : this) {
      ret += selector.invoke(t);
    }
    return ret;
  }

  /**
   * Converts items into typed array
   * @param arrayItemType Result array item type
   * @return Array with elements of this collection.
   */
  default T[] toArray(Class<T> arrayItemType) {
    T[] ret = (T[]) Array.newInstance(arrayItemType, this.size());
    int index = 0;
    for (T item : this) {
      ret[index] = item;
      index++;
    }
    return ret;
  }

  /**
   * Converts items into typed array of different type than the current collection items type.
   * @param arrayItemType Result array item type
   * @return Array with elements of this collection.
   */
  default <K> K[] toArrayUnchecked(Class<K> arrayItemType) {
    K[] ret = (K[]) Array.newInstance(arrayItemType, this.size());
    int index = 0;
    for (T item : this) {
      ret[index] = (K) item;
      index++;
    }
    return ret;
  }

  /**
   * Converts collection into {@link java.util.List} ({@link java.util.ArrayList} by default).
   * @return Java list of items.
   */
  default java.util.List<T> toJavaList() {
    java.util.List<T> ret = new java.util.ArrayList<>();
    this.toJavaList(ret);
    return ret;
  }

  /**
   * Converts collection into {@link java.util.Set} ({@link java.util.HashSet} by default).
   * @return Java set of items. Duplicity items will be skipped.
   */
  default java.util.Set<T> toJavaSet() {
    java.util.Set<T> ret = new java.util.HashSet<>();
    this.toJavaSet(ret);
    return ret;
  }

  /**
   * Converts collection into {@link IMap} ({@link EMap} by default).
   * @param keySelector How key will be obtained from item
   * @param  valueSelector How value will be obtained from item
   * @param <K> Key type
   * @param <V> Value type
   * @return Map of items
   */
  default <K, V> IMap<K, V> toMap(Selector<T, K> keySelector, Selector<T, V> valueSelector) {
    IMap<K, V> ret = new EMap<>();

    for (T t : this) {
      K key = keySelector.invoke(t);
      V value = valueSelector.invoke(t);
      ret.set(key, value);
    }

    return ret;
  }

  /**
   * Tries to return the first element matching predicate from this collection.
   * @param predicate Predicate selecting the element
   * @return The first element, or empty of {@link Optional}
   * @see #getFirst
   */
  default Optional<T> tryGetFirst(Predicate<T> predicate) {
    for (T t : this) {
      if (predicate.test(t))
        return Optional.of(t);
    }
    return Optional.empty();
  }

  /**
   * Tries to return the first element from this collection.
   * @return The first element, or empty of {@link Optional}
   * @see #getFirst
   */
  default Optional<T> tryGetFirst() {
    return tryGetFirst(q -> true);
  }

  /**
   * Tries to return the last element matching predicate from this collection.
   * @param predicate Predicate selecting the element
   * @return The last element, or empty of {@link Optional}
   * @see #getLast
   */
  default Optional<T> tryGetLast(Predicate<T> predicate) {
    Optional<T> ret = Optional.empty();
    for (T t : this) {
      if (predicate.test(t))
        ret = Optional.of(t);
    }
    return ret;
  }

  /**
   * Tries to return the last element from this collection.
   * @return The first element, or empty of {@link Optional}
   * @see #getLast
   */
  default Optional<T> tryGetLast() {
    return tryGetLast(q -> true);
  }

  /**
   * Tries to return a random item from this collection.
   * @return Random item, or empty of {@link Optional} if collection is empty
   * @see #getRandom
   */
  default Optional<T> tryGetRandom() {
    Optional<T> ret = this.isEmpty() ? Optional.empty() : Optional.of(getRandom());
    return ret;
  }

  /**
   * Tries to return a random item from this collection.
   * @param rnd Instance of provided {@link Random}
   * @return Random item, or empty of {@link Optional} if collection is empty
   */
  default Optional<T> tryGetRandom(Random rnd) {
    Optional<T> ret = this.isEmpty() ? Optional.empty() : Optional.of(getRandom(rnd));
    return ret;
  }

  //region Private
  private <V> IList<V> selectNonNull(Selector<T, V> selector) {
    EList<V> ret = new EList<>();
    for (T t : this) {
      V v = selector.invoke(t);
      if (v != null) ret.add(v);
    }
    return ret;
  }
  //endregion
}
