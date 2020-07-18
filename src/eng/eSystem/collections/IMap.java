package eng.eSystem.collections;

import eng.eSystem.functionalInterfaces.Producer;

import java.util.Map;
import java.util.function.Predicate;

public interface IMap<K, V> extends IReadOnlyMap<K, V> {

  void clear();

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
      this.set(key, valueProducerIfKeyNotFound.produce());
    ret = this.get(key);
    return ret;
  }

  void remove(K key);

  default void remove(Predicate<Map.Entry<K, V>> predicate) {
    ISet<K> tmp = this.where(predicate).getKeys();
    this.remove(tmp);
  }

  default void remove(ISet<K> keys) {
    for (K key : keys) {
      this.remove(key);
    }
  }

  void set(K key, V value);

  void set(Map<? extends K, ? extends V> m);

  default void set(IMap<K, ? extends V> m) {
    for (K k : m.getKeys()) {
      V v = m.get(k);
      this.set(k, v);
    }
  }

  default void set(Map.Entry<? extends K, ? extends V> m) {
    this.set(m.getKey(), m.getValue());
  }

  default void set(IList<Map.Entry<? extends K, ? extends V>> m) {
    m.forEach(q -> this.set(q));
  }

  void tryRemove(K key);

}
