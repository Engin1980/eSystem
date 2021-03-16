package eng.eSystem.collection2;

import eng.eSystem.collections.exceptions.NoSuchKeyException;
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
    return Objects.equals(this, o);
  }

  @Override
  public ISet<Map.Entry<K, V>> getEntries() {
    return ESet.of(inner.entrySet());
  }

  @Override
  public ISet<K> getKeys() {
    return ESet.of(inner.keySet());
  }

  @Override
  public ICollection<V> getValues() {
    return EList.of(inner.values());
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
  public String toString() {
    return sf("%s (%d items)", this.getClass().getSimpleName(), this.size());
  }

  @Override
  public void tryRemove(K key) {
    inner.remove(key);
  }
}
