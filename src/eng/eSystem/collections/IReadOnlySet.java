package eng.eSystem.collections;

import eng.eSystem.functionalInterfaces.Selector;

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

  default <K> IMap<K, ISet<T>> groupBy(Selector<T, K> keySelector){
    EMap<K, ISet<T>> ret = new EMap<>();

    for (T item : this) {
      K key = keySelector.getValue(item);
      if (!ret.containsKey(key))
        ret.set(key, new ESet<>());
      ret.get(key).add(item);
    }

    return ret;
  }

}
