package eng.eSystem.collections;

import eng.eSystem.utilites.Selector;

import java.util.Map;
import java.util.function.Predicate;

public interface IReadOnlyMap<K,V> extends Iterable<Map.Entry<K, V>> {

  int size();

  boolean isEmpty();

  boolean containsKey(K key);

  boolean containsValue(V value);

  IMap<K,V> whereKey(Predicate<K> predicate);

  IMap<K,V> whereValue(Predicate<V> predicate);

  IMap<K,V> where(Predicate<Map.Entry<K,V>> predicate);

  V get(K key);

  default V tryGet(K key){
    V ret = tryGet(key, null);
    return ret;
  }

  default V tryGet(K key, V defaultValue) {
    V ret;
    if (this.containsKey(key))
      ret = this.get(key);
    else
      ret = defaultValue;
    return ret;
  }

  ISet<K> getKeys();

  ICollection<V> getValues();

  ISet<Map.Entry<K, V>> getEntries();

  <Knew, Vnew> IMap<Knew, Vnew> select(Selector<K, Knew> keySelector, Selector<V, Vnew> valueSelector);

  int hashCode();

  @Override
  boolean equals(Object o);

}
