package eng.eSystem.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EMap<K, V> {
  private Map<K, V> inner;

  public EMap(Map<K, V> inner) {
    this.inner = inner;
  }

  public EMap() {
    this.inner = new HashMap<>();
  }

  public int size() {
    return inner.size();
  }

  public boolean isEmpty() {
    return inner.isEmpty();
  }

  public boolean containsKey(K key) {
    return inner.containsKey(key);
  }

  public boolean containsValue(V value) {
    return inner.containsValue(value);
  }

  public V get(K key) {
    if (inner.containsKey(key) == false)
      throw new NoSuchKeyException(key);
    else
      return inner.get(key);
  }

  public V tryGet(K key) {
    if (inner.containsKey(key) == false)
      return null;
    else
      return inner.get(key);
  }

  public V put(K key, V value) {
    return inner.put(key, value);
  }

  public V remove(Object key) {
    if (inner.containsKey(key) == false)
      throw new NoSuchKeyException(key);
    else
      return inner.remove(key);
  }

  public V tryRemove(Object key) {
    if (inner.containsKey(key))
      return inner.remove(key);
    else
      return null;
  }

  public void putAll(Map<? extends K, ? extends V> m) {
    inner.putAll(m);
  }

  public void clear() {
    inner.clear();
  }

  public Set<K> keySet() {
    return inner.keySet();
  }

  public Collection<V> values() {
    return inner.values();
  }

  public Set<Map.Entry<K, V>> entrySet() {
    return inner.entrySet();
  }

  public int hashCode() {
    return inner.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return inner.equals(o);
  }
}
