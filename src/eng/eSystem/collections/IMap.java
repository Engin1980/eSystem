package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.NoSuchKeyException;
import eng.eSystem.functionalInterfaces.Producer;
import eng.eSystem.validation.EAssert;

import java.util.Map;
import java.util.function.Predicate;

public interface IMap<K, V> extends IReadOnlyMap<K, V> {
  void clear();

  void set(K key, V value);

  void setMany(Map<? extends K, ? extends V> m);

  void tryRemove(K key);

  default V getOrSet(K key, V valueIfKeyNotFound) {
    V ret;
    if (!this.containsKey(key))
      this.set(key, valueIfKeyNotFound);
    ret = this.get(key);
    return ret;
  }

  default V getOrSet(K key, Producer<V> valueProducerIfKeyNotFound) {
    V ret;
    if (!this.containsKey(key))
      this.set(key, valueProducerIfKeyNotFound.invoke());
    ret = this.get(key);
    return ret;
  }

  default void remove(K key) {
    if (!containsKey(key))
      throw new NoSuchKeyException(key);
    else
      tryRemove(key);
  }

  default void remove(Predicate<Map.Entry<K, V>> predicate) {
    ISet<K> tmp = this.where(predicate).getKeys();
    this.removeMany(tmp);
  }

  default void removeMany(ISet<K> keys) {
    for (K key : keys) {
      this.remove(key);
    }
  }

  default void set(Map.Entry<K, V> m) {
    this.set(m.getKey(), m.getValue());
  }

  default void setMany(IMap<K, ? extends V> m) {
    for (K k : m.getKeys()) {
      V v = m.get(k);
      this.set(k, v);
    }
  }

  default void setMany(Iterable<Map.Entry<K, V>> m) {
    m.forEach(q -> this.set(q));
  }

  default void tryRemoveMany(Iterable<K> keys) {
    for (K key : keys) {
      this.tryRemove(key);
    }
  }

  //region with(...) methods

  default IMap<K, V> with(IMap<K, V> map) {
    return this.with(map.toJavaMap().entrySet());
  }

  default IMap<K, V> with(Map<K, V> map) {
    return this.with(map.entrySet());
  }

  default IMap<K, V> with(ISet<Map.Entry<K, V>> entries) {
    for (Map.Entry<K, V> entry : entries) {
      this.set(entry);
    }
    return this;
  }

  default IMap<K, V> with(Iterable<Map.Entry<K, V>> entries) {
    this.setMany(entries);
    return this;
  }

  default EMap<K, V> with(K k1, V v1) {
    return this.with(new Object[]{k1, v1});
  }

  default EMap<K, V> with(K k1, V v1, K k2, V v2) {
    return this.with(new Object[]{k1, v1, k2, v2});
  }

  default EMap<K, V> with(K k1, V v1, K k2, V v2, K k3, V v3) {
    return this.with(new Object[]{k1, v1, k2, v2, k3, v3});
  }

  default EMap<K, V> with(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
    return this.with(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4});
  }

  default EMap<K, V> with(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
    return this.with(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5});
  }

  default EMap<K, V> with(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
    return this.with(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6});
  }

  default EMap<K, V> with(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
    return this.with(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7});
  }

  default EMap<K, V> with(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
    return this.with(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8});
  }

  default EMap<K, V> with(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
    return this.with(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9});
  }

  default EMap<K, V> with(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
    return this.with(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9, k10, v10});
  }

  private EMap<K, V> with(Object[] params) {
    EAssert.Argument.isTrue(params.length % 2 == 0, "There must be even number  with arguments.");
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

  //endregion
}
