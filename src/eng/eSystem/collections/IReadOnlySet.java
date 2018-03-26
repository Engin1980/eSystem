package eng.eSystem.collections;

import eng.eSystem.utilites.Selector;

import java.util.Set;
import java.util.function.Predicate;

public interface IReadOnlySet<T> extends ICollection<T> {

  ISet<T> where(Predicate<T> predicate);

  T tryGet(Predicate<T> predicate);

  T get(Predicate<T> predicate);

  <V> ISet<V> select(Selector<T, V> selector);

  Set<T> toSet();

  void toSet(Set<T> target);
}
