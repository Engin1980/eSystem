package eng.eSystem.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IMap<K,V> extends IReadOnlyMap<K,V> {

  void add(K key, V value);

  void remove(K key);

  void tryRemove(K key);

  void add(Map<? extends K, ? extends V> m);

  void add(Map.Entry<? extends K, ? extends V> m);

  void clear();

}
