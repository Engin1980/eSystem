package eng.eSystem.collections;

import eng.eSystem.validation.EAssert;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class EAbstractMap<K, V> implements IMap<K, V> {
  protected final Map<K, V> inner;

  protected EAbstractMap(Map<K, V> inner) {
    EAssert.Argument.isNotNull(inner, "inner");
    this.inner = inner;
  }

  @Override
  public void clear() {
    inner.clear();
  }

  @Override
  public boolean containsKey(K key) {
    return inner.containsKey(key);
  }

  @Override
  public boolean containsValue(V value) {
    return inner.containsValue(value);
  }

  @Override
  public boolean equals(Object o) {
    return this == o;
  }

  @Override
  public ISet<Map.Entry<K, V>> getEntries() {
    return new ESet<Map.Entry<K, V>>().with(inner.entrySet());
  }

  @Override
  public ISet<K> getKeys() {
    return new ESet<K>().with(inner.keySet());
  }

  @Override
  public ICollection<V> getValues() {
    IList<V> ret = new EList<>();
    return new EList<V>().with(inner.values());
  }

  @Override
  public int hashCode() {
    return inner.hashCode();
  }

  @Override
  public void set(K key, V value) {
    inner.put(key, value);
  }

  @Override
  public void setMany(Map<? extends K, ? extends V> m) {
    inner.putAll(m);
  }

  @Override
  public void setMany(IMap<K, ? extends V> m) {
    this.inner.putAll(m.toJavaMap());
  }

  @Override
  public int size() {
    return inner.size();
  }

  @Override
  public Map<K, V> toJavaMap() {
    Map<K, V> ret = new HashMap<>();
    ret.putAll(this.inner);
    return ret;
  }

  @Override
  public V get(K key) {
    return this.inner.get(key);
  }

  @Override
  public String toString() {
    return sf("%s (%d items)", this.getClass().getSimpleName(), this.size());
  }

  @Override
  public void tryRemove(K key) {
    inner.remove(key);
  }

  @Override
  public IMap<K, V> with(IMap<K, V> map) {
    this.inner.putAll(map.toJavaMap());
    return this;
  }

  @Override
  public IMap<K, V> with(ISet<Map.Entry<K, V>> entries) {
    for (Map.Entry<K, V> entry : entries) {
      this.inner.put(entry.getKey(), entry.getValue());
    }
    return this;
  }

  @Override
  public IMap<K, V> with(Map<K, V> map) {
    this.inner.putAll(map);
    return this;
  }
}
