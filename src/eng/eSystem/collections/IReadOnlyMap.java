package eng.eSystem.collections;

import eng.eSystem.utilites.Selector;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public interface IReadOnlyMap<K,V> extends Iterable<Map.Entry<K, V>> {

  int size();

  boolean isEmpty();

  boolean containsKey(K key);

  boolean containsValue(V value);

  IMap<K,V> whereKey(Predicate<K> predicate);

  IMap<K,V> whereValue(Predicate<V> predicate);

  V get(K key);

  V tryGet(K key);

  ISet<K> getKeys();

  ICollection<V> getValues();

  ISet<Map.Entry<K, V>> getEntries();

  <Knew, Vnew> IMap<Knew, Vnew> select(Selector<K, Knew> keySelector, Selector<V, Vnew> valueSelector);

  int hashCode();

  @Override
  boolean equals(Object o);

}
