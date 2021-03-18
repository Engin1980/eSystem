package eng.eSystem.collection;

import java.util.Comparator;
import java.util.TreeMap;

public class ESortedMap<K, V> extends EAbstractMap<K, V> {
  private interface ComparatorInterface<K> {
    int compare(K a, K b);
  }

  private static class KeyComparator<K> implements Comparator<K> {
    public final ESortedMap.ComparatorInterface<K> comparatorInterface;

    public KeyComparator(ESortedMap.ComparatorInterface<K> comparatorInterface) {
      this.comparatorInterface = comparatorInterface;
    }

    @Override
    public int compare(K a, K b) {
      return this.comparatorInterface.compare(a, b);
    }
  }

  public ESortedMap(ESortedMap.ComparatorInterface<K> keyComparator) {
    this(new ESortedMap.KeyComparator<>(keyComparator));
  }

  public ESortedMap(ESortedMap.KeyComparator<K> keyComparator) {
    super(new TreeMap<>(keyComparator));
  }

}
