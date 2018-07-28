package eng.eSystem.collections;

import eng.eSystem.utilites.Selector;

import java.util.Set;
import java.util.function.Predicate;

public interface IReadOnlySet<T> extends ICollection<T> {

  ISet<T> where(Predicate<T> predicate);

  Set<T> toSet();

  void toSet(Set<T> target);

  <V> ISet<V> select(Selector<T, V> selector);

  ISet<T> selectCount(int count);

  ISet<T> union(IReadOnlySet<T> otherSet);

  ISet<T> intersection(IReadOnlySet<T> otherSet);

}
