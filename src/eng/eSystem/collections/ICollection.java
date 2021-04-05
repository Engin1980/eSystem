package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.EmptyCollectionException;
import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.exceptions.DeprecatedException;
import eng.eSystem.functionalInterfaces.Selector;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface ICollection<T> extends Iterable<T> {
  //region Common
  boolean contains(T item);

  default boolean isEmpty() {
    return size() == 0;
  }

  int size();

  default int count(Predicate<T> predicate) {
    int ret = 0;
    for (T t : this) {
      if (predicate.test(t)) ret++;
    }
    return ret;
  }

  default int count() {
    return this.size();
  }
  //endregion common

  //region Item getters
  default T getFirst(Predicate<T> predicate) {
    Optional<T> ret = tryGetFirst(predicate);
    if (ret.isEmpty())
      throw new ElementNotFoundException("fulfilling predicate");
    else
      return ret.get();
  }

  default T getFirst() {
    Optional<T> ret = tryGetFirst();
    if (ret.isEmpty())
      throw new ElementNotFoundException("Collection is empty.");
    return ret.get();
  }

  default T getLast(Predicate<T> predicate) {
    Optional<T> ret = tryGetLast(predicate);
    if (ret.isEmpty())
      throw new ElementNotFoundException("fulfilling predicate");
    else
      return ret.get();
  }

  default T getLast() {
    Optional<T> ret = tryGetLast();
    if (ret.isEmpty())
      throw new ElementNotFoundException("Collection is empty.");
    else
      return ret.get();
  }

  default <V extends Comparable<V>> Optional<T> getMaximal(Selector<T, V> criteriaSelector) {
    return this.getMaximal(Comparator.comparing(criteriaSelector::invoke));
  }

  default <V extends Comparable<V>> Optional<T> getMaximal(java.util.Comparator<T> comparator) {
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

  default <V extends Comparable<V>> Optional<T> getMinimal(Selector<T, V> criteriaSelector) {
    return this.getMinimal(Comparator.comparing(criteriaSelector::invoke));
  }

  default <V extends Comparable<V>> Optional<T> getMinimal(java.util.Comparator<T> comparator) {
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

  default T getRandomByWeights(Selector<T, Double> weightSelector) {
    return getRandomByWeights(weightSelector, Common.rnd);
  }

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

  default T getRandom(){
    return this.getRandom(Common.rnd);
  }

  default Optional<T> tryGetRandom(){
    Optional<T> ret = this.isEmpty() ? Optional.empty() : Optional.of(getRandom());
    return ret;
  }

  default Optional<T> tryGetRandom(Random rnd){
    Optional<T> ret = this.isEmpty() ? Optional.empty() : Optional.of(getRandom(rnd));
    return ret;
  }

  /**
   * Returns random element. Collection should not be empty.
   * @param rnd Instance of Random class
   * @return Random element from collection
   * Should throw {link EmptyCollectionException} if collection is empty.
   */
  T getRandom(Random rnd);

  default Optional<T> tryGetFirst(Predicate<T> predicate) {
    for (T t : this) {
      if (predicate.test(t))
        return Optional.of(t);
    }
    return Optional.empty();
  }

  default Optional<T> tryGetFirst() {
    return tryGetFirst(q->true);
  }

  default Optional<T> tryGetLast(Predicate<T> predicate) {
    Optional<T> ret = Optional.empty();
    for (T t : this) {
      if (predicate.test(t))
        ret = Optional.of(t);
    }
    return ret;
  }

  default Optional<T> tryGetLast() {
    return tryGetLast(q->true);
  }
  //endregion

  //region Quantification operations
  default boolean isAll(Predicate<T> predicate) {
    for (T t : this) {
      if (predicate.test(t) == false)
        return false;
    }
    return true;
  }

  default boolean isAny(Predicate<T> predicate) {
    for (T t : this) {
      if (predicate.test(t))
        return true;
    }
    return false;
  }

  default boolean isNone(Predicate<T> predicate) {
    return !isAny(predicate);
  }
  //endregion

  //region Min-Max-Mean operations
  default <V> V aggregate(Selector<T, V> selector, BiFunction<V, V, V> aggregator, V initialAggregatorValue) {
    V ret = initialAggregatorValue;
    for (T t : this) {
      V v = selector.invoke(t);
      ret = aggregator.apply(ret, v);
    }
    return ret;
  }

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

  default OptionalDouble maxDouble(Selector<T, Double> selector) {
    Optional<Double> ret = max(selector);
    return ret.isEmpty() ? OptionalDouble.empty() : OptionalDouble.of(ret.get());
  }

  default OptionalInt maxInt(Selector<T, Integer> selector) {
    Optional<Integer> ret = max(selector);
    return ret.isEmpty() ? OptionalInt.empty() : OptionalInt.of(ret.get());
  }

  default OptionalDouble mean(Selector<T, Double> selector) {
    OptionalDouble ret;
    if (this.isEmpty())
      ret = OptionalDouble.empty();
    else
      ret = OptionalDouble.of(this.sumDouble(selector) / this.size());
    return ret;
  }

  default <V extends Comparable<V>> Optional<V> min(Selector<T, V> selector) {
    IList<V> tmp = selectNonNull(selector);
    if (tmp.isEmpty())
      throw new EmptyCollectionException("All items when processed by selector are null.");

    V ret = null;
    for (V v : tmp) {
      if (ret == null)
        ret = v;
      else if (v != null && v.compareTo(ret) < 0)
        ret = v;
    }
    return ret == null ? Optional.empty() : Optional.of(ret);
  }

  default OptionalDouble minDouble(Selector<T, Double> selector) {
    Optional<Double> ret = min(selector);
    return ret.isEmpty() ? OptionalDouble.empty() : OptionalDouble.of(ret.get());
  }

  default OptionalInt minInt(Selector<T, Integer> selector) {
    Optional<Integer> ret = min(selector);
    return ret.isEmpty() ? OptionalInt.empty() : OptionalInt.of(ret.get());
  }
  //endregion

  //region Sum operations
  default double sumDouble(Selector<T, Double> selector) {
    double ret = 0;
    for (T t : this) {
      ret += selector.invoke(t);
    }
    return ret;
  }

  default int sumInt(Selector<T, Integer> selector) {
    int ret = 0;
    for (T t : this) {
      ret += selector.invoke(t);
    }
    return ret;
  }

  default long sumLong(Selector<T, Long> selector) {
    long ret = 0;
    for (T t : this) {
      ret += selector.invoke(t);
    }
    return ret;
  }
  //endregion

  //region ..toSomething() operations
  default T[] toArray(Class<T> arrayItemType) {
    T[] ret = (T[]) Array.newInstance(arrayItemType, this.size());
    int index = 0;
    for (T item : this) {
      ret[index] = item;
      index++;
    }
    return ret;
  }

  default <K> K[] toArrayUnchecked(Class<K> arrayItemType) {
    K[] ret = (K[]) Array.newInstance(arrayItemType, this.size());
    int index = 0;
    for (T item : this) {
      ret[index] = (K) item;
      index++;
    }
    return ret;
  }

  default <K, V> IMap<K, V> toMap(Selector<T, K> keySelector, Selector<T, V> valueSelector) {
    IMap<K, V> ret = new EMap<>();

    for (T t : this) {
      K key = keySelector.invoke(t);
      V value = valueSelector.invoke(t);
      ret.set(key, value);
    }

    return ret;
  }

  IList<T> toList();

  default java.util.List<T> toJavaList(){
    java.util.List<T> ret = new java.util.ArrayList<>();
    this.toJavaList(ret);
    return ret;
  }

  void toJavaList(java.util.List<T> target);

  ISet<T> toSet();

  default java.util.Set<T> toJavaSet(){
    java.util.Set<T> ret = new java.util.HashSet<>();
    this.toJavaSet(ret);
    return ret;
  }

  void toJavaSet(java.util.Set<T> target);
  //endregion

  //region Private
  private <V> IList<V> selectNonNull(Selector<T,V> selector){
    EList<V> ret = new EList<>();
    for (T t : this) {
      V v = selector.invoke(t);
      if (v != null) ret.add(v);
    }
    return ret;
  }
  //endregion
}
