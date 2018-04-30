package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.NoSuchKeyException;
import eng.eSystem.utilites.Selector;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class EMap<K, V> implements IMap<K, V> {
  private Map<K, V> inner;

  public EMap(Map<K, V> inner) {
    this.inner = inner;
  }

  public EMap(ISet<Map.Entry<K, V>> entries) {
    this.inner = new HashMap<>();
    entries.forEach(q -> inner.put(q.getKey(), q.getValue()));
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

  @Override
  public boolean containsKey(K key) {
    return inner.containsKey(key);
  }

  public boolean containsValue(V value) {
    return inner.containsValue(value);
  }

  @Override
  public IMap<K, V> whereKey(Predicate<K> predicate) {
    EMap<K, V> ret;
    ISet<Map.Entry<K, V>> entries = this.getEntries();
    entries = entries.where(q -> predicate.test(q.getKey()));
    ret = new EMap<>(entries);
    return ret;
  }

  @Override
  public IMap<K, V> whereValue(Predicate<V> predicate) {
    EMap<K, V> ret;
    ISet<Map.Entry<K, V>> entries = this.getEntries();
    entries = entries.where(q -> predicate.test(q.getValue()));
    ret = new EMap<>(entries);
    return ret;
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

  @Override
  public ISet<K> getKeys() {
    return new ESet(inner.keySet());
  }

  @Override
  public ICollection<V> getValues() {
    return new EList<>(inner.values());
  }

  @Override
  public ISet<Map.Entry<K, V>> getEntries() {
    return new ESet<Map.Entry<K, V>>(inner.entrySet());
  }

  @Override
  public <Knew, Vnew> IMap<Knew, Vnew> select(Selector<K, Knew> keySelector, Selector<V, Vnew> valueSelector) {
    IMap<Knew, Vnew> ret = new EMap<>();

    for (K key : this.getKeys()) {
      Knew newKey;
      Vnew newValue;
      newKey = keySelector.getValue(key);
      newValue = valueSelector.getValue(this.get(key));
      ret.set(newKey, newValue);
    }

    return ret;
  }

  @Override
  public void set(K key, V value) {
    inner.put(key, value);
  }

  public void remove(Object key) {
    if (inner.containsKey(key) == false)
      throw new NoSuchKeyException(key);
    else
      inner.remove(key);
  }

  public void tryRemove(Object key) {
    if (inner.containsKey(key))
      inner.remove(key);
  }

  @Override
  public void set(Map<? extends K, ? extends V> m) {
    inner.putAll(m);
  }

  @Override
  public void set(Map.Entry<? extends K, ? extends V> m) {

  }

  public void clear() {
    inner.clear();
  }

  @Override
  public int hashCode() {
    return inner.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return inner.equals(o);
  }

  @Override
  public String toString() {
    return String.format("EMap{%d items}", this.size());
  }
}
