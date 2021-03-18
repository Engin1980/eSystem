package eng.eSystem.collection;

import eng.eSystem.collection.exceptions.EmptyCollectionException;
import eng.eSystem.collections.EMap;
import eng.eSystem.collections.IMap;
import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.exceptions.DeprecatedException;
import eng.eSystem.functionalInterfaces.Selector;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Optional;
import java.util.Random;
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

  @Deprecated
  default <V extends Comparable<V>> T getGreatest(Selector<T, V> selector) {
    throw new DeprecatedException("Use getMaximal() instead.");
  }

  default <V extends Comparable<V>> T getMaximal(Selector<T, V> criteriaSelector) {
    Common.ensureNotEmpty(this);

    return this.getMaximal(Comparator.comparing(criteriaSelector::invoke));
  }

  default <V extends Comparable<V>> T getMaximal(java.util.Comparator<T> comparator) {
    Common.ensureNotEmpty(this);

    T ret = null;
    for (T t : this) {
      if (ret == null) {
        ret = t;
      } else {
        if (comparator.compare(ret, t) > 0)
          ret = t;
      }
    }
    return ret;
  }

  default <V extends Comparable<V>> T getMinimal(Selector<T, V> criteriaSelector) {
    return this.getMinimal(Comparator.comparing(criteriaSelector::invoke));
  }

  default <V extends Comparable<V>> T getMinimal(java.util.Comparator<T> comparator) {
    Common.ensureNotEmpty(this);

    T ret = null;
    for (T t : this) {
      if (ret == null) {
        ret = t;
      } else {
        if (comparator.compare(ret, t) < 0)
          ret = t;
      }
    }
    return ret;
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

  default <V extends Comparable<V>> V max(Selector<T, V> selector) {
    Common.ensureNotEmpty(this);
    IList<V> tmp = selectNonNull(selector);
    if (tmp.isEmpty())
      throw new EmptyCollectionException("All items when processed by selector are null.");

    V ret = null;
    for (V v : tmp) {
      if (ret == null)
        ret = v;
      else if (v != null && v.compareTo(ret) > 0)
        ret = v;
    }
    return ret;
  }

  default double maxDouble(Selector<T, Double> selector) {
    double ret = max(selector);
    return ret;
  }

  default int maxInt(Selector<T, Integer> selector) {
    int ret = max(selector);
    return ret;
  }

  default double mean(Selector<T, Double> selector) {
    Common.ensureNotEmpty(this);
    double ret = this.sumDouble(selector) / this.size();
    return ret;
  }

  default <V extends Comparable<V>> V min(Selector<T, V> selector) {
    Common.ensureNotEmpty(this);
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
    return ret;
  }

  default double minDouble(Selector<T, Double> selector) {
    double ret = min(selector);
    return ret;
  }

  default int minInt(Selector<T, Integer> selector) {
    int ret = min(selector);
    return ret;
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
