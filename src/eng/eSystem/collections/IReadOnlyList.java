package eng.eSystem.collections;

import eng.eSystem.utilites.Selector;

import java.util.function.Predicate;

public interface IReadOnlyList<T> extends ICollection<T> {

  T get(int index);

//  IList<T> where(Predicate<T> predicate);
//
//  T tryGetFirst(Predicate<T> predicate);
//  T getFirst(Predicate<T> predicate);
//  T tryGetLast(Predicate<T> predicate);
//  T getLast(Predicate<T> predicate);
//  T isAny(Predicate<T> predicate);
//  T isAll(Predicate<T> predicate);
//
//  double sum(Selector<T,Double> selector);
//  double min(Selector<T,Double> selector);
//  double max(Selector<T,Double> selector);
//  int count(Predicate<T> predicate);
//
//  <V> IList<V> select(Selector<T,V> selector);

}
