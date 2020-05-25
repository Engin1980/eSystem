package eng.eSystem.collections;

import eng.eSystem.functionalInterfaces.Producer;

import java.util.Map;
import java.util.function.Predicate;

public interface IMap<K, V> extends IReadOnlyMap<K, V> {

  void set(K key, V value);

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

  void tryRemove(K key);

  void set(Map<? extends K, ? extends V> m);

  void set(IMap<K, ? extends V> m);

  V getOrSet(K key, V valueIfKeyNotFound);

  V getOrSet(K key, Producer<V> valueProducerIfKeyNotFound);

  default void set(Map.Entry<? extends K, ? extends V> m) {
    this.set(m.getKey(), m.getValue());
  }

  default void set(IList<Map.Entry<? extends K, ? extends V>> m) {
    m.forEach(q -> this.set(q));
  }

  void clear();

}
