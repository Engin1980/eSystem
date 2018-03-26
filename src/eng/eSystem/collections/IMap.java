package eng.eSystem.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IMap<K,V> {
  int size();

  boolean isEmpty();

  boolean containsKey(K key);

  boolean containsValue(V value);

  V get(K key);

  V tryGet(K key);

  void add(K key, V value);

  void remove(K key);

  void tryRemove(K key);

  void addAll(Map<? extends K, ? extends V> m);

  void clear();

  Set<K> keySet();

  Collection<V> values();

  Set<Map.Entry<K, V>> entrySet();

  int hashCode();

  @Override
  boolean equals(Object o);
}
