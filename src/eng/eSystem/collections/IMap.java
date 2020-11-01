package eng.eSystem.collections;

import eng.eSystem.functionalInterfaces.Producer;

import java.util.Map;
import java.util.function.Predicate;

public interface IMap<K, V> extends IReadOnlyMap<K, V> {

  void clear();

  void remove(K key);

  void set(K key, V value);

  void set(Map<? extends K, ? extends V> m);

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

  default void remove(Predicate<Map.Entry<K, V>> predicate) {
    ISet<K> tmp = this.where(predicate).getKeys();
    this.removeMany(tmp);
  }

  default void removeMany(ISet<K> keys) {
    for (K key : keys) {
      this.remove(key);
    }
  }

  default void tryRemoveMany(Iterable<K> keys) {
    for (K key : keys) {
      this.tryRemove(key);
    }
  }

  default void set(Map.Entry<? extends K, ? extends V> m) {
    this.set(m.getKey(), m.getValue());
  }

  default void setMany(IMap<K, ? extends V> m) {
    for (K k : m.getKeys()) {
      V v = m.get(k);
      this.set(k, v);
    }
  }

  default void setMany(Iterable<Map.Entry<? extends K, ? extends V>> m) {
    m.forEach(q -> this.set(q));
  }

}
