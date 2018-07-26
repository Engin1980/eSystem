package eng.eSystem.collections;

import eng.eSystem.utilites.Action;
import eng.eSystem.utilites.Selector;

import java.util.function.Predicate;

public interface ICollection<T> extends Iterable<T> {
  int size();

  boolean isAny(Predicate<T> predicate);

  boolean isAll(Predicate<T> predicate);

  double sum(Selector<T, Double> selector);

  double min(Selector<T, Double> selector);

  <V extends Comparable<V>> V min2 (Selector<T, V> selector, V minimalValue);

  double max(Selector<T, Double> selector);

  <V extends Comparable<V>> V max2 (Selector<T, V> selector, V maximalValue);

  int count(Predicate<T> predicate);

  boolean isEmpty();

  boolean contains(T item);
}
