package eng.eSystem.collections;

import eng.eSystem.collections.exceptions.NoSuchKeyException;
import eng.eSystem.validation.EAssert;

import java.util.HashMap;
import java.util.Map;

public class EMap<K, V> extends EAbstractMap<K, V> {

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

  public EMap(Map<K, V> inner) {
    super(inner);
  }

  public EMap(IMap<K, V> map) {
    this(map.getEntries());
  }

  public EMap(ISet<Map.Entry<K, V>> entries) {
    super(new HashMap<>());
    entries.forEach(q -> inner.put(q.getKey(), q.getValue()));
  }

  public EMap() {
    super(new HashMap<>());
  }

  @Override
  public String toString() {
    return String.format("EMap{%d items}", this.size());
  }

}
