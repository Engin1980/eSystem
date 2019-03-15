package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.utilites.Selector;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface ICollection<T> extends Iterable<T> {
  int size();

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

  default boolean isAll(Predicate<T> predicate) {
    for (T t : this) {
      if (predicate.test(t) == false)
        return false;
    }
    return true;
  }

  default double sumDouble(Selector<T, Double> selector) {
    double ret = 0;
    for (T t : this) {
      ret += selector.getValue(t);
    }
    return ret;
  }

  default int sumInt(Selector<T, Integer> selector) {
    int ret = 0;
    for (T t : this) {
      ret += selector.getValue(t);
    }
    return ret;
  }

  default long sumLong(Selector<T, Long> selector) {
    long ret = 0;
    for (T t : this) {
      ret += selector.getValue(t);
    }
    return ret;
  }

  default <V> V aggregate(Selector<T, V> selector, BiFunction<V, V, V> aggregator, V initialAgregatorValue) {
    V ret = initialAgregatorValue;
    for (T t : this) {
      V v = selector.getValue(t);
      ret = aggregator.apply(ret, v);
    }
    return ret;
  }

  default double minDouble(Selector<T, Double> selector) {
    double ret = minDouble(selector, Double.MAX_VALUE);
    return ret;
  }

  default double minDouble(Selector<T, Double> selector, double defaultValueIfEmptyList) {
    double ret = min(selector, defaultValueIfEmptyList);
    return ret;
  }

  default int minInt(Selector<T, Integer> selector) {
    int ret = minInt(selector, Integer.MAX_VALUE);
    return ret;
  }

  default int minInt(Selector<T, Integer> selector, int defaultValueIfEmptyList) {
    int ret = min(selector, defaultValueIfEmptyList);
    return ret;
  }

  default double mean(Selector<T, Double> selector, double defaultValueIfEmptyList) {
    double ret;
    if (this.isEmpty())
      ret = defaultValueIfEmptyList;
    else {
      ret = this.sumDouble(selector) / this.size();
    }
    return ret;
  }

  default double mean(Selector<T, Double> selector) {
    double ret = this.mean(selector, Double.NaN);
    return ret;
  }

  default <V extends Comparable<V>> V min(Selector<T, V> selector, V minimalValue) {
    V ret = minimalValue;
    for (T t : this) {
      V v = selector.getValue(t);
      if (v != null && v.compareTo(ret) < 0)
        ret = v;
    }
    return ret;
  }

  default double maxDouble(Selector<T, Double> selector) {
    double ret = maxDouble(selector, Double.MIN_VALUE);
    return ret;
  }

  default double maxDouble(Selector<T, Double> selector, double defaultValueIfEmptyList) {
    double ret = max(selector, defaultValueIfEmptyList);
    return ret;
  }

  default int maxInt(Selector<T, Integer> selector) {
    return maxInt(selector, Integer.MIN_VALUE);
  }

  default int maxInt(Selector<T, Integer> selector, int defaultValueIfEmptyList) {
    int ret = max(selector, defaultValueIfEmptyList);
    return ret;
  }

  default <V extends Comparable<V>> V max(Selector<T, V> selector, V maximalValue) {
    V ret = maximalValue;
    for (T t : this) {
      V v = selector.getValue(t);
      if (v != null && v.compareTo(ret) > 0)
        ret = v;
    }
    return ret;
  }

  default int count(Predicate<T> predicate) {
    int ret = 0;
    for (T t : this) {
      if (predicate.test(t)) ret++;
    }
    return ret;
  }

  default boolean isEmpty() {
    return size() == 0;
  }

  boolean contains(T item);

  default T tryGetFirst(Predicate<T> predicate) {
    return tryGetFirst(predicate, null);
  }

  default T tryGetFirst(Predicate<T> predicate, T defaultValue) {
    for (T t : this) {
      if (predicate.test(t))
        return t;
    }
    return defaultValue;
  }

  default T getFirst(Predicate<T> predicate) {
    T ret = tryGetFirst(predicate);
    if (ret == null)
      throw new ElementNotFoundException("fulfilling predicate");
    else
      return ret;
  }

  default T getFirst() {
    T ret = tryGetFirst();
    if (ret == null)
      throw new ElementNotFoundException("Collection is empty.");
    return ret;
  }

  default T tryGetFirst(T defaultValue) {
    T ret = tryGetFirst(q -> true, defaultValue);
    return ret;
  }

  default T tryGetFirst() {
    return tryGetFirst((T) null);
  }

  default T tryGetLast(Predicate<T> predicate) {
    return tryGetLast(predicate, null);
  }

  default T tryGetLast(Predicate<T> predicate, T defaultValue) {
    T ret = defaultValue;
    for (T t : this) {
      if (predicate.test(t))
        ret = t;
    }
    return ret;
  }

  default T getLast(Predicate<T> predicate) {
    T ret = tryGetLast(predicate);
    if (ret == null)
      throw new ElementNotFoundException("fulfilling predicate");
    else
      return ret;
  }

  default T getLast() {
    T ret = tryGetLast();
    if (ret == null)
      throw new ElementNotFoundException("Collection is empty.");
    else
      return ret;
  }

  default T tryGetLast(T defaultValue) {
    T ret = tryGetLast(q -> true, defaultValue);
    return ret;
  }

  default T tryGetLast() {
    return tryGetLast((T) null);
  }

  default <V extends Comparable<V>> T getSmallest(Selector<T, V> selector) {
    if (this.isEmpty())
      throw new ElementNotFoundException();

    T ret = null;
    V min = null;
    for (T t : this) {
      if (ret == null) {
        ret = t;
        min = selector.getValue(t);
      } else {
        V v = selector.getValue(t);
        if (v.compareTo(min) < 0) {
          ret = t;
          min = v;
        }
      }
    }
    return ret;
  }

  default <V extends Comparable<V>> T getGreatest(Selector<T, V> selector) {
    if (this.isEmpty())
      throw new ElementNotFoundException();

    T ret = null;
    V min = null;
    for (T t : this) {
      if (ret == null) {
        ret = t;
        min = selector.getValue(t);
      } else {
        V v = selector.getValue(t);
        if (v.compareTo(min) > 0) {
          ret = t;
          min = v;
        }
      }
    }
    return ret;
  }

  default T getRandomByWeights(Selector<T, Double> weightSelector) {
    if (this.isEmpty())
      throw new ElementNotFoundException();

    T ret = null;
    double sum = this.sumDouble(q -> weightSelector.getValue(q));
    double val = Math.random() * sum;
    for (T t : this) {
      ret = t;
      val -= weightSelector.getValue(t);
      if (val < 0) break;
    }
    return ret;
  }

  default T getRandomByWeights(Selector<T, Double> weightSelector, Random rnd) {
    if (this.isEmpty())
      throw new ElementNotFoundException();

    T ret = null;
    double sum = this.sumDouble(q -> weightSelector.getValue(q));
    double val = rnd.nextDouble() * sum;
    for (T t : this) {
      ret = t;
      val -= weightSelector.getValue(t);
      if (val < 0) break;
    }
    return ret;
  }

  default T getMinimal(Selector<T, Double> minimizingCriterionSelector){
    if (this.isEmpty())
      throw new UnsupportedOperationException("This method cannot be invoked on an empty collection.");
    Double minimalCriterionValue = Double.MAX_VALUE;
    T ret = null;
    for (T t : this) {
      Double currentCriterionValue = minimizingCriterionSelector.getValue(t);
      if (currentCriterionValue < minimalCriterionValue){
        ret = t;
        minimalCriterionValue = currentCriterionValue;
      }
    }
    return ret;
  }

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
}
