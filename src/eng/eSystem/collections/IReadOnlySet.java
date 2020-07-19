package eng.eSystem.collections;

import eng.eSystem.functionalInterfaces.Selector;

import java.util.Set;
import java.util.function.Predicate;

public interface IReadOnlySet<T> extends ICollection<T> {

  default <K> IMap<K, ISet<T>> groupBy(Selector<T, K> keySelector) {
    EMap<K, ISet<T>> ret = new EMap<>();

    for (T item : this) {
      K key = keySelector.getValue(item);
      if (!ret.containsKey(key))
        ret.set(key, new ESet<>());
      ret.get(key).add(item);
    }

    return ret;
  }

  ISet<T> intersection(IReadOnlySet<T> otherSet);

  ISet<T> minus(IReadOnlySet<T> otherList);

  <V> ISet<V> select(Selector<T, V> selector);

  ISet<T> selectCount(int count);

  Set<T> toJavaSet();

  void toJavaSet(Set<T> target);

  IList<T> toList();

  ISet<T> union(IReadOnlySet<T> otherSet);

  ISet<T> where(Predicate<T> predicate);


}
