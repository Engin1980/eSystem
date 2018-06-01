package eng.eSystem.collections;

import eng.eSystem.utilites.Selector;

import java.util.List;
import java.util.function.Predicate;

public interface IReadOnlyList<T> extends ICollection<T> {

  T get(int index);

  IList<T> where(Predicate<T> predicate);

  T tryGetFirst(Predicate<T> predicate);

  T getFirst(Predicate<T> predicate);

  T tryGetLast(Predicate<T> predicate);

  T getLast(Predicate<T> predicate);

  boolean isAny(Predicate<T> predicate);

  boolean isAll(Predicate<T> predicate);

  double sum(Selector<T, Double> selector);

  double min(Selector<T, Double> selector);

  double max(Selector<T, Double> selector);

  int count(Predicate<T> predicate);

  <V> IList<V> select(Selector<T, V> selector);

  List<T> toList();

  T[] toArray(Class<T> itemType);

  void toList(List<T> target);

  T getRandom();

  T tryGetRandom();

  int getIndexOf(T item);

  Integer tryGetIndexOf(T item);

  int getIndexOf(Predicate<T> predicate);

  Integer tryGetIndexOf(Predicate<T> predicate);

  IList<T> whereItemClassIs(Class clazz, boolean includeInheritance);

  IList<T> distinct();

  <K> IList<T> distinct(Selector<T,K> selector);

  <K> ISet<T> getDuplicateItems(Selector<T,K> selector);
}
