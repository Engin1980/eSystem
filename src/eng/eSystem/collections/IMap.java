package eng.eSystem.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public interface IMap<K,V> extends IReadOnlyMap<K,V> {

  void set(K key, V value);

  void remove(K key);

  void remove(Predicate<Map.Entry<K, V>> predicate);

  void remove(ISet<K> keys);

  void tryRemove(K key);

  void set(Map<? extends K, ? extends V> m);

  void set(IMap<K, ? extends V> m);

  void set(Map.Entry<? extends K, ? extends V> m);

  void set(IList<Map.Entry<? extends K, ? extends V>> m);

  void clear();

}
