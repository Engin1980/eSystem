package eng.eSystem.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public interface IReadOnlyMap<K,V> {

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

  int hashCode();

  @Override
  boolean equals(Object o);

}
