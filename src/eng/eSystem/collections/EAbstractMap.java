package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.NoSuchKeyException;
import eng.eSystem.validation.EAssert;

import java.util.Map;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public abstract class EAbstractMap<K, V> implements IMap<K, V> {

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
    return o instanceof EMap && inner.equals(o);
  }

  @Override
  public V get(K key) {
    if (!inner.containsKey(key))
      throw new NoSuchKeyException(key);
    else
      return inner.get(key);
  }

  @Override
  public ISet<Map.Entry<K, V>> getEntries() {
    return new ESet<>(inner.entrySet());
  }

  @Override
  public ISet<K> getKeys() {
    return new ESet<>(inner.keySet());
  }

  @Override
  public ICollection<V> getValues() {
    return new EList<>(inner.values());
  }

  @Override
  public int hashCode() {
    return inner.hashCode();
  }

  @Override
  public boolean isEmpty() {
    return inner.isEmpty();
  }

  @Override
  public void remove(K key) {
    if (!inner.containsKey(key))
      throw new NoSuchKeyException(key);
    else
      inner.remove(key);
  }

  @Override
  public void set(K key, V value) {
    inner.put(key, value);
  }

  @Override
  public void set(Map<? extends K, ? extends V> m) {
    inner.putAll(m);
  }

  @Override
  public int size() {
    return inner.size();
  }

  @Override
  public String toString() {
    return sf("%s (%d items)", this.getClass().getSimpleName(), this.size());
  }

  @Override
  public void tryRemove(K key) {
    if (inner.containsKey(key))
      inner.remove(key);
  }
}
