package eng.eSystem.collections;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class ESortedMap<K, V> extends EAbstractMap<K, V> {

  private interface ComparatorInterface<K> {
    int compare(K a, K b);
  }

  private static class KeyComparator<K> implements Comparator<K> {
    public final ComparatorInterface<K> comparatorInterface;

    public KeyComparator(ComparatorInterface<K> comparatorInterface) {
      this.comparatorInterface = comparatorInterface;
    }

    @Override
    public int compare(K a, K b) {
      return this.comparatorInterface.compare(a, b);
    }
  }

  public ESortedMap(Map<K, V> inner, Comparator<K> comparator) {
    this(comparator);
    for (K k : inner.keySet()) {
      this.set(k, inner.get(k));
    }
  }

  public ESortedMap(IMap<K, V> map, Comparator<K> comparator) {
    this(comparator);
    for (K k : inner.keySet()) {
      this.set(k, inner.get(k));
    }
  }

  public ESortedMap(ISet<Map.Entry<K, V>> entries, Comparator<K> comparator) {
    this(comparator);
    entries.forEach(q -> inner.put(q.getKey(), q.getValue()));
  }

  public ESortedMap(Comparator<K> comparator) {
    super(new TreeMap<>(comparator));
  }

  public ESortedMap(Map<K, V> inner, ComparatorInterface<K> keyComparator) {
    this(keyComparator);
    for (K k : inner.keySet()) {
      this.set(k, inner.get(k));
    }
  }

  public ESortedMap(IMap<K, V> map, ComparatorInterface<K> keyComparator) {
    this(keyComparator);
    for (K k : inner.keySet()) {
      this.set(k, inner.get(k));
    }
  }

  public ESortedMap(ISet<Map.Entry<K, V>> entries, ComparatorInterface<K> keyComparator) {
    this(keyComparator);
    entries.forEach(q -> inner.put(q.getKey(), q.getValue()));
  }

  public ESortedMap(ComparatorInterface<K> keyComparator) {
    super(new TreeMap<>(new KeyComparator<>(keyComparator)));
  }
}
