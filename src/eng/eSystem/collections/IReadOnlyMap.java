package eng.eSystem.collections;

import eng.eSystem.functionalInterfaces.Producer;
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

  int size();

  Map<K, V> toJavaMap();

  default int count() {
    return size();
  }

  default int count(Predicate<Map.Entry<K, V>> predicate) {
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
      newKey = keySelector.invoke(key);
      newValue = valueSelector.invoke(this.get(key));
      ret.set(newKey, newValue);
    }

    return ret;
  }

  default <Tnew> IList<Tnew> toList(Selector<Map.Entry<K, V>, Tnew> selector) {
    IList<Tnew> ret = new EList<>();

    for (Map.Entry<K, V> entry : this) {
      Tnew item = selector.invoke(entry);
      ret.add(item);
    }

    return ret;
  }

  default <Tnew> ISet<Tnew> toSet(Selector<Map.Entry<K, V>, Tnew> selector) {
    ISet<Tnew> ret = new ESet<>();

    for (Map.Entry<K, V> entry : this) {
      Tnew item = selector.invoke(entry);
      ret.add(item);
    }

    return ret;
  }

  default V tryGet(K key) {
    V ret = tryGet(key, (V) null);
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

  default V tryGet(K key, Producer<V> defaultValueProducer) {
    V ret;
    if (this.containsKey(key))
      ret = this.get(key);
    else
      ret = defaultValueProducer.invoke();
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
