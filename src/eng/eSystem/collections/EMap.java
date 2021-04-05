package eng.eSystem.collections;

import eng.eSystem.validation.EAssert;

import java.util.HashMap;

public class EMap<K, V> extends EAbstractMap<K, V> {
  private final static Class<? extends java.util.Map> DEFAULT_CLASS = HashMap.class;

  public static<K,V>EMap<K,V>of (IReadOnlyMap<K,V> data){
    EMap<K,V> ret = new EMap<>();
    ret.setMany(data);
    return ret;
  }

  public static <K, V> EMap<K, V> of(K k1, V v1) {
    return EMap.of(new Object[]{k1, v1});
  }

  public static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2) {
    return EMap.of(new Object[]{k1, v1, k2, v2});
  }

  public static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3});
  }

  public static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4});
  }

  public static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5});
  }

  public static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6});
  }

  public static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7});
  }

  public static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8});
  }

  public static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
    return EMap.of(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9});
  }

  public static <K, V> EMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
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

  public EMap() {
    this(DEFAULT_CLASS);
  }

  public EMap(Class<? extends java.util.Map> innerType) {
    super(Common.provideInstance(innerType));
  }
}
