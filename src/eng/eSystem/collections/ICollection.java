package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.ElementNotFoundException;
import eng.eSystem.utilites.Selector;

import java.util.Iterator;
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

  default <V> V aggregate(Selector<T, V> selector, BiFunction<V, V, V> aggregator, V initialAgregatorValue) {
    V ret = initialAgregatorValue;
    for (T t : this) {
      V v = selector.getValue(t);
      ret = aggregator.apply(ret, v);
    }
    return ret;
  }

  default double minDouble(Selector<T, Double> selector) {
    double ret = min(selector, Double.MIN_VALUE);
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
    double ret = max(selector, Double.MIN_VALUE);
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
    for (T t : this) {
      if (predicate.test(t))
        return t;
    }
    throw new ElementNotFoundException("No element fulfilling predicate was found.");
  }

  default T getFirst() {
    if (isEmpty())
      throw new ElementNotFoundException("Collection is empty.");
    Iterator<T> iter = this.iterator();
    T ret = iter.next();
    return ret;
  }

  default T tryGetFirst(T defaultValue) {
    if (isEmpty())
      return defaultValue;
    else {
      Iterator<T> iter = this.iterator();
      T ret = iter.next();
      return ret;
    }
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
    boolean found = false;
    T ret = null;
    for (T t : this) {
      if (predicate.test(t)) {
        ret = t;
        if (!found) found = true;
      }
    }
    if (!found)
      throw new ElementNotFoundException("No element fulfilling predicate was found.");
    else
      return ret;
  }

  default T getLast() {
    if (isEmpty())
      throw new ElementNotFoundException("Collection is empty.");
    T ret = null;
    for (T t : this) {
      ret = t;
    }
    return ret;
  }

  default T tryGetLast(T defaultValue) {
    if (isEmpty())
      return defaultValue;
    T ret = defaultValue;
    for (T t : this) {
      ret = t;
    }
    return ret;
  }

  default T tryGetLast() {
    return tryGetLast((T) null);
  }

}
