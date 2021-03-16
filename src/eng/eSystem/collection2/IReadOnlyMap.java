package eng.eSystem.collection2;

import eng.eSystem.collections.exceptions.NoSuchKeyException;
import eng.eSystem.functionalInterfaces.Selector;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public interface IReadOnlyMap<K, V> extends Iterable<Map.Entry<K, V>> {
  boolean containsKey(K key);

  boolean containsValue(V value);

  @Override
  boolean equals(Object o);

  default V get(K key){
    Optional<V> tmp = tryGet(key);
    if (tmp.isEmpty())
      throw new NoSuchKeyException(key);
    else
      return tmp.get();
  }


  ISet<Map.Entry<K, V>> getEntries();

  ISet<K> getKeys();

  ICollection<V> getValues();

  int hashCode();

  default boolean isEmpty() {
    return this.size() == 0;
  }

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

  default Optional<V> tryGet(K key) {
    Optional<V> ret;
    if (this.containsKey(key))
      ret = Optional.of(this.get(key));
    else
      ret = Optional.empty();
    return ret;
  }

  //TODO solve EOptional with producer for non-success?


  default IMap<K, V> where(Predicate<Map.Entry<K, V>> predicate) {
    EMap<K, V> ret;
    ISet<Map.Entry<K, V>> entries = this.getEntries();
    entries = entries.where(q -> predicate.test(q));
    ret = EMap.of(entries);
    return ret;
  }

  default IMap<K, V> whereKey(Predicate<K> predicate) {
    EMap<K, V> ret;
    ISet<Map.Entry<K, V>> entries = this.getEntries();
    entries = entries.where(q -> predicate.test(q.getKey()));
    ret = EMap.of(entries);
    return ret;
  }

  default IMap<K, V> whereValue(Predicate<V> predicate) {
    EMap<K, V> ret;
    ISet<Map.Entry<K, V>> entries = this.getEntries();
    entries = entries.where(q -> predicate.test(q.getValue()));
    ret = EMap.of(entries);
    return ret;
  }
}
