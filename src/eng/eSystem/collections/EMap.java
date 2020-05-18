package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.NoSuchKeyException;
import eng.eSystem.functionalInterfaces.Selector;
import eng.eSystem.validation.EAssert;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;

public class EMap<K, V> implements IMap<K, V> {

  public static <K, V> EMap<K, V> of(K k1, V v1) {
    return EMap.of(new Object[]{k1, v1});
  }

  static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2) {
    return EMap.of(new Object[]{k1, v1, k2, v2});
  }

  static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3});
  }

  static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4});
  }

  static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5});
  }

  static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6});
  }

  static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7});
  }

  static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8});
  }

  static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9});
  }

  static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9, k10, v10});
  }

  private static <K, V> EMap<K, V> of(Object[] params) {
    EAssert.Argument.isTrue(params.length % 2 == 0, "There must be even number of arguments.");
    EMap<K, V> ret = new EMap<>();

    int i = 0;
    while (i < params.length - 1) {
      K key = (K) params[i];
      V value = (V) params[i + 1];
      ret.set(key, value);
      i += 2;
    }

    return ret;
  }

  private final Map<K, V> inner;

  public EMap(Map<K, V> inner) {
    this.inner = inner;
  }

  public EMap(IMap<K, V> map) {
    this(map.getEntries());
  }

  public EMap(ISet<Map.Entry<K, V>> entries) {
    this.inner = new HashMap<>();
    entries.forEach(q -> inner.put(q.getKey(), q.getValue()));
  }

  public EMap() {
    this.inner = new HashMap<>();
  }

  public void clear() {
    inner.clear();
  }

  @Override
  public boolean containsKey(K key) {
    return inner.containsKey(key);
  }

  public boolean containsValue(V value) {
    return inner.containsValue(value);
  }

  @Override
  public boolean equals(Object o) {
    return inner.equals(o);
  }

  public V get(K key) {
    if (inner.containsKey(key) == false)
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
    return new ESet(inner.keySet());
  }

  @Override
  public V getOrSet(K key, V valueIfKeyNotFound) {
    V ret = this.tryGet(key);
    if (ret == null) {
      ret = valueIfKeyNotFound;
      this.set(key, ret);
    }
    return ret;
  }

  @Override
  public ICollection<V> getValues() {
    return new EList<>(inner.values());
  }

  @Override
  public int hashCode() {
    return inner.hashCode();
  }

  public boolean isEmpty() {
    return inner.isEmpty();
  }

  @Override
  public Iterator<Map.Entry<K, V>> iterator() {
    ISet<Map.Entry<K, V>> entries = this.getEntries();
    return entries.iterator();
  }

  public void remove(K key) {
    if (inner.containsKey(key) == false)
      throw new NoSuchKeyException(key);
    else
      inner.remove(key);
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

  @Override
  public void set(Map<? extends K, ? extends V> m) {
    inner.putAll(m);
  }

  @Override
  public void set(IMap<K, ? extends V> m) {
    for (K k : m.getKeys()) {
      V v = m.get(k);
      this.set(k, v);
    }
  }

  public int size() {
    return inner.size();
  }

  @Override
  public String toString() {
    return String.format("EMap{%d items}", this.size());
  }

  public void tryRemove(K key) {
    if (inner.containsKey(key))
      inner.remove(key);
  }

  @Override
  public IMap<K, V> where(Predicate<Map.Entry<K, V>> predicate) {
    EMap<K, V> ret;
    ISet<Map.Entry<K, V>> entries = this.getEntries();
    entries = entries.where(q -> predicate.test(q));
    ret = new EMap<>(entries);
    return ret;
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
}
