package eng.eSystem.collections;

import eng.eSystem.functionalInterfaces.Selector;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;

public interface IReadOnlyMap<K, V> extends Iterable<Map.Entry<K, V>> {

  boolean containsKey(K key);

  boolean containsValue(V value);

  @Override
  boolean equals(Object o);

  V get(K key);

  ISet<Map.Entry<K, V>> getEntries();

  ISet<K> getKeys();

  ICollection<V> getValues();

  int hashCode();

  boolean isEmpty();

  default int count(){
    return size();
  }

  default int count(Predicate<Map.Entry<K, V>> predicate){
    return this.where(predicate).size();
  }

  default Iterator<Map.Entry<K, V>> iterator() {
    ISet<Map.Entry<K, V>> entries = this.getEntries();
    return entries.iterator();
  }

  default <Knew, Vnew> IMap<Knew, Vnew> select(Selector<K, Knew> keySelector, Selector<V, Vnew> valueSelector) {
    IMap<Knew, Vnew> ret = new EMap<>();

    for (K key : this.getKeys()) {
      Knew newKey;
      Vnew newValue;
      newKey = keySelector.select(key);
      newValue = valueSelector.select(this.get(key));
      ret.set(newKey, newValue);
    }

    return ret;
  }

  int size();

  default V tryGet(K key) {
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

  default IMap<K, V> where(Predicate<Map.Entry<K, V>> predicate) {
    EMap<K, V> ret;
    ISet<Map.Entry<K, V>> entries = this.getEntries();
    entries = entries.where(q -> predicate.test(q));
    ret = new EMap<>(entries);
    return ret;
  }

  default IMap<K, V> whereKey(Predicate<K> predicate) {
    EMap<K, V> ret;
    ISet<Map.Entry<K, V>> entries = this.getEntries();
    entries = entries.where(q -> predicate.test(q.getKey()));
    ret = new EMap<>(entries);
    return ret;
  }

  default IMap<K, V> whereValue(Predicate<V> predicate) {
    EMap<K, V> ret;
    ISet<Map.Entry<K, V>> entries = this.getEntries();
    entries = entries.where(q -> predicate.test(q.getValue()));
    ret = new EMap<>(entries);
    return ret;
  }


}
